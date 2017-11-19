import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Queue;

public class BaseballElimination {
    
    private final int numTeams;
    private final String[] teamList;
    private final HashMap<String, Integer> teamNames;
    
    private final int[] w;
    private final int[] lo;
    private final int[] r;
    private final int[][] g;
    
    public BaseballElimination(String filename) {
        // create a baseball division from given filename in format specified below
        In in = new In(filename);
        numTeams = in.readInt();
        teamList = new String[numTeams];
        teamNames = new HashMap<String, Integer>();
        
        w = new int[numTeams];
        lo = new int[numTeams];
        r = new int[numTeams];
        g = new int[numTeams][numTeams];
        
        for (int i = 0; i < numTeams; i++) {
            teamList[i]    = in.readString();
            teamNames.put(teamList[i], i);
            
            w[i]        = in.readInt();
            lo[i]       = in.readInt();
            r[i]        = in.readInt();
            for (int j = 0; j < numTeams; j++)
                g[i][j] = in.readInt();
        }
        in.close();
    }
   
    public int numberOfTeams() {
        // number of teams
        return numTeams;
    }

    public Iterable<String> teams() {
        // all teams
        return teamNames.keySet();
    }
    
    public int wins(String team) {
        // number of wins for given team
        if (!teamNames.containsKey(team))
            throw new java.lang.IllegalArgumentException();
        return w[teamNames.get(team)];
    }
    
    public int losses(String team) {
        // number of losses for given team
        if (!teamNames.containsKey(team))
            throw new java.lang.IllegalArgumentException();
        return lo[teamNames.get(team)];
    }
    
    public int remaining(String team) {
        // number of remaining games for given team
        if (!teamNames.containsKey(team))
            throw new java.lang.IllegalArgumentException();
        return r[teamNames.get(team)];
    }
    
    public int against(String team1, String team2) {
        // number of remaining games between team1 and team2
        if (!teamNames.containsKey(team1) || !teamNames.containsKey(team2))
            throw new java.lang.IllegalArgumentException();
        
        int i = teamNames.get(team1);
        int j = teamNames.get(team2);
        return g[i][j];
    }
    
    public boolean isEliminated(String team) {
        // is given team eliminated?
        if (!teamNames.containsKey(team))
            throw new java.lang.IllegalArgumentException();
        
        String x = team;
        
        // Trivial elimination
        for (String i : teams()) {
            if (wins(x) + remaining(x) < wins(i))
                return true;
        }
        
        // Non-trivial elimination
        int gameVertices = numberOfTeams() * (numberOfTeams() - 1) / 2;
        FlowNetwork fn = new FlowNetwork(gameVertices + numberOfTeams() + 2);
        int s = gameVertices + numberOfTeams();
        int t = s + 1; 
        int nodeId = 0;
        
        for (int i = 0; i < numberOfTeams(); i++) {
            
            if (i == teamNames.get(x))
                continue;
            
            for (int j = i+1; j < numberOfTeams(); j++) {
                
                if ((i == j) || (j == teamNames.get(x)))
                    continue;
                
                // Set stage 1, stage 2 flow network capacity
                fn.addEdge(new FlowEdge(s, nodeId, g[i][j]));
                fn.addEdge(new FlowEdge(nodeId, gameVertices + i, Double.POSITIVE_INFINITY));
                fn.addEdge(new FlowEdge(nodeId, gameVertices + j, Double.POSITIVE_INFINITY));
                
                nodeId += 1;
            }
            
            // Set stage 3 (last) flow network capacity
            fn.addEdge(new FlowEdge(gameVertices + i, t, 
                            Math.max(0, wins(x) + remaining(x) - wins(teamList[i]))));
        }

        new FordFulkerson(fn, s, t);
        
        for (FlowEdge fe : fn.adj(s)) {
            if (fe.flow() < fe.capacity())
                return true;
        }
        
        return false;
    }
    
    public Iterable<String> certificateOfElimination(String team) {
        // subset R of teams that eliminates given team; null if not eliminated
        if (!teamNames.containsKey(team))
            throw new java.lang.IllegalArgumentException();
        
        String x = team;
        List<String> elim = new ArrayList<String>();
        
        // Trivial elimination
        for (String i : teams()) {
            if (wins(x) + remaining(x) < wins(i))
                elim.add(i);
        }
        if (!elim.isEmpty())
            return elim;
        
        // Non-trivial elimination
        int gameVertices = numberOfTeams() * (numberOfTeams() - 1) / 2;
        FlowNetwork fn = new FlowNetwork(gameVertices + numberOfTeams() + 2);
        int s = gameVertices + numberOfTeams();
        int t = s + 1; 
        int nodeId = 0;
        
        for (int i = 0; i < numberOfTeams(); i++) {
            
            if (i == teamNames.get(x))
                continue;
            
            for (int j = i+1; j < numberOfTeams(); j++) {
                
                if ((i == j) || (j == teamNames.get(x)))
                    continue;
                
                // Set stage 1, stage 2 flow network capacity
                fn.addEdge(new FlowEdge(s, nodeId, g[i][j]));
                fn.addEdge(new FlowEdge(nodeId, gameVertices + i, Double.POSITIVE_INFINITY));
                fn.addEdge(new FlowEdge(nodeId, gameVertices + j, Double.POSITIVE_INFINITY));
                
                nodeId += 1;
            }
            
            // Set stage 3 (last) flow network capacity
            fn.addEdge(new FlowEdge(gameVertices + i, t, 
                            Math.max(0, wins(x) + remaining(x) - wins(teamList[i]))));
        }

        FordFulkerson ff = new FordFulkerson(fn, s, t);
        boolean flag = false;
        for (FlowEdge fe : fn.adj(s)) {
            if (fe.flow() < fe.capacity())
                flag = true;
        }
        if (!flag)
            return null; // not eliminated
        
        List<Integer> vertices = bfsFn(fn, s);
        for (int v : vertices) {
            if (v >= gameVertices && ff.inCut(v)) {
                elim.add(teamList[v - gameVertices]);
            }
        }
        if (elim.isEmpty())
            return null;
        return elim;
    }
    
    private List<Integer> bfsFn(FlowNetwork G, int s) {
        List<Integer> li = new ArrayList<Integer>();
        Queue<Integer> q = new Queue<Integer>();
        boolean[] marked = new boolean[G.V()];
        marked[s] = true;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (FlowEdge we : G.adj(v)) {
                int t;
                if (we.from() == v)
                    t = we.to();
                else
                    t = we.from();
                if (we.residualCapacityTo(t) > 0 && !marked[t]) {
                    marked[t] = true;
                    q.enqueue(t);
                    li.add(t);
                }
            }
        }
        return li;
    }
    
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
