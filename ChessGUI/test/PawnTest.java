import ChessGUI.Pawn;
import ChessGUI.ChessTile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PawnTest {

    private ChessTile[][] chessboard;
    private Pawn whitePawn;
    private Pawn blackPawn;

    @Before
    public void setUp() {
        chessboard = new ChessTile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                chessboard[row][col] = new ChessTile(row, col);
            }
        }
        whitePawn = new Pawn("white", 1, 1);
        blackPawn = new Pawn("black", 6, 1);
        chessboard[1][1].setPiece(whitePawn);
        chessboard[6][1].setPiece(blackPawn);
    }

    @Test
    public void testWhitePawnFirstMoveOneSquare() {
        assertTrue(whitePawn.isValidMove(1, 1, 2, 1, chessboard));
    }

    @Test
    public void testWhitePawnFirstMoveTwoSquares() {
        assertTrue(whitePawn.isValidMove(1, 1, 3, 1, chessboard));
    }

    @Test
    public void testWhitePawnBlockedMove() {
        Pawn blockingPawn = new Pawn("black", 2, 1);
        chessboard[2][1].setPiece(blockingPawn);
        assertFalse(whitePawn.isValidMove(1, 1, 2, 1, chessboard));
    }

    @Test
    public void testWhitePawnBlockedTwoSquaresMove() {
        Pawn blockingPawn = new Pawn("black", 2, 1);
        chessboard[2][1].setPiece(blockingPawn);
        assertFalse(whitePawn.isValidMove(1, 1, 3, 1, chessboard));
    }

    @Test
    public void testBlackPawnFirstMoveOneSquare() {
        assertTrue(blackPawn.isValidMove(6, 1, 5, 1, chessboard));
    }

    @Test
    public void testBlackPawnFirstMoveTwoSquares() {
        assertTrue(blackPawn.isValidMove(6, 1, 4, 1, chessboard));
    }

    @Test
    public void testBlackPawnBlockedMove() {
        Pawn blockingPawn = new Pawn("white", 5, 1);
        chessboard[5][1].setPiece(blockingPawn);
        assertFalse(blackPawn.isValidMove(6, 1, 5, 1, chessboard));
    }

    @Test
    public void testBlackPawnBlockedTwoSquaresMove() {
        Pawn blockingPawn = new Pawn("white", 5, 1);
        chessboard[5][1].setPiece(blockingPawn);
        assertFalse(blackPawn.isValidMove(6, 1, 4, 1, chessboard));
    }
    
    // Add a test for pawn promotion once that functionality is complete.

}
