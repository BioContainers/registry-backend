package pro.biocontainers.readers.utilities.dockerfile;



import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;


/**
 * A statement present in a dockerfile.
 */
@Log4j2
public abstract class DockerFileStatement<T extends DockerFileStatement<?>> {

    private DockerFileStatement() {

    }
    public T transform(Map<String, String> env) {
        return (T) this;
    }

    protected String filterForEnvironmentVars(Map<String, String> environmentMap,
                                              String extractedResource) {

        if (environmentMap.size() > 0) {
            String currentResourceContent = extractedResource;

            for (Map.Entry<String, String> entry : environmentMap.entrySet()) {

                String variable = entry.getKey();

                String replacementValue = entry.getValue();

                // handle: $VARIABLE case
                currentResourceContent = currentResourceContent.replaceAll("\\$" + variable,
                        Matcher.quoteReplacement(replacementValue));

                // handle ${VARIABLE} case
                currentResourceContent = currentResourceContent.replaceAll("\\$\\{" + variable + "\\}",
                        Matcher.quoteReplacement(replacementValue));

            }

            return currentResourceContent;
        } else {
            return extractedResource;
        }
    }

    /**
     * A statement that we don't particularly care about.
     */
    public static class OtherLine extends DockerFileStatement {

        public final String statement;

        public OtherLine(String statement) {
            this.statement = statement;
        }

        @Override
        public String toString() {
            return statement;
        }
    }

    /**
     * An ADD or a COPY
     */
    public static class Add extends DockerFileStatement<Add> {

        private static final Pattern ARGUMENT_TOKENIZER = Pattern.compile("(?:\"[^\"]+\")|(\\S+)");

        public final Collection<String> sources;

        public final String destination;

        private Add(Collection<String> sources, String destination) {
            this.sources = sources;
            this.destination = destination;
        }

        @Override
        public Add transform(final Map<String, String> env) {
            Collection<String> resources = sources.parallelStream().map( x-> filterForEnvironmentVars(env, x).trim()).collect(Collectors.toList());
            return new Add(resources, destination);
        }

        public Iterable<String> getFileResources() {
            return sources.parallelStream()
                    .filter(x-> {
                        URI uri;
                        try {
                            uri = new URI(x);
                        } catch (URISyntaxException e) {
                            return false;
                        }
                        return uri.getScheme() == null || "file".equals(uri.getScheme());
                    }).collect(Collectors.toList());
        }

        /**
         * Createa an Add if it matches, or missing if not.
         *
         * @param statement
         *            statement that may be an ADD or a COPY
         * @return optional typed item.
         */
        public static Optional<Add> create(String statement) {
            Matcher argumentMatcher = ARGUMENT_TOKENIZER.matcher(statement.trim());

            if (!argumentMatcher.find()) {
                return Optional.empty();
            }

            String commandName = argumentMatcher.group();
            if (!(StringUtils.equals(commandName, "ADD") || StringUtils.equals(commandName, "COPY"))) {
                return Optional.empty();
            }

            String lastToken = null;
            Collection<String> sources = new ArrayList<>();

            while (argumentMatcher.find()) {
                if (lastToken != null) {
                    sources.add(lastToken);
                }
                lastToken = argumentMatcher.group().replaceAll("(^\")|(\"$)", "");
            }

            if (sources.isEmpty()) {
                log.error("Wrong ADD or COPY format");
            }

            return Optional.of(new Add(sources, lastToken));
        }

        @Override
        public String toString() {
            return new StringBuilder().append("sources : " +  sources)
                    .append("destination : " +  destination)
                    .toString();
        }
    }

    public static class Env extends DockerFileStatement<Env> {

        private static final Pattern ENV_PATTERN = Pattern.compile("^ENV\\s+(.*)\\s+(.*)$");

        public final String variable;

        public final String value;

        private Env(String variable, String value) {
            this.variable = variable;
            this.value = value;
        }

        private Env(Matcher envMatcher) {
            this.variable = envMatcher.group(1).trim();
            this.value = envMatcher.group(2).trim();
        }

        public static Optional<Env> create(String statement) {
            Matcher matcher = ENV_PATTERN.matcher(statement.trim());
            if (!matcher.find()) {
                return Optional.empty();
            }

            if (matcher.groupCount() != 2) {
                log.error("Wrong ENV format");
            }

            return Optional.of(new Env(matcher));
        }

        @Override
        public String toString() {
            return new StringBuilder().append("variable : " +  variable)
                    .append("value : " +  value)
                    .toString();
        }
    }

    /**
     * Return a dockerfile statement
     */
    public static Optional<? extends DockerFileStatement> createFromLine(String cmd) {
        if (cmd.trim().isEmpty() || cmd.startsWith("#")) {
            return Optional.empty();
        }

        Optional<? extends DockerFileStatement> line;

        line = Add.create(cmd);

        if (line.isPresent()) {
            return line;
        }

        line = Env.create(cmd);

        if (line.isPresent()) {
            return line;
        }

        return Optional.of(new OtherLine(cmd));

    }

}