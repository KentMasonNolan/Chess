package CUI;

import java.io.Serializable;

// such a tiny class that I decided to keep in my
class MoveInfo implements Serializable {
    private int sourceRow;
    private int sourceCol;
    private int destRow;
    private int destCol;

    @Override
    public String toString() {
        return String.format("(%d, %d) -> (%d, %d)", sourceRow, sourceCol, destRow, destCol);
    }

    public MoveInfo(int sourceRow, int sourceCol, int destRow, int destCol) {
        this.sourceRow = sourceRow;
        this.sourceCol = sourceCol;
        this.destRow = destRow;
        this.destCol = destCol;
    }
}
