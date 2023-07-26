package CUI;

public class Rook extends Piece {

    private boolean isFirstMove = true;


    protected Rook(String colour, int xLoc, int yLoc) {
        super(colour, "Rook");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {

            boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();
            boolean isDestinationInBounds = (destCol >= 0 && destCol < 8 && destRow >= 0 && destRow < 8);

            if (sourceRow == destRow && sourceCol != destCol) {
                int colStep;
                if (sourceCol < destCol) {
                    colStep = 1;
                } else {
                    colStep = -1;
                }

                for (int col = sourceCol + colStep; col != destCol; col += colStep) {
                    if (chessboard[sourceRow][col].isTileFilled()) {
                        return false;
                    }
                }
                return !isDestinationOccupied && isDestinationInBounds;

            } else if (sourceCol == destCol && sourceRow != destRow) {
                int rowStep;
                if (sourceRow < destRow) {
                    rowStep = 1;
                } else {
                    rowStep = -1;
                }

                for (int row = sourceRow + rowStep; row != destRow; row += rowStep) {
                    if (chessboard[row][sourceCol].isTileFilled()) {
                        return false;
                    }
                }
                return !isDestinationOccupied && isDestinationInBounds;
            }

            return false;
        }



    @Override
    public boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        return false;
    }

    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard) {

        canCaptureRight(sourceRow, sourceCol, chessboard);
        canCaptureLeft(sourceRow, sourceCol, chessboard);
        canCaptureUp(sourceRow, sourceCol, chessboard);
        canCaptureDown(sourceRow, sourceCol, chessboard);

    }
}
