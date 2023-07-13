public abstract class ChessTile {

    int tileNumber;

    public ChessTile(int tileNumber) {
        this.tileNumber = tileNumber;
    }

    public abstract boolean isTileFilled();

    public abstract Piece getPiece();

}
