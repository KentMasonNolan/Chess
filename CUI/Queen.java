package CUI;

public class Queen extends Piece {

    protected Queen(String colour, int xLoc, int yLoc) {
        super(colour, "Queen");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        if (canMoveDiagonal(sourceRow, sourceCol, destRow, destCol, chessboard)
                || canMoveVertical(sourceRow, sourceCol, destRow, destCol, chessboard)
                || canMoveHorizontal(sourceRow, sourceCol, destRow, destCol, chessboard)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        // TODO: Implement valid capture for the Queen (if different from valid moves).
        return false;
    }

    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard) {
        canCaptureRight(sourceRow, sourceCol, chessboard);
        canCaptureLeft(sourceRow, sourceCol, chessboard);
        canCaptureUp(sourceRow, sourceCol, chessboard);
        canCaptureDown(sourceRow, sourceCol, chessboard);
        canCaptureUpRight(sourceRow, sourceCol, chessboard);
        canCaptureDownRight(sourceRow, sourceCol, chessboard);
        canCaptureUpLeft(sourceRow, sourceCol, chessboard);
        canCaptureDownLeft(sourceRow, sourceCol, chessboard);
    }
}
