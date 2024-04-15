package test;

public class Word {

    public final Tile[] tiles;
    public final int row;
    public final int col;
    public final boolean vertical;

    public Word(Tile[] tiles, int row, int col, boolean vertical) {

        this.tiles = tiles;
        this.row = row;
        this.col = col;
        this.vertical = vertical;
    }

    public Tile[] getTiles() {

        return this.tiles;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public boolean getVertical() {
        return this.vertical;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Word other = (Word) obj;
        if (tiles != other.tiles || row != other.row || col != other.col || vertical != other.vertical) {
            return false;
        }
        return true;
    }

}
