package CUI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameState implements Serializable {

    Set<Piece> whiteCapturablePieces = new HashSet<>();
    Set<Piece> blackCapturablePieces = new HashSet<>();

    List<Piece> blackPieces = new ArrayList<>();
    List<Piece> whitePieces = new ArrayList<>(); // Initialize the list here


    public List<Piece> getBlackPieces() {
        return blackPieces;
    }

    public void setBlackPieces(List<Piece> blackPieces) {
        this.blackPieces = blackPieces;
    }

    public List<Piece> getWhitePieces() {
        return whitePieces;
    }

    public void setWhitePieces(List<Piece> whitePieces) {
        this.whitePieces = whitePieces;
    }

    private ChessTile[][] chessboard;
    public String currentPlayer;
    private boolean isCheck;
    protected boolean isCheckmate;

    private int whiteKingRow;
    private int whiteKingCol;
    private int blackKingRow;
    private int blackKingCol;


    public int getWhiteKingRow() {
        return whiteKingRow;
    }

    public int getWhiteKingCol() {
        return whiteKingCol;
    }

    public int getBlackKingRow() {
        return blackKingRow;
    }

    public int getBlackKingCol() {
        return blackKingCol;
    }

    public void setWhiteKingPosition(int row, int col) {
        whiteKingRow = row;
        whiteKingCol = col;
    }

    public void setBlackKingPosition(int row, int col) {
        blackKingRow = row;
        blackKingCol = col;
    }


    public boolean isCheckmate() {
        return isCheckmate;
    }

    public void setCheckmate(boolean checkmate) {
        isCheckmate = checkmate;
    }

    public void switchPlayer() {
        if (currentPlayer.equals("white")) {
            currentPlayer = "black";
        } else {
            currentPlayer = "white";
        }
    }

    public GameState() {
        currentPlayer = "white";
    }


    protected boolean isKingInCheck(String kingcolour) {
        //check if king is in check
        return false;
    }


}

