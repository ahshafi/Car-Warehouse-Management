package client.exceptions;

public class NonExistentException extends Exception {
    public NonExistentException(String source) {
        super(source+"does not exist anymore");
    }
}
