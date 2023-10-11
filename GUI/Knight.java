package GUI;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Knight extends Piece implements Serializable {

    protected Knight(String colour, int row, int col) {
        super(colour, "NKnight");
        this.pieceRow = row;
        this.pieceCol = col;
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
    public List<ChessTile> getValidMoves(ChessTile[][] chessboard) {
        return null;
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
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (colour.equals("white")) {

            if (sourceRow - 2 >= 0) {
                if (sourceCol - 1 >= 0) {
                    chessboard[sourceRow - 2][sourceCol - 1].setCanWhiteCapture(true);
                    if (chessboard[sourceRow - 2][sourceCol - 1].isTileFilled() && chessboard[sourceRow - 2][sourceCol - 1].getPiece().getColour().equals("black")) {
                        gameState.blackCapturablePieces.add(chessboard[sourceRow - 2][sourceCol - 1].getPiece());
                        if (chessboard[sourceRow - 2][sourceCol - 1].getPiece().getType().equals("King")) {
                            this.isAttackingOpponentsKing = true;
                        }
                    }
                }
                if (sourceCol + 1 <= 7) {
                    chessboard[sourceRow - 2][sourceCol + 1].setCanWhiteCapture(true);
                    if (chessboard[sourceRow - 2][sourceCol + 1].isTileFilled() && chessboard[sourceRow - 2][sourceCol + 1].getPiece().getColour().equals("black")) {
                        gameState.blackCapturablePieces.add(chessboard[sourceRow - 2][sourceCol + 1].getPiece());
                        if (chessboard[sourceRow - 2][sourceCol + 1].getPiece().getType().equals("King")) {
                            this.isAttackingOpponentsKing = true;
                        }
                    }
                }
            }

            if (sourceRow + 2 <= 7) {
                if (sourceCol - 1 >= 0) {
                    chessboard[sourceRow + 2][sourceCol - 1].setCanWhiteCapture(true);
                    if (chessboard[sourceRow + 2][sourceCol - 1].isTileFilled() && chessboard[sourceRow + 2][sourceCol - 1].getPiece().getColour().equals("black")) {
                        gameState.blackCapturablePieces.add(chessboard[sourceRow + 2][sourceCol - 1].getPiece());
                        if (chessboard[sourceRow + 2][sourceCol - 1].getPiece().getType().equals("King")) {
                            this.isAttackingOpponentsKing = true;
                        }
                    }
                }
                if (sourceCol + 1 <= 7) {
                    chessboard[sourceRow + 2][sourceCol + 1].setCanWhiteCapture(true);
                    if (chessboard[sourceRow + 2][sourceCol + 1].isTileFilled() && chessboard[sourceRow + 2][sourceCol + 1].getPiece().getColour().equals("black")) {
                        gameState.blackCapturablePieces.add(chessboard[sourceRow + 2][sourceCol + 1].getPiece());
                        if (chessboard[sourceRow + 2][sourceCol + 1].getPiece().getType().equals("King")) {
                            this.isAttackingOpponentsKing = true;
                        }
                    }

                    if (sourceRow - 2 >= 0) {
                        if (sourceCol - 1 >= 0) {
                            chessboard[sourceRow - 2][sourceCol - 1].setCanWhiteCapture(true);
                            if (chessboard[sourceRow - 2][sourceCol - 1].isTileFilled() && chessboard[sourceRow - 2][sourceCol - 1].getPiece().getColour().equals("black")) {
                                gameState.blackCapturablePieces.add(chessboard[sourceRow - 2][sourceCol - 1].getPiece());
                                if (chessboard[sourceRow - 2][sourceCol - 1].getPiece().getType().equals("King")) {
                                    this.isAttackingOpponentsKing = true;
                                }
                            }
                        }
                        if (sourceCol + 1 <= 7) {
                            chessboard[sourceRow - 2][sourceCol + 1].setCanWhiteCapture(true);
                            if (chessboard[sourceRow - 2][sourceCol + 1].isTileFilled() && chessboard[sourceRow - 2][sourceCol + 1].getPiece().getColour().equals("black")) {
                                gameState.blackCapturablePieces.add(chessboard[sourceRow - 2][sourceCol + 1].getPiece());
                                if (chessboard[sourceRow - 2][sourceCol + 1].getPiece().getType().equals("King")) {
                                    this.isAttackingOpponentsKing = true;
                                }
                            }
                        }
                    }

                    if (sourceRow + 2 <= 7) {
                        if (sourceCol - 1 >= 0) {
                            chessboard[sourceRow + 2][sourceCol - 1].setCanWhiteCapture(true);
                            if (chessboard[sourceRow + 2][sourceCol - 1].isTileFilled() && chessboard[sourceRow + 2][sourceCol - 1].getPiece().getColour().equals("black")) {
                                gameState.blackCapturablePieces.add(chessboard[sourceRow + 2][sourceCol - 1].getPiece());
                                if (chessboard[sourceRow + 2][sourceCol - 1].getPiece().getType().equals("King")) {
                                    this.isAttackingOpponentsKing = true;
                                }
                            }
                        }
                        if (sourceCol + 1 <= 7) {
                            chessboard[sourceRow + 2][sourceCol + 1].setCanWhiteCapture(true);
                            if (chessboard[sourceRow + 2][sourceCol + 1].isTileFilled() && chessboard[sourceRow + 2][sourceCol + 1].getPiece().getColour().equals("black")) {
                                gameState.blackCapturablePieces.add(chessboard[sourceRow + 2][sourceCol + 1].getPiece());
                                if (chessboard[sourceRow + 2][sourceCol + 1].getPiece().getType().equals("King")) {
                                    this.isAttackingOpponentsKing = true;
                                }
                            }
                        }
                    }
                }
            }

        } else { // colour must be black

            if (sourceRow - 2 >= 0) {
                if (sourceCol - 1 >= 0) {
                    chessboard[sourceRow - 2][sourceCol - 1].setCanBlackCapture(true);
                    if (chessboard[sourceRow - 2][sourceCol - 1].isTileFilled() && chessboard[sourceRow - 2][sourceCol - 1].getPiece().getColour().equals("white")) {
                        gameState.whiteCapturablePieces.add(chessboard[sourceRow - 2][sourceCol - 1].getPiece());
                        if (chessboard[sourceRow - 2][sourceCol - 1].getPiece().getType().equals("King")) {
                            this.isAttackingOpponentsKing = true;
                        }
                    }
                }
                if (sourceCol + 1 <= 7) {
                    chessboard[sourceRow - 2][sourceCol + 1].setCanBlackCapture(true);
                    if (chessboard[sourceRow - 2][sourceCol + 1].isTileFilled() && chessboard[sourceRow - 2][sourceCol + 1].getPiece().getColour().equals("white")) {
                        gameState.whiteCapturablePieces.add(chessboard[sourceRow - 2][sourceCol + 1].getPiece());
                        if (chessboard[sourceRow - 2][sourceCol + 1].getPiece().getType().equals("King")) {
                            this.isAttackingOpponentsKing = true;
                        }
                    }
                }
            }

            if (sourceRow + 2 <= 7) {
                if (sourceCol - 1 >= 0) {
                    chessboard[sourceRow + 2][sourceCol - 1].setCanBlackCapture(true);
                    if (chessboard[sourceRow + 2][sourceCol - 1].isTileFilled() && chessboard[sourceRow + 2][sourceCol - 1].getPiece().getColour().equals("white")) {
                        gameState.whiteCapturablePieces.add(chessboard[sourceRow + 2][sourceCol - 1].getPiece());
                        if (chessboard[sourceRow + 2][sourceCol - 1].getPiece().getType().equals("King")) {
                            this.isAttackingOpponentsKing = true;
                        }
                    }
                }
                if (sourceCol + 1 <= 7) {
                    chessboard[sourceRow + 2][sourceCol + 1].setCanBlackCapture(true);
                    if (chessboard[sourceRow + 2][sourceCol + 1].isTileFilled() && chessboard[sourceRow + 2][sourceCol + 1].getPiece().getColour().equals("white")) {
                        gameState.whiteCapturablePieces.add(chessboard[sourceRow + 2][sourceCol + 1].getPiece());
                        if (chessboard[sourceRow + 2][sourceCol + 1].getPiece().getType().equals("King")) {
                            this.isAttackingOpponentsKing = true;
                        }
                    }
                }
            }

            if (sourceCol - 2 >= 0) {
                if (sourceRow - 1 >= 0) {
                    chessboard[sourceRow - 1][sourceCol - 2].setCanBlackCapture(true);
                    if (chessboard[sourceRow - 1][sourceCol - 2].isTileFilled() && chessboard[sourceRow - 1][sourceCol - 2].getPiece().getColour().equals("white")) {
                        gameState.whiteCapturablePieces.add(chessboard[sourceRow - 1][sourceCol - 2].getPiece());
                        if (chessboard[sourceRow - 1][sourceCol - 2].getPiece().getType().equals("King")) {
                            this.isAttackingOpponentsKing = true;
                        }
                    }
                }
                if (sourceRow + 1 <= 7) {
                    chessboard[sourceRow + 1][sourceCol - 2].setCanBlackCapture(true);
                    if (chessboard[sourceRow + 1][sourceCol - 2].isTileFilled() && chessboard[sourceRow + 1][sourceCol - 2].getPiece().getColour().equals("white")) {
                        gameState.whiteCapturablePieces.add(chessboard[sourceRow + 1][sourceCol - 2].getPiece());
                        if (chessboard[sourceRow + 1][sourceCol - 2].getPiece().getType().equals("King")) {
                            this.isAttackingOpponentsKing = true;
                        }
                    }
                }
            }

            if (sourceCol + 2 <= 7) {
                if (sourceRow - 1 >= 0) {
                    chessboard[sourceRow - 1][sourceCol + 2].setCanBlackCapture(true);
                    if (chessboard[sourceRow - 1][sourceCol + 2].isTileFilled() && chessboard[sourceRow - 1][sourceCol + 2].getPiece().getColour().equals("white")) {
                        gameState.whiteCapturablePieces.add(chessboard[sourceRow - 1][sourceCol + 2].getPiece());
                        if (chessboard[sourceRow - 1][sourceCol + 2].getPiece().getType().equals("King")) {
                            this.isAttackingOpponentsKing = true;
                        }
                    }
                }
                if (sourceRow + 1 <= 7) {
                    chessboard[sourceRow + 1][sourceCol + 2].setCanBlackCapture(true);
                    if (chessboard[sourceRow + 1][sourceCol + 2].isTileFilled() && chessboard[sourceRow + 1][sourceCol + 2].getPiece().getColour().equals("white")) {
                        gameState.whiteCapturablePieces.add(chessboard[sourceRow + 1][sourceCol + 2].getPiece());
                        if (chessboard[sourceRow + 1][sourceCol + 2].getPiece().getType().equals("King")) {
                            this.isAttackingOpponentsKing = true;
                        }
                    }
                }
            }
        }
    }
}
