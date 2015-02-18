import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Deque class definition using linkedlist implementation
 * @author rangasayee.lalgudi
 */
public class Deque<Item> implements Iterable<Item> {

	private Node first, last, current;
    private int size;
	
	private class Node {
		Item item;
		Node next;
		Node prev;
		
	} // end of inner class Node	
	
	/**
	 * constructor: construct an empty deque
	 */
	public Deque() {
		first = null;
		last = null;
		current = null;
		size = 0;
	
	} // end of constructor
	
	/**
	 * check if deque is empty
	 * @return bool: 1 if empty
	 */
	public boolean isEmpty() { return (first == null); } // end of isEmpty
	
	/**
	 * number of items on the deque 
	 * @return deque size
	 */
	public int size() { return size; } // end of size
	
	/**
	 * add item at the front of deque
	 * @param item
	 */
	public void addFirst(Item item) {
		if (item == null)
			throw new NullPointerException("attempting to add a null item");
		
		Node oldfirst = first;
		boolean empty = false;
		
		if (isEmpty()) empty = true;
		
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		first.prev = null;
		current = first;
		
		size++;
		
		if (empty) last = first;
		else oldfirst.prev = first;
		
	} // end of addFirst

	/**
	 * add item to the end of deque
	 * @param item
	 */
	public void addLast(Item item) {
		if (item == null)
			throw new NullPointerException("attempting to add a null item");
		
		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		last.prev = oldlast;
		current = last;
		
		if (isEmpty()) first = last;
		else   		   oldlast.next = last;
		
		size++;		
		
	} // end of addLast
	
	/**
	 * pop first item in deque
	 * @return
	 */
	public Item removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException("attempting to remove an item from an empty deque");
		
		Item item = first.item;
		Node next = first.next;
		first.item = null; // help GC
		first.next = null; // help GC
		first = next;
		current = first;
		
		if (isEmpty()) last = null;
		else		   first.prev = null;
		
		size--;
		return item;
		
	} // end of removeFirst
	
	/**
	 * pop the last item in deque
	 * @return
	 */
	public Item removeLast() {
		if (isEmpty())
			throw new NoSuchElementException("attempting to remove an item from an empty deque");
		
		Item item = last.item;
		Node prev = last.prev;
		last.item = null; // help GC
		last.prev = null; // help GC
		
		last = prev;
		
		if (last == null) first = null; // deque became empty
		else 			  last.next = null;
		current = last;
		size--;
		
		return item;
		
	} // end of removeLast
	
	/**
	 * instantiate and return an iterator for the deque
	 */
	public Iterator<Item> iterator() {
		return new ListIterator();
		
	} // end of iterator
	
	/**
	 * ListIterator class definition
	 * @author rangasayee.lalgudi
	 *
	 */
	private class ListIterator implements Iterator<Item> {
		
		/**
		 * check if deque contains another item
		 * @return
		 */
		public boolean hasNext() { return current != null; } // end of hasNext()
		
		/**
		 * return the next item of the deque
		 * @return
		 */
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			
			Item item = current.item;
			current = current.next;
			return item;
			
		} // end of next
		
		public void remove() { 
			/* not supported */
			throw new java.lang.UnsupportedOperationException("remove method unsupported in ListIterator interface");
			
		} // end of remove
		
	} // end of ListIterator
	
	@SuppressWarnings("unused")
	private static void printDeque(Deque<String> d, Iterator<String> i) {
    
		System.out.println("\nprinting deque contents: ");
		while (i.hasNext()) {
			System.out.print(" " + i.next());
		}
		
	} // end of print_deque
	
	/**
	 * unit tests in TestDeque.java
	 * @param args
	 */
	public static void main(String[] args) { /* nothing here */ }
	
} // end of Deque.java