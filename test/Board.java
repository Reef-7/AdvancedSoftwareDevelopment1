package test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Board implements Serializable {
    private static Board single_instance = null;
    private final static int BOARD_HEIGHT = 15;
    private final static int BOARD_WIDTH = 15;
    private final Tile[][] board = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
    private static final HashMap<String, String> boardScores = new HashMap<>();
    private final ArrayList<Word> foundWords = new ArrayList<>();

    private Board() {
        initBoardScores();
    }

    public static Board getBoard() {
        if (single_instance == null)
            single_instance = new Board();
        return single_instance;
    }

    private void initBoardScores() {
        // STAR BLOCK
        boardScores.put("0707", "2W");

        // TRIPLE WORD
        boardScores.put("0000", "3W");
        boardScores.put("0700", "3W");
        boardScores.put("0007", "3W");
        boardScores.put("0014", "3W");
        boardScores.put("1400", "3W");
        boardScores.put("1407", "3W");
        boardScores.put("0714", "3W");
        boardScores.put("1414", "3W");

        // DOUBLE WORD
        boardScores.put("0101", "2W");
        boardScores.put("0202", "2W");
        boardScores.put("0303", "2W");
        boardScores.put("0404", "2W");
        boardScores.put("0113", "2W");
        boardScores.put("0212", "2W");
        boardScores.put("0311", "2W");
        boardScores.put("0410", "2W");
        boardScores.put("1301", "2W");
        boardScores.put("1202", "2W");
        boardScores.put("1103", "2W");
        boardScores.put("1004", "2W");
        boardScores.put("1010", "2W");
        boardScores.put("1111", "2W");
        boardScores.put("1212", "2W");
        boardScores.put("1313", "2W");

        // TRIPLE LETTER
        boardScores.put("0501", "3L");
        boardScores.put("0901", "3L");
        boardScores.put("0105", "3L");
        boardScores.put("0505", "3L");
        boardScores.put("0905", "3L");
        boardScores.put("1305", "3L");
        boardScores.put("0109", "3L");
        boardScores.put("0509", "3L");
        boardScores.put("0909", "3L");
        boardScores.put("1309", "3L");
        boardScores.put("0513", "3L");
        boardScores.put("0913", "3L");

        // DOUBLE LETTER
        boardScores.put("0300", "2L");
        boardScores.put("1100", "2L");
        boardScores.put("0602", "2L");
        boardScores.put("0802", "2L");
        boardScores.put("0003", "2L");
        boardScores.put("0703", "2L");
        boardScores.put("1403", "2L");
        boardScores.put("0206", "2L");
        boardScores.put("0606", "2L");
        boardScores.put("0806", "2L");
        boardScores.put("1206", "2L");
        boardScores.put("0307", "2L");
        boardScores.put("1107", "2L");
        boardScores.put("0208", "2L");
        boardScores.put("0608", "2L");
        boardScores.put("0808", "2L");
        boardScores.put("1208", "2L");
        boardScores.put("0011", "2L");
        boardScores.put("0711", "2L");
        boardScores.put("1411", "2L");
        boardScores.put("0612", "2L");
        boardScores.put("0812", "2L");
        boardScores.put("0314", "2L");
        boardScores.put("1114", "2L");
    }

    private int getScore(Word word) {
        int row = word.getRow();
        int col = word.getCol();
        int wordLength = word.getTiles().length;

        int score = 0;
        int wordMultiplier = 1;
        int tileMultiplier = 1;

        for (int i = 0; i < wordLength; i++) {
            String boardRef;
            if (word.getVertical()) {
                boardRef = getBoardRefString(row + i, col);
            } else {
                boardRef = getBoardRefString(row, col + i);
            }
            if (Objects.equals(boardScores.get(boardRef), "2W")) {
                wordMultiplier = wordMultiplier * 2;
            }
            if (Objects.equals(boardScores.get(boardRef), "3W")) {
                wordMultiplier = wordMultiplier * 3;
            }
            if (Objects.equals(boardScores.get(boardRef), "2L")) {
                tileMultiplier = 2;
            }
            if (Objects.equals(boardScores.get(boardRef), "3L")) {
                tileMultiplier = 3;
            }
            score += word.getTiles()[i].score * tileMultiplier;
            if (boardRef.equals("0707")) {
                boardScores.remove("0707");
            }
            tileMultiplier = 1;

        }
        score = score * wordMultiplier;
        return score;
    }

    private String getBoardRefString(int r, int c) {
        String row, col;
        if (r < 10)
            row = String.format("%02d", r);
        else
            row = String.valueOf(r);
        if (c < 10)
            col = String.format("%02d", c);
        else
            col = String.valueOf(c);

        return row + col;
    }

    public Tile[][] getTiles() {
        Tile[][] boardCopy = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(this.board[i], 0, boardCopy[i], 0, this.board[0].length);
        }
        return boardCopy;
    }

    public boolean boardLegal(Word word) {
        if (isBoardEmpty() && isWordOnStarSquare(word) && wordIsInBounds(word))
            return true;
        return wordIsInBounds(word) && wordHasAdjacentOrOverlappingTile(word) && noChangeOfTilesNeeded(word);
    }

    private boolean isBoardEmpty() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                if (board[i][j] != null)
                    return false;
            }
        }
        return true;
    }

    private boolean isWordOnStarSquare(Word word) {
        int wordLength = word.getTiles().length;
        if (word.getVertical()) {
            if (word.getCol() == 7)
                return word.getRow() + wordLength > 7;
        } else {
            if (word.getRow() == 7)
                return word.getCol() + wordLength > 7;
        }
        return false;
    }

    private boolean wordIsInBounds(Word word) {
        if (word.allTilesAreNulls())
            return false;
        int row = word.getRow();
        int col = word.getCol();
        int wordLength = word.getTiles().length;

        if (row < 0 || row >= BOARD_HEIGHT || col < 0 || col >= BOARD_WIDTH)
            return false;

        if (word.getVertical())
            return word.getRow() + wordLength <= BOARD_HEIGHT;
        else
            return word.getCol() + wordLength <= BOARD_WIDTH;
    }

    private boolean wordHasAdjacentOrOverlappingTile(Word word) {
        if (word.getVertical())
            return checkAdjacencyForVertical(word);
        else
            return checkAdjacencyForHorizontal(word);
    }

    private boolean checkAdjacencyForVertical(Word word) {
        int row = word.getRow();
        int col = word.getCol();
        int wordLength = word.getTiles().length;

        if (row - 1 >= 0 && board[row - 1][col] != null)
            return true;

        if (row + wordLength + 1 < BOARD_HEIGHT && board[row + wordLength + 1][col] != null)
            return true;

        for (int i = 0; i < wordLength; i++) {

            if (col >= 1 && col <= 13) {
                if (board[row + i][col - 1] != null || board[row + i][col + 1] != null)
                    return true;
            }

            if (col == BOARD_WIDTH - 1) {
                if (board[row + i][col - 1] != null)
                    return true;
            }

            if (col == 0) {
                if (board[row + i][col + 1] != null)
                    return true;
            }
        }

        return false;
    }

    private boolean checkAdjacencyForHorizontal(Word word) {
        int row = word.getRow();
        int col = word.getCol();
        int wordLength = word.getTiles().length;

        if (col - 1 >= 0 && board[row][col - 1] != null)
            return true;

        if (col + wordLength + 1 < BOARD_WIDTH && board[row][col + wordLength + 1] != null)
            return true;

        for (int i = 0; i < wordLength; i++) {

            if (row >= 1 && row <= 13) {
                if (board[row - 1][col + i] != null || board[row + 1][col + i] != null)
                    return true;
            }

            if (row == BOARD_HEIGHT - 1) {
                if (board[row - 1][col + i] != null)
                    return true;
            }

            if (row == 0) {
                if (board[row + 1][col + i] != null)
                    return true;
            }
        }

        return false;
    }

    private boolean noChangeOfTilesNeeded(Word word) {
        Tile[][] boardCopy = getTiles();
        addWordToBoard(word, boardCopy);

        int row = word.getRow();
        int col = word.getCol();
        int wordLength = word.getTiles().length;
        int countOfSameLetters = 0;

        for (int i = 0; i < wordLength; i++) {
            if (word.getVertical()) {
                if (word.getTiles()[i] == null || board[row + i][col] == null)
                    continue;
                if (boardCopy[row + i][col] == board[row + i][col]) {
                    countOfSameLetters++;
                    continue;
                }
            } else {
                if (word.getTiles()[i] == null || board[row][col + i] == null)
                    continue;
                if (boardCopy[row][col + i] == board[row][col + i]) {
                    countOfSameLetters++;
                    continue;
                }
            }
            return false;
        }
        return wordLength != countOfSameLetters;
    }

    public boolean dictionaryLegal(Word word) {
        return true;
    }

    private ArrayList<Word> getWords(Word word) {
        ArrayList<Word> newWords = new ArrayList<>();

        if (isBoardEmpty()) {

            if (!isWordComplete(word)) {
                return newWords;
            }
            newWords.add(word);
            this.foundWords.add(word);
            return newWords;
        }

        Word completeWord = findCompleteWord(word);
        if (completeWord.hasNullTiles()) {
            return newWords;
        }
        newWords.add(completeWord);

        findNewWords(word, newWords);
        this.foundWords.addAll(newWords);

        return newWords;
    }

    private boolean isWordComplete(Word word) {
        int wordLength = word.getTiles().length;
        for (int i = 0; i < wordLength; i++) {
            if (word.getTiles()[i] == null)
                return false;
        }
        return true;
    }

    private void findNewWords(Word word, ArrayList<Word> words) {
        if (word.getVertical())
            findHorizontalNewWords(word, words);

        if (!word.getVertical())
            findVerticalNewWords(word, words);
    }

    private void findHorizontalNewWords(Word word, ArrayList<Word> words) {
        Tile[][] boardCopy = getTiles();
        addWordToBoard(word, boardCopy);
        int row = word.getRow();
        int col = word.getCol();
        int wordLength = word.getTiles().length;
        int j, length, wordStart;

        for (int i = 0; i < wordLength; i++) {
            wordStart = col;
            j = 1;
            length = 1;
            while (col - j >= 0 && boardCopy[row + i][col - j] != null) {
                wordStart = col - j;
                j++;
                length++;
            }
            j = 1;
            while (col + j < BOARD_WIDTH && boardCopy[row + i][col + j] != null) {
                j++;
                length++;
            }
            if (length > 1) {
                Tile[] tiles = new Tile[length];
                System.arraycopy(boardCopy[row + i], wordStart, tiles, 0, length);
                Word newWord = new Word(tiles, row + i, wordStart, false);
                if (!wordWasAlreadyFound(newWord)) {
                    words.add(newWord);
                }
            }
        }
    }

    private void findVerticalNewWords(Word word, ArrayList<Word> words) {
        Tile[][] boardCopy = getTiles();
        addWordToBoard(word, boardCopy);
        int row = word.getRow();
        int col = word.getCol();
        int wordLength = word.getTiles().length;
        int j, length, wordStart;

        for (int i = 0; i < wordLength; i++) {
            wordStart = row;
            j = 1;
            length = 1;
            while (row - j >= 0 && boardCopy[row - j][col + i] != null) {
                wordStart = row - j;
                j++;
                length++;
            }
            j = 1;
            while (row + j < BOARD_HEIGHT && boardCopy[row + j][col + i] != null) {
                j++;
                length++;
            }
            if (length > 1) {
                Tile[] tiles = new Tile[length];
                for (int k = 0; k < length; k++) {
                    tiles[k] = boardCopy[wordStart + k][col + i];
                }
                Word newWord = new Word(tiles, wordStart, col + i, true);
                if (!wordWasAlreadyFound(newWord)) {
                    words.add(newWord);
                }
            }
        }
    }

    private boolean wordWasAlreadyFound(Word word) {
        int row = word.getRow();
        int col = word.getCol();
        int wordLength = word.getTiles().length;

        for (Word foundWord : foundWords) {
            if (foundWord.getRow() == row && foundWord.getCol() == col && foundWord.getTiles().length == wordLength) {
                return true;
            }
        }
        return false;
    }

    private Word findCompleteWord(Word word) {
        int row = word.getRow();
        int col = word.getCol();
        int wordLength = word.getTiles().length;
        Tile[] tiles = new Tile[wordLength];
        if (word.getVertical()) {
            for (int i = 0; i < wordLength; i++) {
                if (word.getTiles()[i] == null)
                    tiles[i] = board[row + i][col];
                else
                    tiles[i] = word.getTiles()[i];
            }
        } else {
            for (int i = 0; i < wordLength; i++) {
                if (word.getTiles()[i] == null)
                    tiles[i] = board[row][col + i];
                else
                    tiles[i] = word.getTiles()[i];
            }
        }
        word.setTiles(tiles);
        return word;
    }

    public int tryPlaceWord(Word newWord) {
        if (!boardLegal(newWord))
            return -1;

        ArrayList<Word> createdWords = getWords(newWord);

        for (Word word : createdWords)
            if (!dictionaryLegal(word))
                return 0;
        addWordToBoard(newWord, this.board);
        int score = 0;
        for (Word word : createdWords) {
            score += getScore(word);
        }

        return score;
    }

    private void addWordToBoard(Word word, Tile[][] board) {
        int row = word.getRow();
        int col = word.getCol();
        int wordLength = word.getTiles().length;

        if (word.getVertical()) {
            for (int i = 0; i < wordLength; i++) {
                board[row + i][col] = word.getTiles()[i];
            }
        } else {
            for (int i = 0; i < wordLength; i++) {
                board[row][col + i] = word.getTiles()[i];
            }
        }
    }
}