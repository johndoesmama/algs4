/*************************************************************************
 *  Compilation:  javac-algs4 Board.java
 *  Execution:    java-algs4 Board input.txt
 *  
 *
 *************************************************************************/

import java.util.LinkedList;
import java.util.List;

public class Board {
	
	/*
	 * var declarations 
	 */
	private int N;
	private int[][] blocks;
	private int moves;
	
	/*
	 * construct a board from a N-by-N array of blocks
	 * (where blocks[i][j] = block in row i, column j)
	 */
    public Board(int[][] blocks) { this(blocks, 0); } // end of constructor
    
    /*
     * private method to handle non zero moves as well
     */
    private Board(int[][] blocks, int moves) {
    	if (blocks == null)
    		throw new java.lang.NullPointerException();
    	
    	// init
    	N				= blocks.length;
    	this.moves		= moves;
    	this.blocks		= new int[N][N];
    	
    	// set up initial
    	for (int row = 0; row < N; row++) {
    		for (int col = 0 ; col < N; col++) {
    			this.blocks[row][col]	= blocks[row][col];
    		}
    	}
    } // end of Board constructor
    
    /*
     * board dimension N
     */
    public int dimension() { return N; } // end of dimension
    
    /*
     * get block value of goalboard
     */
    private int getGoalboardVal (int row, int col) {
    	if (row == N-1 && col == N-1 )	return 0;
    	else							return row*N + col + 1; 
    } // end of getGoalboardVal
    
    /*
     * number of blocks out of place
     */
    public int hamming() {
    	int dist = moves; // number of moves so far
    	for (int row = 0; row < N; row++) {
    		for (int col = 0; col < N; col++) {
    			if ((blocks[row][col] != 0) && 
    				(blocks[row][col] != getGoalboardVal(row, col))) {
    				dist++;
    			}
    		}
    	}
    	return dist;
    } // end of hamming
    
    /*
     * sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
    	int dist = moves; // number of moves so far
    	for (int row = 0; row < N; row++) {
    		for (int col = 0; col < N; col++) {
    			if (blocks[row][col] !=0 ) {
    				int x = (blocks[row][col] - 1) / N;
    				int y = (blocks[row][col] - 1) - N*x;
    				dist  += Math.abs(row - x) + Math.abs(col - y);
    			}
    		}
    	}
    	return dist;
    } // end of manhattan
        
    /*
     * is this board the goal board?
     */
    public boolean isGoal() {
    	for (int row = 0; row < N; row++) {
    		for (int col = 0; col < N; col++) {
    			if (this.blocks[row][col] != getGoalboardVal(row, col)) {
    				return false;
    			}
    		}
    	}
    	return true;
    } // end of isGoal
    
    /*
     * a board that is obtained by exchanging two adjacent blocks in the same row
     */
    public Board twin() {
    	int[][] newBlocks = new int[N][N];
    	int swapRow = 0;
    	for (int row = 0; row < N; row++) {
    		for (int col = 0; col < N; col++) {
    			newBlocks[row][col] = blocks[row][col]; 
    		}
    	}
    	
    	// if you can't swap the first 2 elements of the 1st row, do the same in the 2nd row
    	if (newBlocks[0][0] == 0 || newBlocks[0][1] == 0)
    		swapRow = 1;
    	
    	// now swap
    	int temp = newBlocks[swapRow][0];
    	newBlocks[swapRow][0] = newBlocks[swapRow][1];
    	newBlocks[swapRow][1] = temp;
    	
    	return new Board(newBlocks, moves); 
    } // end of twin
    
    /*
     * does this board equal y?
     */
    public boolean equals(Object y) {
    	if (y == this) return true;
    	if (y == null) return false;
    	if (y.getClass() != this.getClass()) return false;
    	
    	Board that = (Board) y;
    	if (this.N != that.N) return false; 
    	
    	for (int row = 0; row < this.N; row++) {
    		for (int col = 0; col < this.N; col++) {
    			if (this.blocks[row][col] != that.blocks[row][col]) {
    				return false;
    			}
    		}
    	}
    	return true;
    } // end of equals
    
