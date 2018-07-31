import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Queue;

public class SAP {
    // constructor takes a digraph (not necessarily a DAG)
    private Digraph G;
    public SAP(Digraph G){
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        if(v < 0 || v >= G.V() || w < 0 || w >= G.V()) throw new IllegalArgumentException();
        boolean[] mark1 = new boolean[G.V()], mark2 = new boolean[G.V()];
        int[] dist1 = new int[G.V()], dist2 = new int[G.V()];
        /* bfs search both vertices in lockstep */
        Queue<Integer> q1 = new Queue<>(), q2 = new Queue<>();
        q1.enqueue(v); mark1[v] = true; dist1[v] = 0;
        q2.enqueue(w); mark2[w] = true; dist2[w] = 0;
        int min = Integer.MAX_VALUE;
        while(!q1.isEmpty() || !q2.isEmpty()){
            if(!q1.isEmpty()){
                int cur = q1.dequeue();
                if(mark2[cur]) min = Math.min(min, dist1[cur] + dist2[cur]);
                if(dist1[cur] > min) continue;
                for(int adj : G.adj(cur)){
                    if(!mark1[adj]){
                        q1.enqueue(adj);
                        mark1[adj] = true; dist1[adj] = dist1[cur]+1;
                    }
                }
            }
            if(!q2.isEmpty()){
                int cur = q2.dequeue();
                if(mark1[cur]) min = Math.min(min, dist1[cur] + dist2[cur]);
                if(dist2[cur] > min) continue;
                for(int adj : G.adj(cur)){
                    if(!mark2[adj]){
                        q2.enqueue(adj);
                        mark2[adj] = true; dist2[adj] = dist2[cur]+1;
                    }
                }
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        if(v < 0 || v >= G.V() || w < 0 || w >= G.V()) throw new IllegalArgumentException();
        boolean[] mark1 = new boolean[G.V()], mark2 = new boolean[G.V()];
        int[] dist1 = new int[G.V()], dist2 = new int[G.V()];
        /* bfs search both vertices in lockstep */
        Queue<Integer> q1 = new Queue<>(), q2 = new Queue<>();
        q1.enqueue(v); mark1[v] = true; dist1[v] = 0;
        q2.enqueue(w); mark2[w] = true; dist2[w] = 0;
        int min = Integer.MAX_VALUE, ancester = -1;
        while(!q1.isEmpty() || !q2.isEmpty()){
            if(!q1.isEmpty()){
                int cur = q1.dequeue();
                if(mark2[cur] && dist1[cur] + dist2[cur] < min) {
                    min = dist1[cur] + dist2[cur]; ancester = cur;
                }
                if(dist1[cur] > min) continue;
                for(int adj : G.adj(cur)){
                    if(!mark1[adj]){
                        q1.enqueue(adj);
                        mark1[adj] = true; dist1[adj] = dist1[cur]+1;
                    }
                }
            }
            if(!q2.isEmpty()){
                int cur = q2.dequeue();
                if(mark1[cur] && dist1[cur] + dist2[cur] < min){
                    min = dist1[cur] + dist2[cur]; ancester = cur;
                }
                if(dist2[cur] > min) continue;
                for(int adj : G.adj(cur)){
                    if(!mark2[adj]){
                        q2.enqueue(adj);
                        mark2[adj] = true; dist2[adj] = dist2[cur]+1;
                    }
                }
            }
        }
        return ancester;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        if(v == null || w == null) throw new IllegalArgumentException();
        boolean[] mark1 = new boolean[G.V()], mark2 = new boolean[G.V()];
        int[] dist1 = new int[G.V()], dist2 = new int[G.V()];
        /* bfs search both vertices in lockstep */
        Queue<Integer> q1 = new Queue<>(), q2 = new Queue<>();
        for(int vv : v) {
            if(vv < 0 || vv >= G.V()) throw new IllegalArgumentException();
            q1.enqueue(vv); mark1[vv] = true; dist1[vv] = 0;
        }
        for(int ww : w) {
            if(ww < 0 || ww >= G.V()) throw new IllegalArgumentException();
            q2.enqueue(ww); mark2[ww] = true; dist2[ww] = 0;
        }
        int min = Integer.MAX_VALUE;
        while(!q1.isEmpty() || !q2.isEmpty()){
            if(!q1.isEmpty()){
                int cur = q1.dequeue();
                if(mark2[cur]) min = Math.min(min, dist1[cur] + dist2[cur]);
                if(dist1[cur] > min) continue;
                for(int adj : G.adj(cur)){
                    if(!mark1[adj]){
                        q1.enqueue(adj);
                        mark1[adj] = true; dist1[adj] = dist1[cur]+1;
                    }
                }
            }
            if(!q2.isEmpty()){
                int cur = q2.dequeue();
                if(mark1[cur]) min = Math.min(min, dist1[cur] + dist2[cur]);
                if(dist2[cur] > min) continue;
                for(int adj : G.adj(cur)){
                    if(!mark2[adj]){
                        q2.enqueue(adj);
                        mark2[adj] = true; dist2[adj] = dist2[cur]+1;
                    }
                }
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        if(v == null || w == null) throw new IllegalArgumentException();
        boolean[] mark1 = new boolean[G.V()], mark2 = new boolean[G.V()];
        int[] dist1 = new int[G.V()], dist2 = new int[G.V()];
        /* bfs search both vertices in lockstep */
        Queue<Integer> q1 = new Queue<>(), q2 = new Queue<>();
        for(int vv : v) {
            if(vv < 0 || vv >= G.V()) throw new IllegalArgumentException();
            q1.enqueue(vv); mark1[vv] = true; dist1[vv] = 0;
        }
        for(int ww : w) {
            if(ww < 0 || ww >= G.V()) throw new IllegalArgumentException();
            q2.enqueue(ww); mark2[ww] = true; dist2[ww] = 0;
        }
        int min = Integer.MAX_VALUE, ancester = -1;
        while(!q1.isEmpty() || !q2.isEmpty()){
            if(!q1.isEmpty()){
                int cur = q1.dequeue();
                if(mark2[cur] && dist1[cur] + dist2[cur] < min) {
                    min = dist1[cur] + dist2[cur]; ancester = cur;
                }
                if(dist1[cur] > min) continue;
                for(int adj : G.adj(cur)){
                    if(!mark1[adj]){
                        q1.enqueue(adj);
                        mark1[adj] = true; dist1[adj] = dist1[cur]+1;
                    }
                }
            }
            if(!q2.isEmpty()){
                int cur = q2.dequeue();
                if(mark1[cur] && dist1[cur] + dist2[cur] < min){
                    min = dist1[cur] + dist2[cur]; ancester = cur;
                }
                if(dist2[cur] > min) continue;
                for(int adj : G.adj(cur)){
                    if(!mark2[adj]){
                        q2.enqueue(adj);
                        mark2[adj] = true; dist2[adj] = dist2[cur]+1;
                    }
                }
            }
        }
        return ancester;
    }

    // do unit testing of this class
    public static void main(String[] args){
        In in = new In("input/digraph-ambiguous-ancestor.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt(), w = StdIn.readInt();
            if(v >= G.V() || w >= G.V()) break;
            StdOut.println("length: " + sap.length(v, w) + ", ancestor: " + sap.ancestor(v, w));
        }
    }

}
