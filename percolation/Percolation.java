package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF unionFind;
    private boolean[][] grid;
    private int n;
    private int top;
    private int bottom;
    private int numberOfOpenSites;

    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n needs to be greater than 0");
        }
        this.n = n;
        grid = new boolean[n][n];
        unionFind = new WeightedQuickUnionUF(n * n + 2);
        top = n * n;
        bottom = n * n + 1;
        numberOfOpenSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        System.out.println("Row: " + row + ", Column: " + col);
        throwIfOutOfRange(row, col);
        if (grid[row - 1][col - 1]) {
            return;
        }

        openSite(row, col);

        if (row == 1) {
            unionFind.union(getIndex(row, col), top);
        }
        if (row == n) {
            unionFind.union(getIndex(row, col), bottom);
        }

        unionTop(row, col);
        unionRight(row, col);
        unionBottom(row, col);
        unionLeft(row, col);
        draw();
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        throwIfOutOfRange(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        throwIfOutOfRange(row, col);
        return unionFind.find(getIndex(row, col)) == unionFind.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.find(bottom) == unionFind.find(top);
    }

    private void draw() {
        System.out.println("");
        System.out.println("");
        for (int i = 0; i < n; i++) {
            System.out.print("\uD83D\uDCD8");
        }
        System.out.println("\n----");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isFull(i + 1, j + 1)) {
                    System.out.print("\uD83D\uDCD8");
                }
                else if (isOpen(i + 1, j + 1)) {
                    System.out.print("\uD83D\uDCD4");
                }
                else {
                    System.out.print("\uD83D\uDCD3");
                }
            }
            System.out.println("");
        }
        System.out.print("----\n");
        for (int i = 0; i < n; i++) {
            System.out.print("\uD83D\uDCD8");
        }
        System.out.println("");
    }

    private void unionTop(int row, int col) {
        if (row > 1 && isOpen(row - 1, col)) {
            unionFind.union(getIndex(row, col), getIndex(row - 1, col));
        }
    }

    private void unionRight(int row, int col) {
        if (col < n && isOpen(row, col + 1)) {
            unionFind.union(getIndex(row, col), getIndex(row, col + 1));
        }
    }

    private void unionBottom(int row, int col) {
        if (row < n && isOpen(row + 1, col)) {
            unionFind.union(getIndex(row, col), getIndex(row + 1, col));
        }
    }

    private void unionLeft(int row, int col) {
        if (col > 1 && isOpen(row, col - 1)) {
            unionFind.union(getIndex(row, col), getIndex(row, col - 1));
        }
    }

    private void openSite(int row, int col) {
        grid[row - 1][col - 1] = true;
        numberOfOpenSites++;
    }

    private int getIndex(int row, int col) {
        return (row - 1) * n + col - 1;
    }

    private void throwIfOutOfRange(int row, int col) {
        if (row < 1 || row > n)
            throw new IllegalArgumentException("Row needs to be in range 1 to " + n);
        if (col < 1 || col > n)
            throw new IllegalArgumentException("Column needs to be in range 1 to " + n);
    }
}

