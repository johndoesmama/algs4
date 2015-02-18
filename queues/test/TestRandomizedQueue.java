import java.util.Iterator;
import org.junit.Test;

public class TestRandomizedQueue {
	
	@Test(expected=java.lang.NullPointerException.class) public void testAddNullItem() {
		String s = null;
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		rq.enqueue(s);
	}
	
	@Test(expected=java.util.NoSuchElementException.class) public void testEmptyRemove() {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		rq.dequeue();
	}
	
	@Test(expected=java.lang.UnsupportedOperationException.class) public void testIteratorInvokeMethodRemove() {
		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		int[] IntArray = {10, 20};
		for (int j = 0; j < IntArray.length; j++) {
			rq.enqueue(IntArray[j]);			
		}		
		Iterator<Integer> i = rq.iterator();
		i.remove();
	}
	
	@Test
	public void testIterator() {
		System.out.println("\n\ntest: Iterator");
		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		int[] IntArray = {10, 20, 30, 40, 50, 60};
		for (int j = 0; j < IntArray.length; j++) {
			rq.enqueue(IntArray[j]);			
		}
		
		Iterator<Integer> i = rq.iterator();
		System.out.println("rq size: " + rq.size());
		System.out.print("\nprinting rq at random:");
		while(i.hasNext()) {
			System.out.print(" " + i.next());
		}
	}
}
