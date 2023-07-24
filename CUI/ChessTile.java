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
        this.canBlackCapture = chessTiles;
    }

    public boolean getCanWhiteCapture() {
        return canWhiteCapture;
    }

    public void setCanWhiteCapture(boolean chessTiles) {
        this.canWhiteCapture = chessTiles;
    }

    public void removePiece() {
        piece = null; // Remove the piece from the tile (set it to null)
    }

    private Piece piece;
    private Piece previousPiece;

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

    public Piece getPreviousPiece() {
        return previousPiece;
    }

    public void setPreviousPiece(Piece previousPiece) {
        this.previousPiece = previousPiece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
