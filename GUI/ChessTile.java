package GUI;

public class ChessTile {
    int tileNumber;
    private Piece piece; // Add GUI.Piece property

    public ChessTile(int tileNumber) {
        this.tileNumber = tileNumber;
    }

    public boolean isTileFilled() {
        return piece != null; // Check if the tile has a piece
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}