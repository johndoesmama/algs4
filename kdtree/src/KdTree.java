/**
 * 
 * @author rangasayee.lalgudi
 *
 */

import java.util.LinkedList;
import java.util.List;

public class KdTree {

	private static final boolean HORIZONTAL 	= false;
	private static final boolean VERTICAL		= true;
	private static final boolean LEFTorBOTTOM	= false;
	private static final boolean RIGHTorTOP		= true;
	
	/* private inner class Node */
	private class Node {
		private Point2D p;		// the point
		private RectHV rect;	// the axis-aligned rectangle corresponding to this node
		private Node lb;		// the left/bottom subtree
		private Node rt;		// the right/top subtree
		private boolean div;			// sub-division at the level of the tree
		
		Node(Point2D point) {
			this.p = point;
			lb = null;
			rt = null;
		}
	} // end of inner class Node
	
	private Node root;	// root node of tree
	private int size;	// number of keys in tree
	private List<Point2D> rangeSearchMatches;
	private Point2D champion;
	
	/* construct an empty set of points */
	public KdTree() {
		root = null;
		size = 0;
	}
	
	/* is the set empty? */
    public boolean isEmpty() { return (size() == 0); }
    
    /* number of points in the set */
    public int size() { return size; }
    
    /* add the point to the set (if it is not already in the set) */
    public void insert(Point2D p) {
    	if (p == null) throw new java.lang.NullPointerException();
    	
    	// if tree is empty
    	if (isEmpty()) {
    		Point2D minRect = new Point2D(0.0, 0.0);
    		Point2D maxRect = new Point2D(1.0, 1.0);
    		++size;
    		root = setupNode(new Node(p), VERTICAL, minRect, maxRect);
    		return;
    	}
    	
    	// if tree isn't empty
    	root = put(root, p, VERTICAL, false, null);
    }
    
    /* 
     * recursive method to insert into 2d-tree 
     * compare is based on the division at each level
     * axis-aligned rectangle for the new node is populated  
    */
    private Node put(Node x, Point2D p, boolean div, boolean dir, Node parent) {
    	Node ins = new Node(p);
    	
    	if (x == null) {
    		Point2D minRect;
    		Point2D maxRect;
    		if (div == HORIZONTAL) { // update x
    			if (dir == RIGHTorTOP) { // RIGHT : update min x
    				minRect = new Point2D(parent.p.x(), parent.rect.ymin());
    				maxRect = new Point2D(parent.rect.xmax(), parent.rect.ymax());
    			} else { // LEFT : update max x
    				minRect = new Point2D(parent.rect.xmin(), parent.rect.ymin());
    				maxRect = new Point2D(parent.p.x(), parent.rect.ymax());
    			}
    		} else { // VERTICAL : update y
    			if (dir == RIGHTorTOP) { // TOP : update min y
    				minRect = new Point2D(parent.rect.xmin(), parent.p.y());
    				maxRect = new Point2D(parent.rect.xmax(), parent.rect.ymax());
    			} else { // BOTTOM : update max y 
    				minRect = new Point2D(parent.rect.xmin(), parent.rect.ymin());
    				maxRect = new Point2D(parent.rect.xmax(), parent.p.y());
    			}
    		}
    		++size;
    		ins = setupNode(ins, div, minRect, maxRect);
    		return ins;
    	}
    	
    	int cmp = 0;
    	if (x.div == VERTICAL)	cmp = compareXorder(ins.p, x.p);
    	else 					cmp = compareYorder(ins.p, x.p); 

    	if (cmp < 0)		x.lb = put(x.lb, p, !x.div, LEFTorBOTTOM, x);
    	else if (cmp > 0)	x.rt = put(x.rt, p, !x.div, RIGHTorTOP, x);
    	else 				x.p	 = p;
    	
    	return x;
    }
    
    /* helper function for compare */
    private int compareXorder(Point2D a, Point2D b) {
    	if (a.x() < b.x()) return -1;
    	if (a.x() > b.x()) return 1;
    	if (a.y() < b.y()) return -1;
    	if (a.y() > b.y()) return 1;
    	return 0;
    }
    
    /* helper function for compare */
    private int compareYorder(Point2D a, Point2D b) {
    	if (a.y() < b.y()) return -1;
    	if (a.y() > b.y()) return 1;
    	if (a.x() < b.x()) return -1;
    	if (a.x() > b.x()) return 1;
    	return 0;
    }

    /*
     * set up a new node in the 2d-tree by,
     *   1. updating the half plane division
     *   2. adding the axis-aligned rectangle
     */
    private Node setupNode(Node x, boolean div, Point2D min, Point2D max) {
    	x.div 	= div;
    	x.rect  = new RectHV(min.x(), min.y(), max.x(), max.y()); 
    	return x;
    }    
    
    /* does the set contain point p? */
    public boolean contains(Point2D p) {
    	Node x = root;
    	if (x == null)
    		return false;
    	
    	while (x != null) {
    		if (p.equals(x.p)) return true;
    		
    		int cmp = 0;
    		if (x.div == VERTICAL)	cmp = compareXorder(p, x.p);
    		else					cmp = compareYorder(p, x.p); // horizontal
    		
    		if (cmp < 0)		x = x.lb;
    		else if (cmp > 0)	x = x.rt;
    		else				{ /* do nothing */ }
    	}
    	return false;
    }
    
    /* draw all points to standard draw */
    public void draw() {
    	if (isEmpty()) return;    	
        drawAll(root);
    }
    
