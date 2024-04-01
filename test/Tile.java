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

    public static class Bag {

        int[] quantities;
        Tile[] tiles;
        private static Bag single_instance = null;

        private Bag() {

            this.quantities = new int[26];
            this.tiles = new Tile[26];

            int[] amounts = { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1 };
            int[] values = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10 };

            for (int i = 0; i < 26; i++) {
                quantities[i] = amounts[i];
                // tiles[i].letter = new Tile((char) ('a' + i));
                tiles[i] = new Tile((char) ('A' + i), values[i]);

            }
        }

        public Tile getRand() {
            double x = Math.floor(Math.random() * 26);
            int i = (int) x;
            if (quantities[i] == 0) {
                return null;
            }
            quantities[i]--;
            return tiles[i];

        }

        public Tile getTile(char letter) {
            int i = (int) (letter) - 65;
            if (quantities[i] == 0) {
                return null;
            }
            quantities[i]--;
            return tiles[i];

        }

        public void put(Tile tile) {
            int i = (int) (tile.letter) - 65;
            quantities[i]++;
        }

        public int size() {
            int amounts_sum = 0;
            for (int i = 0; i < 26; i++) {
                amounts_sum += quantities[i];
            }
            return amounts_sum;
        }

        public int[] getQuantities() {
            int[] quantities_copy = (int[]) (quantities.clone());
            return quantities_copy;

        }

        public static Bag getBag() {
            if (single_instance == null) {

                single_instance = new Bag();
            }
            return single_instance;

        }

    }
}
