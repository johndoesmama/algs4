import java.util.Iterator;

/**
 * 
 * @author rangasayee.lalgudi
 *
 * @param <Item>
 */
public class Subset {

	/**
     * test client for Subset
     * @param args k, N strings
     */
    public static void main(String[] args) {
    	int k = Integer.parseInt(args[0]);
    	//System.out.println("k=" + k);
    	String s;
    	
    	RandomizedQueue<String> rq = new RandomizedQueue<String>();
    	// repeatedly read in strings
    	//System.out.println("\nAdding to queue...");
    	while (!StdIn.isEmpty()) {
    		s = StdIn.readString();
    		rq.enqueue(s);
    		//System.out.print(" " + s);
    	}
        
        Iterator<String> i = rq.iterator();
        if (k > rq.size())
        	throw new java.lang.IndexOutOfBoundsException();
        
        //System.out.println("\n\nPrinting " + k + " random values...");
        for (int j = 0; j < k; j++) {
        	System.out.println(i.next());
        }
    }   
    
} // end of RandomizedQueue.java