/* *****************************************************************************
 *  Name:              XiaoLiu
 *  Coursera User ID:  None
 *  Last modified:     TODO
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf1;
    private WeightedQuickUnionUF uf2;
    private boolean[] status;
    private int size;
    private int numOpensites;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        size = n;
        numOpensites = 0;
        uf1 = new WeightedQuickUnionUF(n * n + 2); // add top and bottom node
        uf2 = new WeightedQuickUnionUF(n * n + 1);
        status = new boolean[n * n + 2];

        // initialize the status
        status[0] = true;
        status[n * n + 1] = true;
        for (int i = 1; i < n * n + 1; i++) {
            status[i] = false;
        }
    }

    public void open(int row, int col) {
        checkXY(row, col);
        int pos = xyTo1D(row, col);
        if (!status[pos]) {
            status[pos] = true;
            numOpensites += 1;

            // check up,down,left,right sites status, if opened, union them
            // first check the if it is on the top or bottom row, if it is, connect to the virtul site
            if (row == 1) {
                uf1.union(0, pos);
                uf2.union(0, pos);
            }
            if (row == size) {
                uf1.union(pos, size * size + 1);
            }
            if (row > 1 && isOpen(row - 1, col)) {
                uf1.union(pos, xyTo1D(row - 1, col));
                uf2.union(pos, xyTo1D(row - 1, col));
            }
            if (row < size && isOpen(row + 1, col)) {
                uf1.union(pos, xyTo1D(row + 1, col));
                uf2.union(pos, xyTo1D(row + 1, col));
            }
            if (col > 1 && isOpen(row, col - 1)) {
                uf1.union(pos, xyTo1D(row, col - 1));
                uf2.union(pos, xyTo1D(row, col - 1));
            }
            if (col < size && isOpen(row, col + 1)) {
                uf1.union(pos, xyTo1D(row, col + 1));
                uf2.union(pos, xyTo1D(row, col + 1));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        checkXY(row, col);
        int pos = xyTo1D(row, col);
        return status[pos];
    }

    public boolean isFull(int row, int col) {
        checkXY(row, col);
        int pos = xyTo1D(row, col);
        return isOpen(row, col) && uf2.find(pos) == uf2.find(0); // if connected to the top node
    }

    public int numberOfOpenSites() {
        return numOpensites;
    }

    public boolean percolates() {
        return uf1.find(size * size + 1) == uf1.find(0);
    }

    private int xyTo1D(int x, int y) {
        checkXY(x, y);
        return (x - 1) * size + y;
    }

    private void checkXY(int x, int y) {
        // check x and y
        if (x > size || x <= 0)
            throw new IllegalArgumentException();
        if (y > size || y <= 0)
            throw new IllegalArgumentException();
    }

    // client test API
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        p.open(2, 3);
        StdOut.println(p.isFull(2, 3));
        StdOut.println(p.isOpen(2, 3));
        StdOut.println(p.percolates());
    }

}
