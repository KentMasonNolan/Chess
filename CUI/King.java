package CUI;

public class King extends Piece {

    private boolean isFirstMove = true;

    protected King(String color, int xLoc, int yLoc) {
        super(color, "King");
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

        if (sourceRow > 0 && sourceRow < 7 && sourceCol > 0 && sourceCol < 7) {

            chessboard[sourceRow - 1][sourceCol - 1].setCanBlackCapture(true); //capture down left
            chessboard[sourceRow - 1][sourceCol + 1].setCanBlackCapture(true); //capture down right
            chessboard[sourceRow - 1][sourceCol].setCanBlackCapture(true); //capture down

            chessboard[sourceRow][sourceCol + 1].setCanBlackCapture(true); //capture right
            chessboard[sourceRow][sourceCol - 1].setCanBlackCapture(true); //capture left

            chessboard[sourceRow + 1][sourceCol - 1].setCanBlackCapture(true); //capture up left
            chessboard[sourceRow + 1][sourceCol + 1].setCanBlackCapture(true); //capture up right
            chessboard[sourceRow + 1][sourceCol].setCanBlackCapture(true); //capture up

        } else if (sourceRow == 0 && sourceCol != 0 || sourceCol != 7) {

            chessboard[sourceRow - 1][sourceCol - 1].setCanBlackCapture(true); //capture down left
            chessboard[sourceRow - 1][sourceCol + 1].setCanBlackCapture(true); //capture down right
            chessboard[sourceRow - 1][sourceCol].setCanBlackCapture(true); //capture down

            chessboard[sourceRow][sourceCol + 1].setCanBlackCapture(true); //capture right
            chessboard[sourceRow][sourceCol - 1].setCanBlackCapture(true); //capture left

        } else if (sourceRow == 7 && sourceCol != 0 && sourceCol != 7) {

            chessboard[sourceRow + 1][sourceCol - 1].setCanBlackCapture(true); //capture up left
            chessboard[sourceRow + 1][sourceCol + 1].setCanBlackCapture(true); //capture up right
            chessboard[sourceRow + 1][sourceCol].setCanBlackCapture(true); //capture up

            chessboard[sourceRow][sourceCol + 1].setCanBlackCapture(true); //capture right
            chessboard[sourceRow][sourceCol - 1].setCanBlackCapture(true); //capture left

        } //TODO add testing to find out why I'm getting errors saying this will always be true/false
    }
}
