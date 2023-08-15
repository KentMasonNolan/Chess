package CUI;

import java.util.Objects;
import java.util.Scanner;

public class Pawn extends Piece {

    private boolean isFirstMove = true;

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {
        boolean isDestinationOccupied = chessboard[destRow][destCol].isTileFilled();
        boolean isSquareAboveOccupied = chessboard[destRow - 1][destCol].isTileFilled();
        boolean isSquareBelowOccupied = chessboard[destRow + 1][destCol].isTileFilled();
        boolean isDestinationInBounds = (destCol >= 0 && destCol <= 8) && (destRow >= 0 && destRow <= 8);

        if (Objects.equals(this.getColour(), "black")) {
            if (isFirstMove && (destRow == sourceRow - 1 || destRow == sourceRow - 2) && !isDestinationOccupied && isDestinationInBounds && !isSquareAboveOccupied) {
                if (destRow == 0) {
                    promotePawn(sourceRow, sourceCol, destRow, destCol, chessboard);
                }
                return true;
            } else if (!isFirstMove && destRow == sourceRow - 1 && !isDestinationOccupied && isDestinationInBounds) {
                if (destRow == 0) {
                    promotePawn(sourceRow, sourceCol, destRow, destCol, chessboard);
                }
                return true;
            }
        } else {
            if (isFirstMove && (destRow == sourceRow + 1 || destRow == sourceRow + 2) && !isDestinationOccupied && isDestinationInBounds && !isSquareBelowOccupied) {
                if (destRow == 7) {
                    promotePawn(sourceRow, sourceCol, destRow, destCol, chessboard);
                }
                return true;
            } else if (!isFirstMove && destRow == sourceRow + 1 && !isDestinationOccupied && isDestinationInBounds) {
                if (destRow == 7) {
                    promotePawn(sourceRow, sourceCol, destRow, destCol, chessboard);
                }
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
            if (colour.equals("black")) {
                if (destCol > 0 && destCol < 8 && (destRow == sourceRow + 1 || destRow == sourceRow - 1) && (destCol == sourceCol - 1 || destCol == sourceCol + 1)) {
                    return true;
                }
            } else if (colour.equals("white")) {
                if (destCol > 0 && destCol < 8 && (destRow == sourceRow - 1 || destRow == sourceRow + 1) && (destCol == sourceCol - 1 || destCol == sourceCol + 1)) {
                    return true;
                }

            }
        }
        System.out.println("Invalid capture");
        return false;
    }

    @Override
    public void canCapture(int sourceRow, int sourceCol, ChessTile[][] chessboard) {
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

    protected Pawn(String colour, int xLoc, int yLoc) {
        super(colour, "Pawn");
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }
}
