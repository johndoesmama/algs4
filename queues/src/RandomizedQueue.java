import java.util.Iterator;

/**
 * 
 * @author rangasayee.lalgudi
 *
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] q;
	private Item[] copy;
	private int N;
	
	/**
	 * construct an empty randomized queue
	 */
	public RandomizedQueue() { N = 0; } // end of constructor

	/**
	 * check is queue is empty
	 * @return bool : 1 if empty
	 */
    public boolean isEmpty() { return N == 0; } // end of isEmpty

    /**
     * number of items in queue
     * @return
     */
    public int size() { return N; } // end of size

    /**
     * resize queue to the specified size
     * @param capacity
     */
    private void resize(int capacity) {
    	Item[] copy = (Item[]) new Object[capacity];
    	for (int i = 0; i < N; i++)
    		copy[i] = q[i];
    	q = copy;
    }

    /**
     * add item to queue
     * @param item
     */
    public void enqueue(Item item) {
    	if (item == null)
    		throw new java.lang.NullPointerException("attempting to add null item");
    	
    	if (isEmpty())
    		q = (Item[]) new Object[1];
    	
    	if (N == q.length) resize(2 * q.length);
    	q[N++] = item;
    	
    } // end of enqueue
    
    /**
     * remove and return a random item from queue 
     * @return
     */
    public Item dequeue() {
    	if (isEmpty())
    		throw new java.util.NoSuchElementException("attempting to pop from an empty queue");
    	
    	Item item;
    	int  index;
    	
    	index = StdRandom.uniform(N);
    	item  = q[index];
    	q[index] = q[--N];
    	q[N] = null;
    	if (N > 0 && N == q.length/4)
    		resize(q.length/2);
    	
    	return item;
    	
    } // end of dequeue
    
    /**
     * return (but do not remove) a random item
     * @return
     */
    public Item sample() {
    	if (isEmpty())
    		throw new java.util.NoSuchElementException("attempting to sample from an empty queue");
    	
    	return q[StdRandom.uniform(N)]; 
    	
    } // end of sample
    
    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
    	return new RandomIterator();
    
    } // end of iterator
    
    /**
     * RandomIterator class definition
     * @author rangasayee.lalgudi
     *
     */
    private class RandomIterator implements Iterator<Item> {
		private int i;
		private Item[] copy;
		
		/**
		 * constructor
		 */
		public RandomIterator() {
			i = size();
			
			if (i == 0)
				throw new java.lang.NullPointerException(); 
				
			copy = (Item[]) new Object[i];
			System.arraycopy(q, 0, copy, 0, i);
			StdRandom.shuffle(copy);
		} // end of RandomIterator
		
    	/**
		 * check if deque contains another item
		 * @return
		 */
        public boolean hasNext() { 
        	return i > 0;
        	
        } // end of hasNext()
		
		/**
		 * return the next item of the deque
		 * @return
		 */
		public Item next() {
			Item item;
			if (!hasNext())
				throw new java.util.NoSuchElementException("attempting to read from empty queue");
			
			item = copy[--i];
			copy[i] = null;
			return item;
			
		} // end of next
		
		public void remove() { 
			// not supported
			throw new java.lang.UnsupportedOperationException();
			
		} // end of remove
		
    } // end of RandomIterator<Item>

    /**
     * unit tests in TestRandomizedQueue.java
     * @param args
     */
    public static void main(String[] args) { /* nothing here */ }   
    
} // end of RandomizedQueue.java