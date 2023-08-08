package CUI;

import java.util.Objects;

public class King extends Piece {
    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    private boolean isFirstMove = true;

    protected King(String colour, int xLoc, int yLoc, GameState gameState) {
        super(colour, "King");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        if (colour.equals("white")) {
            gameState.setWhiteKingPosition(xLoc, yLoc);
        } else {
            gameState.setBlackKingPosition(xLoc, yLoc);
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
    public boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {

        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();
        boolean isOpponentColour = (!Objects.equals(this.colour, chessboard[destRow][destCol].getPiece().colour));

        if (isDestinationOccupied && isOpponentColour) {
            if ((Math.abs(destCol - sourceCol) == 1 && destRow == sourceRow)
                    || (Math.abs(destCol - sourceCol) == 1 && Math.abs(destRow - sourceRow) == 1)
                    || (Math.abs(destRow - sourceRow) == 1 && destCol == sourceCol)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard) {
        for (int row = sourceRow - 1; row <= sourceRow + 1; row++) {
            for (int col = sourceCol - 1; col <= sourceCol + 1; col++) {
                if (row >= 0 && row < 8 && col >= 0 && col < 8 && !(row == sourceRow && col == sourceCol)) {
                    chessboard[row][col].setCanBlackCapture(true);
                }
            }
        }
    }
}
