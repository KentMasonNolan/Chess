package CUI;

import javax.swing.text.AttributeSet;
import java.util.Scanner;

public class Game {
    private static final int BOARD_SIZE = 8;
    ChessTile[][] chessboard = createEmptyChessboard();
    private GameState gameState;
    private String currentPlayerColor;

    private boolean playerAbort = false;



    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Game game = new Game();
        GameState gameState = new GameState();
        game.start();

        System.out.println("Welcome to the game of chess. " +
                "This is a two player game with no AI or game engine so you are expected to play " +
                "two player or play both sides. The expected inputs are the square you want to move " +
                "followed by the destination square. e.g. C2 C4. At any point in this game, you can type 'EXIT' to quit." +
                "Good luck.");

        while (!gameState.isCheckmate() && !game.playerAbort){
            System.out.println("It is " + gameState.currentPlayer + "'s turn. Please input your command.");

            try {
                String userInputCommand = input.nextLine();
                game.userInput(userInputCommand);
            } catch (Exception e){
                System.out.println("Please input a valid command. e.g. C2 C4");
            }

        }


    }

    private void start() {
        setupInitialPieces(chessboard);
        drawChessboard(chessboard);
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
        // For testing - set up the pawns on the second row of the chessboard
        String[] colors = {"black", "white"};
        for (String color : colors) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                chessboard[1][col].setPiece(new Pawn(color, 1, col));
                chessboard[6][col].setPiece(new Pawn(color, 6, col)); // Filling the second-to-last row as well
            }
            int kingRow;
            if (color.equals("black")) {
                kingRow = 0;
            } else {
                kingRow = BOARD_SIZE - 1;
            }
            chessboard[kingRow][4].setPiece(new King(color, kingRow, 4));
        }
    }

    private void drawChessboard(ChessTile[][] chessboard) {
        System.out.println("  a b c d e f g h"); // Column labels
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print((8 - row) + " "); // Row label
            for (int col = 0; col < BOARD_SIZE; col++) {
                ChessTile tile = chessboard[row][col];
                if (tile.isTileFilled()) {
                    // Print the piece symbol based on the piece type
                    System.out.print(getPieceSymbol(tile.getPiece()) + " ");
                } else {
                    System.out.print("- "); // Empty tile symbol
                }
            }
            System.out.println();
        }
    }

    private void userInput(String input) {

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

            movePiece(sourceRow, sourceCol, destRow, destCol);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
        }
    }



    private void movePiece(int sourceRow, int sourceCol, int destRow, int destCol) {
        // TODO: Implement the movement logic

        Piece piece = chessboard[sourceRow][sourceCol].getPiece();

        if (piece != null && piece.getColor().equals(gameState.currentPlayer)) {
            if (piece.isValidMove(sourceRow, sourceCol, destRow, destCol, chessboard)) {
                chessboard[sourceRow][sourceCol].removePiece();
                chessboard[destRow][destCol].setPiece(piece);

                if (piece instanceof Pawn) {
                    ((Pawn) piece).setFirstMove(false);
                }

                // TODO: check for captures, check/checkmate, and switch player turns

            } else {
                System.out.println("Invalid move. Please try again.");
            }
        } else {
            System.out.println("No piece found or it's not your turn. Please try again.");
        }
    }

    private int letterToNumber(char inputLetter){

        int outputInt = -1; // allows for error checking.

        if (inputLetter == 'A'){
            outputInt = 0;
        }
        if (inputLetter == 'B'){
            outputInt = 1;
        }
        if (inputLetter == 'C'){
            outputInt = 2;
        }
        if (inputLetter == 'D'){
            outputInt = 3;
        }
        if (inputLetter == 'E'){
            outputInt = 4;
        }
        if (inputLetter == 'F'){
            outputInt = 5;
        }
        if (inputLetter == 'G'){
            outputInt = 6;
        }
        if (inputLetter == 'H'){
            outputInt = 7;
        }

        return outputInt;
    }



    private String getPieceSymbol(Piece piece) {
        // not sure if I need this
        return piece.getType().substring(0, 1).toUpperCase();
    }

    private void clearBlackCaptureflag(ChessTile[][] chessboard){
        //TODO loop through all tiles and set flag to false
    }
    private void clearWhiteCaptureflag(ChessTile[][] chessboard){

        //TODO loop through all tiles and set flag to false
    }

    private void setBlackCaptureflag(ChessTile[][] chessboard){
        //TODO loop through all tiles if they have a piece, check where they can capture and set flag
    }
    private void setWhiteCaptureflag(ChessTile[][] chessboard){

        //TODO loop through all tiles if they have a piece, check where they can capture and set flag
    }

}

