package pro.biocontainers.readers.utilities.dockerfile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;


public class FilePathUtil {
    private FilePathUtil() {
        // utility class
    }

    /**
     * Return the relative path. Path elements are separated with / char.
     *
     * @param baseDir
     *            a parent directory of {@code file}
     * @param file
     *            the file to get the relative path
     * @return the relative path
     */
    public static String relativize(File baseDir, File file) throws FileException {
        try {
            baseDir = baseDir.getCanonicalFile();
            file = file.getCanonicalFile();

            return baseDir.toURI().relativize(file.toURI()).getPath();
        } catch (IOException e) {
            throw new FileException(e.getMessage());
        }
    }

    /**
     * Return the relative path. Path elements are separated with / char.
     *
     * @param baseDir
     *            a parent directory of {@code file}
     * @param file
     *            the file to get the relative path
     * @return the relative path
     */
    public static String relativize(Path baseDir, Path file) {
        String path = baseDir.toUri().relativize(file.toUri()).getPath();
        if (!"/".equals(baseDir.getFileSystem().getSeparator())) {
            // For windows
            return path.replace(baseDir.getFileSystem().getSeparator(), "/");
        } else {
            return path;
        }
    }
}
