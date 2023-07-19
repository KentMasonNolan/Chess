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

        if (destCol == sourceCol + 1 || destCol == sourceCol - 1 || destCol == sourceCol
                && destRow == sourceRow + 1 || destRow == sourceRow - 1 || destRow == sourceRow
                && !isDestinationOccupied) {
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
