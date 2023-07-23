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

        //todo this should be a try/catch because it will error if it is out of bounds
        if ((destCol >= 0 && destCol <= 8) && (destRow >= 0 && destRow <= 8)) {
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

        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();
        boolean isOpponentColour = (!Objects.equals(this.colour, chessboard[destRow][destCol].getPiece().colour));


        if (isDestinationOccupied && isOpponentColour) {
            if (colour.equals("black")) { //I might not need this because if the flag is the oppoentcolour is flase, it won't make it into the next if statement.
                if (destCol > 0 && destCol < 8 && (destRow == sourceRow + 1 || destRow == sourceRow - 1) && destCol == sourceCol - 1) {
                    return true;
                }
            } else if (colour.equals("white")) {
                if (destCol > 0 && destCol < 8 && (destRow == sourceRow - 1 || destRow == sourceRow + 1) && destCol == sourceCol + 1) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard) {

        if (colour.equals("black")) { // remember, black pieces are moving down
            //todo update to black moving up the board.
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
