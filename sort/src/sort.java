
public class sort {

	public sort() { /* nothing here */ } // end of constructor
	
	/**
	 * pretty print method
	 */
	private void printPretty(String s) {
		System.out.println("\n\n----\nTEST CLIENT FOR " + s + "...");
		
	} // end of printPretty

	/**
	 * test client for selection sort
	 */
	public void testSelectionSort(String[] args) {
		printPretty("SELECTION SORT");
		BasicSorts<Integer> a = new BasicSorts<Integer>(args.length);
		for (int k = 0; k < args.length; k++)
			a.insert(Integer.parseInt(args[k]));
		a.selectionSort();
		
	} // end of testSelectionSort
	
	/**
	 * test client for insertion sort
	 */
	public void testInsertionSort(String[] args) {
		printPretty("INSERTION SORT");
		BasicSorts<Integer> a = new BasicSorts<Integer>(args.length);
		for (int k = 0; k < args.length; k++)
			a.insert(Integer.parseInt(args[k]));
		a.insertionSort();
		
	} // end of testInsertionSort
	
	/**
	 * test client for shell sort
	 */
	public void testShellSort(String[] args) {
		printPretty("SHELL SORT");
		BasicSorts<Integer> a = new BasicSorts<Integer>(args.length);
		for (int k = 0; k < args.length; k++)
			a.insert(Integer.parseInt(args[k]));
		a.shellSort();
		
	} // end of testShellSort

	/**
	 * test client for merge sort
	 */
	public void testMergeSort(String[] args) {
		printPretty("MERGE SORT");
		BasicSorts<Integer> a = new BasicSorts<Integer>(args.length);
		for (int k = 0; k < args.length; k++)
			a.insert(Integer.parseInt(args[k]));
		a.mergeSort();
		
	} // end of testMergeSort

	/**
	 * test client for bottom up merge sort
	 */
	public void testBottomUpMergeSort(String[] args) {
		printPretty("BOTTOM UP MERGE SORT");
		BasicSorts<Integer> a = new BasicSorts<Integer>(args.length);
		for (int k = 0; k < args.length; k++)
			a.insert(Integer.parseInt(args[k]));
		a.BUmergeSort();
		
	} // end of testBottomUpMergeSort
	
	/**
	 * test client for quick sort
	 */
	public void testQuickSort(String[] args) {
		printPretty("QUICK SORT");
		BasicSorts<Integer> a = new BasicSorts<Integer>(args.length);
		int[] x = new int[args.length];
		for (int k = 0; k < args.length; k++)
			x[k] = Integer.parseInt(args[k]);
		
		StdRandom.shuffle(x);
		for (int k = 0; k < args.length; k++)
			a.insert(x[k]);
		a.quickSort(0, args.length-1);
		
	} // end of testQuickSort
	
	/**
	 * test client for binary heap
	 * @param args
	 */
	public void testBinaryHeap(String[] args) {
		printPretty("BINARY HEAP");
		int[] a = new int[args.length];
		BinaryHeap<Integer> bh = new BinaryHeap<Integer>(args.length);
		for (int k = 0; k < args.length; k++) {
			a[k] = Integer.parseInt(args[k]);
			bh.insertInPosition(a[k], k);
		}
		
		System.out.print("sorting heap | ");
		bh.sortedHeap();
		bh.insert(88);
		bh.insert(91);
		bh.insert(82);
		bh.popSortedHeap();

	} // end of testBinaryHeap
	
	/**
	 * test client for integer arrays and generic heaps
	 * @param args
	 */
	public static void main(String[] args) {
		sort s = new sort();
		s.testSelectionSort(args);
		s.testInsertionSort(args);
		s.testShellSort(args);
		s.testMergeSort(args);
		s.testBottomUpMergeSort(args);
		s.testQuickSort(args);
		s.testBinaryHeap(args);
	}
}