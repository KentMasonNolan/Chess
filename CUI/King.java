package CUI;

public class King extends Piece {

    private boolean isFirstMove = true;

    protected King(String colour, int xLoc, int yLoc) {
        super(colour, "King");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {

        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();
        boolean isDestinationInBounds = false;

        if ((destCol >= 0 && destCol <= 8) && (destRow >= 0 && destRow <= 8)) {
            isDestinationInBounds = true;
        }

        if (destCol == sourceCol + 1 || destCol == sourceCol - 1 || destCol == sourceCol && destRow == sourceRow + 1 || destRow == sourceRow - 1 || destRow == sourceRow && !isDestinationOccupied && isDestinationInBounds) {
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
        for (int row = sourceRow - 1; row <= sourceRow + 1; row++) {
            for (int col = sourceCol - 1; col <= sourceCol + 1; col++) {
                if (row >= 0 && row < 8 && col >= 0 && col < 8 && !(row == sourceRow && col == sourceCol)) {
                    chessboard[row][col].setCanBlackCapture(true);
                }
            }
        }
    }
}
