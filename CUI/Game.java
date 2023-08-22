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

        while (!game.isCheckmate(game.gameState) || !game.playerAbort) {
            game.drawChessboard(game.chessboard);
            System.out.println("\nIt is " + gameState.currentPlayer + "'s turn. Please input your command.");

            if (gameState.currentPlayer.equals("white")) {
                game.resetEnPassantFlags(gameState.getWhitePieces());
            } else {
                game.resetEnPassantFlags(gameState.getBlackPieces());
            }

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
                } else if (userInputCommand.equalsIgnoreCase("history")) {
                    game.printMoveHistory(game.moveHistory);
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




            if (chessboard[sourceRow][destCol].isTileFilled() && chessboard[sourceRow][destCol].getPiece().isJustMovedTwoSquares()) {
                enPassantCapture(sourceRow, sourceCol, destRow, destCol, gameState);
            } else if (chessboard[destRow][destCol].isTileFilled()) {
                capturePiece(sourceRow, sourceCol, destRow, destCol, gameState);
            } else {
                movePiece(sourceRow, sourceCol, destRow, destCol, gameState);
            }


        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred in the userInput method. Please try again. - Catch is UserInput");
        }
    }


    private void movePiece(int sourceRow, int sourceCol, int destRow, int destCol, GameState gameState) {

        clearAllCaptureFlags();


        Piece piece = chessboard[sourceRow][sourceCol].getPiece();

        if (piece != null && piece.getColour().equals(gameState.currentPlayer)) {

            if (piece.type.equals("king") && Math.abs(destCol - sourceCol) == 2) { // castling check
                if (canCastle(piece, sourceRow, sourceCol, destRow, destCol, chessboard)) {
                    clearAllCaptureFlags();
                    performCastling(piece, sourceRow, sourceCol, destRow, destCol, chessboard);

                    setAllCaptureFlags(gameState);
                    isBlackKingInCheck();
                    isWhiteKingInCheck();

                    System.out.println("Player is switched.");
                    gameState.switchPlayer();
                    MoveInfo moveInfo = new MoveInfo(sourceRow, sourceCol, destRow, destCol);
                    moveHistory.add(moveInfo);

                    return;
                }
            }

            if (piece.isValidMove(sourceRow, sourceCol, destRow, destCol, chessboard)) { //move method
                chessboard[sourceRow][sourceCol].removePiece();
                chessboard[destRow][destCol].setPiece(piece);


                // updating any pieces that have had their first move
                if (piece instanceof Pawn) {
                    piece.setFirstMove(false);
                    if (Math.abs(destRow - sourceRow) == 2) {
                        ((Pawn) piece).setJustMovedTwoSquares(true);
                    }
                }
                if (piece instanceof Rook) {
                    piece.setFirstMove(false);
                }
                if (piece instanceof King) {
                    piece.setFirstMove(false);
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


                // this maybe should be its own method

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

    public boolean canCastle(Piece king, int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {

        Piece rook;
        int direction;

        // make sure we get the correct pieces
        if (king.getColour().equals("white")) {
            if (destCol < sourceCol) {
                rook = chessboard[0][0].getPiece();
                direction = -1; // moving left
            } else {
                rook = chessboard[0][7].getPiece();
                direction = 1; // moving right
            }
        } else { //king is black
            if (destCol < sourceCol) {
                rook = chessboard[7][0].getPiece();
                direction = -1;
            } else {
                rook = chessboard[7][7].getPiece();
                direction = 1;
            }
        }

        if (king.isFirstMove() && rook.isFirstMove()) { // is this fails, they have moved before.
            for (int i = sourceCol + direction; i != destCol; i += direction) {
                if (king.getColour().equals("white")) {
                    if (chessboard[sourceRow][i].isTileFilled() || chessboard[sourceRow][i].canBlackCapture) {
                        return false;
                    }
                } else {
                    if (chessboard[sourceRow][i].isTileFilled() || chessboard[sourceRow][i].canWhiteCapture) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public void performCastling(Piece king, int sourceRow, int sourceCol, int destRow, int destCol, ChessTile[][] chessboard) {

        Piece rook;
        int rookOption;

        final int QUEENSIDE_WHITE = 1;
        final int KINGSIDE_WHITE = 2;
        final int QUEENSIDE_BLACK = 3;
        final int KINGSIDE_BLACK = 4;

        // make sure we get the correct pieces
        if (king.getColour().equals("white")) {
            if (destCol < sourceCol) {
                rook = chessboard[0][0].getPiece();
                rookOption = QUEENSIDE_WHITE; // moving left
            } else {
                rook = chessboard[0][7].getPiece();
                rookOption = KINGSIDE_WHITE; // moving right
            }
        } else { //king is black
            if (destCol < sourceCol) {
                rook = chessboard[7][0].getPiece();
                rookOption = QUEENSIDE_BLACK;
            } else {
                rook = chessboard[7][7].getPiece();
                rookOption = KINGSIDE_BLACK;
            }
        }

        chessboard[sourceRow][sourceCol].removePiece();
        chessboard[destRow][destCol].setPiece(king);

        switch (rookOption) { //starting top left to bottom right
            case QUEENSIDE_WHITE: //
                chessboard[0][0].removePiece();
                chessboard[0][2].setPiece(rook);
                break;
            case KINGSIDE_WHITE:
                chessboard[0][7].removePiece();
                chessboard[0][5].setPiece(rook);
                break;
            case QUEENSIDE_BLACK: //
                chessboard[7][0].removePiece();
                chessboard[7][2].setPiece(rook);
                break;
            case KINGSIDE_BLACK:
                chessboard[7][7].removePiece();
                chessboard[7][5].setPiece(rook);
                break;
            default:
                System.out.println("Something went wrong in the performing castling method switch case");
        }


        if (king.getColour().equals("black")) {
            gameState.setBlackKingPosition(destRow, destCol);
        } else if (king.getColour().equals("white")) {
            gameState.setWhiteKingPosition(destRow, destCol);
        }

        king.setFirstMove(false);
        rook.setFirstMove(false);

    }

    private void revertBoard(int sourceRow, int sourceCol, int destRow, int destCol) {

        Piece currentPiece = chessboard[destRow][destCol].getPiece();
        Piece previousPiece = chessboard[destRow][destCol].getPreviousPiece();

        chessboard[destRow][destCol].removePiece();
        chessboard[destRow][destCol].setPiece(previousPiece);

        if (previousPiece.getColour().equals("white")) { //adding the piece back to the list after it was removed.
            gameState.getWhitePieces().add(previousPiece);
        } else
            gameState.getBlackPieces().add(previousPiece);

        chessboard[sourceRow][sourceCol].setPiece(currentPiece);

    }

    private void capturePiece(int sourceRow, int sourceCol, int destRow, int destCol, GameState gameState) {

        Piece piece = chessboard[sourceRow][sourceCol].getPiece();
        Piece capturedPiece = chessboard[destRow][destCol].getPiece();


        if (piece != null && capturedPiece != null && piece.getColour().equals(gameState.currentPlayer)) {
            if (piece.isValidCapture(sourceRow, sourceCol, destRow, destCol, chessboard)) {

                chessboard[destRow][destCol].setPreviousPiece(capturedPiece);
                chessboard[destRow][destCol].removePiece();
                gameState.getWhitePieces().remove(chessboard[destRow][destCol].getPiece()); //removes the piece from the list now
                gameState.getBlackPieces().remove(chessboard[destRow][destCol].getPiece());

                piece.setPreviousCol(sourceCol);
                piece.setPreviousRow(sourceRow);
                chessboard[sourceRow][sourceCol].removePiece();
                chessboard[destRow][destCol].setPiece(piece);

                if (isCheckmate(gameState)) {
                    System.out.println("Checkmate");
                }

                gameState.switchPlayer();
            }
        }

    }

    private void enPassantCapture(int sourceRow, int sourceCol, int destRow, int destCol, GameState gameState) {
        Piece piece = chessboard[sourceRow][sourceCol].getPiece();
        Piece capturedPiece = chessboard[sourceRow][destCol].getPiece(); // Captured pawn is on the same row as the destination column

        if (piece != null && capturedPiece != null && piece.getColour().equals(gameState.currentPlayer)) {
            if (piece.isValidCapture(sourceRow, sourceCol, destRow, destCol, chessboard)) {
                chessboard[destRow][destCol].setPreviousPiece(capturedPiece);
                chessboard[destRow][destCol].removePiece();
                gameState.getWhitePieces().remove(chessboard[destRow][destCol].getPiece());
                gameState.getBlackPieces().remove(chessboard[destRow][destCol].getPiece());

                piece.setPreviousCol(sourceCol);
                piece.setPreviousRow(sourceRow);
                chessboard[sourceRow][sourceCol].removePiece();
                chessboard[destRow][destCol].setPiece(piece);

                if (isCheckmate(gameState)) {
                    System.out.println("Checkmate");
                }

                gameState.switchPlayer();
            }
        }
    }


    public void resetEnPassantFlags(List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece instanceof Pawn) {
                ((Pawn) piece).setJustMovedTwoSquares(false);
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
        for (Piece piece : gameState.getWhitePieces()) {
            piece.canCapture(piece.getPieceRow(), piece.getPieceCol(), chessboard, gameState);
        }

        for (Piece piece : gameState.getBlackPieces()) {
            piece.canCapture(piece.getPieceRow(), piece.getPieceCol(), chessboard, gameState);
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
        int rowDirection = Integer.compare(kingRow, attackingPiece.getPieceRow());
        int colDirection = Integer.compare(kingCol, attackingPiece.getPieceCol());

        // The naming convention here is a bit off but currentRow is referring to a
        // row location, rather than an entire rank or file.

        int currentRow = attackingPiece.getPieceRow() + rowDirection;
        int currentCol = attackingPiece.getPieceCol() + colDirection;

        while (currentRow != kingRow || currentCol != kingCol) {
            path.add(chessboard[currentRow][currentCol]);
            currentRow += rowDirection;
            currentCol += colDirection;
        }

        return path;
    }

    public boolean isCheckmate(GameState gameState) {

        for (Piece piece : gameState.getWhitePieces()) {
            if (piece.isAttackingOpponentsKing) { // found a white piece attacking the black king
                // Checking if the black king can escape
                if (chessboard[gameState.getBlackKingRow()][gameState.getBlackKingCol()].getPiece().hasValidMoveToEscape(chessboard)) {
                    return false;
                }

                // Checking if the attacking white piece can be captured
                if (gameState.whiteCapturablePieces.contains(piece)) {
                    return false;
                }

                // Check if the path can be blocked.
                List<ChessTile> path = getPathBetweenAttackingPieceAndKing(gameState, piece);

                for (Piece otherPiece : gameState.getBlackPieces()) {
                    if (!otherPiece.equals(piece)) {
                        for (ChessTile tile : path) {
                            if (otherPiece.isValidMove(otherPiece.getPieceRow(), otherPiece.getPieceCol(), tile.getRow(), tile.getCol(), chessboard)) {
                                return false; // There's a white piece that can block the path
                            }
                        }
                    }
                }
            }
        }

        for (Piece piece : gameState.getBlackPieces()) {
            if (piece.isAttackingOpponentsKing) { // found a black piece attacking the white king
                // Checking if the white king can escape
                if (chessboard[gameState.getWhiteKingRow()][gameState.getWhiteKingCol()].getPiece().hasValidMoveToEscape(chessboard)) {
                    return false;
                }

                // Checking if the attacking black piece can be captured
                if (gameState.blackCapturablePieces.contains(piece)) {
                    return false;
                }

                // Check if the path can be blocked.
                List<ChessTile> path = getPathBetweenAttackingPieceAndKing(gameState, piece);

                for (Piece otherPiece : gameState.getWhitePieces()) {
                    if (!otherPiece.equals(piece)) {
                        for (ChessTile tile : path) {
                            if (otherPiece.isValidMove(otherPiece.getPieceRow(), otherPiece.getPieceCol(), tile.getRow(), tile.getCol(), chessboard)) {
                                return false; // There's a white piece that can block the path
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public void printMoveHistory(List<MoveInfo> moveHistory) {
        System.out.println("Move History:");

        for (int i = 0; i < moveHistory.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, moveHistory.get(i));
        }
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

