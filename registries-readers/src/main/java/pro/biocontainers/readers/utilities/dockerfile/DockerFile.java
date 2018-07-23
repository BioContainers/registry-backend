package pro.biocontainers.readers.utilities.dockerfile;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Parse a Dockerfile.
 */
@Log4j2
public class DockerFile {

    public final File dockerFile;

    private final File baseDirectory;

    public DockerFile(File dockerFile, File baseDirectory) {
        if (!dockerFile.exists()) {
            throw new IllegalStateException(String.format("Dockerfile %s does not exist", dockerFile.getAbsolutePath()));
        }

        if (!dockerFile.isFile()) {
            throw new IllegalStateException(String.format("Dockerfile %s is not a file", dockerFile.getAbsolutePath()));
        }

        this.dockerFile = dockerFile;

        if (!baseDirectory.exists()) {
            throw new IllegalStateException(String.format("Base directory %s does not exist", baseDirectory.getAbsolutePath()));
        }

        if (!baseDirectory.isDirectory()) {
            throw new IllegalStateException(String.format("Base directory %s is not a directory", baseDirectory.getAbsolutePath()));
        }

        this.baseDirectory = baseDirectory;
    }

    private static class LineTransformer implements Function<String, Optional<? extends DockerFileStatement>> {

        private int line = 0;

        @Override
        public Optional<? extends DockerFileStatement> apply(String input)  {
            try {
                line++;
                return DockerFileStatement.createFromLine(input);

            } catch (Exception ex) {
                log.error("Error on dockerfile line " + line);
            }
            return Optional.empty();
        }
    }

    public Iterable<DockerFileStatement> getStatements() throws IOException, FileException {
        Collection<String> dockerFileContent = FileUtils.readLines(dockerFile);

        if (dockerFileContent.size() <= 0) {
            throw new FileException(String.format("Dockerfile %s is empty", dockerFile));
        }

        Collection<Optional<? extends DockerFileStatement>> optionals = dockerFileContent.stream().map(new LineTransformer()).collect(Collectors.toList());
        return optionals.stream().filter(x -> x.isPresent()).map(x -> x.get()).collect(Collectors.toList());
    }

    public List<String> getIgnores() throws IOException, FileException {
        List<String> ignores = new ArrayList<String>();
        File dockerIgnoreFile = new File(baseDirectory, ".dockerignore");
        if (dockerIgnoreFile.exists()) {
            int lineNumber = 0;
            List<String> dockerIgnoreFileContent = FileUtils.readLines(dockerIgnoreFile);
            for (String pattern : dockerIgnoreFileContent) {
                lineNumber++;
                pattern = pattern.trim();
                if (pattern.isEmpty()) {
                    continue; // skip empty lines
                }
                pattern = FilenameUtils.normalize(pattern);
                ignores.add(pattern);
            }
        }
        return ignores;
    }

    public ScannedResult parse() throws IOException, FileException {
        return new ScannedResult();
    }

    /**
     * Result of scanning / parsing a docker file.
     */
    public class ScannedResult {

        final List<String> ignores;

        final List<File> filesToAdd = new ArrayList<File>();

        public InputStream buildDockerFolderTar() throws FileException {
            return buildDockerFolderTar(baseDirectory);
        }

        public InputStream buildDockerFolderTar(File directory) throws FileException {

            File dockerFolderTar = null;

            try {
                final String archiveNameWithOutExtension = UUID.randomUUID().toString();

                dockerFolderTar = CompressArchiveUtil.archiveTARFiles(directory, filesToAdd,
                        archiveNameWithOutExtension);

                long length = dockerFolderTar.length();

                final FileInputStream tarInputStream = FileUtils.openInputStream(dockerFolderTar);
                final File tarFile = dockerFolderTar;

                return new InputStream() {

                    @Override
                    public int available() throws IOException {
                        return tarInputStream.available();
                    }

                    @Override
                    public int read() throws IOException {
                        return tarInputStream.read();
                    }

                    @Override
                    public int read(byte[] buff, int offset, int len) throws IOException {
                        return tarInputStream.read(buff, offset, len);
                    }

                    @Override
                    public void close() throws IOException {
                        IOUtils.closeQuietly(tarInputStream);
                        FileUtils.deleteQuietly(tarFile);
                    }
                };

            } catch (IOException ex) {
                FileUtils.deleteQuietly(dockerFolderTar);
                throw new FileException("Error occurred while preparing Docker context folder.");
            }
        }

        @Override
        public String toString() {
            return new StringBuilder().append("ignores : " + ignores)
                    .append("filesToAdd : " + filesToAdd).toString();
        }

        public ScannedResult() throws IOException, FileException {

            ignores = getIgnores();

            String matchingIgnorePattern = effectiveMatchingIgnorePattern(dockerFile);

            if (matchingIgnorePattern != null) {
                throw new FileException(String.format(
                        "Dockerfile is excluded by pattern '%s' in .dockerignore file", matchingIgnorePattern));
            }

            addFilesInDirectory(baseDirectory);
        }

        /**
         * Adds all files found in <code>directory</code> and subdirectories to
         * <code>filesToAdd</code> collection. It also adds any empty directories
         * if found.
         *
         * @param directory directory
         * @throws FileException when IO error occurs
         */
        private void addFilesInDirectory(File directory) throws FileException {
            File[] files = directory.listFiles();

            if (files == null) {
                throw new FileException("Failed to read build context directory: " + baseDirectory.getAbsolutePath());
            }

            if (files.length != 0) {
                for (File f : files) {
                    if (effectiveMatchingIgnorePattern(f) == null) {
                        if (f.isDirectory()) {
                            addFilesInDirectory(f);
                        } else {
                            filesToAdd.add(f);
                        }
                    }
                }
                // base directory should at least contains Dockerfile, but better check
            } else if (!isBaseDirectory(directory)) {
                // add empty directory
                filesToAdd.add(directory);
            }
        }

        private boolean isBaseDirectory(File directory) {
            return directory.compareTo(baseDirectory) == 0;
        }

        /**
         * Returns all matching ignore patterns for the given file name.
         */
        private List<String> matchingIgnorePatterns(String fileName) throws FileException {
            List<String> matches = new ArrayList<String>();

            int lineNumber = 0;
            for (String pattern : ignores) {
                String goLangPattern = pattern.startsWith("!") ? pattern.substring(1) : pattern;
                lineNumber++;
                try {
                    if (GoLangFileMatch.match(goLangPattern, fileName)) {
                        matches.add(pattern);
                    }
                } catch (FileException e) {
                    throw new FileException(String.format(
                            "Invalid pattern '%s' on line %s in .dockerignore file", pattern, lineNumber));
                }
            }

            return matches;
        }

        /**
         * Returns the matching ignore pattern for the given file or null if it should NOT be ignored. Exception rules like "!Dockerfile"
         * will be respected.
         */
        private String effectiveMatchingIgnorePattern(File file) throws FileException {
            // normalize path to replace '/' to '\' on Windows
            String relativeFilename = FilenameUtils.normalize(FilePathUtil.relativize(baseDirectory, file));

            List<String> matchingPattern = matchingIgnorePatterns(relativeFilename);

            if (matchingPattern.isEmpty()) {
                return null;
            }

            String lastMatchingPattern = matchingPattern.get(matchingPattern.size() - 1);

            return !lastMatchingPattern.startsWith("!") ? lastMatchingPattern : null;
        }
    }
}