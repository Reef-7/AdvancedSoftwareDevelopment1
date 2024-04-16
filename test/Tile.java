package test;

import java.util.Random;

public class Tile {

    public final char letter;
    public final int score;

    private Tile(char letter, int score) {

        this.letter = letter;
        this.score = score;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + letter;
        result = prime * result + score;
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || (getClass() != obj.getClass())) {
            return false;
        }
        Tile other = (Tile) obj;
        if (letter != other.letter || score != other.score) {
            return false;
        }
        return true;

    }

    public static class Bag {

        private final int[] quantities;
        private final Tile[] tiles;
        private static Bag singleInstance = null;

        private Bag() {

            this.quantities = new int[] { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2,
                    1 };
            this.tiles = new Tile[quantities.length];
            int[] values = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10 };
            for (int i = 0; i < tiles.length; i++) {
                tiles[i] = new Tile((char) ('A' + i), values[i]);
            }
        }

        public Tile getRand() {
            if (this.size() == 0) {
                return null;
            }
            Random rnd = new Random();
            int i = rnd.nextInt(this.quantities.length);

            while (quantities[i] == 0) {
                i = rnd.nextInt(this.quantities.length);
            }
            quantities[i]--;
            return tiles[i];

        }

        public Tile getTile(char letter) {
            if (letter < 'A' || letter > 'Z') {
                return null; // Return null for invalid characters
            }
            int i = (int) (letter - 'A');
            if (quantities[i] == 0) {
                return null;
            }
            quantities[i]--;
            return tiles[i];

        }

        public void put(Tile tile) { // * */
            int[] amounts = { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2,
                    1 };
            int i = (int) (tile.letter - 'A');
            if (quantities[i] < amounts[i]) {
                quantities[i]++;
            }
        }

        public int size() {
            // another option: maintain a variable size in the class Bag
            int amountsSum = 0;
            for (int i = 0; i < quantities.length; i++) {
                amountsSum += quantities[i];
            }
            return amountsSum;
        }

        public int[] getQuantities() {

            return quantities.clone();

        }

        public static Bag getBag() {
            if (singleInstance == null) {

                singleInstance = new Bag();
            }
            return singleInstance;

        }

    }
}
