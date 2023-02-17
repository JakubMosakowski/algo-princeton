package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private int trials;
    private double[] results;

    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("N needs to be greater than 0");
        }

        if (trials <= 0) {
            throw new IllegalArgumentException("Trials number needs to be greater than 0");
        }

        this.trials = trials;
        results = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int num = StdRandom.uniformInt(n * n);
                int row = num / n + 1;
                int col = num % n + 1;
                percolation.open(row, col);
            }
            results[i] = ((double) percolation.numberOfOpenSites() / (n * n));
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);

        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + "[" + stats.confidenceLo() + ", "
                                   + stats.confidenceHi() + "]");
    }
}
