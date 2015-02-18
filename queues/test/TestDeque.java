import java.util.Iterator;
import org.junit.Test;

public class TestDeque {
	
	@Test(expected=java.lang.NullPointerException.class) public void testAddNullItemFirst() {
		String s = null;
		Deque<String> deque = new Deque<String>();
		deque.addFirst(s);
	}
	
	@Test(expected=java.lang.NullPointerException.class) public void testAddNullItemLast() {
		String s = null;
		Deque<String> deque = new Deque<String>();
		deque.addLast(s);
	}
	
	@Test(expected=java.util.NoSuchElementException.class) public void testEmptyDequeRemoveFirst() {
		Deque<String> deque = new Deque<String>();
		deque.removeFirst();
	}
	
	@Test(expected=java.util.NoSuchElementException.class) public void testEmptyDequeRemoveLast() {
		Deque<String> deque = new Deque<String>();
		deque.removeLast();
	}

	@Test(expected=java.lang.UnsupportedOperationException.class) public void testIteratorInvokeMethodRemove() {
		Deque<String> deque = new Deque<String>();
		Iterator<String> i = deque.iterator();
		i.remove();
	}
	
	@Test
	public void testIterator() {
		System.out.println("\n\ntest: Iterator");
		Deque<Integer> deque = new Deque<Integer>();
		Iterator<Integer> i = deque.iterator();
		int[] IntArray = {10, 20, 30, 40, 50, 60};
		for (int j = 0; j < IntArray.length; j++) {
			deque.addFirst(IntArray[j]);			
		}
		
		System.out.println("deque size: " + deque.size());
		System.out.print("\nprinting deque:");
		while(i.hasNext()) {
			System.out.print(" " + i.next());
		}
	}
	
	@Test
	public void testAddFirstThenRemoveLast() {
		System.out.println("\n\ntest: AddFirstThenRemoveLast");
		Deque<Integer> deque = new Deque<Integer>();
		int[] IntArray = {1, 2, 3, 4, 5, 6};
		int j;
		
		for (j = 0; j < IntArray.length; j++) {
			deque.addFirst(IntArray[j]);
		}
		
		for (j = 0; j < IntArray.length; j++) {
			System.out.print(" " + deque.removeLast());
		}
		
	}
	
	@Test
	public void testAddLastThenRemoveFirst() {
		System.out.println("\n\ntest: AddFirstThenRemoveLast");
		Deque<Integer> deque = new Deque<Integer>();
		int[] IntArray = {1, 2, 3, 4, 5, 6};
		int j;
		
		for (j = 0; j < IntArray.length; j++) {
			deque.addLast(IntArray[j]);
		}
		
		System.out.println("\n\nRemove First...");
		for (j = 0; j < IntArray.length; j++) {
			System.out.print(" " + deque.removeFirst());
		}
		
	}	
	
}
