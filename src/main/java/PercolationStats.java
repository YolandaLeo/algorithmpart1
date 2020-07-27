import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double mean;
    private final double stddev;
    private final double trials;
    private final double[] result;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        result = new double[trials];
        for (int i = 0; i < trials; ++i) {
            Percolation p = new Percolation(n);
            do {
                int possibleRow = StdRandom.uniform(1, n + 1);
                int possibleCol = StdRandom.uniform(1, n + 1);
                p.open(possibleRow, possibleCol);
            } while (!p.percolates());
            result[i] = (double) (p.numberOfOpenSites()) / (double) (n * n);
        }
        mean = StdStats.mean(result);
        stddev = StdStats.stddev(result);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(
            Integer.parseInt(args[0]),
            Integer.parseInt(args[1])
        );
        System.out.println("Mean                    = " + ps.mean());
        System.out.println("StdDev                  = " + ps.stddev());
        System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - ((1.96 * stddev) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + ((1.96 * stddev) / Math.sqrt(trials));
    }

}
