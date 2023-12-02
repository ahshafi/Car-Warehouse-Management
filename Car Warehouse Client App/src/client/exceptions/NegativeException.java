package client.exceptions;

public class NegativeException extends  Exception {
    public NegativeException(String source) {
        super(source+"cannot be negative");
    }
}
