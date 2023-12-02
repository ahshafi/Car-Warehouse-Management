package client.exceptions;

public class OutOfStockException extends Exception {
    @Override
    public String getMessage() {
        return "The car is out of stock";
    }
}
