package CUI;

public class King extends Piece{
    protected King(String color, int xLoc, int yLoc) {
        super(color, "King");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {

        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();
        boolean isDestinationInBounds = false;

        if ((destCol >= 0 && destCol <= 8) && (destRow >= 0 && destRow <= 8)){
            isDestinationInBounds = true;
        }

        if (destCol == sourceCol + 1 || destCol == sourceCol - 1 || destCol == sourceCol
                && destRow == sourceRow + 1 || destRow == sourceRow - 1 || destRow == sourceRow
                && !isDestinationOccupied
                && isDestinationInBounds) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        return false;
    }

    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard) {

    }
}