    /*
     * all neighboring boards : linkedlist implementation
     */
    public Iterable<Board> neighbors() {
    	
    	List<Board> neighbors = new LinkedList<Board>();
    	int spaceRow = 0;
    	int spaceCol = 0; 
    	
    	// find space in board : row, col
    	for (int row = 0; row < N; row++) {
    		for (int col = 0; col < N; col++) {
    			if (blocks[row][col] == 0) {
    				spaceRow = row;
    				spaceCol = col;
    				break;
    			}
    		}
    	}
    	
    	// neighbor : left
    	if (spaceCol > 0) {

    		// copy
    		int[][] neighbor = new int[N][N];
    		for (int row = 0; row < N; row++) {
    			for (int col = 0; col < N; col++) {
    				neighbor[row][col] = blocks[row][col];
    			}
    		}
    		
    		// swap empty space with block on left    		
    		int temp							= neighbor[spaceRow][spaceCol];
    		neighbor[spaceRow][spaceCol]		= neighbor[spaceRow][spaceCol-1];
    		neighbor[spaceRow][spaceCol - 1]	= temp;
    		
    		neighbors.add(new Board(neighbor, moves+1));
    	}
    	
    	// neighbor : right
    	if (spaceCol < N-1) {
    		
    		// copy
    		int[][] neighbor = new int[N][N];
    		for (int row = 0; row < N; row++) {
    			for (int col = 0; col < N; col++) {
    				neighbor[row][col] = blocks[row][col];
    			}
    		}
    		
    		// swap empty space with block on right    		
    		int temp							= neighbor[spaceRow][spaceCol];
    		neighbor[spaceRow][spaceCol]		= neighbor[spaceRow][spaceCol + 1];
    		neighbor[spaceRow][spaceCol + 1]	= temp;
    		
    		neighbors.add(new Board(neighbor, moves+1));
    	}
    	
    	// neighbor : up
    	if (spaceRow > 0) {
    		
    		// copy
    		int[][] neighbor = new int[N][N];
    		for (int row = 0; row < N; row++) {
    			for (int col = 0; col < N; col++) {
    				neighbor[row][col] = blocks[row][col];
    			}
    		}
    		
    		// swap empty space with block on right
    		int temp							= neighbor[spaceRow][spaceCol];
    		neighbor[spaceRow][spaceCol]		= neighbor[spaceRow - 1][spaceCol];
    		neighbor[spaceRow - 1][spaceCol]	= temp;
    		
    		neighbors.add(new Board(neighbor, moves+1));
    	}
    	
    	// neighbor : down
    	if (spaceRow < N-1) {
    		
    		// copy
    		int[][] neighbor = new int[N][N];
    		for (int row = 0; row < N; row++) {
    			for (int col = 0; col < N; col++) {
    				neighbor[row][col] = blocks[row][col];
    			}
    		}
    		
    		// swap empty space with block on right    		
    		int temp							= neighbor[spaceRow][spaceCol];
    		neighbor[spaceRow][spaceCol]		= neighbor[spaceRow + 1][spaceCol];
    		neighbor[spaceRow + 1][spaceCol]	= temp;
    		
    		neighbors.add(new Board(neighbor, moves+1));    		
    	}
    	
    	return neighbors;
    } // end of neighbors
    
    /*
     * string representation of this board (in the output format specified below)
     */
    public String toString() {
    	StringBuilder s = new StringBuilder(N + "\n");
    	for (int i = 0; i < N; i++) {
    		for (int j = 0; j < N; j++) {
    			s.append(" " + this.blocks[i][j]);
    		}
    		s.append("\n");
    	}
    	return s.toString();
    } // end of toString
    
    /**
     * unit tests (not graded)
     * @param args
     */
    public static void main(String[] args) { /* nothing here */ }

} // end of Board.java