package client.exceptions;

public class SpaceWithinException extends Exception {

    public SpaceWithinException(String source) {
        super(source+"cannot contain empty space");
    }
}
