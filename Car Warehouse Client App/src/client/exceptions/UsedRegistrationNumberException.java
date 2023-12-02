package client.exceptions;

public class UsedRegistrationNumberException extends Exception {
    @Override
    public String getMessage() {
        return "Registration number currently in use";
    }
}
