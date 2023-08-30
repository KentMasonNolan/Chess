package CUI;

import java.io.Serializable;

// such a tiny class that I decided to keep in my
class MoveInfo implements Serializable {
    private int sourceRow;
    private int sourceCol;
    private int destRow;
    private int destCol;

// chatGPT wrote this but was less help that you would think
    private String convertToChessCoordinate(int row, int col) {
        char file = (char) ('h' -col);
        char rank = (char) ('1' + row);
        return String.valueOf(file) + rank;
    }

    @Override
    public String toString() {
        String sourceCoordinate = convertToChessCoordinate(sourceRow, sourceCol);
        String destCoordinate = convertToChessCoordinate(destRow, destCol);
        return String.format("%s -> %s", sourceCoordinate, destCoordinate);
    }
    public MoveInfo(int sourceRow, int sourceCol, int destRow, int destCol) {
        this.sourceRow = sourceRow;
        this.sourceCol = sourceCol;
        this.destRow = destRow;
        this.destCol = destCol;
    }
}
