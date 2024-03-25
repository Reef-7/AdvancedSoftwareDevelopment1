package test;

public class Tile {

    public final char letter;
    public final int score;

    private Tile(char letter, int score) {

        this.letter = letter;
        this.score = score;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    public class Bag {

        int[] quantities;
        Tile[] tiles;

        private Bag() {
            this.quantities = new int[26];
            this.tiles = new Tile[26];
        }
    }
}
