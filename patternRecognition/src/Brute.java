public class Brute {
 
	public static void main(String[] args) {
		In in = new In(args[0]);
		int N = in.readInt(); // number of points
		Point[] p = new Point[N];
		
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.005);  // make the points a bit larger
		
		for (int i = 0; i < N; i++) {
			if (!in.isEmpty()) {
				int x = in.readInt();
				int y = in.readInt();
				p[i] = new Point(x, y);
				p[i].draw();
			}
		}
		StdDraw.setPenRadius();
		
		// display to screen all at once
        StdDraw.show(0);
        
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				for (int k = j+1; k < N; k++) {
					for (int l = k+1; l < N; l++) {
						
						if (p[i].slopeTo(p[j]) == p[i].slopeTo(p[k])  
							&& p[i].slopeTo(p[j]) == p[i].slopeTo(p[l])) {
							
							int[] ind = {i, j, k, l};

							Point[] pTemp = new Point[ind.length];
							for (int z = 0; z < ind.length; z++)
								pTemp[z] = p[ind[z]];
							
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
							pTemp[0].drawTo(pTemp[3]);
							System.out.println(pTemp[0].toString() + " -> " + pTemp[1].toString() 
									+ " -> " + pTemp[2].toString() + " -> " + pTemp[3].toString());
						}
					}
				}
			}
		}

		// display to screen all at once
        StdDraw.show(0);
	}
}