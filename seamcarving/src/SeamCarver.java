import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    // static variables
    private final static boolean HORIZONTAL = false;
    private final static boolean VERTICAL = true;    
    
    // instance variables
    private Picture p;  // current picture
    private double[][] energyTo;
    private int[][] edgeTo;
    
    public SeamCarver(Picture picture) {
        // create a seam carver object based on the given picture
        
        if (picture == null)
            throw new java.lang.NullPointerException();
            
        p = new Picture(picture);
    }
    
    public Picture picture() {
        // current picture
        return p;
    }
    
    public     int width() {
        // width of current picture
        return p.width();
    }
    
    public     int height() {
        // height of current picture
        return p.height();
    }
    
    private int isBorderCol(int a) {
        // check if Col is on any border
        if (a < (p.width() - 1) && a > 0)
            return 0;
        if (a == 0)
            return -1;  // leftmost column
        return 1;   // rightmost column
    }
    
    private int isBorderRow(int a) {
        // check if Row is on any border
        if (a < (p.height() - 1) && a > 0)
            return 0;
        if (a == 0)
            return -1;  // top row
        return 1; // bottom row
    }
    
    private double computeDeltaXsquare(int x, int y) {
        int colCheck = isBorderCol(x);
        Color px1, px2;
        double r, g, b;
        if (colCheck == 0) {
            // happy path: not border column
           px1 = p.get(x+1, y);
           px2 = p.get(x-1, y);
           r = px1.getRed() - px2.getRed();
           g = px1.getGreen() - px2.getGreen();
           b = px1.getBlue() - px2.getBlue();
        }
        else if (colCheck == -1) {
            // leftmost column
            px1 = p.get(x+1, y);            
            r = px1.getRed();
            g = px1.getBlue();
            b = px1.getGreen();
        }
        else {
            // rightmost column
            px1 = p.get(x-1, y);
            r = px1.getRed();
            g = px1.getBlue();
            b = px1.getBlue();
        }
        return (Math.pow(r, 2) + Math.pow(g, 2) + Math.pow(b, 2));
    }
    
    private double computeDeltaYsquare(int x, int y) {
        int rowCheck = isBorderRow(y);
        Color px1, px2;
        double r, g, b;
        if (rowCheck == 0) {
            // happy path: not border row
           px1 = p.get(x, y+1);
           px2 = p.get(x, y-1);
           r = px1.getRed() - px2.getRed();
           g = px1.getGreen() - px2.getGreen();
           b = px1.getBlue() - px2.getBlue();
        }
        else if (rowCheck == -1) {
            // top row
            px1 = p.get(x, y+1);            
            r = px1.getRed();
            g = px1.getBlue();
            b = px1.getGreen();
        }
        else {
            // bottom row
            px1 = p.get(x, y-1);
            r = px1.getRed();
            g = px1.getBlue();
            b = px1.getBlue();
        }
        return (Math.pow(r, 2) + Math.pow(g, 2) + Math.pow(b, 2));        
    }
    
    public  double energy(int x, int y) {
        // energy of pixel at column x and row y
        
        // check that x & y are within bounds
        if (x < 0 || x >= this.width() || y < 0 || y >= this.height())
            throw new java.lang.IndexOutOfBoundsException();
        
        // force energy of border pixels to 1000.0
        if (x == 0 || x == (this.width() - 1) || y == 0 || y == (this.height() - 1))
            return 1000.0;
        
        double deltaXsquare = computeDeltaXsquare(x, y);
        double deltaYsquare = computeDeltaYsquare(x, y);
                
        return Math.sqrt(deltaXsquare + deltaYsquare);            
    }
    
    private void relax(int x1, int y1, int x2, int y2) {
        if (x2 < 0 || x2 >= this.width() || y2 < 0 || y2 >= this.height())
            return;
            
        if (energyTo[x2][y2] > energyTo[x1][y1] + energy(x2, y2)) {
            energyTo[x2][y2] = energyTo[x1][y1] + energy(x2, y2);
            edgeTo[x2][y2] = x1;
        }
    }
    
    /*
    private void printEdgeTo() {
        StdOut.println();
        for (int row = 0; row < p.height(); row++) {
            StdOut.print("row " + row + ":\t");
            for (int col = 0; col < p.width(); col++) {
                StdOut.print(edgeTo[col][row] + " ");
            }
            StdOut.println();
        }            
    }    

    private void printEnergyTo() {
        StdOut.println();
        for (int row = 0; row < p.height(); row++) {
            StdOut.print("row " + row + ":\t");
            for (int col = 0; col < p.width(); col++) {
                StdOut.printf("%7.2f ", energyTo[col][row]);
            }
            StdOut.println();
        }            
    }    
    */
    
    private Picture transposePicture(Picture picture) {
        // return transpose of a picture
        Picture transpose = new Picture(picture.height(), picture.width());
        
        // transpose the picture
        for (int col = 0; col < transpose.width(); col++) {
            for (int row = 0; row < transpose.height(); row++) {
                transpose.set(col, row, picture.get(row, col));
            }
        }
        return transpose;
    }
    
    public   int[] findHorizontalSeam() {
        // sequence of indices for horizontal seam
        Picture orig = this.p;
        Picture transpose = transposePicture(orig);
        
        // horizontal seam is the vertical seam of the transposed image
        this.p = transpose;
        int[] seam = findVerticalSeam();
        this.p = orig;

        return seam;
    }    
    
    public   int[] findVerticalSeam() {
        // sequence of indices for vertical seam
        
        energyTo = new double[this.width()][this.height()];
        edgeTo = new int[this.width()][this.height()];
        
        // initialize energyTo of first row to 1000.0 
        for (int col = 0; col < p.width(); col++) {
            energyTo[col][0] = 1000.0;
        }
        
        // initialize energyTo for other pixels
        for (int row = 1; row < p.height(); row++) {
            for (int col = 0; col < p.width(); col++)
                energyTo[col][row] = Double.POSITIVE_INFINITY;
        }
        
        // for each pixel, relax up to 3 pixels
        for (int row = 0; row < (p.height() - 1); row++) {
            for (int col = 0; col < p.width(); col++) {
                int colCheck = isBorderCol(col);
                
                // 1. relax same column, next row always
                relax(col, row, col, row+1); // row directly below

                // 2. relax if not leftmost column
                if (colCheck != -1)
                    relax(col, row, col-1, row+1); // row below, to the immediate left
                
                // 3. relax if not rightmost column
                if (colCheck != 1)
                    relax(col, row, col+1, row+1); // row below, to the immediate right
            }
        }
        
        // find minimum energy path
        int lastRow = p.height() - 1;
        double minEnergy = Double.POSITIVE_INFINITY;
        int minEnergyCol = -1;
        for (int col = 0; col < p.width(); col++) {
            if (energyTo[col][lastRow] < minEnergy) {
                minEnergy = energyTo[col][lastRow];
                minEnergyCol = col; 
            }
        }
        
        assert minEnergyCol != -1; // assert that we found a vertical seam
        // printEdgeTo();
        // printEnergyTo();
        
        // construct vertical seam by traversing from last row to first
        int[] seam = new int[p.height()];
        seam[lastRow] = minEnergyCol;
        int prevCol = edgeTo[minEnergyCol][lastRow]; // col for last but one row
        for (int row = lastRow - 1; row >= 0; row--) { 
            seam[row] = prevCol;
            prevCol = edgeTo[prevCol][row];
        }
        
        return seam;
    }
    
    private void validateSeam(int[] seam, boolean seamType) {
        // Check null pointer
        if (seam == null)
            throw new java.lang.NullPointerException();
        
        // Throw a java.lang.IllegalArgumentException if removeVerticalSeam() or removeHorizontalSeam() 
        // is called with an array of the wrong length or if the array is not a valid seam
        if ((seamType == VERTICAL && seam.length != this.height()) ||
            (seamType == HORIZONTAL && seam.length != this.width()))   
            throw new java.lang.IllegalArgumentException();

        // Throw a java.lang.IllegalArgumentException 
        // if removeVerticalSeam() is called when the width of the picture is less than or equal to 1 or 
        // if removeHorizontalSeam() is called when the height of the picture is less than or equal to 1
        if ((seamType == VERTICAL && this.width() <= 1) ||
            (seamType == HORIZONTAL && this.height() <= 1))
            throw new java.lang.IllegalArgumentException();
        
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || 
                (seamType == VERTICAL && seam[i] >= this.width()) || 
                (seamType == HORIZONTAL && seam[i] >= this.height()))
                throw new java.lang.IllegalArgumentException();
            
            if (i > 0 && Math.abs(seam[i] - seam[i-1]) > 1)
                throw new java.lang.IllegalArgumentException();
        }
    }
    
    public    void removeHorizontalSeam(int[] seam) {
        // remove horizontal seam from current picture
        validateSeam(seam, HORIZONTAL);
        Picture orig = this.p;
        Picture transpose = transposePicture(orig);
        this.p = transpose;
        transpose = null;
        orig = null;
        
        removeVerticalSeam(seam);
        
        orig = this.p;
        transpose = transposePicture(orig);
        this.p = transpose;
        transpose = null;
        orig = null;
    }
    
    public    void removeVerticalSeam(int[] seam) {
        // remove vertical seam from current picture
        validateSeam(seam, VERTICAL);
        Picture orig = this.p;
        Picture carved = new Picture(orig.width() - 1, orig.height());
        
        for (int row = 0; row < orig.height(); row++) {
            for (int col = 0; col < seam[row]; col++)                
                carved.set(col, row, orig.get(col, row));
            
            for (int col = seam[row]; col < orig.width() - 1; col++)
                carved.set(col, row, orig.get(col+1, row));
        }
        this.p = carved;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
