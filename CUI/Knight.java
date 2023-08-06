package CUI;

import java.util.Objects;

public class Knight extends Piece {

    protected Knight(String colour, int xLoc, int yLoc) {
        super(colour, "Knight");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {

        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();

        if (!isDestinationOccupied) {
            if (Math.abs(destCol - sourceCol) == 2 && Math.abs(destRow - sourceRow) == 1) {
                return true;
            } else if (Math.abs(destCol - sourceCol) == 1 && Math.abs(destRow - sourceRow) == 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {

        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();
        boolean isOpponentColour = (!Objects.equals(this.colour, chessboard[destRow][destCol].getPiece().colour));

        if (isDestinationOccupied && isOpponentColour) {
            if (Math.abs(destCol - sourceCol) == 2 && Math.abs(destRow - sourceRow) == 1) {
                return true;
            } else if (Math.abs(destCol - sourceCol) == 1 && Math.abs(destRow - sourceRow) == 2) {
                return true;
            }
        }
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
            if (sourceCol - 2 >= 0) {
                if (sourceRow - 1 >= 0) {
                    chessboard[sourceRow - 1][sourceCol - 2].setCanWhiteCapture(true);
                }
                if (sourceCol + 1 <= 7) {
                    chessboard[sourceRow + 1][sourceCol - 2].setCanWhiteCapture(true);
                }
            }
            if (sourceCol + 2 <= 7) {
                if (sourceRow - 1 >= 0) {
                    chessboard[sourceRow - 1][sourceCol + 2].setCanWhiteCapture(true);
                }
                if (sourceCol + 1 <= 7) {
                    chessboard[sourceRow + 1][sourceCol + 2].setCanWhiteCapture(true);
                }
            }
        } else { // colour must be black

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
            if (sourceCol - 2 >= 0) {
                if (sourceRow - 1 >= 0) {
                    chessboard[sourceRow - 1][sourceCol - 2].setCanBlackCapture(true);
                }
                if (sourceCol + 1 <= 7) {
                    chessboard[sourceRow + 1][sourceCol - 2].setCanBlackCapture(true);
                }
            }
            if (sourceCol + 2 <= 7) {
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
