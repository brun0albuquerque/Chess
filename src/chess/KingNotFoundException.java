package chess;

public class KingNotFoundException extends Exception {
    public KingNotFoundException() {
        super();
    }

    public KingNotFoundException(String message) {
        super(message);
    }

    public KingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
