package percolation;

public class PercolationDrawer {

    static void draw(int n, Percolation percolation) {
        System.out.println("----------");

        for (int i = 0; i < n; i++) {
            System.out.print("\uD83D\uDCD8");
        }

        System.out.print("\n");
        for (int i = 0; i < n; i++) {
            System.out.print("↕️");
        }
        System.out.print("\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (percolation.isFull(i + 1, j + 1)) {
                    System.out.print("\uD83D\uDCD8");
                }
                else if (percolation.isOpen(i + 1, j + 1)) {
                    System.out.print("\uD83D\uDCD4");
                }
                else {
                    System.out.print("\uD83D\uDCD3");
                }
            }
            System.out.println("");
        }

        for (int i = 0; i < n; i++) {
            System.out.print("↕️");
        }
        System.out.print("\n");

        for (int i = 0; i < n; i++) {
            System.out.print("\uD83D\uDCD4");
        }

        System.out.println("");
    }
}
