package CUI;

import java.util.Objects;

public class Pawn extends Piece {

    private boolean isFirstMove = true;

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    private boolean isFirstMove() {
        return !isFirstMove;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {

        //TODO add error detection for going out of bounds

        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();
        boolean isDestinationInBounds = false;

        if ((destCol >= 0 && destCol <= 8) && (destRow >= 0 && destRow <= 8)){
            isDestinationInBounds = true;
        }

        if (Objects.equals(this.getcolour(), "black")) {
            if (isFirstMove && (destRow == sourceRow - 1 || destRow == sourceRow - 2) && !isDestinationOccupied && isDestinationInBounds) {
                return true;
            } else if (!isFirstMove && destRow == sourceRow - 1 && !isDestinationOccupied && isDestinationInBounds) {
                return true;
            }
        } else /* colour will be white */ {
            if (isFirstMove && (destRow == sourceRow + 1 || destRow == sourceRow + 2) && !isDestinationOccupied && isDestinationInBounds) {
                return true;
            } else if (!isFirstMove && destRow == sourceRow + 1 && !isDestinationOccupied && isDestinationInBounds) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {

        //TODO if a piece can capture the tile, the tile's flag must be changed.

        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();

        if (!isDestinationOccupied) {
            return false;
        } else {
            return false;
        }

    }

    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard) {

        if (colour.equals("black")) { // remember, black pieces are moving down
            if (sourceCol > 0 && sourceCol < 8) {
                chessboard[sourceRow - 1][sourceCol - 1].setCanBlackCapture(true); //capture down left
                chessboard[sourceRow - 1][sourceCol + 1].setCanBlackCapture(true); //capture down right
            } else if (sourceCol == 0) {
                chessboard[sourceRow - 1][sourceCol + 1].setCanBlackCapture(true); //only capture down right (left will be off the board)
            } else {
                chessboard[sourceRow - 1][sourceCol - 1].setCanBlackCapture(true); //only capture down left (right will be off the board)

            }
        } else if (colour.equals("white")) { //white are moving up
            if (sourceCol > 0 && sourceCol < 8) {
                chessboard[sourceRow + 1][sourceCol - 1].setCanBlackCapture(true); //capture up left
                chessboard[sourceRow + 1][sourceCol + 1].setCanBlackCapture(true); //capture up right
            } else if (sourceCol == 0) {
                chessboard[sourceRow + 1][sourceCol + 1].setCanBlackCapture(true); //only capture up right (left will be off the board)
            } else {
                chessboard[sourceRow + 1][sourceCol - 1].setCanBlackCapture(true); //only capture up left (right will be off the board)

            }
        }
    }

    protected Pawn(String colour, int xLoc, int yLoc) {
        super(colour, "Pawn");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }
}
