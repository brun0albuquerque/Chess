package application;

import boardgame.Position;

public interface MoveListener {
    void onMovePiece(Position source, Position target);
}
