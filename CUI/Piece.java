package CUI;

abstract class Piece {
    protected String colour;
    protected String type;

    protected int xLoc;
    protected int yLoc;

    protected int previousCol;

    protected int previousRow;

    public int getPreviousCol() {
        return previousCol;
    }

    public void setPreviousCol(int previousCol) {
        this.previousCol = previousCol;
    }

    public int getPreviousRow() {
        return previousRow;
    }

    public void setPreviousRow(int previousRow) {
        this.previousRow = previousRow;
    }

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

    public String getColour() {
        return colour;
    }

    public String getType() {
        return type;
    }


    public abstract boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard);

    public abstract boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard);

    public abstract void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard);
}