    /* recursive method to print all nodes of tree */
    private void printTree(Node x) {
    	if (x == null) return;
    	printTree(x.lb);
    	printTree(x.rt);
    	System.out.println(x.p.toString());
    }
    
    /* recursive method to draw nodes & subdivisions */
    private void drawAll(Node x) {
    	if (x == null) return;
    	drawPoint(x.p);
    	drawSubdivision(x);
    	StdDraw.show();
    	drawAll(x.lb);
    	drawAll(x.rt);
    }
    
    /* set pen, draw point*/
    private void drawPoint(Point2D point) {
    	StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
    	point.draw();
    	StdDraw.show();
    }
    
    /* set pen, draw subdivision */
    private void drawSubdivision(Node a) {
        StdDraw.setPenRadius(.002);
    	if (a.div == VERTICAL) {
    		StdDraw.setPenColor(StdDraw.RED);
    		Point2D start	= new Point2D(a.p.x(), a.rect.ymin());
    		Point2D end		= new Point2D(a.p.x(), a.rect.ymax());
    		start.drawTo(end);
    		StdDraw.show();
    	} else { // div = HORIZONTAL
    		StdDraw.setPenColor(StdDraw.BLUE);
    		Point2D start	= new Point2D(a.rect.xmin(), a.p.y());
    		Point2D end		= new Point2D(a.rect.xmax(), a.p.y());
    		start.drawTo(end);
    		StdDraw.show();
    	}
    }
    
    /* all points that are inside the rectangle */
    public Iterable<Point2D> range(RectHV rect) {
    	if (rect == null) throw new java.lang.NullPointerException();
    	
    	rangeSearchMatches = new LinkedList<Point2D>();
    	Node node = root;
    	getPointsInRange(node, rect);
    	return rangeSearchMatches;
    }
    
    /*
     * recursive method to traverse 2-d tree and push range search matches on to a list 
     */
    private void getPointsInRange(Node a, RectHV rect) {
    	if (a == null) return;
    	
    	if (a.div == VERTICAL) {
    		if (rect.xmax() < a.p.x())			getPointsInRange(a.lb, rect); // fully on the left 
   			else if (rect.xmin() > a.p.x())		getPointsInRange(a.rt, rect); // fully on the right
   			else { 				
   				// neither, so check both left & right 
   				getPointsInRange(a.lb, rect);
   				getPointsInRange(a.rt, rect);
   				
   				if (rect.contains(a.p))	rangeSearchMatches.add(a.p);
   			}
   		} else { // div = HORIZONTAL
   			if (rect.ymax() < a.p.y())			getPointsInRange(a.lb, rect); // fully at the bottom
   			else if (rect.ymin() > a.p.y())		getPointsInRange(a.rt, rect); // fully on top
   			else {
   				// neither, so check both bottom & top
   				getPointsInRange(a.lb, rect);
   				getPointsInRange(a.rt, rect);
   				
   				if (rect.contains(a.p))	rangeSearchMatches.add(a.p);
   			}
   		}
   	}

    /* a nearest neighbor in the set to point p; null if the set is empty */
    public Point2D nearest(Point2D p) {
    	if (p == null) throw new java.lang.NullPointerException();
    	if (isEmpty()) return null;
    	
    	champion = null;
    	Node node = root;
    	findNearest(node, p);
    	return champion;
    }
    
    /* recursive method to traverse 2-d tree and find the nearest neighbor of a given point */
    private void findNearest(Node a, Point2D point) {
    	if (a == null) return;
    	
    	if ((champion == null) || 
    		(point.distanceSquaredTo(a.p) < point.distanceSquaredTo(champion)))
    		champion = a.p;
    		
    	if (a.div == VERTICAL) {

    		// if point is closer to node than champion, look at both half planes
    		if (point.distanceSquaredTo(a.p) < point.distanceSquaredTo(champion)) {
    			if (compareXorder(a.p, point) > 0) {
    				// node is farther along the x-axis than the point
    				// left then right
    				findNearest(a.lb, point);
    				findNearest(a.rt, point);
    			} else {
    				findNearest(a.rt, point);
    				findNearest(a.lb, point);
    			}
    		} else {
    			int cmp = compareXorder(a.p, point); 
    			if (cmp > 0)		findNearest(a.lb, point);
    			else if (cmp < 0)	findNearest(a.rt, point);
    			else {
    				findNearest(a.lb, point);
    				findNearest(a.rt, point);
    			}
    		}
    	} else { // div = HORIZONTAL

    		// if point is closer to node than champion, look at both half planes
    		if (point.distanceSquaredTo(a.p) < point.distanceSquaredTo(champion)) {
    			if (compareYorder(a.p, point) > 0) {
    				// node is farther along the y-axis than the point
    				// top then bottom
    				findNearest(a.lb, point);
    				findNearest(a.rt, point);
    			} else {
    				findNearest(a.rt, point);
    				findNearest(a.lb, point);    				
    			}
    		} else {
    			int cmp = compareYorder(a.p, point);
    			if (cmp > 0)		findNearest(a.lb, point);
    			else if (cmp > 0)	findNearest(a.rt, point);
    			else {
    				findNearest(a.lb, point);
    				findNearest(a.rt, point);
    			}
    		}
    	}
    }
    
    /* unit testing of the methods (optional) */
    public static void main(String[] args) {
        /*
    	String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the data structures with N points from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        System.out.println("2-d tree size = " + kdtree.size() + "\n\n");
        kdtree.draw();
    }
    */
        // initialize the data structures with N points from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        kdtree.draw();
    }
}