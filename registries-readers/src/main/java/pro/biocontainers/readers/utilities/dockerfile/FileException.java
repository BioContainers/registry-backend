package pro.biocontainers.readers.utilities.dockerfile;

public class FileException extends Exception {

    public FileException(String message, Exception e) {
        super(message, e);
    }

    public FileException(String message) {
        super(message);
    }
}
