import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Outcast {

    private final WordNet wnet;
    
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wnet = wordnet;
    }
    
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        if (nouns == null)
            throw new java.lang.NullPointerException();
        
        int distance;
        int max = 0;
        boolean earlyFind = false;
        String outcast = "0";
        for (String n1 : nouns) {
            distance = 0;
            if (earlyFind)
                continue;
            for (String n2 : nouns) {
                if ( (n1 != n2) && !earlyFind ) {
                    int d = wnet.distance(n1, n2);
                    
                    if (d == -1) {
                        // if no SAP exists, outcast already found
                        earlyFind = true;
                        outcast = n1;
                    }

                    distance += d;
                    // StdOut.println(n1 + ", " + n2 + " : " + d);
                }
            }
            // StdOut.println("total distance(" + n1 + "): " + distance);
            if (distance > max) {
                max = distance;
                outcast = n1;
                // StdOut.println("outcast updated: " + n1);
            }
        }
        return outcast;
    }
    
    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}