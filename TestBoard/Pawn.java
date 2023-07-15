package TestBoard;

public class Pawn extends Piece{

    private boolean isFirstMove = false;

    private boolean isFirstMove(){
        return !isFirstMove;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol) {
        return false;
    }

    @Override
    public boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol) {
        return false;
    }

    protected Pawn(String color) {
        super(color, "P");
    }
}
