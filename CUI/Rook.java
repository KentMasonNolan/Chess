package CUI;

public class Rook extends Piece {

    private boolean isFirstMove = true;


    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    protected Rook(String colour, int xLoc, int yLoc) {
        super(colour, "Rook");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    //todo Castling

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        if (canMoveVertical(sourceRow, sourceCol, destRow, destCol, chessboard) || canMoveHorizontal(sourceRow, sourceCol, destRow, destCol, chessboard)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        if (sourceRow == destRow) { // moving left or right
            if (sourceCol < destCol) { // moving right
                for (int i = sourceCol + 1; i < destCol; i++) {
                    if (chessboard[sourceRow][i].isTileFilled()) {
                        return false;
                    }
                }
            } else { // moving left
                for (int i = sourceCol - 1; i > destCol; i--) {
                    if (chessboard[sourceRow][i].isTileFilled()) {
                        return false;
                    }
                }
            }
            return true;
        }

        if (sourceCol == destCol) { // moving up and down
            if (sourceRow < destRow) { // moving down
                for (int i = sourceRow + 1; i < destRow; i++) {
                    if (chessboard[i][sourceCol].isTileFilled()) {
                        return false;
                    }
                }
            } else { // moving up
                for (int i = sourceRow - 1; i > destRow; i--) {
                    if (chessboard[i][sourceCol].isTileFilled()) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false; // If none of the conditions are met, it's an invalid capture
    }


    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard) {

        canCaptureRight(sourceRow, sourceCol, chessboard);
        canCaptureLeft(sourceRow, sourceCol, chessboard);
        canCaptureUp(sourceRow, sourceCol, chessboard);
        canCaptureDown(sourceRow, sourceCol, chessboard);

    }
}
