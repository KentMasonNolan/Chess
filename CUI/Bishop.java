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
        if (Math.abs(sourceRow - destRow) == Math.abs(sourceCol - destCol)) { // diagonal movement
            int rowStep, colStep;

            if (sourceRow < destRow) {
                rowStep = 1; // moving down
            } else {
                rowStep = -1; // moving up
            }

            if (sourceCol < destCol) {
                colStep = 1; // moving right
            } else {
                colStep = -1; // moving left
            }

            int row = sourceRow + rowStep;
            int col = sourceCol + colStep;

            while (row != destRow && col != destCol) {
                if (chessboard[row][col].isTileFilled()) {
                    return false; // There's an obstruction in the diagonal path
                }
                row += rowStep;
                col += colStep;
            }

            return true; // The diagonal path is clear
        }

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
