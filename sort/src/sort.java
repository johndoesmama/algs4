
public class sort {

	private int compares, exchanges, merges; 
	
	public sort() {
		compares = 0;
		exchanges = 0;
		merges = 0;
		
	} // end of constructor
	
	public int getExchanges() {
		return exchanges;
	}
	
	public int getCompares() {
		return compares;
	}
	
	public int getMerges() {
		return merges;
	}
	
	public void exch(int[] a, int i, int j) {
		exchanges++;
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public boolean less(int i, int j) {
		compares++;
		if (i < j) return true;
		return false;
	}
	
	public void printArr(int[] a) {
		for (int i = 0; i < a.length; i++)
			System.out.print(" " + a[i]);
	}
	
	public void insertion(int[] a) {
		for (int i = 1; i < a.length; i++) {
			for (int j = i; j > 0; j--) {
				if (less(a[j], a[j-1])) {
					exch(a, j, j-1);
					System.out.println("\nexchange=" + getExchanges() + " :");
					printArr(a);
					/*
					if (getExchanges() == 6) { 
						printArr(a);
						System.exit(0);
					}
					*/
				}
				else break;
			}
		}
		//printArr(a);
		//System.out.println("\nlength = " + a.length + "; compares = " + getCompares() + "; exchanges = " + getExchanges());
	}
	
	public void shellsort(int[] a) {
		int N = a.length;
		int h = 1;
		int exchCount = 1;
		
		while (h < N/3) h = 3*h + 1;
		
		while (h >= 1) {
			System.out.print("\n\nh = " + h + ":\n");
			for (int i = h; i < N; i++) {
				for (int j = i; j >= h && less(a[j], a[j-h]); j=j-h) {
					exch(a, j, j-h);
					System.out.print("exchange=" + getExchanges() + "; j=" + j + ":");
					printArr(a);
					System.out.println("");
				}
			}
			printArr(a);
			h = h/3;
		}
	}
	
	public void merge(int[] a, int[] aux, int lo, int mid, int hi) {
		merges++;
		for (int i = lo; i <= hi; i++)
			aux[i] = a[i];
		
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++){
			if (i > mid)					a[k] = aux[j++];
			else if (j > hi)				a[k] = aux[i++];
			else if (less(aux[j], aux[i]))	a[k] = aux[j++];
			else							a[k] = aux[i++];
		}
		
		System.out.print("\nmerges = " + getMerges() + " :");
		for (int k = 0; k < a.length; k++)
			System.out.print(" " + a[k]);
		//System.out.println("");
	}
	
	public void msort(int[] a, int[] aux, int lo, int hi) {
		if (hi <= lo) return;
		int mid = lo + (hi - lo) / 2;
		msort(a, aux, lo, mid);
		msort(a, aux, mid+1, hi);
		merge(a, aux, lo, mid, hi);
	}
	
	public void mergesort(int[] a) {
		int[] aux = new int[a.length];
		msort(a, aux, 0, aux.length-1);
	}
	
	public void BUmergesort(int[] a) {
		int N = a.length;
		int[] aux = new int[N];
		for (int sz = 1; sz < N; sz = sz+sz)
			for (int lo = 0; lo < N-sz; lo += sz+sz)
				merge(a, aux, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
	}

	public int partition(int[] a, int lo, int hi) {
		int i = lo, j = hi+1;
		while (true) {
			while (less(a[++i], a[lo]))
				if (i == hi) break;
			
			while (less(a[lo], a[--j]))
				if (j == lo) break;
			
			if (i >= j) break;
			exch(a, i, j);
		}
		
		exch(a, lo, j);
		return j;
	}
	
	public void quicksort(int[] a, int lo, int hi) {
		if (hi <= lo) return;
		int j = partition(a, lo, hi);
		System.out.print("after partition...");
		printArr(a);
		System.out.println("");
		quicksort(a, lo, j-1);
		quicksort(a, j+1, hi);
	}

	public static void main(String[] args) {
		
		sort s = new sort();
		int[] a = new int[args.length];
		
		for (int k = 0; k < args.length; k++)
			a[k] = Integer.parseInt(args[k]);
		
		//s.insertion(a);
		//s.shellsort(a);
		//s.mergesort(a);
		//s.BUmergesort(a);
		
		StdRandom.shuffle(a);
		s.quicksort(a, 0, a.length-1);
	}	
}