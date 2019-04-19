import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class BubbleGrid {
    private int[][] grid;
    private int gridXlength;
    private int gridYlength;
    private int gridSites;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        gridXlength = grid[0].length;
        gridYlength = grid.length;
        gridSites = gridXlength * gridYlength + 2;
        // Extra site for Disjoint set use: 0 = Top and gridSites - 1 = Bottom
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        // TODO
        int[] fallBubble = new int[darts.length];

        // Create new grid and copy, which is used to record the impact of dart
        int[][] tempGrid = new int[gridYlength][gridXlength];
        for (int j = 0; j < gridYlength; j += 1) {
            for (int i = 0; i < gridXlength; i += 1) {
                tempGrid[j][i] = grid[j][i];
            }
        }

        // Examine every dart impact
        for (int i = 0; i < darts.length; i += 1) {
            fallBubble[i] = fallHelper(tempGrid, darts[i][0], darts[i][1]);
        }
        return fallBubble;
    }

    // Helper method for counting
    private int fallHelper(int[][] grid, int dartRow, int dartCol) {
        int fallCNT = 0;
        grid[dartRow][dartCol] = 0;

        WeightedQuickUnionUF wqu = new WeightedQuickUnionUF(gridSites);
        for (int j = 0; j < gridYlength; j += 1) {
            for (int i = 0; i < gridXlength; i += 1) {
                int cur = xyToInt(j, i);
                // if there is bubble, connect to Top or nearest bubble; otherwise, connect to bottom
                if (grid[j][i] == 1) {
                    // If at Row 0, connect to 0; otherwise, find nearby bubble for connection
                    if (j == 0) {
                        wqu.union(0, cur);
                    } else {
                        int connectTo = anyBubble(grid, j, i);
                        if (connectTo != 0) {
                            wqu.union(connectTo, cur);
                        }
                    }
                } else {
                    wqu.union(gridSites - 1, cur);
                }
            }
        }
        // If there is any bubble which parent is not Top(0), it will fall
        for (int j = 0; j < gridYlength; j += 1) {
            for (int i = 0; i < gridXlength; i += 1) {
                int val = xyToInt(j, i);
                if (grid[j][i] == 1 && !wqu.connected(0,val)) {
                    fallCNT += 1;
                }
            }
        }
        return fallCNT;
    }

    // Helper method for check any bubble nearby input x-y could be connected
    private int anyBubble(int[][] grid, int row, int col) {
        int val = 0;
        // Examine priority: Up / Left / Right
        if (row - 1 >= 0 && grid[row - 1][col] == 1) {
            val = xyToInt(row - 1, col);
        } else if (col - 1 >= 0 && grid[row][col - 1] == 1){
            val = xyToInt(row, col - 1);
        } else if (col + 1 < gridXlength - 1 && grid[row][col + 1] == 1) {
            val = xyToInt(row, col + 1);
        }
        return val;
    }

    // Helper method for transfer x-y to int
    private int xyToInt(int row, int column) {
        return (row * gridXlength + column + 1);
    }
}
