
public class PercolationStats {
    
    private double[] x; // fraction of open sites for percolation
	private int totalsites, numexp;
	private Percolation p;
	
	public PercolationStats(int N, int T) {
    	// perform T independent experiments on an N-by-N grid
		int i, j, k, count;
		
		// init
		x = new double[T];
		numexp = x.length;
		totalsites = N * N;
		//System.out.println("total sites = " + totalsites);
		
		// run T experiments and log fraction of open sites for percolation in x[]
		for (k = 0; k < T; k++) {
			count = 0;
			p = new Percolation(N);
			
			while (!p.percolates()) {
				i = StdRandom.uniform(1, N+1);
				j = StdRandom.uniform(1, N+1);
				//System.out.println("opening site at random : (" + i + "," + j + ")");
				if (!p.isOpen(i, j)) { // open a site if it is not already
					p.open(i, j);
					count++; // increment number of open sites
				}
			}
			x[k] = (double) count / (double) totalsites;
			//System.out.println("exp " + (k+1) + ": open sites = " + count + "; fraction = " + x[k]);
		}
    }
       
    public double mean() {
    	// sample mean of percolation threshold
    	return StdStats.mean(x);
    }
    
    public double stddev() {
    	// sample standard deviation of percolation threshold
    	return StdStats.stddevp(x);
    }
    
    public double confidenceLo() {
    	// low  endpoint of 95% confidence interval
    	return (mean() - (1.96 * stddev() / Math.sqrt((double) numexp)));
    }
    
    public double confidenceHi() {
    	// high endpoint of 95% confidence interval
    	return (mean() + (1.96 * stddev() / Math.sqrt((double) numexp)));
    }

   public static void main(String[] args) {
	   // test client (described below)
       In in = new In(args[0]);      // input file
       int[] intargs = in.readAllInts();
       if (intargs.length < 2) {
    	   throw new IllegalArgumentException("at least 2 inputs, N & T, required.");
       }
       
       int N = intargs[0]; // N-by-N percolation system
       int T = intargs[1]; // number of tests
	   
	   if (N <= 0) {
		   throw new IllegalArgumentException("Illegal parameter value");
	   }
	   if (T <= 0) {
		   throw new IllegalArgumentException("Illegal parameter value");
	   }
	   
	   //System.out.print("N=" + N + "; T=" + T + "\n");
	   PercolationStats ps = new PercolationStats(N, T);
	   System.out.println("mean\t\t\t= " + ps.mean());
	   System.out.println("stddev\t\t\t= " + ps.stddev());
	   System.out.println("95% confidence interval\t= " + ps.confidenceLo() + ", " + ps.confidenceHi());	   
   }
}