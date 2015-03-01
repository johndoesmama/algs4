/*************************************************************************
 *  Compilation:  javac-algs4 Board.java
 *  Execution:    java-algs4 Board input.txt
 *  
 *
 *************************************************************************/

import java.util.Comparator;

public class Solver {
	
	/*
	 * var declarations
	 */
	private MinPQ<Node> pq;
	private MinPQ<Node> pqTwin;
	private boolean solvable;
	private boolean solvableTwin;
	private Node finalNode;
	
	private class Node {
		private Board board;
		private Node  prev;
		
		public Node(Board board, Node prev) {
			this.board	= board;
			this.prev	= prev;
		}
	} // end of inner class Node

    /* 
     * Comparator : Hamming distance
     */
    private final Comparator<Node> HAMMING_DISTANCE = new HammingDistance();	// YOUR DEFINITION HERE
    private class HammingDistance implements Comparator<Node> {
    	public int compare(Node a, Node b) { return a.board.hamming() - b.board.hamming(); }
    }

    /* 
     * Comparator : Hamming distance
     */
    private final Comparator<Node> MANHATTAN_DISTANCE = new ManhattanDistance();	// YOUR DEFINITION HERE
    private class ManhattanDistance implements Comparator<Node> {
    	public int compare(Node a, Node b) { return a.board.manhattan() - b.board.manhattan(); }
    }
    
	/*
	 * find a solution to the initial board (using the A* algorithm)
	 */
	public Solver(Board initial) {
		if (initial == null)
			throw new java.lang.NullPointerException();
		
		solvable		= false;
		solvableTwin	= false;
		
		Node initialNode = new Node(initial, null);
		pq = new MinPQ<Node>(MANHATTAN_DISTANCE);
		pq.insert(initialNode);
		
		Node initialNodeTwin = new Node(initial, null);
		initialNodeTwin.board = initialNode.board.twin();
		pqTwin = new MinPQ<Node>(MANHATTAN_DISTANCE);
		pqTwin.insert(initialNodeTwin);
		
		while (!solvable && !pq.isEmpty() && !solvableTwin && !pqTwin.isEmpty()) {
			solvable		= trySolvingPuzzle(pq);
			solvableTwin	= trySolvingPuzzle(pqTwin);
		}
	} // end of Solver constructor
	
	/*
	 * if board with min distance is not goal board, enqueue all neighbors 
	 */
	private boolean trySolvingPuzzle(MinPQ<Node> q) {
		Node curr = q.delMin();
		if (curr.board.isGoal()) {
			finalNode = curr;
			return true;
		}
		
		// critical optimization: insert neighbors only if they don't equal the previous board 
		for (Board brd : curr.board.neighbors()) {
			if ((curr.prev == null) || !brd.equals(curr.prev.board)) {
				Node neighbor = new Node(brd, curr);
				q.insert(neighbor);
			}
		}
		return false;
	}
	
	/*
	 * is the initial board solvable 
	 */
    public boolean isSolvable() { return (solvable || solvableTwin); } // end of isSolvable
    
    /*
     * min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
    	if (isSolvable()) {
    		Node curr = finalNode;
    		int moves = 0;
    		while (curr.prev != null) {
    			moves++;
    			curr = curr.prev;
    		}
    		return moves;
    	}
    	return -1;
    } // end of moves
    
    /*
     * sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
    	if (isSolvable()) {
    		// return stack of boards all the way from the initial to the end
    		Stack<Board> boardStack = new Stack<Board>();
    		
    		Node curr = finalNode;   		
    		while (curr != null) {
    			boardStack.push(curr.board);
    			curr = curr.prev;
    		}
    		return boardStack;
    	}
    	return null;
    } // end of solution
    
    /*
     * test client: solve a slider puzzle (given below)
     */
    public static void main(String[] args) {
    	
    	// create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
} // end of Solver.java