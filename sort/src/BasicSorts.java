import java.util.Iterator;

/**
 * 
 * @author rangasayee.lalgudi
 *
 * @param <Item>
 */
public class BasicSorts<Item extends Comparable<Item>> {
	private Item[] pq, pqaux;
	private int N;
	private int compares, exchanges, merges;
	
	/**
	 * construct an empty randomized queue
	 */
	public BasicSorts(int capacity) { 
		compares = 0;
		exchanges = 0;
		merges = 0;
		N = 0;
		pq = (Item[]) new Comparable[capacity];
		pqaux = (Item[]) new Comparable[capacity]; 
	
	} // end of constructor
	
	/**
	 * check is queue is empty
	 * @return bool : 1 if empty
	 */
    public boolean isEmpty() { return N == 0; } // end of isEmpty
    
    /**
     * is queue out of memory
     */
    public boolean isOutOfMemory() { return N == pq.length; } // end of isOutOfMemory 
    
    /**
     * compare 2 items
     */
    public boolean less (Item[] obj, int i, int j) { 
    	compares++;
    	return (obj[i].compareTo(obj[j]) < 0); 
    
    } // end of less
    
    /**
     * Accessor methods
     */
    public int getExchanges() { return exchanges; } // end of getExchanges
    public int getCompares() { return compares; } // end of getCompares
    public int getMerges() { return merges; } // end of getMerges
    
    /** 
     * exchange 2 items
     */
    public void exch (Item[] obj, int i, int j) {
    	exchanges++;
    	Item item;
    	item = obj[i];
    	obj[i] = obj[j];
    	obj[j] = item;
    	
    } // end of exch
    
    /**
     * resize queue to the specified size
     * @param capacity
     */
    private void resize(int capacity) {
    	Item[] copy = (Item[]) new Comparable[capacity];
    	for (int i = 0; i < N; i++)
    		copy[i] = pq[i];
    	pq = copy;
    
    } // end of resize
    
    /**
     * add item to queue
     * @param item
     */
    public void insert(Item item) {
    	if (item == null)
    		throw new java.lang.NullPointerException("attempting to add null item");
    	
    	if (isOutOfMemory()) resize(2*pq.length);
    	pq[N++] = item;
    	
    } // end of insert
    
    /**
     * pop last item in array 
     * @return
     */
    public Item delete() {
    	if (isEmpty())
    		throw new java.util.NoSuchElementException("attempting to pop from an empty queue");
    	
    	Item del = pq[--N];
    	pq[N+1] = null; // avoid loitering
    	
    	if (N > 0 && N == pq.length/4)
    		resize(pq.length/2);
    	
    	return del;
    	
    } // end of delete
    
    /**
     * print array
     */
    public void printArr() {
    	for (int i = 0; i < N; i++) {
    		StdOut.print(" " + pq[i]);
    	}
    	
    } // end of printArr
    
    /**
     * selection sort
     */
    public void selectionSort() {
    	for (int i=0; i<pq.length; i++) {
    		int min = i;
    		for (int j=i+1; j<N; j++) {
    			if (less(pq, j, min))
    				min = j;
    		}
    		exch(pq, i, min);
    		System.out.print("\nexchange=" + getExchanges() + " :");
    		printArr();
    	}
    } // end of selectionSort
    
    /**
     * insertion sort
     */
	public void insertionSort() {
		for (int i = 0; i < pq.length; i++) {
			for (int j = i; j > 0; j--) {
				if (less(pq, j, j-1)) {
					exch(pq, j, j-1);
					System.out.print("\nexchange=" + getExchanges() + " :");
					printArr();
				}
				else break;
			}
		}
		
	} // end of insertionSort

	/**
	 * shell sort
	 */
	public void shellSort() {
		int h = 1;
		
		while (h < N/3) h = 3*h + 1;
		
		while (h >= 1) {
			System.out.print("\n\nh = " + h + ":\n");
			for (int i = h; i < N; i++) {
				for (int j = i; j >= h && less(pq, j, j-h); j=j-h) {
					exch(pq, j, j-h);
					System.out.print("exchange=" + getExchanges() + "; j=" + j + ":");
					printArr();
					System.out.println("");
				}
			}
			printArr();
			h = h/3;
		}
	} // end of shellSort
	
	/**
	 * merge sort
	 */
	public void mergeSort() {
		Item[] pqaux = (Item[]) new Comparable[N];
		msort(0, pqaux.length-1);
		
	} // end of mergeSort
	
	/**
	 * merge sort merge method
	 * @param a
	 * @param aux
	 * @param lo
	 * @param mid
	 * @param hi
	 */
	private void merge(int lo, int mid, int hi) {
		merges++;
		for (int i = lo; i <= hi; i++)
			pqaux[i] = pq[i];
		
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++){
			if (i > mid)					pq[k] = pqaux[j++];
			else if (j > hi)				pq[k] = pqaux[i++];
			else if (less(pqaux, j, i))		pq[k] = pqaux[j++];
			else							pq[k] = pqaux[i++];
		}
		
		System.out.print("\nmerges = " + getMerges() + " :");
		printArr();
		
	} // end of merge
	
	/**
	 * merge sort recursive split of array
	 * @param a
	 * @param aux
	 * @param lo
	 * @param hi
	 */
	private void msort(int lo, int hi) {
		if (hi <= lo) return;
		int mid = lo + (hi - lo) / 2;
		msort(lo, mid);
		msort(mid+1, hi);
		merge(lo, mid, hi);

	} // end of msort
	
	/**
	 * bottom up merge sort
	 * @param a
	 */
	public void BUmergeSort() {
		for (int sz = 1; sz < N; sz = sz+sz)
			for (int lo = 0; lo < N-sz; lo += sz+sz)
				merge(lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
	
	} // end of bottom up merge sort
	
	/**
	 * partition method for quick sort 
	 * @param lo
	 * @param hi
	 * @return
	 */
	private int partition(int lo, int hi) {
		int i = lo, j = hi+1;
		while (true) {
			while (less(pq, ++i, lo))
				if (i == hi) break;
			
			while (less(pq, lo, --j))
				if (j == lo) break;
			
			if (i >= j) break;
			exch(pq, i, j);
		}
		
		exch(pq, lo, j);
		return j;
	
	} // end of partition
	
	/**
	 * quick sort
	 * @param a
	 * @param lo
	 * @param hi
	 */
	public void quickSort(int lo, int hi) {
		if (hi <= lo) return;
		int j = partition(lo, hi);
		System.out.print("after partition at pos " + j + ": ");
		printArr();
		System.out.println("");
		quickSort(lo, j-1);
		quickSort(j+1, hi);
		
	} // end of quickSort
	
    /**
     * test client
     * @param args
     */
    public static void main(String[] args) { /* nothing here */ }
    
} // end of class BasicSorts<Item extends Comparable<Item>>