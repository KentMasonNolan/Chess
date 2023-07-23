package CUI;

import java.util.Scanner;

public class Game {
    private static final int BOARD_SIZE = 8;
    ChessTile[][] chessboard = createEmptyChessboard();
    private GameState gameState = new GameState();

    private boolean playerAbort = false;


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        GameState gameState = new GameState();
        Game game = new Game();
        game.start();

        System.out.println("Welcome to the game of chess.\n\n" + "This is a two-player game with no AI or game engine, so you are expected to play two-player or play both sides.\n" + "The expected inputs are the square you want to move followed by the destination square. e.g. C2 C4.\n" + "At any point in this game, you can type 'EXIT' to quit.\n\n" + "You are playing black. Good luck.\n");


        while (!gameState.isCheckmate() && !game.playerAbort) {
            game.drawChessboard(game.chessboard);
            System.out.println("\nIt is " + gameState.currentPlayer + "'s turn. Please input your command.");

            try {
                String userInputCommand = input.nextLine();
                game.userInput(userInputCommand, gameState);
            } catch (Exception e) {
                System.out.println("Please input a valid command. e.g. C2 C4");
            }

        }


    }

    private void start() {
        setupInitialPieces(chessboard);
//        drawChessboard(chessboard);
    }

    private ChessTile[][] createEmptyChessboard() {
        ChessTile[][] chessboard = new ChessTile[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                chessboard[row][col] = new ChessTile(row, col);
            }
        }
        return chessboard;
    }

    private void setupInitialPieces(ChessTile[][] chessboard) {
        String[] colours = {"white", "black"};
        for (String colour : colours) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                chessboard[1][col].setPiece(new Pawn("white", 1, col));
            }
            for (int col = 0; col < BOARD_SIZE; col++) {
                chessboard[6][col].setPiece(new Pawn("black", 6, col)); // Filling the second-to-last row as well
            }
            int kingRow;
            if (colour.equals("black")) {
                kingRow = BOARD_SIZE - 1;
            } else {
                kingRow = 0;
            }
            chessboard[kingRow][4].setPiece(new King(colour, kingRow, 4));
        }
    }

    private void drawChessboard(ChessTile[][] chessboard) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print((row + 1) + " "); // Row label
            for (int col = 0; col < BOARD_SIZE; col++) {
                ChessTile tile = chessboard[row][col];
                if (tile.isTileFilled()) {
                    // Print the piece symbol based on the piece type
                    System.out.print(getPieceSymbol(tile.getPiece()) + " ");
                } else {
                    System.out.print("-  "); // Empty tile symbol
                }
            }
            System.out.println();
        }
        System.out.println("  h  g  f  e  d  c  b  a"); // Column labels
    }

    private void userInput(String input, GameState gameState) {


        try {
            // expected input to be something like "C4 C5"
            String[] squares = input.split(" ");
            if (squares.length != 2) {
                throw new IllegalArgumentException("Invalid input. Please enter two squares separated by a space.");
            }

            String sourceSquare = squares[0].toUpperCase();
            String destSquare = squares[1].toUpperCase();

            int sourceRow = Character.getNumericValue(sourceSquare.charAt(1)) - 1;
            int sourceCol = letterToNumber(sourceSquare.charAt(0));
            int destRow = Character.getNumericValue(destSquare.charAt(1)) - 1;
            int destCol = letterToNumber(destSquare.charAt(0));

            if (chessboard[destRow][destCol].isTileFilled()) {
                capturePiece(sourceRow, sourceCol, destRow, destCol, gameState);
            } else {
                movePiece(sourceRow, sourceCol, destRow, destCol, gameState);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred in the userInput method. Please try again.");
        }
    }


    private void movePiece(int sourceRow, int sourceCol, int destRow, int destCol, GameState gameState) {

        Piece piece = chessboard[sourceRow][sourceCol].getPiece();

        if (piece != null && piece.getcolour().equals(gameState.currentPlayer)) {
            if (piece.isValidMove(sourceRow, sourceCol, destRow, destCol, chessboard)) {
                chessboard[sourceRow][sourceCol].removePiece();
                chessboard[destRow][destCol].setPiece(piece);

                if (piece instanceof Pawn) {
                    ((Pawn) piece).setFirstMove(false);
                }

                gameState.switchPlayer();

                // TODO: check for captures, check/checkmate

            } else {
                System.out.println("Invalid move. Please try again.");
            }
        } else {
            System.out.println("No piece found or it's not your turn. Please try again.");
        }
    }

    private void capturePiece(int sourceRow, int sourceCol, int destRow, int destCol, GameState gameState) {

        Piece piece = chessboard[sourceRow][sourceCol].getPiece();
        Piece capturedPiece = chessboard[destRow][destCol].getPiece();


        if (piece != null && capturedPiece != null && piece.getcolour().equals(gameState.currentPlayer)) {
            if (piece.isValidCapture(sourceRow, sourceCol, destRow, destCol, chessboard)) {

                chessboard[destRow][destCol].setPreviousPiece(capturedPiece);
                chessboard[destRow][destCol].removePiece();

                piece.setPreviousCol(sourceCol);
                piece.setPreviousRow(sourceRow);
                chessboard[sourceRow][sourceCol].removePiece();
                chessboard[destRow][destCol].setPiece(piece);

                //todo check for checkmate here

                gameState.switchPlayer();
            }
        }

    }

    private int letterToNumber(char inputLetter) {

        int outputInt = -1; // allows for error checking.

        if (inputLetter == 'A') {
            outputInt = 7;
        }
        if (inputLetter == 'B') {
            outputInt = 6;
        }
        if (inputLetter == 'C') {
            outputInt = 5;
        }
        if (inputLetter == 'D') {
            outputInt = 4;
        }
        if (inputLetter == 'E') {
            outputInt = 3;
        }
        if (inputLetter == 'F') {
            outputInt = 2;
        }
        if (inputLetter == 'G') {
            outputInt = 1;
        }
        if (inputLetter == 'H') {
            outputInt = 0;
        }

        return outputInt;
    }


    private String getPieceSymbol(Piece piece) {
        // not sure if I need this

        String colourPrefix;
        if (piece.getcolour().equals("white")) {
            colourPrefix = "w";
        } else {
            colourPrefix = "b";
        }

        String pieceType = piece.getType().substring(0, 1).toUpperCase();


        return colourPrefix + pieceType;
    }

    private void clearBlackCaptureflag(ChessTile[][] chessboard) {
        //TODO loop through all tiles and set flag to false
    }

    private void clearWhiteCaptureflag(ChessTile[][] chessboard) {

        //TODO loop through all tiles and set flag to false
    }

    private void setBlackCaptureflag(ChessTile[][] chessboard) {
        //TODO loop through all tiles if they have a piece, check where they can capture and set flag
    }

    private void setWhiteCaptureflag(ChessTile[][] chessboard) {

        //TODO loop through all tiles if they have a piece, check where they can capture and set flag
    }

}

