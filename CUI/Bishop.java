package CUI;

import java.io.Serializable;
import java.util.Objects;

public class Bishop extends Piece implements Serializable {
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

        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();
        boolean isOpponentColour = (!Objects.equals(this.colour, chessboard[destRow][destCol].getPiece().colour));

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

            if (isDestinationOccupied && isOpponentColour) {
                return true;
            }
        }

        return false;
    }


    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {

        canCaptureUpRight(sourceRow, sourceCol, chessboard, gameState);
        canCaptureUpLeft(sourceRow, sourceCol, chessboard, gameState);
        canCaptureDownRight(sourceRow, sourceCol, chessboard, gameState);
        canCaptureDownLeft(sourceRow, sourceCol, chessboard, gameState);

    }
}
