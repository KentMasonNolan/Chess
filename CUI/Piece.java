package CUI;

import java.io.Serializable;
import java.util.Objects;

abstract class Piece implements Serializable {
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

    public abstract void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState);

    protected void canCaptureDown(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceRow + 1 < 8) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow + 1][sourceCol].isTileFilled()) {
                    canCaptureDown(sourceRow + 1, sourceCol, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow + 1][sourceCol].getPiece().getColour())) {
                    chessboard[sourceRow + 1][sourceCol].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow + 1][sourceCol].getPiece());
                }
            } else {
                if (!chessboard[sourceRow + 1][sourceCol].isTileFilled()) {
                    canCaptureDown(sourceRow + 1, sourceCol, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow + 1][sourceCol].getPiece().getColour())) {
                    chessboard[sourceRow + 1][sourceCol].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow + 1][sourceCol].getPiece());
                }
            }
        }
    }

    protected void canCaptureUp(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceRow - 1 >= 0) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow - 1][sourceCol].isTileFilled()) {
                    canCaptureUp(sourceRow - 1, sourceCol, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow - 1][sourceCol].getPiece().getColour())) {
                    chessboard[sourceRow - 1][sourceCol].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow - 1][sourceCol].getPiece());
                }
            } else {
                if (!chessboard[sourceRow - 1][sourceCol].isTileFilled()) {
                    canCaptureUp(sourceRow - 1, sourceCol, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow - 1][sourceCol].getPiece().getColour())) {
                    chessboard[sourceRow - 1][sourceCol].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow - 1][sourceCol].getPiece());
                }
            }
        }
    }

    protected void canCaptureRight(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceCol + 1 < 8) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow][sourceCol + 1].isTileFilled()) {
                    canCaptureRight(sourceRow, sourceCol + 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow][sourceCol + 1].getPiece().getColour())) {
                    chessboard[sourceRow][sourceCol + 1].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow][sourceCol + 1].getPiece());
                }
            } else {
                if (!chessboard[sourceRow][sourceCol + 1].isTileFilled()) {
                    canCaptureRight(sourceRow, sourceCol + 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow][sourceCol + 1].getPiece().getColour())) {
                    chessboard[sourceRow][sourceCol + 1].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow][sourceCol + 1].getPiece());
                }
            }
        }
    }

    protected void canCaptureLeft(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceCol - 1 >= 0) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow][sourceCol - 1].isTileFilled()) {
                    canCaptureLeft(sourceRow, sourceCol - 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow][sourceCol - 1].getPiece().getColour())) {
                    chessboard[sourceRow][sourceCol - 1].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow][sourceCol - 1].getPiece());
                }
            } else {
                if (!chessboard[sourceRow][sourceCol - 1].isTileFilled()) {
                    canCaptureLeft(sourceRow, sourceCol - 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow][sourceCol - 1].getPiece().getColour())) {
                    chessboard[sourceRow][sourceCol - 1].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow][sourceCol - 1].getPiece());
                }
            }
        }
    }

    protected void canCaptureUpRight(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceCol < 7 && sourceRow > 0) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow - 1][sourceCol + 1].isTileFilled()) {
                    canCaptureUpRight(sourceRow - 1, sourceCol + 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow - 1][sourceCol + 1].getPiece().getColour())) {
                    chessboard[sourceRow - 1][sourceCol + 1].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow - 1][sourceCol + 1].getPiece());
                }
            } else {
                if (!chessboard[sourceRow - 1][sourceCol + 1].isTileFilled()) {
                    canCaptureUpRight(sourceRow - 1, sourceCol + 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow - 1][sourceCol + 1].getPiece().getColour())) {
                    chessboard[sourceRow - 1][sourceCol + 1].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow - 1][sourceCol + 1].getPiece());
                }
            }
        }
    }

    protected void canCaptureUpLeft(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceCol > 0 && sourceRow > 0) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow - 1][sourceCol - 1].isTileFilled()) {
                    canCaptureUpLeft(sourceRow - 1, sourceCol - 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow - 1][sourceCol - 1].getPiece().getColour())) {
                    chessboard[sourceRow - 1][sourceCol - 1].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow - 1][sourceCol - 1].getPiece());
                }
            } else {
                if (!chessboard[sourceRow - 1][sourceCol - 1].isTileFilled()) {
                    canCaptureUpLeft(sourceRow - 1, sourceCol - 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow - 1][sourceCol - 1].getPiece().getColour())) {
                    chessboard[sourceRow - 1][sourceCol - 1].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow - 1][sourceCol - 1].getPiece());
                }
            }
        }
    }

    protected void canCaptureDownRight(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceCol < 7 && sourceRow < 7) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow + 1][sourceCol + 1].isTileFilled()) {
                    canCaptureDownRight(sourceRow + 1, sourceCol + 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow + 1][sourceCol + 1].getPiece().getColour())) {
                    chessboard[sourceRow + 1][sourceCol + 1].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow + 1][sourceCol + 1].getPiece());
                }
            } else {
                if (!chessboard[sourceRow + 1][sourceCol + 1].isTileFilled()) {
                    canCaptureDownRight(sourceRow + 1, sourceCol + 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow + 1][sourceCol + 1].getPiece().getColour())) {
                    chessboard[sourceRow + 1][sourceCol + 1].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow + 1][sourceCol + 1].getPiece());
                }
            }
        }
    }

    protected void canCaptureDownLeft(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceCol > 0 && sourceRow < 7) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow + 1][sourceCol - 1].isTileFilled()) {
                    canCaptureDownLeft(sourceRow + 1, sourceCol - 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow + 1][sourceCol - 1].getPiece().getColour())) {
                    chessboard[sourceRow + 1][sourceCol - 1].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow + 1][sourceCol - 1].getPiece());
                }
            } else {
                if (!chessboard[sourceRow + 1][sourceCol - 1].isTileFilled()) {
                    canCaptureDownLeft(sourceRow + 1, sourceCol - 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow + 1][sourceCol - 1].getPiece().getColour())) {
                    chessboard[sourceRow + 1][sourceCol - 1].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow + 1][sourceCol - 1].getPiece());
                }
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
