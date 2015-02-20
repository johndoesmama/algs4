public class Fast {
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		int N = in.readInt(); // number of points
		Point[] pOrig = new Point[N];
		
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.008); // make the points a bit larger
		
		for (int i = 0; i < N; i++) {
			if (!in.isEmpty()) {
				int x = in.readInt();
				int y = in.readInt();
				pOrig[i] = new Point(x, y);
				pOrig[i].draw();
			}
		}
		StdDraw.setPenRadius();
		
		// display to screen all at once
        StdDraw.show(0);
               
        for (int k = 0; k < N; k++) {
    		
        	//System.out.println("k=" + k);
        	
        	// create working copy of pOrig in p
        	Point[] p = new Point[N-k]; // working copy of points
        	for (int copyCnt = k; copyCnt < N; copyCnt++) {
            	p[copyCnt-k] = pOrig[copyCnt];
            }
        	
        	// sort (shell sort) points in p based on slopeTo p
        	int h = 1;
        	int n = N-k;
            while (h < n/3) h = 3*h + 1;
            
            while (h >= 1) {
            	//System.out.println("h=" + h);

            	for (int i = h; i < n; i++) {
            		//System.out.println("i=" + i);
            		for (int j = i; j >= h && (p[0].SLOPE_ORDER.compare(p[j], p[j-h]) < 0); j = j-h) {
            			//System.out.println("j=" + j);
            			
            			// exchange
            			Point temp = new Point(0, 0);
            			temp = p[j];
            			p[j] = p[j-h];
            			p[j-h] = temp;
            		}
            	} // end of exchanges for h value
            	
            	h = h/3; // decrement h
            	
            } // end of h-sort iteration
            
            /*
             * find collinear points and draw line segments
             */
            int i;
            for (i = 3; i < n; i++) {
            	if (p[0].slopeTo(p[i]) == p[0].slopeTo(p[i-1]) 
            		&& p[0].slopeTo(p[i]) == p[0].slopeTo(p[i-2])) {
            		
            		// more than 4 points?
            		int d;
            		for (d = i+1; d < n; d++) {
            			if (p[0].slopeTo(p[d]) != p[0].compareTo(p[i]))
            				break;
            		}
            		i = d-1;
            		
            		// draw line segment for all collinear points identified
            		int[] ind = new int[d-(i-2)+1]; // set up indices
            		Point[] pTemp = new Point[ind.length];
            		
            		ind[0] = 0;
            		pTemp[0] = p[ind[0]];
            		for (int z = 1; z < ind.length; z++) {
            			ind[z] = (z-1) + (i-2);
            			pTemp[z] = p[ind[z]];
            		}
            		
            		/*
            		// 4 collinear points, draw line segment
            		int[] ind = {0, i, i-1, i-2};
            		
            		Point[] pTemp = new Point[ind.length];
					for (int z = 0; z < ind.length; z++)
						pTemp[z] = p[ind[z]];
					*/
					
            		// sort collinear points lexicographically
					for (int x = 1; x < ind.length; x++) {
						for (int y = x; y > 0; y--) {
							if (pTemp[y].compareTo(pTemp[y-1]) == -1) {
								Point pSwap = new Point(0, 0);
								pSwap = pTemp[y];
								pTemp[y] = pTemp[y-1];
								pTemp[y-1] = pSwap;
							}
						}
					}
					pTemp[0].drawTo(pTemp[ind.length-1]);
					System.out.print(pTemp[0].toString());
					for (int z = 1; z < ind.length; z++)
						System.out.print(" -> " + pTemp[z].toString());
					System.out.println("");
            	}
            } // end of i loop (collinear points line segment)
        } // end of k loop
        
		// display to screen all at once
        StdDraw.show(0);
	}
}