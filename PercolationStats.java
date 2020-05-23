import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private int expCount; //количество экспериментов
    private double[] kuski; //средний процент открытых за все экспы

    /**
     * Performs T independent computational experiments on an N-by-N grid.
     */
    //Вроде готово
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Given N <= 0 || T <= 0");
        }
        expCount = trials;
        kuski = new double[expCount]; //количество кусков соответствует количеству экспериментов
        for (int expNum = 0; expNum < expCount; expNum++) // expNum номер экспериментов
        {
            //чтобы вызывать функции другого класса
            Percolation per = new Percolation(n);
            while (!per.percolates()) {
                int row = StdRandom.uniform(1, n + 1); // n+1 так как [a, b)
                int col = StdRandom.uniform(1, n + 1); // n+1 так как [a, b)
                if (!per.isOpen(row, col)) {
                    per.open(row, col);
                }
            }
            int openSites = per.numberOfOpenSites();
            double kusok = (double) openSites / (n*n); //открытые делим на ВСЕГО
            kuski[expNum] = kusok; // присваиваем к данному эксперименту
        }
    }

    //ГОТОВО
    // среднее значения порога перколяции
    public double mean() {
        return StdStats.mean(kuski);
    }

    //ГОТОВО
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(kuski); // stddev -считает отклонение
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() //формулу взял из ТЗ
    {
        return mean() - (1.96 * stddev()) / (Math.sqrt(expCount));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() //формулу взял из ТЗ
    {
        return mean() + (1.96 * stddev()) / (Math.sqrt(expCount));
    }

    // test client (see below)
    public static void main(String[] args) {
        int gridSize = 10;
        int trialCount = 10;
        if (args.length >= 2) {
            gridSize = Integer.parseInt(args[0]);
            trialCount = Integer.parseInt(args[1]);
        }
        PercolationStats ps = new PercolationStats(gridSize, trialCount);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}

