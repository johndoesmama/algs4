

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;

public class SAP {

    private final Topological t;
    private final Digraph dG;
    private final boolean acyclic;
    
    // constructor takes a digraph (not necessarily a DAG) 
    public SAP(Digraph G) {
        
        if (G == null)
            throw new java.lang.NullPointerException();
        
        dG = new Digraph(G);        
        t = new Topological(G);
        acyclic = t.hasOrder();        
    }
    
    private void validateVertex(int v) {
        if (v < 0 || v > (dG.V()-1))
            throw new java.lang.IndexOutOfBoundsException();
    }
    
    // given two vertices, find shortest ancestral path (sap), sap length, sap ancestor
    private int[] lengthAncestor(int v, int w) {
        
        validateVertex(v);
        validateVertex(w);
        
        // special case where the vertices are the same, we don't have to do anything!
        if (v == w)
            return new int[]{0, v};
        
        // initialization
        boolean sapExists = false;
        int sapDist = -1;
        int ancestor = -1;
        
        if (acyclic) {
            // If DAG (acyclic), run on fewer vertices. Eliminate vertices using topological order.
            int lower, upper;
            if (t.rank(v) <= t.rank(w)) {
                lower = v;
                upper = w;
            } else {
                lower = w;
                upper = v;
            }            
            
            // run BFS to find SAP
            // Topological order allows elimination of destinations below upper vertex   
            BreadthFirstDirectedPaths bfsLower = new BreadthFirstDirectedPaths(dG, lower);
            BreadthFirstDirectedPaths bfsUpper = new BreadthFirstDirectedPaths(dG, upper);
            
            for (int ord : t.order()) {
                if (t.rank(ord) < t.rank(upper))
                    continue;
                
                if (sapExists && bfsLower.hasPathTo(ord) && bfsUpper.hasPathTo(ord)) {
                    if (sapDist > (bfsLower.distTo(ord) + bfsUpper.distTo(ord))) {
                        sapDist = (bfsLower.distTo(ord) + bfsUpper.distTo(ord));
                        ancestor = ord;
                    }
                }
                else if (bfsLower.hasPathTo(ord) && bfsUpper.hasPathTo(ord)) {
                    sapExists = true;
                    sapDist = bfsLower.distTo(ord) + bfsUpper.distTo(ord);
                    ancestor = ord;
                   }
            }
        }
        else {
            // Since graph has cycles, need to run BFS on all vertices
            // Note that this can be further optimized
            BreadthFirstDirectedPaths vbfs = new BreadthFirstDirectedPaths(dG, v);
            BreadthFirstDirectedPaths wbfs = new BreadthFirstDirectedPaths(dG, w);
            for (int x = 0; x < dG.V(); x++) {
                if (vbfs.hasPathTo(x) && wbfs.hasPathTo(x)) {
                    if (sapExists) {
                        if (sapDist > (vbfs.distTo(x) + wbfs.distTo(x))) {
                            sapDist = vbfs.distTo(x) + wbfs.distTo(x);
                            ancestor = x;
                        }
                    }
                    else {
                            sapExists = true;
                            sapDist = vbfs.distTo(x) + wbfs.distTo(x);
                            ancestor = x;
                    }
                }
            }
        }
        
        return new int[]{sapDist, ancestor};
    }
    
    
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        int[] la = lengthAncestor(v, w);
        return la[0]; // length
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        int[] la = lengthAncestor(v, w);
        return la[1]; // ancestor
    }

    // given two vertex subsets, find shortest ancestral path (sap), sap length, sap ancestor
    private int[] lengthAncestorIter(Iterable<Integer> v, Iterable<Integer> w) {
        
        if (v == null || w == null)
            throw new java.lang.NullPointerException();
        
        int[] laIter = new int[]{-1, -1};
        for (int vi : v) {
            for (int wi : w) {
                int[] la = lengthAncestor(vi, wi);
                if (laIter[0] > -1) {
                    if (laIter[0] < la[0])
                        System.arraycopy(la, 0, laIter, 0, la.length);
                }
                else
                    System.arraycopy(la, 0, laIter, 0, la.length);
            }
        }
        return laIter;
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        int[] laIter = lengthAncestorIter(v, w);
        return laIter[0];        
    }
    
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        int[] laIter = lengthAncestorIter(v, w);
        return laIter[1];
    }

    // do unit testing of this class

    public static void main(String[] args) {
        In in = new In(args[0]);        
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            
            if (v < 0 || v > (G.V()-1) || w < 0 || w > (G.V()-1))
                throw new java.lang.IndexOutOfBoundsException();
            
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}