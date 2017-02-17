import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;

/**
 * 
 * @author rangasayee.lalgudi
 *
 * @param <Item>
 */
public class BinaryHeap<Item extends Comparable<Item>> {
	private Item[] pq;
	private int N;
	
	/**
	 * construct an empty randomized queue
	 */
	public BinaryHeap(int capacity) { 
		N = capacity;
		pq = (Item[]) new Comparable[capacity+1];
	
	} // end of constructor

	/**
	 * init heap Init
	 */
	public void initHeap(Item[] a) {
		for (int i = 0; i < a.length; i++) {
			pq[i + 1] = a[i];
		}
		
	} // end of init
	
	/**
	 * check is queue is empty
	 * @return bool : 1 if empty
	 */
    public boolean isEmpty() { return N == 0; } // end of isEmpty
    
    /**
     * is queue out of memory
     */
    public boolean isOutOfMemory() { return N == (pq.length - 1); } // end of isOutOfMemory 
    
    /**
     * compare 2 items
     */
    public boolean less (int i, int j) { return (pq[i].compareTo(pq[j]) < 0); } // end of less
    
    /** 
     * exchange 2 items
     */
    public void exch (int i, int j) {
    	Item item;
    	item = pq[i];
    	pq[i] = pq[j];
    	pq[j] = item;
    	
    } // end of exch
    
    /**
     * swimming up the heap
     */
    public void swim(int i) {
    	while (i > 1 && less(i/2, i)) {
    			exch(i, i/2);
    			i = i/2;
    	}
    	
    } // end of swim
    
    /**
     * sink down the heap
     */
    public void sink(int i) { 	
    	while (2*i <= N) {
    		int j = 2*i;
    		if (j < N && less(j, j+1)) j++;
    		if (!less(i, j)) break;
    		exch(i, j);
    		i = j;
    	}
    	
    } // end of sink

    /**
     * resize queue to the specified size
     * @param capacity
     */
    private void resize(int capacity) {
    	Item[] copy = (Item[]) new Comparable[capacity];
    	for (int i = 1; i <= N; i++)
    		copy[i] = pq[i];
    	pq = copy;
    }    
    
    /**
     * add item to queue
     * @param item
     */
    public void insert(Item item) {
    	if (item == null)
    		throw new java.lang.NullPointerException("attempting to add null item");
    	
    	if (isOutOfMemory()) resize(2*pq.length);
    	System.out.print("inserting " + item + " | ");
    	pq[++N] = item;
    	swim(N);
    	printHeap();
    	
    } // end of insert
    
    /**
     * use only for initializing the heap with preset values
     * @param item
     */
    public void insertInPosition(Item item, int pos) {
    	if (pos >= N)
    		throw new java.lang.ArrayIndexOutOfBoundsException();
		
    	pq[pos+1] = item;
    	
    } // end of insertInPosition
    
    /**
     * delete key with max value 
     * @return
     */
    public Item delMax() {
    	if (isEmpty())
    		throw new java.util.NoSuchElementException("attempting to pop from an empty queue");
    	
    	//System.out.println("deleting max...");
    	Item max = pq[1];
    	exch(1, N--);
    	sink(1);
    	//pq[N+1] = null;
    	return max;
    	
    } // end of delMax
    
    /**
     * print heap
     */
    public void printHeap() {
    	StdOut.print("heap:");
    	for (int i=1; i <= N; i++) {
    		StdOut.print(" " + pq[i]);
    	}
    	StdOut.print("\n");
    }
    
    /**
     * heap sort
     */
    public void sortedHeap() {
    	for (int i=N; i>0; i--)
    		sink(i);
    	printHeap();
    }
    
    /**
     * pop sorted heap
     */
    public void popSortedHeap() {
    	StdOut.print("Popping heap | ");
    	int m=N;
    	for (int i=N; i>0; i--)
    		delMax();
    	
    	StdOut.print("heap:");
    	for (int i=1; i <= m; i++)
    		StdOut.print(" " + pq[i]);
    	StdOut.println("\n");
    }
    
    /**
     * test client
     * @param args
     */
    public static void main(String[] args) { /* nothing here */ }
    
} // end of class BinaryHeap<Item extends Comparable<Item>>