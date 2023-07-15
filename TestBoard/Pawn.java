package TestBoard;

import java.util.Objects;

public class Pawn extends Piece{

    private boolean isFirstMove = true;

    private boolean isFirstMove(){
        return !isFirstMove;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {

        //TODO add error detection for going out of bounds

        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();

        if (Objects.equals(this.getColor(), "black")){
            if (isFirstMove && (destCol == sourceCol-1 || destCol == sourceCol-2) && !isDestinationOccupied){
                return true;
            } else if (!isFirstMove && destCol == sourceCol-1 && !isDestinationOccupied) {
                return true;
            }
        } else /* colour will be white */ {
            if (isFirstMove && (destCol == sourceCol+1 || destCol == sourceCol+2) && !isDestinationOccupied){
                return true;
            } else if (!isFirstMove && destCol == sourceCol+1 && !isDestinationOccupied) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {

        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();

        if (!isDestinationOccupied){
            return false;
        } else {
            return false;
        }

    }

    protected Pawn(String color, int xLoc, int yLoc) {
        super(color, "Pawn");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }
}
