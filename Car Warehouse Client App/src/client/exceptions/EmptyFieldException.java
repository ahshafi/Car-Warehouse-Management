package client.exceptions;

public class EmptyFieldException extends Exception {
    public EmptyFieldException(String source) {
        super(source+"cannot be left empty");
    }
}
