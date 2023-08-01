package CUI;

public class Knight extends Piece {

    protected Knight(String colour, int xLoc, int yLoc) {
        super(colour, "Knight");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        // TODO: Implement movement options for the Knight.
        return false;
    }

    @Override
    public boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        // TODO: Implement capture options for the Knight (if different from valid moves).
        return false;
    }

    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard) {
        // TODO: Implement a method to determine squares that the Knight can capture.
    }
}
