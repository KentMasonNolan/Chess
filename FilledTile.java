public class FilledTile extends ChessTile{
    public FilledTile(int tileNumber) {
        super(tileNumber);
    }

    @Override
    public boolean isTileFull() {
        return true;
    }
}
