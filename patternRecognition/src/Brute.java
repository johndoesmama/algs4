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
				p[i] = new Point(x,y);
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
						
						if (p[i].slopeTo(p[j]) == p[i].slopeTo(p[k]) && 
							p[i].slopeTo(p[j]) == p[i].slopeTo(p[l])) {
							
							
							System.out.println(p[i].toString() + " -> " + p[j].toString() + 
									" -> " + p[k].toString() + " -> " + p[l].toString());
							
							int ind[] = {i, j, k, l};
							int min = ind[0];
							int max = ind[0];
							for (int x = 1; x < ind.length; x++) {
								if (p[ind[x]].compareTo(p[min]) == -1)
									min = ind[x];
								if (p[ind[x]].compareTo(p[max]) == 1)
									max = ind[x];
							}
												
							p[min].drawTo(p[max]);
						}
					}
				}
			}
		}

		// display to screen all at once
        StdDraw.show(0);
	}
}