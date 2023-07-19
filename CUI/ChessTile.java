package CUI;

class ChessTile {
    private int row;
    private int col;

    protected boolean canBlackCapture = false;
    private boolean canWhiteCapture = false;

    public boolean getCanBlackCapture() {
        return canBlackCapture;
    }

    public void setCanBlackCapture(boolean chessTiles) {
        this.canBlackCapture = true;
    }

    public boolean getCanWhiteCapture() {
        return canWhiteCapture;
    }

    public void setCanWhiteCapture(ChessTile chessTiles) {
        this.canWhiteCapture = true;
    }

    public void removePiece() {
        piece = null; // Remove the piece from the tile (set it to null)
    }

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
