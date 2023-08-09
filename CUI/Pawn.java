package CUI;

import java.util.Objects;

public class Pawn extends Piece {

    private boolean isFirstMove = true;

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {


        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();
        boolean isSquareAboveOccupied = chessboard[destRow-1][destCol].isTileFilled(); // we need this because a pawn cannot jump a piece.
        boolean isSquareBelowOccupied = chessboard[destRow+1][destCol].isTileFilled(); // we need this because a pawn cannot jump a piece.
        boolean isDestinationInBounds = false;

        if ((destCol >= 0 && destCol <= 8) && (destRow >= 0 && destRow <= 8)) {
            isDestinationInBounds = true;
        }

        if (Objects.equals(this.getColour(), "black")) {
            if (isFirstMove && (destRow == sourceRow - 1 || destRow == sourceRow - 2) && !isDestinationOccupied && isDestinationInBounds && !isSquareAboveOccupied) {
                return true;
            } else if (!isFirstMove && destRow == sourceRow - 1 && !isDestinationOccupied && isDestinationInBounds) {
                return true;
            }
        } else /* colour will be white */ {
            if (isFirstMove && (destRow == sourceRow + 1 || destRow == sourceRow + 2) && !isDestinationOccupied && isDestinationInBounds && !isSquareBelowOccupied) {
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
            if (colour.equals("black")) {
                if (destCol > 0 && destCol < 8 && (destRow == sourceRow + 1 || destRow == sourceRow - 1) && (destCol == sourceCol - 1 || destCol == sourceCol + 1)) {
                    return true;
                }
            } else if (colour.equals("white")) {
                if (destCol > 0 && destCol < 8 && (destRow == sourceRow - 1 || destRow == sourceRow + 1) && (destCol == sourceCol - 1 || destCol == sourceCol + 1)) {
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
                chessboard[sourceRow + 1][sourceCol - 1].setCanWhiteCapture(true); // capture up left
            }
            if (sourceRow < 7 && sourceCol < 7) {
                chessboard[sourceRow + 1][sourceCol + 1].setCanWhiteCapture(true); // capture up right
            }
        }
    }


    protected Pawn(String colour, int xLoc, int yLoc) {
        super(colour, "Pawn");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }
}
