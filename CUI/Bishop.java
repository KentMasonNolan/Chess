package CUI;

public class Bishop extends Piece {
    protected Bishop(String colour, int xLoc, int yLoc) {
        super(colour, "Bishop");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        if (canMoveDiagonal(sourceRow, sourceCol, destRow, destCol, chessboard)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        return false;
    }

    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard) {

        canCaptureUpRight(sourceRow, sourceCol, chessboard);
        canCaptureUpLeft(sourceRow, sourceCol, chessboard);
        canCaptureDownRight(sourceRow, sourceCol, chessboard);
        canCaptureDownLeft(sourceRow, sourceCol, chessboard);

    }
}
