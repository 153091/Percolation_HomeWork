import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] opened;
    private int top;
    private int bottom;
    private int size; //длина стороны матрицы
    private int sizeSquared; //матрица
    private int number; //количество откртых позицый
    private WeightedQuickUnionUF qu;


    // creates n-by-n grid, with all sites  blocked
    /**Вроде готово*/
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("N must be > 0");
        size = n;
        sizeSquared = n*n;
        number = 0;
        top = sizeSquared;
        bottom = sizeSquared+1;
        qu = new WeightedQuickUnionUF(sizeSquared+2);
        opened = new boolean[size][size];

    }

    // opens the site (row, col) if it is not open already
    /**Готово-открытие позиции */
    public void open(int row, int col) {

        validateSite(row, col);

        int shiftRow = row - 1;
        int shiftCol = col - 1;
        int flatIndex = flattenGrid(row, col) -1 ; //-1 чтобы был отсчет с нуля

        // If already open, stop
        if (isOpen(row, col)) {
            return;
        }


        // Open Site

        opened[shiftRow][shiftCol] = true;
        number++;


        if (row == 1) { //Top row
            qu.union(flatIndex, top);
        }
        if (row == size) { //Bottom row
            qu.union(flatIndex, bottom);
        }

        //Check and Open Up
        if (isOnGrid(row-1, col) && isOpen(row-1, col)) {
            qu.union(flatIndex, flattenGrid(row-1, col) - 1); //-1 чтобы был отсчет с нуля
        }

        //Check and Open Down
        if (isOnGrid(row+1, col) && isOpen(row+1, col)) {
            qu.union(flatIndex, flattenGrid(row+1, col) -1); //-1 чтобы был отсчет с нуля
        }

        //Check and Open Left
        if (isOnGrid(row, col-1) && isOpen(row, col-1)) {
            qu.union(flatIndex, flattenGrid(row, col-1) -1); //-1 чтобы был отсчет с нуля
        }

        //Check and Open Right
        if (isOnGrid(row, col+1) && isOpen(row, col+1)) {
            qu.union(flatIndex, flattenGrid(row, col+1) -1); //-1 чтобы был отсчет с нуля
        }
    }

    // is the site (row, col) open?
    /**Готово*/
    public boolean isOpen(int row, int col) {
        validateSite(row, col);
        return opened[row-1][col-1]; //-1 чтобы отсчет был от нуля
    }

    // is the site (row, col) full?
    /**Готово*/
    public boolean isFull(int row, int col) {
        validateSite(row, col);
        return qu.connected(top, flattenGrid(row, col) - 1);
    }

    // returns the number of open sites
    /**Готово*/
    public int numberOfOpenSites() {
        return number;
    }

    // does the system percolate?
    /**Готово*/
    public boolean percolates() {
        return qu.connected(top, bottom);
    }

    //положение на сетке
    /**Готово*/
    private int flattenGrid(int row, int col) {
        return size*(row-1)+col;
    }

    //Если не находитсяч на сетке то выдать
    private void validateSite(int row, int col) {
        if (!isOnGrid(row, col)) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    //находится ли он на сетке?
    private boolean isOnGrid(int row, int col) {
        int shiftRow = row - 1;
        int shiftCol = col - 1;
        return (shiftRow >= 0 && shiftCol >= 0 && shiftRow < size && shiftCol < size);
    }


}
