package TestBoard;

class ChessTile {
    private int row;
    private int col;
    private Piece piece;

    public ChessTile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isTileFilled() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
