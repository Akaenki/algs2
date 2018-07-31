import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;

public class WordNet {
    private SAP sap;
    private HashMap<Integer, String> ids;
    private HashMap<String, HashSet<Integer>> nouns;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        if(synsets == null || hypernyms == null) throw new IllegalArgumentException();
        ids = new HashMap<>(); nouns = new HashMap<>();
        In syns = new In(synsets), hypers = new In(hypernyms);
        while(syns.hasNextLine()){
            String[] cur = syns.readLine().split(",");
            String[] ns = cur[1].split(" ");
            //System.out.println(Arrays.toString(ns));
            int id = Integer.parseInt(cur[0]);
            ids.put(id, cur[1]);
            for(String n : ns){
                if(!nouns.containsKey(n)) nouns.put(n, new HashSet<>());
                nouns.get(n).add(id);
            }
        }

        Digraph G = new Digraph(ids.size());
        while(hypers.hasNextLine()){
            String[] cur = hypers.readLine().split(",");
            int source = Integer.parseInt(cur[0]);
            for(int i = 1; i<cur.length; ++i)
                G.addEdge(source, Integer.parseInt(cur[i]));
        }
        //System.out.println("Vertices: " + G.V() + ", Edges: " + G.E());
        if(hasCycle(G) || !rooted(G)) throw new IllegalArgumentException();
        sap = new SAP(G);
    }

    private boolean hasCycle(Digraph G){
        DirectedCycle dc = new DirectedCycle(G);
        return dc.hasCycle();
    }

    private boolean rooted(Digraph G){
        int root = 0;
        for(int v = 0; v < G.V(); ++v){
            if(G.outdegree(v) == 0) root++;
        }
        return root == 1;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if(word == null) throw new IllegalArgumentException();
        return nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if(!nouns.containsKey(nounA) || !nouns.containsKey(nounB)) throw new IllegalArgumentException();
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if(!nouns.containsKey(nounA) || !nouns.containsKey(nounB)) throw new IllegalArgumentException();
        int ancester = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
        return ids.get(ancester);
    }

    // do unit testing of this class
    public static void main(String[] args){
        WordNet wn = new WordNet("input/synsets.txt", "input/hypernyms.txt");
        System.out.println(wn.distance("Black_Plague", "black_marlin"));
    }
}
