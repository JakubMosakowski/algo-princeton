package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int size;
    private boolean[][] grid;
    private WeightedQuickUnionUF algo;
    private WeightedQuickUnionUF backAlgo;
    private int top;
    private int bottom;
    private int openSites;

    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException("Size needs to be greater than 0!");

        size = n;
        grid = new boolean[n][n];
        algo = new WeightedQuickUnionUF(n * n + 2); // With top and bottom virtual sites
        backAlgo = new WeightedQuickUnionUF(n * n + 1);  // With top virtual site
        top = n * n;
        bottom = n * n + 1;
        openSites = 0;
    }

    public void open(int row, int col) {
        throwIfOutOfRange(row, col);
        if (isOpen(row, col)) {
            return;
        }

        openSite(row, col);

        if (row == 1) {
            algo.union(getIndex(row, col), top);
            backAlgo.union(getIndex(row, col), top);
        }

        if (row == size) {
            algo.union(getIndex(row, col), bottom);
        }

        unionTop(row, col);
        unionRight(row, col);
        unionBottom(row, col);
        unionLeft(row, col);
    }

    public boolean isOpen(int row, int col) {
        throwIfOutOfRange(row, col);
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        throwIfOutOfRange(row, col);
        return backAlgo.find(getIndex(row, col)) == backAlgo.find(top);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return algo.find(top) == algo.find(bottom);
    }

    private void unionTop(int row, int col) {
        if (row > 1 && isOpen(row - 1, col)) {
            algo.union(getIndex(row, col), getIndex(row - 1, col));
            backAlgo.union(getIndex(row, col), getIndex(row - 1, col));
        }
    }

    private void unionRight(int row, int col) {
        if (col < size && isOpen(row, col + 1)) {
            algo.union(getIndex(row, col), getIndex(row, col + 1));
            backAlgo.union(getIndex(row, col), getIndex(row, col + 1));
        }
    }

    private void unionBottom(int row, int col) {
        if (row < size && isOpen(row + 1, col)) {
            algo.union(getIndex(row, col), getIndex(row + 1, col));
            backAlgo.union(getIndex(row, col), getIndex(row + 1, col));
        }
    }

    private void unionLeft(int row, int col) {
        if (col > 1 && isOpen(row, col - 1)) {
            algo.union(getIndex(row, col), getIndex(row, col - 1));
            backAlgo.union(getIndex(row, col), getIndex(row, col - 1));
        }
    }

    private void openSite(int row, int col) {
        grid[row - 1][col - 1] = true;
        openSites++;
    }

    private int getIndex(int row, int col) {
        return (row - 1) * size + col - 1;
    }

    private void throwIfOutOfRange(int row, int col) {
        if (row < 1 || row > size)
            throw new IllegalArgumentException("Row is out of range: " + row);
        if (col < 1 || col > size)
            throw new IllegalArgumentException("Col is out of range: " + col);
    }
}
