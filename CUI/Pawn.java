package CUI;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Pawn extends Piece implements Serializable {

    private boolean isFirstMove = true;

    private boolean justMovedTwoSquares = false;

    public boolean isJustMovedTwoSquares() {
        return justMovedTwoSquares;
    }

    public void setJustMovedTwoSquares(boolean justMovedTwoSquares) {
        this.justMovedTwoSquares = justMovedTwoSquares;
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        // check if the destination tile is occupied
        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();

        // check if the tile above the destination is occupied
        boolean isSquareAboveOccupied = chessboard[sourceRow - 1][destCol].isTileFilled();

        // check if the tile below the destination is occupied
        boolean isSquareBelowOccupied = chessboard[sourceRow + 1][destCol].isTileFilled();

        // check if the destination is within the bounds of the chessboard
        boolean isDestinationInBounds = (destCol >= 0 && destCol <= 8) && (destRow >= 0 && destRow <= 8);

        if (Objects.equals(this.getColour(), "black")) {
            // if the piece is a black pawn
            if (isFirstMove && (destRow == sourceRow - 1 || destRow == sourceRow - 2) && !isDestinationOccupied && isDestinationInBounds && !isSquareAboveOccupied) {
                if (destRow == 0) {
                    // promote pawn if it reaches the opposite end
                    promotePawn(sourceRow, sourceCol, destRow, destCol, chessboard);
                }
                return true;
            } else if (!isFirstMove && destRow == sourceRow - 1 && !isDestinationOccupied && isDestinationInBounds) {
                if (destRow == 0) {
                    // promote pawn if it reaches the opposite end
                    promotePawn(sourceRow, sourceCol, destRow, destCol, chessboard);
                }
                return true;
            }
        } else {
            // if the piece is a white pawn
            if (isFirstMove && (destRow == sourceRow + 1 || destRow == sourceRow + 2) && !isDestinationOccupied && isDestinationInBounds && !isSquareBelowOccupied) {
                if (destRow == 7) {
                    // promote pawn if it reaches the opposite end
                    promotePawn(sourceRow, sourceCol, destRow, destCol, chessboard);
                }
                return true;
            } else if (!isFirstMove && destRow == sourceRow + 1 && !isDestinationOccupied && isDestinationInBounds) {
                if (destRow == 7) {
                    // promote pawn if it reaches the opposite end
                    promotePawn(sourceRow, sourceCol, destRow, destCol, chessboard);
                }
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
        // check if the destination tile is occupied
        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();

        // check if the piece at the destination is of opponent's color
        boolean isOpponentColour = (!Objects.equals(this.colour, chessboard[destRow][destCol].getPiece().colour));

        if (isDestinationOccupied && isOpponentColour) {
            if (colour.equals("black")) {
                // check valid capture for black pawn
                if (destCol > 0 && destCol < 8 && (destRow == sourceRow + 1 || destRow == sourceRow - 1) && (destCol == sourceCol - 1 || destCol == sourceCol + 1)) {
                    return true;
                }
            } else if (colour.equals("white")) {
                // check valid capture for white pawn
                if (destCol > 0 && destCol < 8 && (destRow == sourceRow - 1 || destRow == sourceRow + 1) && (destCol == sourceCol - 1 || destCol == sourceCol + 1)) {
                    return true;
                }
            }
        }
        System.out.println("invalid capture");
        return false;
    }


    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard, GameState gameState) {
        if (colour.equals("black")) {
            if (sourceRow > 0 && sourceCol > 0) {
                chessboard[sourceRow - 1][sourceCol - 1].setCanBlackCapture(true); // capture down left
            }
            if (sourceRow > 0 && sourceCol < 7) {
                chessboard[sourceRow - 1][sourceCol + 1].setCanBlackCapture(true); // capture down right
            }
        } else if (colour.equals("white")) {
            if (sourceRow < 7 && sourceCol > 0) {
                chessboard[sourceRow + 1][sourceCol - 1].setCanWhiteCapture(true); // capture up left
            }
            if (sourceRow < 7 && sourceCol < 7) {
                chessboard[sourceRow + 1][sourceCol + 1].setCanWhiteCapture(true); // capture up right
            }
        }
    }


    private void promotePawn(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        // Prompt the user to choose a promotion piece (you can modify this part as needed)
        System.out.println("Pawn promotion: Choose a promotion piece (Q/R/N/B):");
        Scanner scanner = new Scanner(System.in);
        String promotionChoice = scanner.nextLine().toUpperCase();

        Piece promotionPiece;

        // Create the appropriate piece based on the user's choice
        switch (promotionChoice) {
            case "Q":
                promotionPiece = new Queen(this.getColour(), destRow, destCol);
                break;
            case "R":
                promotionPiece = new Rook(this.getColour(), destRow, destCol);
                break;
            case "N":
                promotionPiece = new Knight(this.getColour(), destRow, destCol);
                break;
            case "B":
                promotionPiece = new Bishop(this.getColour(), destRow, destCol);
                break;
            default:
                // Default to promoting to a Queen
                promotionPiece = new Queen(this.getColour(), destRow, destCol);
                break;
        }

        // Update the chessboard with the promoted piece
        chessboard[destRow][destCol].setPiece(promotionPiece);
        chessboard[sourceRow][sourceCol].removePiece();
    }

    protected Pawn(String colour, int row, int col) {
        super(colour, "Pawn");
        this.pieceRow = row;
        this.pieceCol = col;
    }
}
