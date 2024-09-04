package boardgame;

public class BoardException extends RuntimeException {
    private static final long SerialVerionUID = 1L;

    public BoardException(String message) {
        super(message);
    }
}
