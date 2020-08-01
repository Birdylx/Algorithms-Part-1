/* *****************************************************************************
 *  Name:              XiaoLiu
 *  Coursera User ID:  None
 *  Last modified:     TODO
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] threshArray;
    private final double CONFIDENCE95 = 1.96;
    private int T;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        threshArray = new double[trials];
        T = trials;

        for (int t = 0; t < trials; t++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                while (p.isOpen(row, col)) {
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);
                }
                p.open(row, col);
            }
            double thresh = (double) p.numberOfOpenSites() / ((double) n * (double) n);
            threshArray[t] = thresh;
        }
    }

    public double mean() {
        return StdStats.mean(threshArray);
    }

    public double stddev() {
        return StdStats.stddev(threshArray);
    }

    public double confidenceLo() {
        return mean() - CONFIDENCE95 * stddev() / Math.sqrt(T);
    }

    public double confidenceHi() {
        return mean() + CONFIDENCE95 * stddev() / Math.sqrt(T);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, T);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + "["
                               + ps.confidenceLo() + ", "
                               + ps.confidenceHi() + "]");
    }
}
