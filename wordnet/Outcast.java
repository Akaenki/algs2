import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordnet;
    public Outcast(WordNet wordnet){
        this.wordnet = wordnet;
    }
    public String outcast(String[] nouns){
        int max = 0; String oc = "";
        for(String n1 : nouns) {
            int dist = 0;
            for(String n2 : nouns)
                dist += wordnet.distance(n1,n2);
            if(dist > max){
                max = dist; oc = n1;
            }
        }
        return oc;
    }
    public static void main(String[] args){
        WordNet wordnet = new WordNet("input/synsets.txt", "input/hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);

        In in = new In("input/outcast5.txt");
        String[] nouns = in.readAllStrings();
        StdOut.println(outcast.outcast(nouns));
    }
}
