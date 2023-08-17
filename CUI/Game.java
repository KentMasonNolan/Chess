package CUI;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Game implements Serializable {

    private List<MoveInfo> moveHistory = new ArrayList<>();

    private static final int BOARD_SIZE = 8;
    ChessTile[][] chessboard = createEmptyChessboard();
    private GameState gameState = new GameState();

    private boolean playerAbort = false;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        GameState gameState = new GameState();
        Game game = new Game();
        game.start(gameState);

        System.out.println("Welcome to the game of chess.\n\n" + "This is a two-player game with no AI or game engine, so you are expected to play two-player or play both sides.\n" + "The expected inputs are the square you want to move followed by the destination square. e.g. C2 C4.\n" + "At any point in this game, you can type 'EXIT' to quit or 'SAVE' to save the game.\n\n" + "You are playing black. Good luck.\n");

        while (!gameState.isCheckmate() && !game.playerAbort) {
            game.drawChessboard(game.chessboard);
            System.out.println("\nIt is " + gameState.currentPlayer + "'s turn. Please input your command.");

            try {
                String userInputCommand = input.nextLine();

                if (userInputCommand.equalsIgnoreCase("save")) {
                    System.out.println("Enter the filename to save the game:");
                    String filename = input.nextLine();
                    game.saveGame(filename);
                } else if (userInputCommand.equalsIgnoreCase("load")) {
                    System.out.println("Enter the filename to load the game:");
                    String filename = input.nextLine();
                    Game loadedGame = Game.loadGameFromFile(filename);
                    if (loadedGame != null) {
                        game = loadedGame; // Update the game instance with the loaded game
                    }
                } else {
                    game.userInput(userInputCommand, gameState);
                }
            } catch (Exception e) {
                System.out.println("Please input a valid command. e.g. C2 C4");
            }
        }
    }

    private void start(GameState gameState) {

        System.out.println("What board would you like to play?");
        System.out.println("1. normal chessboard");
        System.out.println("2. test chessboard");
        // todo setup a showcase board for markers to test

        Scanner input = new Scanner(System.in);
        int menu = input.nextInt();

        switch (menu) {
            case 1:
                setupInitialPieces(chessboard, gameState);
                break;
            case 2:
                setupTestPieces(chessboard, gameState);
                break;
            default:
                System.out.println("Please choose a valid option");
                break;
        }
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

    private void setupInitialPieces(ChessTile[][] chessboard, GameState gameState) {
        // Set white pieces
        Rook whiteRook1 = new Rook("white", 0, 0);
        Knight whiteKnight1 = new Knight("white", 0, 1);
        Bishop whiteBishop1 = new Bishop("white", 0, 2);
        Queen whiteQueen = new Queen("white", 0, 3);
        King whiteKing = new King("white", 0, 4, gameState);
        Bishop whiteBishop2 = new Bishop("white", 0, 5);
        Knight whiteKnight2 = new Knight("white", 0, 6);
        Rook whiteRook2 = new Rook("white", 0, 7);

        // Add white pieces to the gameState's whitePieces list
        gameState.getWhitePieces().add(whiteRook1);
        gameState.getWhitePieces().add(whiteKnight1);
        gameState.getWhitePieces().add(whiteBishop1);
        gameState.getWhitePieces().add(whiteQueen);
        gameState.getWhitePieces().add(whiteKing);
        gameState.getWhitePieces().add(whiteBishop2);
        gameState.getWhitePieces().add(whiteKnight2);
        gameState.getWhitePieces().add(whiteRook2);

        // Set pieces on the board
        chessboard[0][0].setPiece(whiteRook1);
        chessboard[0][1].setPiece(whiteKnight1);
        chessboard[0][2].setPiece(whiteBishop1);
        chessboard[0][3].setPiece(whiteQueen);
        chessboard[0][4].setPiece(whiteKing);
        chessboard[0][5].setPiece(whiteBishop2);
        chessboard[0][6].setPiece(whiteKnight2);
        chessboard[0][7].setPiece(whiteRook2);

        // Set white pawns in the second row
        for (int col = 0; col < BOARD_SIZE; col++) {
            Pawn whitePawn = new Pawn("white", 1, col);
            gameState.getWhitePieces().add(whitePawn);
            chessboard[1][col].setPiece(whitePawn);
        }

        // Set black pieces
        Rook blackRook1 = new Rook("black", 7, 0);
        Knight blackKnight1 = new Knight("black", 7, 1);
        Bishop blackBishop1 = new Bishop("black", 7, 2);
        Queen blackQueen = new Queen("black", 7, 3);
        King blackKing = new King("black", 7, 4, gameState);
        Bishop blackBishop2 = new Bishop("black", 7, 5);
        Knight blackKnight2 = new Knight("black", 7, 6);
        Rook blackRook2 = new Rook("black", 7, 7);

        // Add black pieces to the gameState's blackPieces list
        gameState.getBlackPieces().add(blackRook1);
        gameState.getBlackPieces().add(blackKnight1);
        gameState.getBlackPieces().add(blackBishop1);
        gameState.getBlackPieces().add(blackQueen);
        gameState.getBlackPieces().add(blackKing);
        gameState.getBlackPieces().add(blackBishop2);
        gameState.getBlackPieces().add(blackKnight2);
        gameState.getBlackPieces().add(blackRook2);

        // Set pieces on the board
        chessboard[7][0].setPiece(blackRook1);
        chessboard[7][1].setPiece(blackKnight1);
        chessboard[7][2].setPiece(blackBishop1);
        chessboard[7][3].setPiece(blackQueen);
        chessboard[7][4].setPiece(blackKing);
        chessboard[7][5].setPiece(blackBishop2);
        chessboard[7][6].setPiece(blackKnight2);
        chessboard[7][7].setPiece(blackRook2);

        // Set black pawns in the second-to-last row
        for (int col = 0; col < BOARD_SIZE; col++) {
            Pawn blackPawn = new Pawn("black", 6, col);
            gameState.getBlackPieces().add(blackPawn);
            chessboard[6][col].setPiece(blackPawn);
        }
    }

    private void setupTestPieces(ChessTile[][] chessboard, GameState gameState) { //this is for testing
        //black king in the usual spot
        chessboard[7][4].setPiece(new King("black", 7, 4, this.gameState));
        chessboard[1][5].setPiece(new Pawn("black", 1, 5));

        this.gameState.setBlackKingPosition(7, 4);

        //white pawn one move away
        chessboard[3][2].setPiece(new Bishop("white", 2, 3));
        chessboard[1][0].setPiece(new Pawn("white", 0, 0));
        chessboard[1][3].setPiece(new Queen("white", 1, 3));

        chessboard[5][5].setPiece(new Rook("white", 5, 5));
    }


//  todo  Identify the piece attacking the king

//   todo Find the path it is taking to attack the king, if the opponent controls one of those squares,
//    the checkmate can be stopped. The king cannot contribute to this list

    private void drawChessboard(ChessTile[][] chessboard) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print((row + 1) + " "); // Row label
            for (int col = 0; col < BOARD_SIZE; col++) {
                ChessTile tile = chessboard[row][col];
                if (tile.isTileFilled()) {
                    // Print the piece symbol based on the piece type
                    System.out.print(getPieceSymbol(tile.getPiece()) + " ");
                } else {
                    System.out.print(" - "); // Empty tile symbol
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

            int sourceRow = Character.getNumericValue(sourceSquare.charAt(1) - 1);
            int sourceCol = letterToNumber(sourceSquare.charAt(0));
            int destRow = Character.getNumericValue(destSquare.charAt(1) - 1);
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

        clearAllCaptureFlags();


        Piece piece = chessboard[sourceRow][sourceCol].getPiece();

        if (piece != null && piece.getColour().equals(gameState.currentPlayer)) {
            if (piece.isValidMove(sourceRow, sourceCol, destRow, destCol, chessboard)) {
                chessboard[sourceRow][sourceCol].removePiece();
                chessboard[destRow][destCol].setPiece(piece);

                if (piece instanceof Pawn) {
                    ((Pawn) piece).setFirstMove(false);
                }
                if (piece instanceof Rook) {
                    ((Rook) piece).setFirstMove(false);
                }
                if (piece instanceof King) {
                    ((King) piece).setFirstMove(false);
                    if (Objects.equals(piece.getColour(), "black")) {
                        this.gameState.setBlackKingPosition(destRow, destCol);
                    } else {
                        this.gameState.setWhiteKingPosition(destRow, destCol);
                    }


                }
                if (piece instanceof King && Objects.equals(piece.getColour(), "black")) {
                    gameState.setBlackKingPosition(destRow, destCol);
                } else if (piece instanceof King && Objects.equals(piece.getColour(), "white")) {
                    gameState.setWhiteKingPosition(destRow, destCol);
                }

                setAllCaptureFlags(gameState);
                isBlackKingInCheck();
                isWhiteKingInCheck();


                if (piece.colour.equals("white") && isWhiteKingInCheck()) {
                    //revert
                    revertBoard(sourceRow, sourceCol, destRow, destCol);
                    System.out.println("That move resulted in the white king being in check and therefore illegal ");
                } else if (piece.colour.equals("black") && isBlackKingInCheck()) {
                    revertBoard(sourceRow, sourceCol, destRow, destCol);
                    System.out.println("That move resulted in the black king being in check and therefore illegal ");
                } else {
                    System.out.println("Player is switched.");
                    gameState.switchPlayer();
                    MoveInfo moveInfo = new MoveInfo(sourceRow, sourceCol, destRow, destCol);
                    moveHistory.add(moveInfo);
                }


            } else {
                System.out.println("Invalid move. Please try again.");
            }
        } else {
            System.out.println("No piece found or it's not your turn. Please try again.");
        }
    }

    private void revertBoard(int sourceRow, int sourceCol, int destRow, int destCol) {

        Piece currentPiece = chessboard[destRow][destCol].getPiece();
        Piece previousPiece = chessboard[destRow][destCol].getPreviousPiece();

        chessboard[destRow][destCol].removePiece();
        chessboard[destRow][destCol].setPiece(previousPiece);
        chessboard[sourceRow][sourceCol].setPiece(currentPiece);

    }

    private void capturePiece(int sourceRow, int sourceCol, int destRow, int destCol, GameState gameState) {

        Piece piece = chessboard[sourceRow][sourceCol].getPiece();
        Piece capturedPiece = chessboard[destRow][destCol].getPiece();


        if (piece != null && capturedPiece != null && piece.getColour().equals(gameState.currentPlayer)) {
            if (piece.isValidCapture(sourceRow, sourceCol, destRow, destCol, chessboard)) {

                chessboard[destRow][destCol].setPreviousPiece(capturedPiece);
                chessboard[destRow][destCol].removePiece();

                piece.setPreviousCol(sourceCol);
                piece.setPreviousRow(sourceRow);
                chessboard[sourceRow][sourceCol].removePiece();
                chessboard[destRow][destCol].setPiece(piece);

                // todo check for checkmate here

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

        String colourPrefix;
        if (piece.getColour().equals("white")) {
            colourPrefix = "w";
        } else {
            colourPrefix = "b";
        }

        String pieceType = piece.getType().substring(0, 1).toUpperCase();


        return colourPrefix + pieceType;
    }

    private void setAllCaptureFlags(GameState gameState) {

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Piece piece = chessboard[row][col].getPiece();
                if (piece != null) {
                    piece.canCapture(row, col, chessboard, gameState);
                }
            }
        }
    }

    private boolean isBlackKingInCheck() {
        if (chessboard[gameState.getBlackKingRow()][gameState.getBlackKingCol()].getCanWhiteCapture()) {
            System.out.println("Black King is in check");
            return true;
        } else
            return false;
    }

    private boolean isWhiteKingInCheck() {
        if (chessboard[gameState.getWhiteKingRow()][gameState.getWhiteKingCol()].getCanBlackCapture()) {
            System.out.println("White King is in check");
            return true;
        } else
            return false;
    }

    private void clearAllCaptureFlags() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                chessboard[row][col].setCanBlackCapture(false);
                chessboard[row][col].setCanWhiteCapture(false);
            }
        }
    }

    public void saveGame(String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(this);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save the game: " + e.getMessage());
        }
    }

    public static Game loadGameFromFile(String filename) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            Game loadedGame = (Game) inputStream.readObject();
            System.out.println("Game loaded successfully.");
            return loadedGame;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load the game: " + e.getMessage());
            return null;
        }
    }

    public List<ChessTile> getPathBetweenAttackingPieceAndKing(GameState gameState, Piece attackingPiece) {
        List<ChessTile> path = new ArrayList<>();

        //this method should only be called on pieces that can attack along a rank/file e.g. queen, rook, bishop.

        int kingRow;
        int kingCol;

        if (attackingPiece.getColour().equals("white")) {
            kingRow = gameState.getWhiteKingRow();
            kingCol = gameState.getWhiteKingCol();
        } else {
            kingRow = gameState.getBlackKingRow();
            kingCol = gameState.getBlackKingCol();
        }

        // Determine the direction of the path based on piece's position relative to the king
        int rowDirection = Integer.compare(kingRow, attackingPiece.getyLoc());
        int colDirection = Integer.compare(kingCol, attackingPiece.getxLoc());

        // The naming convention here is a bit off but currentRow is referring to a
        // row location, rather than an entire rank or file.

        int currentRow = attackingPiece.getyLoc() + rowDirection;
        int currentCol = attackingPiece.getxLoc() + colDirection;

        while (currentRow != kingRow || currentCol != kingCol) {
            path.add(chessboard[currentRow][currentCol]);
            currentRow += rowDirection;
            currentCol += colDirection;
        }

        return path;
    }

    public boolean isCheckmate(ChessTile[][] chessboard, GameState gameState) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Piece piece = chessboard[row][col].getPiece();

                if (piece != null && piece.isAttackingOpponentsKing) { // found a piece attacking the king


                    // Checking if the king can escape
                    if (piece.getColour().equals("white") && chessboard[gameState.getBlackKingRow()][gameState.getBlackKingCol()].getPiece().hasValidMoveToEscape(chessboard)) {
                        return false;
                    } else if (piece.getColour().equals("black") && chessboard[gameState.getWhiteKingRow()][gameState.getWhiteKingCol()].getPiece().hasValidMoveToEscape(chessboard)) {
                        return false;
                    }
                    // The King can't get away

                    // Checking if the piece attacking the king can be captured
                    if (piece.getColour().equals("white") && gameState.whiteCapturablePieces.contains(piece)) {
                        return false;
                    } else if (piece.getColour().equals("black") && gameState.blackCapturablePieces.contains(piece)) {
                        return false;
                    }
                    // the attacking piece can't be captured.


                    //check if the path can be blocked.
                    List<ChessTile> path = getPathBetweenAttackingPieceAndKing(gameState, piece);

                }


                return false;
            }
        }
        return false;
    }

    private class MoveInfo implements Serializable {
        private int sourceRow;
        private int sourceCol;
        private int destRow;
        private int destCol;

        public MoveInfo(int sourceRow, int sourceCol, int destRow, int destCol) {
            this.sourceRow = sourceRow;
            this.sourceCol = sourceCol;
            this.destRow = destRow;
            this.destCol = destCol;
        }
    }

}

