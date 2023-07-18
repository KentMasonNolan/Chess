package CUI;

class ChessTile {
    private int row;
    private int col;

    private boolean canBlackCapture = false;
    private boolean canWhiteCapture = false;

    public boolean isCanBlackCapture() {
        return canBlackCapture;
    }

    public void setCanBlackCapture(boolean canBlackCapture) {
        this.canBlackCapture = canBlackCapture;
    }

    public boolean isCanWhiteCapture() {
        return canWhiteCapture;
    }

    public void setCanWhiteCapture(boolean canWhiteCapture) {
        this.canWhiteCapture = canWhiteCapture;
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
