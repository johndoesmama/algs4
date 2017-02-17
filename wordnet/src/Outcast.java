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
        
        int distance = 0;
        int max = 0;
        String outcast = "0";
        for (String n1 : nouns) {
            for (String n2 : nouns) {
                if (n1 != n2)
                    distance += wnet.distance(n1, n2);
            }
            if (distance > max) {
                max = distance;
                outcast = n1;
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