package TestBoard;

abstract class Piece {
    private String color;
    protected String type;

    protected int xLoc;
    protected int yLoc;

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

    public Piece(String color, String type) {
        this.color = color;
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public abstract boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol);

    public abstract boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol);
}
