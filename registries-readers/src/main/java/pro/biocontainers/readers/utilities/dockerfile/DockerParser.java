package pro.biocontainers.readers.utilities.dockerfile;



import pro.biocontainers.readers.utilities.dockerfile.models.DockerContainer;
import pro.biocontainers.readers.utilities.dockerfile.models.commands.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class DockerParser {

    DockerContainer dockerfile = new DockerContainer();
    public String localDockerfilePath;
    public String localPath;

    public From from;
    public List<Maintainer> maintainer = new ArrayList<>();
    public Cmd cmd;
    public EntryPoint entryPoint;
    public StopSignal stopSignal;
    public Healthcheck healthcheck;

    public List<Run> runs = new ArrayList<>();
    public List<Label> labels = new ArrayList<>();
    public List<Env> envs = new ArrayList<>();
    public List<Expose> exposes = new ArrayList<>();
    public List<Add> adds = new ArrayList<>();
    public List<Copy> copies = new ArrayList<>();
    public List<Volume> volumes = new ArrayList<>();
    public List<User> users = new ArrayList<>();
    public List<WorkDir> workDirs = new ArrayList<>();
    public List<Arg> args = new ArrayList<>();
    public List<OnBuild> onBuilds = new ArrayList<>();
    public List<Comment> comments = new ArrayList<>();

    public DockerParser(String localpath, String localDockerfilePath) {
        String[] pathTokens = localDockerfilePath.split("/");
        if (pathTokens.length == 1) {
            this.localDockerfilePath = "";
        } else {
            StringBuilder newPath = new StringBuilder();
            for (int i = 0; i < pathTokens.length - 1; i++) {
                newPath.append(pathTokens[i]).append("/");
            }
            this.localDockerfilePath = newPath.toString();
        }
        this.localPath = localpath;
    }

    public DockerParser(String localpath) {
        this.localDockerfilePath = "";
        this.localPath = localpath;
    }

    public DockerContainer getParsedDockerfileObject(File rawDockerfile) throws IOException {
        dockerfile = new DockerContainer();
        File fileToBeFlat = new File(rawDockerfile.getPath());
        File flatDockerfile = getFlatDockerFile(fileToBeFlat);
        doClassificationOfLines(flatDockerfile);
        assignToDockerObject(fileToBeFlat);
        return dockerfile;
    }

    public DockerContainer getDockerfileObject() throws IOException {
        dockerfile = new DockerContainer();
        File fileToBeFlat = new File(this.localPath + "/" + this.localDockerfilePath + "/" + "Dockerfile");
        File flatDockerfile = getFlatDockerFile(fileToBeFlat);
        doClassificationOfLines(flatDockerfile);
        assignToDockerObject(fileToBeFlat);
        return dockerfile;
    }

    public void printLines(File file) throws IOException {
        try (Stream<String> lines = Files.lines(file.toPath(), Charset.defaultCharset())) {
            lines.forEachOrdered(System.out::println);
        }
    }

    public boolean checkForInstruction(String line) {
        boolean a = isContainExactWord(line, "FROM");
        boolean b = isContainExactWord(line, "ADD");
        boolean c = isContainExactWord(line, "COPY");
        boolean d = isContainExactWord(line, "RUN");
        boolean e = isContainExactWord(line, "LABEL");
        boolean f = isContainExactWord(line, "ENV");
        boolean g = isContainExactWord(line, "ARG");
        boolean h = isContainExactWord(line, "VOLUME");
        boolean i = isContainExactWord(line, "MAINTAINER");
        boolean j = isContainExactWord(line, "HEALTHCHECK");
        boolean k = isContainExactWord(line, "STOPSIGNAL");
        boolean l = isContainExactWord(line, "EXPOSE");
        boolean m = isContainExactWord(line, "CMD");
        boolean n = isContainExactWord(line, "ENTRYPOINT");
        boolean o = isContainExactWord(line, "ONBUILD");
        boolean p = isContainExactWord(line, "USER");
        boolean q = isContainExactWord(line, "WORKDIR");
        return a || b || c || d || e || f || g || h || i || j || k || l || m || n || o || p || q;
    }

    public boolean isContainExactWord(String fullString, String partWord) {
        String pattern = "\\b" + partWord + "\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(fullString);
        return m.find();
    }

    public List<Comment> getCommentsFromDockerfile(File flatDockerFile, DockerContainer dockerContainer) throws IOException {
        List<Comment> comments = new ArrayList<>();
        FileInputStream fis = new FileInputStream(flatDockerFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        String line;
        String instruction = "";
        String newLine = "";
        boolean out = false;
        boolean commentFlag = false;
        boolean header = false;
        while ((line = reader.readLine()) != null) {
            statement:
            if (line.startsWith("#")) {
                String tempLine = line;
                tempLine = tempLine.replaceFirst("#", "");

                if (checkForInstruction(tempLine)) {
                    boolean outCommInstruction = true;
                    Comment c = new Comment(dockerContainer, "commented out: " + getInstructionInString(tempLine), tempLine);
                    comments.add(c);
                    break statement;
                }
                if (commentFlag) {
                    String concatComment = line.replaceFirst("#", " ");
                    newLine += concatComment;
                } else {
                    newLine += line;
                    commentFlag = true;
                }
            } else if (line.trim().isEmpty()) {
                if (commentFlag && header) {
                    Comment c = new Comment(dockerContainer, "standalone", newLine);
                    comments.add(c);
                } else if (commentFlag) {
                    Comment c = new Comment(dockerContainer, "header", newLine);
                    comments.add(c);
                    header = true;
                }
                newLine = "";
                commentFlag = false;

            } else if (doesLineHaveAnInstruction(line) && commentFlag) {
                if (line.contains("\t")) {
                    String uname = " ";
                    line = line.replaceAll("\t", uname);
                }
                String arr[] = line.split(" ", 2);

                String foundInstruction = arr[0];

                Comment c = new Comment(dockerContainer, "before " + foundInstruction, newLine);
                comments.add(c);

                newLine = "";
                commentFlag = false;
            }
        }
        reader.close();
        fis.close();

        if (comments.size() > 0) {
            return comments;
        }
        return comments;
    }

    public File getDockerFileFromAbsolutePath(File dockerFile) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(dockerFile));
        FileInputStream fis = new FileInputStream(dockerFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        String line;
        String newLine = null;
        boolean hc = false;
        boolean concatFlag = false;
        while ((line = reader.readLine()) != null) {
            if (doesLineHaveAnInstruction(line) && line.contains(" \\")) {
                newLine = "";
                newLine += line;
                concatFlag = true;
            } else if (line.contains(" \\") && concatFlag) {
                newLine += line;
            } else if (newLine != null && newLine.contains("HEALTHCHECK") && !hc) {
                newLine += line;
                hc = true;
            } else if (!doesLineHaveAnInstruction(line) && !line.contains(" \\") && concatFlag) {
                newLine += line;
                newLine = newLine.replace(" \\", "");
                newLine = newLine.trim().replaceAll(" +", " ");
                writer.write(newLine);
                writer.newLine();
                concatFlag = false;
            } else if (doesLineHaveAnInstruction(line) && !line.contains(" \\")) {
                line = line.trim().replaceAll(" +", " ");
                writer.write(line);
                writer.newLine();
            }
        }
        writer.close();
        reader.close();
        fis.close();

        return dockerFile;
    }

    public File getFlatDockerFile(File dockerFile) throws IOException {
        File flatDockerfile = new File(dockerFile.getParentFile().getPath() + "/DockerFileFlat");
        BufferedWriter writer = new BufferedWriter(new FileWriter(flatDockerfile));
        FileInputStream fis = new FileInputStream(dockerFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        String line = null;
        String newLine = null;
        boolean hc = false;
        boolean concatFlag = false;
        while ((line = reader.readLine()) != null) {
            //line = line.replace("\\S+", "\\");
            if (doesLineHaveAnInstruction(line) && line.contains(" \\")) {
                newLine = "";
                newLine += line;
                concatFlag = true;
            } else if (line.contains(" \\") && concatFlag) {
                newLine += line;
            } else if (newLine != null && newLine.contains("HEALTHCHECK") && !hc) {
                newLine += line;
                hc = true;
            } else if (!doesLineHaveAnInstruction(line) && !line.contains(" \\") && concatFlag) {
                newLine += line;
                newLine = newLine.replace(" \\", "");
                newLine = newLine.trim().replaceAll(" +", " ");
                writer.write(newLine);
                writer.newLine();
                concatFlag = false;
            } else if (doesLineHaveAnInstruction(line) && !line.contains(" \\")) {
                line = line.trim().replaceAll(" +", " ");
                writer.write(line);
                writer.newLine();
            }
        }
        writer.close();
        reader.close();
        fis.close();

        return flatDockerfile;
    }

    public File getFlatDockerFile(String localRepoPath, String localDockerfilePath) throws IOException {
        String localPathtoDockerfile = localRepoPath.concat("/").concat(localDockerfilePath);
        File dockerfile = findFile("Dockerfile", new File(localPathtoDockerfile));
        return getFlatDockerFile(dockerfile);
    }

    public File getFlatDockerFile(String fileName) throws IOException {
        String localPathtoDockerfile = localPath.concat("/").concat("/").concat(localDockerfilePath);
        File dockerfile = findFile(fileName, new File(localPathtoDockerfile));
        return getFlatDockerFile(dockerfile);
    }

    public static String getInstructionInString(String line) {
        if (line.contains("ADD")) {
            return "ADD";
        } else if (line.contains("FROM")) {
            return "FROM";
        } else if (line.contains("CMD")) {
            return "CMD";
        } else if (line.contains("COPY")) {
            return "COPY";
        } else if (line.contains("ENTRYPOINT")) {
            return "ENTRYPOINT";
        } else if (line.contains("ENV")) {
            return "ENV";
        } else if (line.contains("EXPOSE")) {
            return "EXPOSE";
        } else if (line.contains("FROM")) {
            return "FROM";
        } else if (line.contains("HEALTHCHECK")) {
            return "HEALTHCHECK";
        } else if (line.contains("INSTRUCTION")) {
            return "INSTRUCTION";
        } else if (line.contains("LABEL")) {
            return "LABEL";
        } else if (line.contains("MAINTAINER")) {
            return "MAINTAINER";
        } else if (line.contains("ONBUILD")) {
            return "ONBUILD";
        } else if (line.contains("RUN")) {
            return "RUN";
        } else if (line.contains("STOPSIGNAL")) {
            return "STOPSIGNAL";
        } else if (line.contains("USER")) {
            return "USER";
        } else if (line.contains("VOLUME")) {
            return "VOLUME";
        } else if (line.contains("WORKDIR")) {
            return "WORKDIR";
        } else {
            return "";
        }
    }

    public static boolean doesLineHaveAnInstruction(String line) {
        if (line.trim().startsWith("ADD")) {
            return true;
        } else if (line.trim().startsWith("FROM")) {
            return true;
        } else if (line.trim().startsWith("CMD")) {
            return true;
        } else if (line.trim().startsWith("COPY")) {
            return true;
        } else if (line.trim().startsWith("ENTRYPOINT")) {
            return true;
        } else if (line.trim().startsWith("ENV")) {
            return true;
        } else if (line.trim().startsWith("EXPOSE")) {
            return true;
        } else if (line.trim().startsWith("FROM")) {
            return true;
        } else if (line.trim().startsWith("HEALTHCHECK")) {
            return true;
        } else if (line.trim().startsWith("INSTRUCTION")) {
            return true;
        } else if (line.trim().startsWith("LABEL")) {
            return true;
        } else if (line.trim().startsWith("MAINTAINER")) {
            return true;
        } else if (line.trim().startsWith("ONBUILD")) {
            return true;
        } else if (line.trim().startsWith("RUN")) {
            return true;
        } else if (line.trim().startsWith("STOPSIGNAL")) {
            return true;
        } else if (line.trim().startsWith("USER")) {
            return true;
        } else if (line.trim().startsWith("VOLUME")) {
            return true;
        } else if (line.trim().startsWith("WORKDIR")) {
            return true;

        } else return line.startsWith("#");
    }

    public void doClassificationOfLines(File file) throws IOException {
        try (Stream<String> lines = Files.lines(file.toPath(), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> {
                String instruction = getInstructionInString(line);
                if(doesLineHaveAnInstruction(line)){
                    if ((isSingleInstruction(instruction))) {
                            getInstructionOneOfOne(line);
                    } else {
                        if(areMultipleInstructionsInOneLineAllowed(line)){
                            getMultipleInstructionsInOneLine(line);
                        }else{
                            getInstructionOneOfMany(line);
                        }
                    }
                }


            });
        }
    }

    private boolean areMultipleInstructionsInOneLineAllowed(String instructionToCheck) {
        if (instructionToCheck.trim().startsWith("ENV")) {
            return false;
        } else if (instructionToCheck.trim().startsWith("ADD")) {
            return false;
        } else if (instructionToCheck.trim().startsWith("COPY")) {
            return false;
        } else if (instructionToCheck.trim().startsWith("USER")) {
            return false;
        } else if (instructionToCheck.trim().startsWith("WORKDIR")) {
            return false;
        } else if (instructionToCheck.trim().startsWith("ARG")) {
            return false;
        } else return !instructionToCheck.trim().startsWith("ONBUILD");
    }

    public boolean isSingleInstruction(String instructionToCheck) {
        if (instructionToCheck.trim().startsWith(("FROM"))) {
            return true;
        } else if (instructionToCheck.trim().startsWith("CMD")) {
            return true;
        } else if (instructionToCheck.trim().startsWith("ENTRYPOINT")) {
            return true;
        } else if (instructionToCheck.trim().startsWith("HEALTHCHECK")) {
            return true;
        } else return instructionToCheck.trim().startsWith("STOPSIGNAL");
    }


    public <T extends Instruction> T getInstructionOneOfOne(String newLine) {
        String line = newLine;
        if (line.startsWith("#")) {
            return null;
        }

        if (line.contains("\t")) {
            String uname = " ";
            line = line.replaceAll("\t", uname);
        }

        String arr[] = line.split(" ", 2);

        String instruction = arr[0];   //Instruction
        String command;
        if (arr.length > 1) {
            command = arr[1];
        } else {
            command = "";
        }

        switch (instruction) {
            case "FROM":
                return (T) parseFromInstruction(command);
            case "CMD":
                return (T) (parseAndGetCMDInstruction(command));
            case "ENTRYPOINT":
                return (T) (parseAndGetEntryPointInstruction(command));
            case "STOPSIGNAL":
                return (T) (parseAndGetStopSignalInstruction(command));
            case "HEALTHCHECK":
                return (T) (parseAndGetHealthCheckInstruction(command));
        }
        return null;
    }

    public <T extends Instruction> List<T> getMultipleInstructionsInOneLine(String newLine) {
        String line = newLine;
        if (line.startsWith("#")) {
            return null;
        }

        if (line.contains("\t")) {
            String uname = " ";
            line = line.replaceAll("\t", uname);
        }

        String arr[] = line.split(" ", 2);

        String instruction = arr[0];   //Instruction
        String command;
        if (arr.length > 1) {
            command = arr[1];
        } else {
            command = "";
        }


        switch (instruction) {
            case "RUN":
                return (List<T>) parseAndGetRunInstruction(command);
            case "EXPOSE":
                return (List<T>) parseAndGetExposeInstruction(command);
            case "LABEL":
                return (List<T>) parseAndGetLabelInstruction(command);
            case "VOLUME":
                return (List<T>) parseAndGetVolumeInstruction(command);
            case "MAINTAINER":
                return (List<T>) parseAndGetMaintainerInstruction(command);
        }
        return null;
    }

    public <T extends Instruction> T getInstructionOneOfMany(String newLine) {
        String line = newLine;
        if (line.startsWith("#")) {
            return null;
        }

        if (line.contains("\t")) {
            String uname = " ";
            line = line.replaceAll("\t", uname);
        }

        String arr[] = line.split(" ", 2);

        String instruction = arr[0];   //Instruction
        String command;
        if (arr.length > 1) {
            command = arr[1];
        } else {
            command = "";
        }

        switch (instruction) {
            case "ENV":
                return (T) parseAndGetEnvInstruction(command);
            case "ADD":
                return (T) parseAndGetAddInstruction(command);
            case "COPY":
                return (T) parseAndGetCopyInstruction(command);
            case "USER":
                return (T) parseAndGetUserInstruction(command);
            case "WORKDIR":
                return (T) parseAndGetWorkDirInstruction(command);
            case "ARG":
                return (T) parseAndGetArgInstruction(command);
            case "ONBUILD":
                return (T) parseAndGetOnBuildInstruction(command);
        }
        return null;
    }

    public List<Expose> parseAndGetExposeInstruction(String ports) {
        List<Expose> exposes = new ArrayList<>();
        if (ports.matches(".*\\d+.*")) {
            String[] parts = ports.split(" ");
            for (String part : parts) {
                exposes.add(new Expose(dockerfile, part));
            }
        }
        this.exposes = exposes;
        return exposes;
    }

    public Instruction parseAndGetEntryPointInstruction(String command) {
        String executable = null;
        List<String> params = new ArrayList<>();
        if (command.contains("[")) {
            Pattern p = Pattern.compile("\"(.*?)\"");
            Matcher m = p.matcher(command);

            List<String> matches = new ArrayList<>();
            while (m.find()) {
                matches.add(m.group(1));
            }

            for (int i = 0; i < matches.size(); i++) {
                if (i == 0) {
                    executable = matches.get(i);
                } else {
                    params.add(matches.get(i));
                }
            }
        } else {
            String[] parts = command.split(" ");

            for (int i = 0; i < parts.length; i++) {
                if (i == 0) {
                    executable = parts[i];

                } else {
                    params.add(parts[i]);
                }
            }

        }
        if (executable.length() > 0) {
            entryPoint = new EntryPoint(dockerfile, executable, params);
        }
        return entryPoint;

    }

    public Add add(String command, String instruction) {
        String source = "";
        String destinatation = "";
        if (command.contains("[")) {
            Pattern p = Pattern.compile("\"(.*?)\"");
            Matcher m = p.matcher(command);

            List<String> matches = new ArrayList<>();
            while (m.find()) {
                matches.add(m.group(1));
            }
            for (int i = 0; i < matches.size(); i++) {
                if (i == 0) {
                    source = matches.get(i);
                } else {
                    destinatation = matches.get(i);
                }
            }
        } else {
            String[] parts = command.split(" ");
            for (int i = 0; i < parts.length; i++) {
                if (i == 0) {
                    source = parts[i];

                } else {
                    destinatation = parts[i];
                }
            }
        }

        if (instruction.equals("ADD")) {
            adds.add(new Add(dockerfile, source, destinatation));
            return new Add(dockerfile, source, destinatation);
        }
        return null;
    }

    public Copy copy(String command, String instruction) {
        String source = "";
        String destinatation = "";
        if (command.contains("[")) {
            Pattern p = Pattern.compile("\"(.*?)\"");
            Matcher m = p.matcher(command);

            List<String> matches = new ArrayList<>();
            while (m.find()) {
                matches.add(m.group(1));
            }
            for (int i = 0; i < matches.size(); i++) {
                if (i == 0) {
                    source = matches.get(i);
                } else {
                    destinatation = matches.get(i);
                }
            }
        } else {
            String[] parts = command.split(" ");
            for (int i = 0; i < parts.length; i++) {
                if (i == 0) {
                    source = parts[i];

                } else {
                    destinatation = parts[i];
                }
            }
        }
        if (instruction.equals("COPY")) {
            copies.add(new Copy(dockerfile, source, destinatation));
            return new Copy(dockerfile, source, destinatation);
        }
        return null;
    }

    public Copy parseAndGetCopyInstruction(String command) {
        return copy(command, "COPY");

    }

    public Add parseAndGetAddInstruction(String command) {
        return add(command, "ADD");
    }

    public Instruction parseAndGetWorkDirInstruction(String path) {
        workDirs.add(new WorkDir(dockerfile, path));
        return new WorkDir(dockerfile, path);
    }

    public List<Label> parseAndGetLabelInstruction(String command) {
        List<Label> labels = new ArrayList<>();
        List<String> parts = new ArrayList<>();

        Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
        Matcher regexMatcher = regex.matcher(command);
        while (regexMatcher.find()) {
            if (regexMatcher.group(1) != null) {
                // Add double-quoted string without the quotes
                parts.add(regexMatcher.group(1));
            } else if (regexMatcher.group(2) != null) {
                // Add single-quoted string without the quotes
                parts.add(regexMatcher.group(2));
            } else {
                // Add unquoted word
                parts.add(regexMatcher.group());
            }
        }

        List<String> newParts = new ArrayList<>();
        for (String part : parts) {
            String[] valuekeyJoint = part.split("=");
            newParts.addAll(Arrays.asList(valuekeyJoint));
        }
        parts = newParts;

        for(int i = 0; i < parts.size() -1 ; i=i+2){
            String key = parts.get(i).replace("=", "");
            String value = parts.get(i+1).trim();
            labels.add(new Label(dockerfile, key, value));
        }


        this.labels.addAll(labels);
        return labels;
    }

    public Instruction parseAndGetHealthCheckInstruction(String command) {
        Healthcheck hc = null;
        String instruction = getInstructionInString(command);

        String[] split = command.split(" ", 2);
        if (command.startsWith(instruction)) {
            hc = new Healthcheck(dockerfile, instruction, split[1]);
        } else {
            int index = command.indexOf(instruction);
            String paramter = command.substring(0, index-1);
            String fullInstruction = command.substring(index);
            String[] fullInstructions = fullInstruction.split(" ", 2);
            hc = new Healthcheck(dockerfile, instruction, paramter, fullInstructions[1]);
        }
        healthcheck = hc;
        return hc;
    }

    public Instruction parseAndGetCMDInstruction(String command) {
        String executable = null;
        List<String> params = new ArrayList<>();
        if (command.contains("[")) {
            Pattern p = Pattern.compile("\"(.*?)\"");
            Matcher m = p.matcher(command);

            List<String> matches = new ArrayList<>();
            while (m.find()) {
                matches.add(m.group(1));
            }

            for (int i = 0; i < matches.size(); i++) {
                if (i == 0) {
                    executable = matches.get(i);
                } else {
                    params.add(matches.get(i));
                }
            }
        } else {
            String[] parts = command.split(" ");

            for (int i = 0; i < parts.length; i++) {
                if (i == 0) {
                    executable = parts[i];

                } else {
                    params.add(parts[i]);
                }
            }

        }
        if (executable == null) {
            String[] parts = command.split(" ");

            for (int i = 0; i < parts.length; i++) {
                if (i == 0) {
                    executable = parts[i];

                } else {
                    params.add(parts[i]);
                }
            }
        } else {
            if (executable.length() > 0) {
                cmd = new Cmd(dockerfile, executable, params);
            }
        }

        return cmd;
    }

    public Instruction parseAndGetStopSignalInstruction(String signal) {
        stopSignal = new StopSignal(dockerfile, signal);

        return stopSignal;


    }

    public Instruction parseAndGetArgInstruction(String arg) {
        args.add(new Arg(dockerfile, arg));
        return new Arg(dockerfile, arg);

    }

    public List<Run> parseAndGetRunInstruction(String commandx) {
        List<Run> runsLocal = new ArrayList<>();
        String command = commandx;
        if (commandx.contains("(") && !commandx.contains("echo ")) {
            Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(commandx);
            while (m.find()) {
                command = m.group(1);
            }
        }

        String[] runentry = command.split(" && ");

        for (String run : runentry) {
            String executable = "";
            List<String> params = new ArrayList<>();

            if (commandx.contains("echo")) {
                executable = "echo";
                String arr[] = commandx.split(" ", 2);
                Collections.addAll(params, arr);

            } else {
                if (command.contains("[")) {
                    Pattern p = Pattern.compile("\"(.*?)\"");
                    Matcher m = p.matcher(command);

                    List<String> matches = new ArrayList<>();
                    while (m.find()) {
                        matches.add(m.group(1));
                    }

                    for (int i = 0; i < matches.size(); i++) {
                        if (i == 0) {
                            executable = matches.get(i);
                        } else {
                            params.add(matches.get(i));
                        }
                    }
                } else {
                    String[] parts = run.split(" ");

                    for (int i = 0; i < parts.length; i++) {
                        if (i == 0) {
                            executable = parts[i];

                        } else {
                            params.add(parts[i]);
                        }
                    }

                }
            }
            if (executable.length() > 0) {
                runsLocal.add(new Run(dockerfile, executable, params));
                runs.add(new Run(dockerfile, executable, params));
            }
        }
        return runsLocal;
    }


    public List<Volume> parseAndGetVolumeInstruction(String command) {
        List<Volume> volumes = new ArrayList<>();
        if (command.contains("[")) {
            Pattern p = Pattern.compile("\"(.*?)\"");
            Matcher m = p.matcher(command);

            List<String> matches = new ArrayList<>();
            while (m.find()) {
                matches.add(m.group(1));
            }

            for (String match :
                    matches) {
                volumes.add(new Volume(dockerfile, match));
            }
        } else {
            String[] parts = command.split(" ");
            for (String part : parts) {
                volumes.add(new Volume(dockerfile, part));
            }
        }
        this.volumes = volumes;
        return volumes;
    }

    public Instruction parseAndGetOnBuildInstruction(String command) {
        OnBuild onBuild = null;
        String instruction = getInstructionInString(command);

        String[] split = command.split(" ", 2);
        if (checkForInstruction(split[0]) && split.length > 0) {
            onBuild = new OnBuild(dockerfile, instruction, split[1]);
        }
        onBuilds.add(onBuild);
        return onBuild;
    }

    public Instruction parseAndGetUserInstruction(String command) {
        users.add(new User(dockerfile, command));
        return new User(dockerfile, command);


    }

    public List<Instruction> parseAndGetMaintainerInstruction(String user) {
        maintainer.add(new Maintainer(dockerfile, user));
        return Collections.singletonList(new  Maintainer(dockerfile, user));
    }

    public Env parseAndGetEnvInstruction(String command) {
        String replaced = command.replaceAll("\\s+", " ");
        String[] parts = replaced.split(" ");
        String key = parts[0];
        String value = "";
        if (parts.length > 1) {
            value = parts[1];
        }
        envs.add(new Env(dockerfile, key, value));
        return new Env(dockerfile, key, value);
    }

    public Instruction parseFromInstruction(String command) {
        if (command.contains("/")) {
            from = new From(dockerfile, command);
            return from;
        } else if (command.contains("@")) {
            String[] parts = command.split("@");
            String image = parts[0];
            String imageVersion = parts[1];
            from = new From(dockerfile, image, imageVersion, "digest");
            return from;
        } else if (command.split("\\w+").length == 0) {
            from = new From(dockerfile, command);
            return from;
        } else if (command.matches(".*\\d+.*")) {
            Double imageVersion = Double.parseDouble(getStringFromRegexPattern("(?:\\d*\\.)?\\d+", command));
            String[] parts = command.split(":");
            String image = parts[0];
            from = new From(dockerfile, image, imageVersion);
            return from;
        } else if (command.matches("\\w*(:)\\w+")) {
            String[] parts = command.split(":");
            String image = parts[0];
            String imageVersion = parts[1];
            from = new From(dockerfile, image, imageVersion, "imageVersion");
            return from;
        }
        return from;
    }


    public String getStringFromRegexPattern(String patternInput, String data) {
        Pattern pattern = Pattern.compile(patternInput);
        Matcher matcher = pattern.matcher(data);
        try {
            matcher.find();
            return matcher.group(0);
        } catch (Exception e) {
            // e.printStackTrace();
            return "";
        }
    }

    public File findFile(String name, File path) {
        File[] list = path.listFiles();
        if (list != null)
            for (File file : list) {
                if (file.isDirectory()) {
                    findFile(name, file);
                } else if (name.equalsIgnoreCase(file.getName())) {
                    return file;
                }
            }
        return null;
    }

    public void assignToDockerObject(File file) throws IOException {
        dockerfile.comments = getCommentsFromDockerfile(file, dockerfile);
        dockerfile.from = from;
        dockerfile.maintainer = maintainer;
        dockerfile.cmd = cmd;
        dockerfile.entryPoint = entryPoint;
        dockerfile.stopSignals = stopSignal;
        dockerfile.healthCheck = healthcheck;

        dockerfile.runs = this.runs;

        dockerfile.labels = this.labels;
        dockerfile.envs = this.envs;

        dockerfile.exposes = this.exposes;
        dockerfile.adds = this.adds;
        dockerfile.copies = this.copies;
        dockerfile.volumes = this.volumes;
        dockerfile.users = this.users;
        dockerfile.workDirs = this.workDirs;
        dockerfile.args = this.args;
        dockerfile.onBuilds = this.onBuilds;
    }
}