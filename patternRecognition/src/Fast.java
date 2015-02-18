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
        StdDraw.setPenRadius(0.004);  // make the points a bit larger
		
		for (int i = 0; i < N; i++) {
			if (!in.isEmpty()) {
				int x = in.readInt();
				int y = in.readInt();
				pOrig[i] = new Point(x,y);
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
        	int h=1;
        	int n = N-k;
            while (h < n/3) h = 3*h + 1;
            
            while (h >= 1) {
            	//System.out.println("h=" + h);

            	for (int i = h; i < n; i++) {
            		//System.out.println("i=" + i);
            		for (int j = i; j >=h && (p[0].SLOPE_ORDER.compare(p[j], p[j-h]) < 0); j=j-h) {
            			//System.out.println("j=" + j);
            			
            			// exchange
            			Point temp = new Point(0, 0);
            			temp = p[j];
            			p[j] = p[j-h];
            			p[j-h] = temp;
            		}
            	} // end of exchanges for h value
            	
            	/*
            	// print sorted array at each h-sort iteration
            	System.out.print("\nh = " + h + ": ");
            	for (int i = 0; i < n; i++) {
            		//System.out.print(p[i].toString() + " -> ");
            		System.out.print(p[0].slopeTo(p[i]) + " -> ");
            	}
            	System.out.println("");
            	*/
            	
            	h = h/3; // decrement h
            	
            } // end of h-sort iteration
            
            // find collinear points and draw line segments
            for (int i = 3; i < n; i++) {
            	if (p[0].slopeTo(p[i]) == p[0].slopeTo(p[i-1]) &&
            		p[0].slopeTo(p[i]) == p[0].slopeTo(p[i-2])) {
            		
            		// 4 collinear points, draw line segment
            		int[] ind = {0, i, i-1, i-2};
            		int min = ind[0];
    				int max = ind[0];
    				for (int x = 1; x < ind.length; x++) {
    					if (p[ind[x]].compareTo(p[min]) == -1)
    						min = ind[x];
    					if (p[ind[x]].compareTo(p[max]) == 1)
    						max = ind[x];
    				}
    									
    				p[min].drawTo(p[max]);
					System.out.println(p[0].toString() + " -> " + p[i].toString() + 
							" -> " + p[i-1].toString() + " -> " + p[i-2].toString());
					
            	} 
            } // end of i loop (collinear points line segment)
        } // end of k loop
        
		// display to screen all at once
        StdDraw.show(0);
	}
}