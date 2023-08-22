package CUI;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class King extends Piece implements Serializable {
    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    private boolean isFirstMove = true;

    protected King(String colour, int row, int col, GameState gameState) {
        super(colour, "King");
        this.pieceCol = row;
        this.pieceRow = col;
        if (colour.equals("white")) {
            gameState.setWhiteKingPosition(row, col);
        } else {
            gameState.setBlackKingPosition(row, col);
        }
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        int rowDiff = Math.abs(destRow - sourceRow);
        int colDiff = Math.abs(destCol - sourceCol);

        // The King can move one square in any direction
        if (rowDiff <= 1 && colDiff <= 1) {
            boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();
            boolean isDestinationInBounds = (destCol >= 0 && destCol < 8 && destRow >= 0 && destRow < 8);

            if (!isDestinationOccupied && isDestinationInBounds) {
                isFirstMove = false; // Set isFirstMove to false once the King moves for the first time.
                return true;
            }
        }

        return false;
    }

    @Override
    public List<ChessTile> getValidMoves(ChessTile[][] chessboard) {
        return null;
    }

    public boolean hasValidMoveToEscape(ChessTile[][] chessboard) {
        int kingRow = this.getPieceRow();
        int kingCol = this.getPieceCol();

        for (int row = kingRow - 1; row <= kingRow + 1; row++) {
            for (int col = kingCol - 1; col <= kingCol + 1; col++) {
                if (row >= 0 && row < 8 && col >= 0 && col < 8 && !(row == kingRow && col == kingCol)) {
                    if (this.getColour().equals("white")) {
                        if (!chessboard[row][col].getCanBlackCapture() && !chessboard[row][col].isTileFilled()) {
                            return true; // At least one valid move found, no checkmate
                        }
                    } else if (this.getColour().equals("black")) {
                        if (!chessboard[row][col].getCanWhiteCapture() && !chessboard[row][col].isTileFilled()) {
                            return true; // At least one valid move found, no checkmate
                        }
                    }
                }
            }
        }


        return false; // No valid moves found, potential checkmate
    }

    @Override
    public boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {

        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();
        boolean isOpponentColour = (!Objects.equals(this.colour, chessboard[destRow][destCol].getPiece().colour));

        if (isDestinationOccupied && isOpponentColour) {
            if ((Math.abs(destCol - sourceCol) == 1 && destRow == sourceRow) || (Math.abs(destCol - sourceCol) == 1 && Math.abs(destRow - sourceRow) == 1) || (Math.abs(destRow - sourceRow) == 1 && destCol == sourceCol)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        for (int row = sourceRow - 1; row <= sourceRow + 1; row++) {
            for (int col = sourceCol - 1; col <= sourceCol + 1; col++) {
                if (row >= 0 && row < 8 && col >= 0 && col < 8 && !(row == sourceRow && col == sourceCol)) {
                    chessboard[row][col].setCanBlackCapture(true);
                }
            }
        }
    }
}
