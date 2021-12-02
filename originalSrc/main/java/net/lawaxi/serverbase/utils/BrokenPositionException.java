package net.lawaxi.serverbase.utils;

public class BrokenPositionException extends Exception {
    public BrokenPositionException() {
        super();
    }

    public BrokenPositionException(String message) {
        super(message);
    }

    public BrokenPositionException(String message, Throwable cause) {
        super(message, cause);
    }

    public BrokenPositionException(Throwable cause) {
        super(cause);
    }
}
