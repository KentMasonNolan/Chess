package CUI;

public class Knight extends Piece {

    protected Knight(String colour, int xLoc, int yLoc) {
        super(colour, "Knight");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        // TODO: Implement movement options for the Knight.
        return false;
    }

    @Override
    public boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        // TODO: Implement capture options for the Knight (if different from valid moves).
        return false;
    }

    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard) {
        if (colour.equals("white")) {

            if (sourceRow - 2 >= 0) {
                if (sourceCol - 1 >= 0) {
                    chessboard[sourceRow - 2][sourceCol - 1].setCanWhiteCapture(true);
                }
                if (sourceCol + 1 <= 7) {
                    chessboard[sourceRow - 2][sourceCol + 1].setCanWhiteCapture(true);
                }
            }
            if (sourceRow + 2 <= 7) {
                if (sourceCol - 1 >= 0) {
                    chessboard[sourceRow + 2][sourceCol - 1].setCanWhiteCapture(true);
                }
                if (sourceCol + 1 <= 7) {
                    chessboard[sourceRow + 2][sourceCol + 1].setCanWhiteCapture(true);
                }
            }
            if (sourceCol -2 >= 0){
                if (sourceRow - 1 >= 0) {
                    chessboard[sourceRow - 1][sourceCol - 2].setCanWhiteCapture(true);
                }
                if (sourceCol + 1 <= 7) {
                    chessboard[sourceRow + 1][sourceCol - 2].setCanWhiteCapture(true);
                }
            }
            if (sourceCol + 2 <= 7){
                if (sourceRow - 1 >= 0) {
                    chessboard[sourceRow - 1][sourceCol + 2].setCanWhiteCapture(true);
                }
                if (sourceCol + 1 <= 7) {
                    chessboard[sourceRow + 1][sourceCol + 2].setCanWhiteCapture(true);
                }
            }
        }
        else { // colour must be black

            if (sourceRow - 2 >= 0) {
                if (sourceCol - 1 >= 0) {
                    chessboard[sourceRow - 2][sourceCol - 1].setCanBlackCapture(true);
                }
                if (sourceCol + 1 <= 7) {
                    chessboard[sourceRow - 2][sourceCol + 1].setCanBlackCapture(true);
                }
            }
            if (sourceRow + 2 <= 7) {
                if (sourceCol - 1 >= 0) {
                    chessboard[sourceRow + 2][sourceCol - 1].setCanBlackCapture(true);
                }
                if (sourceCol + 1 <= 7) {
                    chessboard[sourceRow + 2][sourceCol + 1].setCanBlackCapture(true);
                }
            }
            if (sourceCol -2 >= 0){
                if (sourceRow - 1 >= 0) {
                    chessboard[sourceRow - 1][sourceCol - 2].setCanBlackCapture(true);
                }
                if (sourceCol + 1 <= 7) {
                    chessboard[sourceRow + 1][sourceCol - 2].setCanBlackCapture(true);
                }
            }
            if (sourceCol + 2 <= 7){
                if (sourceRow - 1 >= 0) {
                    chessboard[sourceRow - 1][sourceCol + 2].setCanBlackCapture(true);
                }
                if (sourceCol + 1 <= 7) {
                    chessboard[sourceRow + 1][sourceCol + 2].setCanBlackCapture(true);
                }
            }
        }

    }
}
