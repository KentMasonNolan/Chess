package CUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Game extends JFrame implements Serializable {


    private static final int BOARD_SIZE = 8;

    private static final int DEFAULT_WIDTH = 800; // Default width of the JFrame
    private static final int DEFAULT_HEIGHT = 800; // Default height of the JFrame

    private JPanel chessBoardPanel; // Panel to hold the Chess board

    ChessTile[][] chessboard = createEmptyChessboard();
    private GameState gameState = new GameState();

    static FileIOManager fileIOManager = new FileIOManager();
    List<String> savedGamesList = fileIOManager.getSavedGamesList("saved_games_metadata.txt");

    private boolean playerAbort = false;

    private List<User> userList = new ArrayList<>();
    private List<MoveInfo> moveHistory = new ArrayList<>();


    public Game() throws IOException {
        setTitle("Chess Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the Chess board panel with a GridLayout
        chessBoardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        add(chessBoardPanel, BorderLayout.CENTER);

        // Create and add the Chess board squares
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                ChessTile tile = chessboard[row][col];
                JPanel square = createSquarePanel(tile);
                chessBoardPanel.add(square);
            }
        }

        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT); // Set the default size
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public GameState getGameState() {
        return gameState;
    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        GameState gameState = new GameState();
        Game game = new Game();
        game.start(gameState);

        System.out.println("Welcome to the game of chess.\n\n" + "This is a two-player game with no AI or game engine, so you are expected to play two-player or play both sides.\n" + "The expected inputs are the square you want to move followed by the destination square. e.g. C2 C4.\n" + "At any point in this game, you can type 'EXIT' to quit or 'SAVE' to save the game.\n\n" + "You are playing black. Good luck.\n");

        while (!game.isCheckmate(gameState) && !game.playerAbort) {
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
                    fileIOManager.writeSavedGameMetadata("saved_games_metadata.txt", filename);

                } else if (userInputCommand.equalsIgnoreCase("load")) {
                    System.out.println("List of Saved Games:");

                    // Retrieve the list of saved games
                    List<String> savedGamesList = fileIOManager.getSavedGamesList("saved_games_metadata.txt");

                    // Display the list of saved games
                    for (String gameInfo : savedGamesList) {
                        System.out.println(gameInfo);
                    }

                    System.out.println("Enter the filename to load the game:");
                    String filename = input.nextLine();
                    Game loadedGame = Game.loadGameFromFile(filename);
                    if (loadedGame != null) {
                        game = loadedGame; // Update the game instance with the loaded game
                        gameState = game.getGameState(); // Update the game state

                    }
                } else if (userInputCommand.equalsIgnoreCase("history")) {
                    List<String> moveHistory = game.loadMoveHistory(); // Load move history
                    game.printMoveHistory(moveHistory); // Print the loaded move history
                } else if (userInputCommand.equalsIgnoreCase("exit") || userInputCommand.equalsIgnoreCase("quit"))  {
                    System.out.println("Thank you for playing.");
                    game.playerAbort = true;
                } else {
                    game.userInput(userInputCommand, gameState);
                }
            } catch (Exception e) {
                System.out.println("Please input a valid command. e.g. C2 C4");
            }
        }
    }


    private JPanel createSquarePanel(ChessTile tile) {
        JPanel square = new JPanel();
        square.setBackground(getSquareColor(tile));
        int borderWidth = 1;
        Border border = BorderFactory.createLineBorder(Color.BLACK, borderWidth);
        square.setBorder(border);
        return square;
    }


    private Color getSquareColor(ChessTile tile) {
        int row = tile.getRow();
        int col = tile.getCol();
        if ((row + col) % 2 == 0) {
            return new Color(238, 238, 210); // Light color
        } else {
            return new Color(118, 150, 86); // Dark color
        }
    }

    private void start(GameState gameState) {
        loadUserList(); // Load the list of users at the start

        System.out.println("What board would you like to play?");
        System.out.println("1. Normal chessboard");
        System.out.println("2. Puzzle chessboard");
        // todo setup a showcase board for markers to test

        Scanner input = new Scanner(System.in);
        int menu;

        while (true) {
            try {
                menu = Integer.parseInt(input.nextLine());
                if (menu >= 1 && menu <= 2) {
                    break;
                } else {
                    System.out.println("Please enter a valid option (1 or 2).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }

        switch (menu) {
            case 1:
                setupPlayersAndInitialPieces(gameState);
                setupInitialPieces(chessboard, gameState);
                break;
            case 2:
                setupPlayersAndInitialPieces(gameState);
                setupTestPieces(chessboard, gameState);
                break;
        }
    }

    private void setupPlayersAndInitialPieces(GameState gameState) {
        String whitePlayerName = selectUser("White");
        String blackPlayerName = selectUser("Black");

    }

    private String selectUser(String color) {
        Scanner input = new Scanner(System.in);

        System.out.println("Select " + color + " player:");

        for (int i = 0; i < userList.size(); i++) {
            System.out.println((i + 1) + ". " + userList.get(i).getName());
        }

        System.out.println((userList.size() + 1) + ". Enter a new name");

        while (true) {
            String userInput = input.nextLine();
            try {
                int selection = Integer.parseInt(userInput);
                if (selection >= 1 && selection <= userList.size() + 1) {
                    if (selection <= userList.size()) {
                        return userList.get(selection - 1).getName();
                    } else {
                        System.out.println("Enter the new " + color + " player's name:");
                        String newName = input.nextLine();
                        addUser(newName); // Add the new user to the list
                        return newName;
                    }
                } else {
                    System.out.println("Please enter a valid option.");
                }
            } catch (NumberFormatException e) {
                if (!userInput.isEmpty()) {
                    addUser(userInput); // Add the new user to the list
                    return userInput;
                } else {
                    System.out.println("Please enter a valid option or a new name.");
                }
            }
        }
    }


    private void addUser(String name) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter " + name + " player's ELO rating:");
        int elo = input.nextInt();
        input.nextLine(); // Consume the newline

        User newUser = new User(name, elo);
        userList.add(newUser);

        System.out.println("Player " + name + " added with ELO rating " + elo + ".");

        saveUserList(); // Save the user list after adding a new user
    }


    // creates a 2d array of tiles for our board.
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

        // Set white pawns in the second row
        for (int col = 0; col < BOARD_SIZE; col++) {
            Pawn whitePawn = new Pawn("white", 1, col);
            gameState.getWhitePieces().add(whitePawn);
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

        // Set black pawns in the second-to-last row
        for (int col = 0; col < BOARD_SIZE; col++) {
            Pawn blackPawn = new Pawn("black", 6, col);
            gameState.getBlackPieces().add(blackPawn);
        }


        // Set pieces on the board
        for (Piece whitePiece : gameState.getWhitePieces()) {
            chessboard[whitePiece.getPieceRow()][whitePiece.getPieceCol()].setPiece(whitePiece);
        }

        for (Piece blackPiece : gameState.getBlackPieces()) {
            chessboard[blackPiece.getPieceRow()][blackPiece.getPieceCol()].setPiece(blackPiece);
        }
    }

    private void setupTestPieces(ChessTile[][] chessboard, GameState gameState) {


        Rook whiteRook1 = new Rook("white", 0, 0);
        gameState.getWhitePieces().add(whiteRook1);
        chessboard[0][0].setPiece(whiteRook1);

        Bishop whiteBishop1 = new Bishop("white", 0, 2);
        gameState.getWhitePieces().add(whiteBishop1);
        chessboard[0][2].setPiece(whiteBishop1);

        King whiteKing = new King("white", 0, 3, gameState);
        gameState.getWhitePieces().add(whiteKing);
        chessboard[0][3].setPiece(whiteKing);

        Queen whiteQueen = new Queen("white", 0, 4);
        gameState.getWhitePieces().add(whiteQueen);
        chessboard[0][4].setPiece(whiteQueen);

        Rook whiteRook2 = new Rook("white", 0, 7);
        gameState.getWhitePieces().add(whiteRook2);
        chessboard[0][7].setPiece(whiteRook2);

        Pawn pawn1 = new Pawn("white", 1, 0);
        gameState.getWhitePieces().add(pawn1);
        chessboard[1][0].setPiece(pawn1);

        Pawn pawn2 = new Pawn("white", 1, 1);
        gameState.getWhitePieces().add(pawn2);
        chessboard[1][1].setPiece(pawn2);

        Pawn pawn3 = new Pawn("white", 1, 2);
        gameState.getWhitePieces().add(pawn3);
        chessboard[1][2].setPiece(pawn3);

        Knight whiteKnight1 = new Knight("white", 1, 3);
        gameState.getWhitePieces().add(whiteKnight1);
        chessboard[1][3].setPiece(whiteKnight1);

        Bishop whiteBishop2 = new Bishop("white", 1, 4);
        gameState.getWhitePieces().add(whiteBishop2);
        chessboard[1][4].setPiece(whiteBishop2);

        Pawn pawn4 = new Pawn("white", 1, 7);
        gameState.getWhitePieces().add(pawn4);
        chessboard[1][7].setPiece(pawn4);

        Pawn pawn5 = new Pawn("white", 2, 3);
        gameState.getWhitePieces().add(pawn5);
        chessboard[2][3].setPiece(pawn5);

        Pawn pawn6 = new Pawn("white", 3, 4);
        gameState.getWhitePieces().add(pawn6);
        chessboard[3][4].setPiece(pawn6);

        Pawn pawn7 = new Pawn("white", 1, 5);
        gameState.getWhitePieces().add(pawn7);
        chessboard[1][5].setPiece(pawn7);

        Pawn pawn8 = new Pawn("white", 2, 6);
        gameState.getWhitePieces().add(pawn8);
        chessboard[2][6].setPiece(pawn8);

        Queen blackQueen = new Queen("black", 2, 7);
        gameState.getBlackPieces().add(blackQueen);
        chessboard[2][7].setPiece(blackQueen);

        Knight blackKnight2 = new Knight("black", 3, 6);
        gameState.getBlackPieces().add(blackKnight2);
        chessboard[3][6].setPiece(blackKnight2);

        Bishop blackBishop2 = new Bishop("black", 4, 2);
        gameState.getBlackPieces().add(blackBishop2);
        chessboard[4][2].setPiece(blackBishop2);

        Pawn Bpawn1 = new Pawn("black", 6, 0);
        gameState.getBlackPieces().add(Bpawn1);
        chessboard[6][0].setPiece(Bpawn1);

        Pawn Bpawn2 = new Pawn("black", 6, 1);
        gameState.getBlackPieces().add(Bpawn2);
        chessboard[6][1].setPiece(Bpawn2);

        Pawn Bpawn3 = new Pawn("black", 6, 2);
        gameState.getBlackPieces().add(Bpawn3);
        chessboard[6][2].setPiece(Bpawn3);

        Pawn Bpawn4 = new Pawn("black", 6, 3);
        gameState.getBlackPieces().add(Bpawn4);
        chessboard[6][3].setPiece(Bpawn4);

        Pawn Bpawn6 = new Pawn("black", 6, 5);
        gameState.getBlackPieces().add(Bpawn6);
        chessboard[6][5].setPiece(Bpawn6);

        Pawn Bpawn7 = new Pawn("black", 6, 6);
        gameState.getBlackPieces().add(Bpawn7);
        chessboard[6][6].setPiece(Bpawn7);

        Pawn Bpawn8 = new Pawn("black", 6, 7);
        gameState.getBlackPieces().add(Bpawn8);
        chessboard[6][7].setPiece(Bpawn8);

        Rook blackRook1 = new Rook("black", 7, 0);
        gameState.getBlackPieces().add(blackRook1);
        chessboard[7][0].setPiece(blackRook1);

        Knight blackKnight1 = new Knight("black", 7, 1);
        gameState.getBlackPieces().add(blackKnight1);
        chessboard[7][1].setPiece(blackKnight1);

        Bishop blackBishop1 = new Bishop("black", 7, 2);
        gameState.getBlackPieces().add(blackBishop1);
        chessboard[7][2].setPiece(blackBishop1);

        King blackKing = new King("black", 7, 3, gameState);
        gameState.getBlackPieces().add(blackKing);
        chessboard[7][3].setPiece(blackKing);

        Rook blackRook2 = new Rook("black", 7, 7);
        gameState.getBlackPieces().add(blackRook2);
        chessboard[7][7].setPiece(blackRook2);

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

// processes user input to perform chess moves

    private void userInput(String input, GameState gameState) {
        try {
            // expected input format: "c4 c5"
            String[] squares = input.split(" ");

            // check if the input consists of two squares
            if (squares.length != 2) {
                throw new IllegalArgumentException("invalid input. please enter two squares separated by a space.");
            }

            // convert squares to uppercase for consistency
            String sourceSquare = squares[0].toUpperCase();
            String destSquare = squares[1].toUpperCase();

            // convert characters to row and column indices
            int sourceRow = Character.getNumericValue(sourceSquare.charAt(1)) - 1;
            int sourceCol = letterToNumber(sourceSquare.charAt(0));
            int destRow = Character.getNumericValue(destSquare.charAt(1)) - 1;
            int destCol = letterToNumber(destSquare.charAt(0));

            // check for en passant capture
            if (chessboard[sourceRow][destCol].isTileFilled() && chessboard[sourceRow][destCol].getPiece().isJustMovedTwoSquares()) {
                enPassantCapture(sourceRow, sourceCol, destRow, destCol, gameState);
            }
            // check for regular capture
            else if (chessboard[destRow][destCol].isTileFilled()) {
                capturePiece(sourceRow, sourceCol, destRow, destCol, gameState);
            }
            // perform a standard move
            else {
                movePiece(sourceRow, sourceCol, destRow, destCol, gameState);
            }

        } catch (IllegalArgumentException e) {
            System.out.println("error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("an unexpected error occurred in the userInput method. please try again. - catch is userInput");
        }
    }


// moves a chess piece and handles related game mechanics

    private void movePiece(int sourceRow, int sourceCol, int destRow, int destCol, GameState gameState) {

        // get the piece from the source square
        Piece piece = chessboard[sourceRow][sourceCol].getPiece();

        // check if the piece exists and belongs to the current player
        if (piece != null && piece.getColour().equals(gameState.currentPlayer)) {

            // check for castling
            if (piece.type.equals("King") && Math.abs(destCol - sourceCol) == 2) {
                if (canCastle(piece, sourceRow, sourceCol, destRow, destCol, chessboard)) {
                    // perform castling
                    clearAllCaptureFlags();
                    performCastling(piece, sourceRow, sourceCol, destRow, destCol, chessboard);
                    setAllCaptureFlags(gameState);
                    isBlackKingInCheck(gameState);
                    isWhiteKingInCheck(gameState);
                    System.out.println("player is switched.");
                    gameState.switchPlayer();
                    MoveInfo moveInfo = new MoveInfo(sourceRow, sourceCol, destRow, destCol);
                    moveHistory.add(moveInfo);
                    return;
                }
            }

            // check if the move is valid
            if (piece.isValidMove(sourceRow, sourceCol, destRow, destCol, chessboard)) {
                // move the piece
                clearAllCaptureFlags();
                chessboard[sourceRow][sourceCol].removePiece();
                chessboard[destRow][destCol].setPiece(piece);
                piece.setPieceCol(destCol);
                piece.setPieceRow(destRow);

                // update special cases for pieces' first moves
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

                // update game state
                setAllCaptureFlags(gameState);
                isBlackKingInCheck(gameState);
                isWhiteKingInCheck(gameState);

                // check if the move results in check
                if (piece.colour.equals("white") && isWhiteKingInCheck(gameState)) {
                    revertBoard(sourceRow, sourceCol, destRow, destCol);
                    System.out.println("that move resulted in the white king being in check and therefore illegal ");
                } else if (piece.colour.equals("black") && isBlackKingInCheck(gameState)) {
                    revertBoard(sourceRow, sourceCol, destRow, destCol);
                    System.out.println("that move resulted in the black king being in check and therefore illegal ");
                } else {
                    System.out.println("player is switched.");
                    gameState.switchPlayer();
                    MoveInfo moveInfo = new MoveInfo(sourceRow, sourceCol, destRow, destCol);
                    moveHistory.add(moveInfo);
                    saveMoveHistory();

                    // check for checkmate
                    if (isCheckmate(gameState)) {
                        System.out.println("checkmate");
                    }
                }
            } else {
                System.out.println("invalid move. please try again. Error in movePiece");
            }
        } else {
            System.out.println("no piece found or it's not your turn. please try again.");
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

        //chatGPT did this
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

        // ensure the correct pieces are obtained
        if (king.getColour().equals("white")) {
            if (destCol < sourceCol) {
                rook = chessboard[0][0].getPiece();
                rookOption = QUEENSIDE_WHITE; // moving left
            } else {
                rook = chessboard[0][7].getPiece();
                rookOption = KINGSIDE_WHITE; // moving right
            }
        } else { // king is black
            if (destCol < sourceCol) {
                rook = chessboard[7][0].getPiece();
                rookOption = QUEENSIDE_BLACK;
            } else {
                rook = chessboard[7][7].getPiece();
                rookOption = KINGSIDE_BLACK;
            }
        }

        // move the pieces for castling
        chessboard[sourceRow][sourceCol].removePiece();
        chessboard[destRow][destCol].setPiece(king);

        switch (rookOption) { // starting top left to bottom right
            case QUEENSIDE_WHITE:
                chessboard[0][0].removePiece();
                chessboard[0][3].setPiece(rook);
                break;
            case KINGSIDE_WHITE:
                chessboard[0][7].removePiece();
                chessboard[0][5].setPiece(rook);
                break;
            case QUEENSIDE_BLACK:
                chessboard[7][0].removePiece();
                chessboard[7][3].setPiece(rook);
                break;
            case KINGSIDE_BLACK:
                chessboard[7][7].removePiece();
                chessboard[7][5].setPiece(rook);
                break;
            default:
                System.out.println("something went wrong in the performing castling method switch case");
        }

        // update king's position and first move status
        if (king.getColour().equals("black")) {
            gameState.setBlackKingPosition(destRow, destCol);
        } else if (king.getColour().equals("white")) {
            gameState.setWhiteKingPosition(destRow, destCol);
        }

        king.setFirstMove(false);
        rook.setFirstMove(false);
    }


// reverts the chessboard after a move. this happens if the player moves a piece and puts itself into check.

    private void revertBoard(int sourceRow, int sourceCol, int destRow, int destCol) {

        // holding pieces
        Piece currentPiece = chessboard[destRow][destCol].getPiece();
        Piece previousPiece = chessboard[destRow][destCol].getPreviousPiece();

        // moves the piece
        chessboard[destRow][destCol].removePiece();
        chessboard[destRow][destCol].setPiece(previousPiece);

        // null check and re-add to player's list
        if (previousPiece != null) {
            if (previousPiece.getColour().equals("white")) {
                gameState.getWhitePieces().add(previousPiece);
            } else {
                gameState.getBlackPieces().add(previousPiece);
            }
        }

        // restore original piece position
        chessboard[sourceRow][sourceCol].setPiece(currentPiece);
    }


    // captures the piece by moving the current piece into the tiles previous piece object. removes the piece from the
    // list of available pieces then checks if that move puts the king into checkmate.
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


    // special method as a pawn can capture a piece that is not on its capture square.
    private void enPassantCapture(int sourceRow, int sourceCol, int destRow, int destCol, GameState gameState) {
        Piece piece = chessboard[sourceRow][sourceCol].getPiece();
        Piece capturedPiece = chessboard[sourceRow][destCol].getPiece(); // captured pawn is on the same row as the destination column

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


    // because the move can only be done directly after the pawn has moved, we need to reset the flags for each colour after its own move
    public void resetEnPassantFlags(List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece instanceof Pawn) {
                ((Pawn) piece).setJustMovedTwoSquares(false);
            }
        }
    }


    // changes the board letters to number system used in the boards array
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


    // displays the pieces as white + piece type. e.g. wP is white pawn.
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


    // checking if a white piece can capture the tile the black king is standing on

    private boolean isBlackKingInCheck(GameState gameState) {
        if (chessboard[gameState.getBlackKingRow()][gameState.getBlackKingCol()].getCanWhiteCapture()) {
            System.out.println("Black King is in check");
            return true;
        } else
            return false;
    }

    // checking if a black piece can capture the tile the white king is standing on

    private boolean isWhiteKingInCheck(GameState gameState) {
        if (chessboard[gameState.getWhiteKingRow()][gameState.getWhiteKingCol()].getCanBlackCapture()) {
            System.out.println("White King is in check");
            return true;
        } else
            return false;
    }

    //loops through all pieces and ensures if they are able to attack a tile, the tiles flag is set.
    private void setAllCaptureFlags(GameState gameState) {
        for (Piece piece : gameState.getWhitePieces()) {
            piece.canCapture(piece.getPieceRow(), piece.getPieceCol(), chessboard, gameState);
        }

        for (Piece piece : gameState.getBlackPieces()) {
            piece.canCapture(piece.getPieceRow(), piece.getPieceCol(), chessboard, gameState);
        }
    }

    // resets all capture flags. this is then
    private void clearAllCaptureFlags() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                chessboard[row][col].setCanBlackCapture(false);
                chessboard[row][col].setCanWhiteCapture(false);
            }
        }
    }


    public List<ChessTile> getPathBetweenAttackingPieceAndKing(GameState gameState, Piece attackingPiece) {
        List<ChessTile> path = new ArrayList<>();

        //this method should only be called on pieces that can attack along a rank/file e.g. queen, rook, bishop.

        int kingRow;
        int kingCol;

        if (attackingPiece.getColour().equals("white")) {
            kingRow = gameState.getBlackKingRow();
            kingCol = gameState.getBlackKingCol();
        } else {
            kingRow = gameState.getWhiteKingRow();
            kingCol = gameState.getWhiteKingCol();
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

                // For knights, no need to check the path
                if (piece.getType().equals("Knight")) {
                    System.out.println("Checkmate");
                    return true;
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
                System.out.println("Checkmate");
                return true;
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

                // For knights, no need to check the path
                if (piece.getType().equals("NKnight")) {
                    System.out.println("Checkmate");
                    return true;
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
                System.out.println("Checkmate");
                return true;
            }
        }

        return false;
    }


    //prints the move history by printing each line.
    //todo change because it is shit.
    public void printMoveHistory(List<String> moveHistory) {
        System.out.println("Move History:");

        for (int i = 0; i < moveHistory.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, moveHistory.get(i));
        }
    }

// saves the move history to a file

    private void saveMoveHistory() {
        try {
            FileIOManager fileIOManager = new FileIOManager();
            fileIOManager.writeMoveHistory("move_history.txt", moveHistory);
            System.out.println("move history saved successfully.");
        } catch (IOException e) {
            System.out.println("failed to save move history: " + e.getMessage());
        }
    }

// loads the move history from a file

    private List<String> loadMoveHistory() {
        try {
            FileIOManager fileIOManager = new FileIOManager();
            return fileIOManager.readMoveHistory("move_history.txt");
        } catch (IOException e) {
            System.out.println("failed to load move history: " + e.getMessage());
            return new ArrayList<>();
        }
    }

// saves the game state to a file

    public void saveGame(String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(this);
            System.out.println("game saved successfully.");
        } catch (IOException e) {
            System.out.println("failed to save the game: " + e.getMessage());
        }
    }

// loads a game state from a file

    public static Game loadGameFromFile(String filename) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            Game loadedGame = (Game) inputStream.readObject();
            System.out.println("game loaded successfully.");
            return loadedGame;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("failed to load the game: " + e.getMessage());
            return null;
        }
    }

// loads the user list from a file

    private void loadUserList() {
        try {
            FileIOManager fileIOManager = new FileIOManager();
            List<String> userLines = fileIOManager.readUserList("user_list.txt");

            for (String userLine : userLines) {
                String[] userFields = userLine.split(",");
                if (userFields.length >= 2) {
                    String name = userFields[0];
                    int elo = Integer.parseInt(userFields[1]);

                    User user = new User(name, elo);
                    userList.add(user);
                }
            }

            System.out.println("user list loaded successfully.");
        } catch (IOException e) {
            System.out.println("failed to load user list: " + e.getMessage());
        }
    }

// saves the user list to a file

    private void saveUserList() {
        try {
            FileIOManager fileIOManager = new FileIOManager();
            List<String> userLines = new ArrayList<>();

            for (User user : userList) {
                String userLine = user.getName() + "," + user.getElo();
                userLines.add(userLine);
            }

            fileIOManager.writeUserList("user_list.txt", userLines);

            System.out.println("user list saved successfully.");
        } catch (IOException e) {
            System.out.println("failed to save user list: " + e.getMessage());
        }
    }


}

