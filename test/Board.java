package test;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Map;

public class Board {

    private static final int BOARD_SIZE = 15;
    private static Board singleInstance = null;
    private final Tile[][] tiles;

    enum Bonuses {
        DoubleWord,
        DoubleLetter,
        TripleLetter,
        TripleWord,
        Star
    }

    private static final Dictionary<String, Bonuses> bonusSquares = Map.of(
            "0,3", DoubleLetter,
            "0,11", DoubleLetter,
            "2,6", DoubleLetter,
            "2,8", DoubleLetter,
            "3,0", DoubleLetter,
            "3,7", DoubleLetter,
            "3,14", DoubleLetter,
            "6,2", DoubleLetter,
            "6,6", DoubleLetter,
            "6,8", DoubleLetter,
            "6,12", DoubleLetter,
            "7,3", DoubleLetter,
            "7,11", DoubleLetter,
            "8,2", DoubleLetter,
            "8,6", DoubleLetter,
            "8,8", DoubleLetter,
            "8,12", DoubleWord,
            "11,0", DoubleLetter,
            "11,7", DoubleLetter,
            "11,14", DoubleLetter,
            "12,6", DoubleLetter,
            "12,8", DoubleLetter,
            "14,3", DoubleLetter,
            "14,11", DoubleLetter,
            "1,5", TripleLetter,
            "1,9", TripleLetter,
            "5,1", TripleLetter,
            "5,5", TripleLetter,
            "5,9", TripleLetter,
            "5,13", TripleLetter,
            "9,1", TripleLetter,
            "9,5", TripleLetter,
            "9,9", TripleLetter,
            "9,13", TripleLetter,
            "13,5", TripleLetter,
            "13,9", TripleLetter,
            "1,1", DoubleWord,
            "2,2", DoubleWord,
            "3,3", DoubleWord,
            "4,4", DoubleWord,
            "10,10", DoubleWord,
            "11,11", DoubleWord,
            "12,12", DoubleWord,
            "13,13", DoubleWord,
            "1,13", DoubleWord,
            "2,12", DoubleWord,
            "3,11", DoubleLetter,
            "4,10", DoubleLetter,
            "10,4", DoubleLetter,
            "11,3", DoubleWord,
            "12,2", DoubleWord,
            "13,1", DoubleWord,
            "7,7", Star,
            "0,0", TripleWord,
            "0,7", TripleWord,
            "0,14", TripleWord,
            "7,0", TripleWord,
            "7,14", TripleWord,
            "14,0", TripleWord,
            "14,7", TripleWord,
            "14,14", TripleWord

    );

    private Board() {
        this.tiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        // Initialize the board with null tiles
        for (Tile[] row : tiles) {
            Arrays.fill(row, null);
        }

    }

    public static Board getBoard() {
        if (singleInstance == null) {
            singleInstance = new Board();
        }
        return singleInstance;
    }

    public Tile[][] getTiles() {

        Tile[][] copy = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

    private boolean dictionaryLegal(Word word) {
        // Implementation: check if the word is legal according to the given dictionary
        return true;
    }

}
