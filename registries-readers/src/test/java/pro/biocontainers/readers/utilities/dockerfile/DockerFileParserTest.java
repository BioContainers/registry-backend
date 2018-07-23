package pro.biocontainers.readers.utilities.dockerfile;

import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class DockerFileParserTest {

    @Test
    public void parse() {

        try {
            URI uri = DockerFileParserTest.class.getClassLoader().getResource("files/Dockerfile").toURI();
            File file = new File(uri);
            DockerFile document = DockerFileParser.parse(file);
            System.out.println(document.toString());
        } catch (URISyntaxException | FileException e) {
            e.printStackTrace();
        }
    }
}