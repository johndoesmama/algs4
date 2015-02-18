
public class sort {

	private int compares, exchanges; 
	
	public sort() {
		compares = 0;
		exchanges = 0;
		
	} // end of constructor
	
	public int getExchanges() {
		return exchanges;
	}
	
	public int getCompares() {
		return compares;
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
	
	public static void main(String[] args) {
		
		sort s = new sort();
		int[] a = new int[args.length];
		
		for (int k = 0; k < args.length; k++)
			a[k] = Integer.parseInt(args[k]);
		
		s.insertion(a);
		//s.shellsort(a);
	}
}