import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
    private final int n;
    private final int[][] tiles;
    private final int[] goal;
    private int hamming;
    private int manhattan;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        goal = new int[n * n];
        for (int i = 0; i < n * n -1; i++) {
            goal[i] = i + 1;
        }
        goal[n * n - 1] = 0;

        int hammingResult = 0;
        int manhattanResult = 0;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];

                if (tiles[i][j] != goal[i * n + j] && tiles[i][j] != 0) {
                    hammingResult++;
                    int goalIdx = tiles[i][j] - 1;
                    int col =  goalIdx % n;
                    int row = goalIdx / n;
                    manhattanResult += (Math.abs(col - j) + Math.abs(row - i));
                }
            }
        }
        this.hamming = hammingResult;
        this.manhattan = manhattanResult;
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(tiles[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();

    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return this.hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return this.manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y instanceof Board) {
            Board other = (Board) y;
            return Arrays.deepEquals(tiles, other.tiles);
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int zeroRow = 0;
        int zeroCol = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }
        List<Board> result = new ArrayList<>();
        //Move up
        if (zeroRow > 0) {
            int[][] newTiles = arrayCopy(tiles);
            swap(newTiles, zeroRow, zeroCol, zeroRow - 1, zeroCol);
            result.add(new Board(newTiles));
        }
        //Move down
        if (zeroRow < n - 1) {
            int[][] newTiles = arrayCopy(tiles);
            swap(newTiles, zeroRow, zeroCol, zeroRow + 1, zeroCol);
            result.add(new Board(newTiles));
        }
        //Move left
        if (zeroCol > 0) {
            int[][] newTiles = arrayCopy(tiles);
            swap(newTiles, zeroRow, zeroCol, zeroRow, zeroCol - 1);
            result.add(new Board(newTiles));
        }
        //Move right
        if (zeroCol < n -1) {
            int[][] newTiles = arrayCopy(tiles);
            swap(newTiles, zeroRow, zeroCol, zeroRow, zeroCol + 1);
            result.add(new Board(newTiles));
        }
        return result;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        for (int i = 0; i < n * n - 1; i++) {
            int x = i / n;
            int y = i % n;
            int xx = (i + 1) / n;
            int yy = (i + 1) % n;
            if (tiles[x][y] != 0 && tiles[xx][yy] != 0) {
                int[][] twinTiles = arrayCopy(tiles);
                swap(twinTiles, x, y, xx, yy);
                return new Board(twinTiles);
            }
        }
        return null;
    }

    private void swap(int[][] array, int origRow, int origCol, int newRow, int newCol) {
        int tmp = array[origRow][origCol];
        array[origRow][origCol] = array[newRow][newCol];
        array[newRow][newCol] = tmp;
    }

    private int[][] arrayCopy(int[][] origin) {
        int[][] newTiles = new int[origin.length][];
        for (int i = 0; i < origin.length; i++) {
            newTiles[i] = Arrays.copyOf(origin[i], origin.length);
        }
        return newTiles;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board test = new Board(tiles);
        System.out.print(test);
        System.out.println(test.hamming());
        System.out.println(test.manhattan());
//        for (Board b : test.neighbors()) {
//            System.out.println("neighbor: " + b);
//        }
        Board twin = test.twin();
        System.out.println(twin);
    }

}