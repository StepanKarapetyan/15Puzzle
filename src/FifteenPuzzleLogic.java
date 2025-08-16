import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FifteenPuzzleLogic {

    private final int[][] BOARD = new int[4][4];
    private int emptyRow = 3;
    private int emptyCol = 3;

    public int[][] getBoard() {
        return BOARD;
    }

    public void initBoard() {
        int num = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                BOARD[i][j] = num++;
            }
        }
        BOARD[3][3] = 0;
        emptyRow = 3;
        emptyCol = 3;
    }

    public void shuffleBoard() {
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            List<int[]> moves = getPossibleMove();
            int[] move = moves.get(rand.nextInt(moves.size()));
            makeMove(move[0], move[1]);
        }
    }

    private List<int[]> getPossibleMove() {
        List<int[]> moves = new ArrayList<>();
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int[] d : dirs) {
            int newRow = emptyRow + d[0];
            int newCol = emptyCol + d[1];

            if (newRow >= 0 && newRow < 4 && newCol >= 0 && newCol < 4) {
                moves.add(new int[]{newRow, newCol});
            }
        }
        return moves;
    }

    public boolean makeMove(int row, int col) {
        if (isAdjacent(row, col, emptyRow, emptyCol)) {
            int temp = BOARD[row][col];
            BOARD[row][col] = 0;
            BOARD[emptyRow][emptyCol] = temp;
            emptyRow = row;
            emptyCol = col;
            return true;
        }
        return false;
    }

    private boolean isAdjacent(int row1, int col1, int row2, int col2) {
        return (Math.abs(row1 - row2) == 1 && col1 == col2) || (Math.abs(col1 - col2) == 1 && row1 == row2);
    }

    public boolean isSolved() {
        int expected = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 3 && j == 3) {
                    return BOARD[i][j] == 0;
                }
                if (BOARD[i][j] != expected++) {
                    return false;
                }
            }
        }
        return true;
    }
}