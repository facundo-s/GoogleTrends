package ngordnet;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;

/** Provides a simple user interface for exploring WordNet and NGram data.
 *  @author Facundo Severi
 */
public class NgordnetUI {
    public static void main(String[] args) {
        In in = new In("./ngordnet/ngordnetui.config");
        System.out.println("Reading ngordnetui.config...");

        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        System.out.println("\nBased on ngordnetui.config, using the following: "
                           + wordFile + ", " + countFile + ", " + synsetFile +
                           ", and " + hyponymFile + ".");

        System.out.println("\nFor tips on implementing NgordnetUI, see ExampleUI.java.");
        NGramMap ngram = new NGramMap(wordFile, countFile);
        WordNet net = new WordNet(synsetFile, hyponymFile);
        int startDate = ngram.min();
        int endDate = ngram.max();
        YearlyRecordProcessor yrrcrd = new WordLengthProcessor();

        while (true) {
            System.out.print("> ");
            String line = StdIn.readLine();
            String[] rawTokens = line.split(" ");
            String command = rawTokens[0];
            String[] tokens = new String[rawTokens.length - 1];
            System.arraycopy(rawTokens, 1, tokens, 0, rawTokens.length - 1);
            switch (command) {
                case "quit": 
                    return;
                case "help":
                    in = new In("help.txt");
                    String helpStr = in.readAll();
                    System.out.println(helpStr);
                    break;  
                case "range": 
                    startDate = Integer.parseInt(tokens[0]); 
                    endDate = Integer.parseInt(tokens[1]);
                    System.out.println("Start date: " + startDate);
                    System.out.println("End date: " + endDate);
                    break;
                case "count":
                    System.out.println(ngram.countInYear(tokens[0], Integer.parseInt(tokens[1])));
                    break;
                case "hyponyms":
                    System.out.println(net.hyponyms(tokens[0]));
                    break;
                case "history":
                    Plotter.plotAllWords(ngram, tokens, startDate, endDate);
                    break;
                case "hypohist":
                    Plotter.plotCategoryWeights(ngram, net, tokens, startDate, endDate);
                    break;
                case "wordlength":
                    Plotter.plotProcessedHistory(ngram, startDate, endDate, yrrcrd);
                    break;
                case "zipf":
                    Plotter.plotZipfsLaw(ngram, Integer.parseInt(tokens[0]));
                    break;
                default:
                    System.out.println("Invalid command.");  
                    break;
            }
        }

    }
} 
