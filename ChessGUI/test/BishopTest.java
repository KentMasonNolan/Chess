import ChessGUI.Bishop;
import ChessGUI.ChessTile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BishopTest {

    private ChessTile[][] chessboard;
    private Bishop whiteBishop;

    @Before
    public void setUp() {
        chessboard = new ChessTile[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                chessboard[row][col] = new ChessTile(row, col);
            }
        }
        whiteBishop = new Bishop("white", 4, 4);
        chessboard[4][4].setPiece(whiteBishop);
    }

    @Test
    public void testDiagonalMove() {
        assertTrue(whiteBishop.isValidMove(4, 4, 6, 6, chessboard));
    }

    @Test
    public void testInvalidHorizontalMove() {
        assertFalse(whiteBishop.isValidMove(4, 4, 4, 6, chessboard));
    }

    @Test
    public void testInvalidVerticalMove() {
        assertFalse(whiteBishop.isValidMove(4, 4, 6, 4, chessboard));
    }

    @Test
    public void testInvalidRandomMove() {
        assertFalse(whiteBishop.isValidMove(4, 4, 5, 6, chessboard));
    }
}
