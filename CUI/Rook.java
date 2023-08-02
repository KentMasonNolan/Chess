package CUI;

public class Rook extends Piece {

    private boolean isFirstMove = true;


    protected Rook(String colour, int xLoc, int yLoc) {
        super(colour, "Rook");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }


    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        if (canMoveVertical(sourceRow, sourceCol, destRow, destCol, chessboard)
                || canMoveHorizontal(sourceRow, sourceCol, destRow, destCol, chessboard)) {
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

        canCaptureRight(sourceRow, sourceCol, chessboard);
        canCaptureLeft(sourceRow, sourceCol, chessboard);
        canCaptureUp(sourceRow, sourceCol, chessboard);
        canCaptureDown(sourceRow, sourceCol, chessboard);

    }
}
