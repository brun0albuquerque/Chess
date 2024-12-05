package chess;

public enum ChessColor {
    BLACK,
    WHITE;

    @Override
    public String toString() {
        return name().charAt(0) + name()
                .substring(1)
                .toLowerCase();
    }
}

