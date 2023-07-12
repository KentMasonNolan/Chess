public class EmptyTile extends ChessTile{
    public EmptyTile(int tileNumber) {
        super(tileNumber);
    }

    @Override
    public boolean isTileFull() {
        return false;
    }
}
