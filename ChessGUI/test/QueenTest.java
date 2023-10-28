import ChessGUI.Queen;
import ChessGUI.ChessTile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class QueenTest {

    private ChessTile[][] chessboard;
    private Queen whiteQueen;

    @Before
    public void setUp() {
        chessboard = new ChessTile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                chessboard[row][col] = new ChessTile(row, col);
            }
        }
        whiteQueen = new Queen("white", 4, 4);
        chessboard[4][4].setPiece(whiteQueen);
    }

    @Test
    public void testHorizontalMove() {
        assertTrue(whiteQueen.isValidMove(4, 4, 4, 6, chessboard));
    }

    @Test
    public void testVerticalMove() {
        assertTrue(whiteQueen.isValidMove(4, 4, 6, 4, chessboard));
    }

    @Test
    public void testDiagonalMove() {
        assertTrue(whiteQueen.isValidMove(4, 4, 6, 6, chessboard));
    }

    @Test
    public void testInvalidMove() {
        assertFalse(whiteQueen.isValidMove(4, 4, 5, 6, chessboard));
    }
}
