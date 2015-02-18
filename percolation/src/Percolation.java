
public class Percolation {

    private static int OPEN = 1;
    private static int CLOSED = 0;
    private int[] grid; 
    private WeightedQuickUnionUF uf;
    private int side;
    
    /**
     * constructor
     * @param N indicates width of the N-by-N grid
     */
    public Percolation(int N) {
    	
        if (N <= 0) throw new IllegalArgumentException("grid size should be > 0 !");
        
        // init :
        side = N; // init width of N-by-N grid
        grid = new int[N*N]; // create N-by-N grid, with all sites CLOSED
        for (int i = 0; i < grid.length; i++) {
        	grid[i] = CLOSED;
        }

        uf = new WeightedQuickUnionUF(N*N + 2); // includes top & bottom virtual sites

    } // end of constructor
    
    /**
     * Check bounds for site
     */
    private void checkRange(int i, int j) {
        if (i < 1 || i > side || j < 1 || j > side)
        	throw new IndexOutOfBoundsException("index out of bounds: i=" + i + "; j=" + j);    	
    }
    
    /**
     * Get position in 1D array
     * @param i
     * @param j
     */
    private int getIndex(int i, int j) {
    	return ((i-1)*side + j - 1);
    }
    
    /**
     * open site (i, j) and 
     * connect it to adjacent sites that are already open
     * 
     * if site in top row, connect to top virtual site (N*N)
     * if site in bottom row, connect to bottom virtual site (N*N + 1) 
     * 
     * @param i indicates row of site i,j. i in [1, N]
     * @param j indicates column of site i,j. j in [1, N]
     */
    public void open(int i, int j) {
        // open site (row i, column j) if it is not open already
    	
    	checkRange(i, j);
        grid[getIndex(i, j)] = OPEN; // open site

        // now connect to adjacent open sites
    	if ((i > 1) && isOpen(i-1, j))  { 
    		// if not top row & site above is open
    		uf.union(getIndex(i, j), getIndex(i-1, j)); // above
    		
    	} else if (i == 1) { // top row
    		uf.union(getIndex(i, j), side*side); // connect to top virtual site

    	}
    	
    	if ((i < side) && isOpen(i+1, j)) {  
    		// if not bottom row & site below is open
    		uf.union(getIndex(i, j), getIndex(i+1, j)); // below
    		
    	} else if (i == side) { // bottom row
    		uf.union(getIndex(i, j), side*side + 1); // connect to bottom virtual site
    		
    	}
    	
    	if ((j > 1) && isOpen(i, j-1)) { 
    		// if not the left most column & site to the left is open
    		uf.union(getIndex(i, j), getIndex(i, j-1)); // left
    		
    	}
    	
    	if ((j < side) && isOpen(i, j+1)) {
    		// not the right most column and site to the right is open 
    		uf.union(getIndex(i, j), getIndex(i, j+1)); // right
    		
    	}
    }

    /**
     * check whether a site (i,j) is OPEN
     * @param i
     * @param j
     * @return
     */
    public boolean isOpen(int i, int j) {
    	
    	checkRange(i, j);  	
    	//System.out.println("index: " + i + "," + j + ": grid[i*side+j] = " + grid[getIndex(i,j)] );
        return grid[getIndex(i, j)] == OPEN;
    }
    
    /**
     * check if a site (i, j) is FULL
     * @param i
     * @param j
     * @return
     */
    public boolean isFull(int i, int j) {
    	
    	checkRange(i, j);    	      
        return uf.connected(getIndex(i, j), side * side); // is site connected to top virtual site?
    }

    /**
     * is top virtual node connected to bottom virtual node?
     * @return
     */
    public boolean percolates() {
        // does the system percolate?
        return ( uf.connected(side*side, side*side + 1) ) ;
    }

    /**
     * test client
     * @param args
     */
    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
       	int N = in.readInt();
       	System.out.println("N=" + N);
      	
    	Percolation p = new Percolation(N);
    	while (!in.isEmpty()) {
   			int i = in.readInt();
   			int j = in.readInt();

    		if (!p.isOpen(i, j)) {
    			System.out.println("opening site\t\t: "+ i + ", " + j);
    			p.open(i, j);
    		} else {
    			System.out.println("already open site\t: "+ i + ", " + j);
    		}
    		
    		if (p.percolates()) {
        		System.out.println("Percolates!");
        	}
    	}
    	return;
    }
}