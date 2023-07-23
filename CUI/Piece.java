package CUI;

abstract class Piece {
    protected String colour;
    protected String type;

    protected int xLoc;
    protected int yLoc;

    protected int previousXLoc;

    public int getPreviousXLoc() {
        return previousXLoc;
    }

    public void setPreviousXLoc(int previousXLoc) {
        this.previousXLoc = previousXLoc;
    }

    public int getPreviousYLoc() {
        return PreviousYLoc;
    }

    public void setPreviousYLoc(int previousYLoc) {
        PreviousYLoc = previousYLoc;
    }

    protected int PreviousYLoc;

    public int getxLoc() {
        return xLoc;
    }

    public void setxLoc(int xLoc) {
        this.xLoc = xLoc;
    }

    public int getyLoc() {
        return yLoc;
    }

    public void setyLoc(int yLoc) {
        this.yLoc = yLoc;
    }

    public Piece(String colour, String type) {
        this.colour = colour;
        this.type = type;
    }

    public String getcolour() {
        return colour;
    }

    public String getType() {
        return type;
    }


    public abstract boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard);

    public abstract boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard);

    public abstract void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard);
}
