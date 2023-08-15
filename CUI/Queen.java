package CUI;

import java.io.Serializable;

public class Queen extends Piece implements Serializable {

    protected Queen(String colour, int xLoc, int yLoc) {
        super(colour, "Queen");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        if (canMoveDiagonal(sourceRow, sourceCol, destRow, destCol, chessboard) || canMoveVertical(sourceRow, sourceCol, destRow, destCol, chessboard) || canMoveHorizontal(sourceRow, sourceCol, destRow, destCol, chessboard)) {
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

        return false; // If none of the conditions are met, it's an invalid capture
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
