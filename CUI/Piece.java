package CUI;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

abstract class Piece implements Serializable {
    protected String colour;
    protected String type;


    protected int pieceCol;
    protected int pieceRow;

    protected boolean isAttackingOpponentsKing = false;

    public boolean isAttackingOpponentsKing() {
        return isAttackingOpponentsKing;
    }

    public void setAttackingOpponentsKing(boolean attackingOpponentsKing) {
        isAttackingOpponentsKing = attackingOpponentsKing;
    }

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

    public int getPieceCol() {
        return pieceCol;
    }

    public boolean isFirstMove;

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    public void setPieceCol(int pieceCol) {
        this.pieceCol = pieceCol;
    }

    public int getPieceRow() {
        return pieceRow;
    }

    public void setPieceRow(int pieceRow) {
        this.pieceRow = pieceRow;
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

    public abstract List<ChessTile> getValidMoves(ChessTile[][] chessboard);

    public abstract boolean isValidCapture(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard);

    public abstract void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState);

    protected void canCaptureDown(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceRow + 1 < 8) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow + 1][sourceCol].isTileFilled()) {
                    chessboard[sourceRow + 1][sourceCol].setCanWhiteCapture(true);
                    canCaptureDown(sourceRow + 1, sourceCol, chessboard, gameState);
                } else if (chessboard[sourceRow + 1][sourceCol].getPiece().getColour().equals("black")) {
                    chessboard[sourceRow + 1][sourceCol].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow + 1][sourceCol].getPiece());
                    Piece targetPiece = chessboard[sourceRow + 1][sourceCol].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("black")) {
                        this.isAttackingOpponentsKing = true;
                    }

                }
            } else {
                canCaptureDown(sourceRow + 1, sourceCol, chessboard, gameState);
                if (!chessboard[sourceRow + 1][sourceCol].isTileFilled()) {
                    chessboard[sourceRow + 1][sourceCol].setCanBlackCapture(true);
                    canCaptureDown(sourceRow + 1, sourceCol, chessboard, gameState);
                } else if (chessboard[sourceRow + 1][sourceCol].getPiece().getColour().equals("white")) {
                    chessboard[sourceRow + 1][sourceCol].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow + 1][sourceCol].getPiece());
                    Piece targetPiece = chessboard[sourceRow + 1][sourceCol].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("white")) {
                        this.isAttackingOpponentsKing = true;
                    }
                }
            }
        }
    }

    protected void canCaptureUp(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceRow - 1 >= 0) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow - 1][sourceCol].isTileFilled()) {
                    chessboard[sourceRow-1][sourceCol].setCanWhiteCapture(true);
                    canCaptureUp(sourceRow - 1, sourceCol, chessboard, gameState);
                } else if (chessboard[sourceRow - 1][sourceCol].getPiece().getColour().equals("black")) {
                    chessboard[sourceRow - 1][sourceCol].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow - 1][sourceCol].getPiece());
                    Piece targetPiece = chessboard[sourceRow - 1][sourceCol].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("black")) {
                        this.isAttackingOpponentsKing = true;
                    }
                }
            } else {
                if (!chessboard[sourceRow - 1][sourceCol].isTileFilled()) {
                    chessboard[sourceRow - 1][sourceCol].setCanBlackCapture(true);
                    canCaptureUp(sourceRow - 1, sourceCol, chessboard, gameState);
                } else if (chessboard[sourceRow - 1][sourceCol].getPiece().getColour().equals("white")) {
                    chessboard[sourceRow - 1][sourceCol].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow - 1][sourceCol].getPiece());
                    Piece targetPiece = chessboard[sourceRow - 1][sourceCol].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("white")) {
                        this.isAttackingOpponentsKing = true;
                    }
                }
            }
        }
    }

    protected void canCaptureRight(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceCol + 1 < 8) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow][sourceCol + 1].isTileFilled()) {
                    chessboard[sourceRow][sourceCol + 1].setCanWhiteCapture(true);
                    canCaptureRight(sourceRow, sourceCol + 1, chessboard, gameState);
                } else if (chessboard[sourceRow][sourceCol+1].getPiece().getColour().equals("black")) {
                    chessboard[sourceRow][sourceCol + 1].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow][sourceCol + 1].getPiece());
                    if (sourceRow+1 < 8){
                    Piece targetPiece = chessboard[sourceRow + 1][sourceCol].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("black")) {
                        this.isAttackingOpponentsKing = true;
                    }
                    }
                }
            } else {
                if (!chessboard[sourceRow][sourceCol + 1].isTileFilled()) {
                    chessboard[sourceRow][sourceCol + 1].setCanBlackCapture(true);
                    canCaptureRight(sourceRow, sourceCol + 1, chessboard, gameState);
                } else if (chessboard[sourceRow][sourceCol+1].getPiece().getColour().equals("white")) {
                    chessboard[sourceRow][sourceCol + 1].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow][sourceCol + 1].getPiece());
                    Piece targetPiece = chessboard[sourceRow + 1][sourceCol].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("white")) {
                        this.isAttackingOpponentsKing = true;
                    }
                }
            }
        }
    }

    protected void canCaptureLeft(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceCol - 1 >= 0) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow][sourceCol - 1].isTileFilled()) {
                    chessboard[sourceRow][sourceCol-1].setCanWhiteCapture(true);
                    canCaptureLeft(sourceRow, sourceCol - 1, chessboard, gameState);
                } else if (chessboard[sourceRow][sourceCol-1].getPiece().getColour().equals("black")) {
                    chessboard[sourceRow][sourceCol - 1].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow][sourceCol - 1].getPiece());
                    Piece targetPiece = chessboard[sourceRow][sourceCol-1].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("black")) {
                        this.isAttackingOpponentsKing = true;
                    }
                }
            } else {
                if (!chessboard[sourceRow][sourceCol - 1].isTileFilled()) {
                    chessboard[sourceRow][sourceCol - 1].setCanBlackCapture(true);
                    canCaptureLeft(sourceRow, sourceCol - 1, chessboard, gameState);
                } else if (chessboard[sourceRow][sourceCol-1].getPiece().getColour().equals("white")) {
                    chessboard[sourceRow][sourceCol - 1].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow][sourceCol - 1].getPiece());
                    Piece targetPiece = chessboard[sourceRow][sourceCol-1].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("white")) {
                        this.isAttackingOpponentsKing = true;
                    }
                }
            }
        }
    }

    protected void canCaptureUpRight(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceCol < 7 && sourceRow > 0) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow - 1][sourceCol + 1].isTileFilled()) {
                    chessboard[sourceRow - 1][sourceCol + 1].setCanWhiteCapture(true);
                    canCaptureUpRight(sourceRow - 1, sourceCol + 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow - 1][sourceCol + 1].getPiece().getColour())) {
                    chessboard[sourceRow - 1][sourceCol + 1].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow - 1][sourceCol + 1].getPiece());
                    Piece targetPiece = chessboard[sourceRow - 1][sourceCol + 1].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("black")) {
                        this.isAttackingOpponentsKing = true;
                    }
                }
            } else {
                if (!chessboard[sourceRow - 1][sourceCol + 1].isTileFilled()) {
                    chessboard[sourceRow - 1][sourceCol + 1].setCanBlackCapture(true);
                    canCaptureUpRight(sourceRow - 1, sourceCol + 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow - 1][sourceCol + 1].getPiece().getColour())) {
                    chessboard[sourceRow - 1][sourceCol + 1].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow - 1][sourceCol + 1].getPiece());
                    Piece targetPiece = chessboard[sourceRow - 1][sourceCol + 1].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("white")) {
                        this.isAttackingOpponentsKing = true;
                    }
                }
            }
        }
    }

    protected void canCaptureUpLeft(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceCol > 0 && sourceRow > 0) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow - 1][sourceCol - 1].isTileFilled()) {
                    chessboard[sourceRow - 1][sourceCol - 1].setCanWhiteCapture(true);
                    canCaptureUpLeft(sourceRow - 1, sourceCol - 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow - 1][sourceCol - 1].getPiece().getColour())) {
                    chessboard[sourceRow - 1][sourceCol - 1].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow - 1][sourceCol - 1].getPiece());
                    Piece targetPiece = chessboard[sourceRow - 1][sourceCol - 1].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("black")) {
                        this.isAttackingOpponentsKing = true;
                    }
                }
            } else {
                if (!chessboard[sourceRow - 1][sourceCol - 1].isTileFilled()) {
                    chessboard[sourceRow - 1][sourceCol - 1].setCanBlackCapture(true);
                    canCaptureUpLeft(sourceRow - 1, sourceCol - 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow - 1][sourceCol - 1].getPiece().getColour())) {
                    chessboard[sourceRow - 1][sourceCol - 1].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow - 1][sourceCol - 1].getPiece());
                    Piece targetPiece = chessboard[sourceRow - 1][sourceCol - 1].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("white")) {
                        this.isAttackingOpponentsKing = true;
                    }
                }
            }
        }
    }

    protected void canCaptureDownRight(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceCol < 7 && sourceRow < 7) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow + 1][sourceCol + 1].isTileFilled()) {
                    chessboard[sourceRow + 1][sourceCol + 1].setCanWhiteCapture(true);
                    canCaptureDownRight(sourceRow + 1, sourceCol + 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow + 1][sourceCol + 1].getPiece().getColour())) {
                    chessboard[sourceRow + 1][sourceCol + 1].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow + 1][sourceCol + 1].getPiece());
                    Piece targetPiece = chessboard[sourceRow + 1][sourceCol + 1].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("black")) {
                        this.isAttackingOpponentsKing = true;
                    }
                }
            } else {
                if (!chessboard[sourceRow + 1][sourceCol + 1].isTileFilled()) {
                    chessboard[sourceRow + 1][sourceCol + 1].setCanBlackCapture(true);
                    canCaptureDownRight(sourceRow + 1, sourceCol + 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow + 1][sourceCol + 1].getPiece().getColour())) {
                    chessboard[sourceRow + 1][sourceCol + 1].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow + 1][sourceCol + 1].getPiece());
                    Piece targetPiece = chessboard[sourceRow + 1][sourceCol + 1].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("white")) {
                        this.isAttackingOpponentsKing = true;
                    }
                }
            }
        }
    }

    protected void canCaptureDownLeft(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (sourceCol > 0 && sourceRow < 7) {
            if (colour.equals("white")) {
                if (!chessboard[sourceRow + 1][sourceCol - 1].isTileFilled()) {
                    chessboard[sourceRow + 1][sourceCol - 1].setCanWhiteCapture(true);
                    canCaptureDownLeft(sourceRow + 1, sourceCol - 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow + 1][sourceCol - 1].getPiece().getColour())) {
                    chessboard[sourceRow + 1][sourceCol - 1].setCanWhiteCapture(true);
                    gameState.blackCapturablePieces.add(chessboard[sourceRow + 1][sourceCol - 1].getPiece());
                    Piece targetPiece = chessboard[sourceRow + 1][sourceCol - 1].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("black")) {
                        this.isAttackingOpponentsKing = true;
                    }
                }
            } else {
                if (!chessboard[sourceRow + 1][sourceCol - 1].isTileFilled()) {
                    chessboard[sourceRow + 1][sourceCol - 1].setCanBlackCapture(true);
                    canCaptureDownLeft(sourceRow + 1, sourceCol - 1, chessboard, gameState);
                } else if (!Objects.equals(this.colour, chessboard[sourceRow + 1][sourceCol - 1].getPiece().getColour())) {
                    chessboard[sourceRow + 1][sourceCol - 1].setCanBlackCapture(true);
                    gameState.whiteCapturablePieces.add(chessboard[sourceRow + 1][sourceCol - 1].getPiece());
                    Piece targetPiece = chessboard[sourceRow + 1][sourceCol - 1].getPiece();
                    if (targetPiece.type.equals("King") && targetPiece.colour.equals("white")) {
                        this.isAttackingOpponentsKing = true;
                    }
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

    public boolean hasValidMoveToEscape(ChessTile[][] chessboard) {
        return false;
    }

    public boolean isFirstMove() {
        return false;
    }

    public boolean isJustMovedTwoSquares() {
        return false;
    }
}
