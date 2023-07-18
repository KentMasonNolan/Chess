package CUI;

import javax.swing.plaf.IconUIResource;
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

        //TODO if a piece can capture the tile, the tile's flag must be changed.

        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();

        if (!isDestinationOccupied){
            return false;
        } else {
            return false;
        }

    }

    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard) {

        //TODO add error detection for capturing outside of board

        if (color.equals("black")){ // remember, black pieces are moving down
            if (sourceCol > 0 && sourceCol < 8){
                chessboard[sourceRow - 1][sourceCol - 1].setCanBlackCapture(true); //capture down left
                chessboard[sourceRow - 1][sourceCol + 1].setCanBlackCapture(true); //capture down right
            } else if (sourceCol == 0) {
                chessboard[sourceRow - 1][sourceCol + 1].setCanBlackCapture(true); //only capture down right (left will be off the board)
            } else {
                chessboard[sourceRow - 1][sourceCol - 1].setCanBlackCapture(true); //only capture down left (right will be off the board)

            }
        }
    }

    protected Pawn(String color, int xLoc, int yLoc) {
        super(color, "GUI.Pawn");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }
}
