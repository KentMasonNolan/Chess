package CUI;

import java.util.Objects;

public class Pawn extends Piece {

    private boolean isFirstMove = true;

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
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

        if (Objects.equals(this.getColour(), "black")) {
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
            if (colour.equals("black")) { //I might not need this because if the flag is the opponent colour is false, it won't make it into the next if statement.
                if (destCol > 0 && destCol < 8 && (destRow == sourceRow + 1 || destRow == sourceRow - 1) && destCol == sourceCol - 1) {
                    return true;
                }
            } else if (colour.equals("white")) {
                if (destCol > 0 && destCol < 8 && (destRow == sourceRow - 1 || destRow == sourceRow + 1) && destCol == sourceCol - 1) {
                    return true;
                }
            }
        }
        System.out.println("Invalid capture");
        return false;
    }

    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard) {
        if (colour.equals("black")) {
            if (sourceRow > 0 && sourceCol > 0) {
                chessboard[sourceRow - 1][sourceCol - 1].setCanBlackCapture(true); // capture down left
            }
            if (sourceRow > 0 && sourceCol < 7) {
                chessboard[sourceRow - 1][sourceCol + 1].setCanBlackCapture(true); // capture down right
            }
        } else if (colour.equals("white")) {
            if (sourceRow < 7 && sourceCol > 0) {
                chessboard[sourceRow + 1][sourceCol - 1].setCanBlackCapture(true); // capture up left
            }
            if (sourceRow < 7 && sourceCol < 7) {
                chessboard[sourceRow + 1][sourceCol + 1].setCanBlackCapture(true); // capture up right
            }
        }
    }


    protected Pawn(String colour, int xLoc, int yLoc) {
        super(colour, "Pawn");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }
}
