

import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

// WordNet data type. Implement an immutable data type WordNet with the following API:

public class WordNet {
    
    private final SeparateChainingHashST<Integer, String> id2synset = 
            new SeparateChainingHashST<Integer, String>();
    
    private final SeparateChainingHashST<String, Bag<Integer>> nouns2ids =
            new SeparateChainingHashST<String, Bag<Integer>>();
    
    private final Digraph G;
    private final SAP sap;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {        
        if (synsets == null)
            throw new java.lang.NullPointerException("synsets is null");
        
        if (hypernyms == null)
            throw new java.lang.NullPointerException("hypernyms is null");
        
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            String[] nouns = fields[1].trim().split(" "); 
            
            // for Digraph, (key=id, value=synset)
            int id = Integer.parseInt(fields[0].trim());
            id2synset.put(id, fields[1].trim());
            
            // for noun look up we use a Bag for values because synonyms are not unique
            for (String noun : nouns) {
                if (!nouns2ids.contains(noun))
                    nouns2ids.put(noun, new Bag<Integer>());
                nouns2ids.get(noun).add(id);
            }
        }
        
        in = new In(hypernyms);
        int sz = id2synset.size();
        G = new Digraph(sz);
        
        // directed edges from 
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            int v = Integer.parseInt(fields[0].trim());
            for (int i = 1; i < fields.length; i++) {
                int w = Integer.parseInt(fields[i].trim());
                G.addEdge(v, w);
            }
        }
        
        verifyCycle(G);
        verifyRooted(G);
        
        // set up for SAP
        sap = new SAP(G);
    }
    
    private void verifyCycle(Digraph d) {
        DirectedCycle dc = new DirectedCycle(d);
        if (dc.hasCycle())
            throw new java.lang.IllegalArgumentException();
    }
    
    private void verifyRooted(Digraph d) {
        int numRoots = 0;
        for (int v = 0; v < d.V(); v++) {
            if (d.outdegree(v) == 0) 
                numRoots += 1;
        }
        
        if (numRoots != 1)
            throw new java.lang.IllegalArgumentException();
    }
    
    // returns all WordNet nouns
    public Iterable<String> nouns() {
       return nouns2ids.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new java.lang.NullPointerException();
        return nouns2ids.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        validateNoun(nounA);
        validateNoun(nounB);
        return sap.length(nouns2ids.get(nounA), nouns2ids.get(nounB));
    }
    
    private void validateNoun(String noun) {
        if (noun == null)
            throw new java.lang.NullPointerException();        
        if (!nouns2ids.contains(noun))
            throw new java.lang.IllegalArgumentException();
    }
    
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        validateNoun(nounA);
        validateNoun(nounB);
        return id2synset.get(sap.ancestor(nouns2ids.get(nounA), nouns2ids.get(nounB)));
    }

    // do unit testing of this class
    public static void main(String[] args) {
       
    }
}