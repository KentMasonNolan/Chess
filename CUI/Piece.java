package CUI;

abstract class Piece {
    protected String colour;
    protected String type;

    protected int xLoc;
    protected int yLoc;

    protected int previousCol;

    protected int previousRow;

    public int getPreviousCol() {
        return previousCol;
    }

    public void setPreviousCol(int previousCol) {
        this.previousCol = previousCol;
    }

    public int getPreviousRow() {
        return previousRow;
    }

    public void setPreviousRow(int previousRow) {
        this.previousRow = previousRow;
    }

    public int getxLoc() {
        return xLoc;
    }

    public void setxLoc(int xLoc) {
        this.xLoc = xLoc;
    }

    public int getyLoc() {
        return yLoc;
    }

    public void setyLoc(int yLoc) {
        this.yLoc = yLoc;
    }

    public Piece(String colour, String type) {
        this.colour = colour;
        this.type = type;
    }

    public String getColour() {
        return colour;
    }

    public String getType() {
        return type;
    }


    public abstract boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard);

    public abstract boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard);

    public abstract void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard);

    protected void canCaptureRight(int sourceRow, int sourceCol, ChessTile[][] chessboard) {
        if (sourceRow < 7) {
            if (colour.equals("white")) {
                chessboard[sourceRow][sourceCol].setCanWhiteCapture(true);
            } else {
                chessboard[sourceRow][sourceCol].setCanBlackCapture(true);
            }
            canCaptureRight(sourceRow + 1, sourceCol, chessboard);
        }
    }

    protected void canCaptureLeft(int sourceRow, int sourceCol, ChessTile[][] chessboard) {
        if (sourceRow >= 0) {
            if (colour.equals("white")) {
                chessboard[sourceRow][sourceCol].setCanWhiteCapture(true);
            } else {
                chessboard[sourceRow][sourceCol].setCanBlackCapture(true);
            }
            if (!chessboard[sourceRow][sourceCol].isTileFilled()) {
                canCaptureLeft(sourceRow - 1, sourceCol, chessboard);
            }
        }
    }

    protected void canCaptureDown(int sourceRow, int sourceCol, ChessTile[][] chessboard) {
        if (sourceCol < 8) {
            if (colour.equals("white")) {
                chessboard[sourceRow][sourceCol].setCanWhiteCapture(true);
            } else {
                chessboard[sourceRow][sourceCol].setCanBlackCapture(true);
            }
            if (!chessboard[sourceRow][sourceCol].isTileFilled()) {
                canCaptureDown(sourceRow, sourceCol + 1, chessboard);
            }
        }
    }

    protected void canCaptureUp(int sourceRow, int sourceCol, ChessTile[][] chessboard) {
        if (sourceCol >= 0) {
            if (colour.equals("white")) {
                chessboard[sourceRow][sourceCol].setCanWhiteCapture(true);
            } else {
                chessboard[sourceRow][sourceCol].setCanBlackCapture(true);
            }
            if (!chessboard[sourceRow][sourceCol].isTileFilled()) {
                canCaptureUp(sourceRow, sourceCol - 1, chessboard);
            }
        }
    }

    protected void canCaptureUpRight(int sourceRow, int sourceCol, ChessTile[][] chessboard) {
        if (sourceCol < 7 && sourceRow > 0) {
            if (colour.equals("white")) {
                chessboard[sourceRow][sourceCol].setCanWhiteCapture(true);
            } else {
                chessboard[sourceRow][sourceCol].setCanBlackCapture(true);
            }
            if (!chessboard[sourceRow][sourceCol].isTileFilled()) {
                canCaptureUpRight(sourceRow - 1, sourceCol + 1, chessboard);
            }
        }
    }

    protected void canCaptureUpLeft(int sourceRow, int sourceCol, ChessTile[][] chessboard) {
        if (sourceCol > 0 && sourceRow > 0) {
            if (colour.equals("white")) {
                chessboard[sourceRow][sourceCol].setCanWhiteCapture(true);
            } else {
                chessboard[sourceRow][sourceCol].setCanBlackCapture(true);
            }
            if (!chessboard[sourceRow][sourceCol].isTileFilled()) {
                canCaptureUpLeft(sourceRow - 1, sourceCol - 1, chessboard);
            }
        }
    }

    protected void canCaptureDownRight(int sourceRow, int sourceCol, ChessTile[][] chessboard) {
        if (sourceCol < 7 && sourceRow < 7) {
            if (colour.equals("white")) {
                chessboard[sourceRow][sourceCol].setCanWhiteCapture(true);
            } else {
                chessboard[sourceRow][sourceCol].setCanBlackCapture(true);
            }
            if (!chessboard[sourceRow][sourceCol].isTileFilled()) {
                canCaptureDownRight(sourceRow + 1, sourceCol + 1, chessboard);
            }
        }
    }

    protected void canCaptureDownLeft(int sourceRow, int sourceCol, ChessTile[][] chessboard) {
        if (sourceCol > 0 && sourceRow < 7) {
            if (colour.equals("white")) {
                chessboard[sourceRow][sourceCol].setCanWhiteCapture(true);
            } else {
                chessboard[sourceRow][sourceCol].setCanBlackCapture(true);
            }
            if (!chessboard[sourceRow][sourceCol].isTileFilled()) {
                canCaptureDownLeft(sourceRow + 1, sourceCol - 1, chessboard);
            }
        }
    }

    protected boolean canMoveHorizontal(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        if (sourceRow != destRow) {
            return false; // The movement should be in the same row
        }

        int colStep;
        if (destCol > sourceCol) {
            colStep = 1;
        } else {
            colStep = -1;
        }

        int col = sourceCol + colStep;
        while (col != destCol) {
            if (chessboard[sourceRow][col].isTileFilled()) {
                return false;
            }
            col += colStep;
        }

        return true;
    }


    protected boolean canMoveVertical(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        if (sourceCol != destCol) {
            return false; // The movement should be in the same column
        }

        int rowStep;
        if (destRow > sourceRow) {
            rowStep = 1;
        } else {
            rowStep = -1;
        }

        int row = sourceRow + rowStep;
        while (row != destRow) {
            if (chessboard[row][sourceCol].isTileFilled()) {
                return false;
            }
            row += rowStep;
        }

        return true;
    }


    protected boolean canMoveDiagonal(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        int rowStep;
        int colStep;

        if (destRow > sourceRow) {
            rowStep = 1;
        } else {
            rowStep = -1;
        }

        if (destCol > sourceCol) {
            colStep = 1;
        } else {
            colStep = -1;
        }

        int row = sourceRow + rowStep;
        int col = sourceCol + colStep;

        while (row != destRow && col != destCol) {
            if (chessboard[row][col].isTileFilled()) {
                return false;
            }
            row += rowStep;
            col += colStep;
        }

        return row == destRow && col == destCol;
    }


}
