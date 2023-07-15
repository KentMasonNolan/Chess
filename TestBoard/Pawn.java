package TestBoard;

import java.util.Objects;

public class Pawn extends Piece{

    private boolean isFirstMove = true;

    private boolean isFirstMove(){
        return !isFirstMove;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol) {

        //TODO add error detection for going out of bounds
        //TODO add check for if tile is occupied

        if (Objects.equals(this.getColor(), "black")){
            if (isFirstMove && destCol == sourceCol-1 || destCol == sourceCol-2){
                return true;
            } else if (!isFirstMove && destCol == sourceCol-1) {
                return true;
            }
        } else /* colour will be white */ {
            if (isFirstMove && destCol == sourceCol+1 || destCol == sourceCol+2){
                return true;
            } else if (!isFirstMove && destCol == sourceCol+1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol) {
        return false;
    }

    protected Pawn(String color, int xLoc, int yLoc) {
        super(color, "Pawn");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }
}
