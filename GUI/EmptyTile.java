package GUI;

public class EmptyTile extends ChessTile{



    public EmptyTile(int tileNumber) {
        super(tileNumber);
    }

    @Override
    public boolean isTileFilled() {
        return false;
    }

    @Override
    public Piece getPiece() {
        return null;
    }
}
