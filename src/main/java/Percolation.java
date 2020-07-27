import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int OPEN = 1;
    private static final int BLOCK = 0;
    private final int n;
    private final int head;
    private final int bottom;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufBack;
    private final int status[][];
    private int openSize = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.head = n * n;
        this.bottom = n * n + 1;
        status = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                status[i][j] = BLOCK;
            }
        }
        uf = new WeightedQuickUnionUF(n * n + 2); //for virtual top and bottom node
        ufBack = new WeightedQuickUnionUF(n * n + 2);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(5);
            int col = StdRandom.uniform(5);
            percolation.open(row + 1, col + 1);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        argsCheck(row - 1, col - 1);
        if (status[row - 1][col - 1] == BLOCK) {
            status[row - 1][col - 1] = OPEN;
            openSize++;
            int product = toUFIndex(row - 1, col - 1);
            if (row == 1) {
                uf.union(product, head);
                ufBack.union(product, head);
            }
            if (row == n) {
                uf.union(product, bottom);
            }
            //left
            if (row - 2 >= 0 && status[row - 2][col - 1] == OPEN) {
                uf.union(product, toUFIndex(row - 2, col - 1));
                ufBack.union(product, toUFIndex(row - 2, col - 1));
            }
            //right
            if (row < n && status[row][col - 1] == OPEN) {
                uf.union(product, toUFIndex(row, col - 1));
                ufBack.union(product, toUFIndex(row, col - 1));
            }
            //up
            if (col - 2 >= 0 && status[row - 1][col - 2] == OPEN) {
                uf.union(product, toUFIndex(row - 1, col - 2));
                ufBack.union(product, toUFIndex(row - 1, col - 2));
            }
            //down
            if (col < n && status[row - 1][col] == OPEN) {
                uf.union(product, toUFIndex(row - 1, col));
                ufBack.union(product, toUFIndex(row - 1, col));
            }
        }
    }

    private int toUFIndex(int row, int col) {
        return n * row + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        argsCheck(row - 1, col - 1);
        return status[row - 1][col - 1] == OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        argsCheck(row - 1, col - 1);
        return (status[row - 1][col - 1] == OPEN) && (ufBack.find(toUFIndex(row - 1, col - 1)) == ufBack.find(head));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSize;
    }

    // does the system percolate?
    public boolean percolates() {
//        return uf.find(head) == uf.find(bottom);
        return uf.connected(head, bottom);
    }

    private void argsCheck(int row, int col) {
        if (row < 0 || col < 0 || row >= n || col >= n) {
            throw new IllegalArgumentException("row=" + row + ", col=" + col);
        }
    }
}
