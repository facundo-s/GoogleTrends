package ngordnet;
import edu.princeton.cs.introcs.In;
import java.util.HashMap;
import java.util.Collection;
import java.util.Collections;


public class NGramMap {
    /** received help from Nathan Pannell, mainly ideas. */
    private HashMap<Integer, YearlyRecord> wordFile = new HashMap<Integer, YearlyRecord>();
    private TimeSeries<Long> countFile = new TimeSeries<Long>();
    private HashMap<String, TimeSeries<Integer>> seriesTime = 
        new HashMap<String, TimeSeries<Integer>>();
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        In wordsIn = new In(wordsFilename);
        In countsIn = new In(countsFilename);
        while (wordsIn.hasNextLine()) {
            String wordline = wordsIn.readLine();
            String[] wordarray = wordline.split("\t");
            String word = wordarray[0];
            Integer tempYear = Integer.parseInt(wordarray[1]);
            Integer tempCount = Integer.parseInt(wordarray[2]);
            YearlyRecord record;
            if (wordFile.containsKey(tempYear)) {
                wordFile.get(tempYear).put(word, tempCount);
            } else {
                record = new YearlyRecord();
                record.put(word, tempCount);
                wordFile.put(tempYear, record);
            }
            TimeSeries<Integer> tSeries;
            if (!seriesTime.containsKey(word)) {
                tSeries = new TimeSeries<Integer>();
            } else {
                tSeries = seriesTime.get(word);
            }
            tSeries.put(tempYear, tempCount);
            seriesTime.put(word, tSeries);
        }
        while (countsIn.hasNextLine()) {
            String countline = countsIn.readLine();
            String[] countarray = countline.split(",");
            Integer numYear = Integer.parseInt(countarray[0]);
            Long tempcount = Long.parseLong(countarray[1]);
            countFile.put(numYear, tempcount);
        }
    }

    /** idea to write min and max methods received from Nathan Pannell. */ 
    int min() {
        return Collections.min(wordFile.keySet());
    }

    int max() {
        return Collections.max(wordFile.keySet());
    }
    
    /** Returns the absolute count of WORD in the given YEAR. If the word
      * did not appear in the given year, return 0. */
    public int countInYear(String word, int year) {
        if (wordFile.get(year) != null) {
            return wordFile.get(year).count(word);
        } else {
            return 0;
        }
    }

    /** Returns a defensive copy of the YearlyRecord of WORD. */
    public YearlyRecord getRecord(int year) {
        YearlyRecord rcrd = new YearlyRecord(wordFile.get(year));
        return rcrd; 
    }

    /** Returns the total number of words recorded in all volumes. */
    public TimeSeries<Long> totalCountHistory() {
        TimeSeries<Long> time = new TimeSeries<Long>(countFile);
        return time;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Integer> countHistory(String word, int startYear, int endYear) {
        if (seriesTime.containsKey(word)) {
            TimeSeries<Integer> intSeries = 
                new TimeSeries<Integer>(seriesTime.get(word), startYear, endYear);
            return intSeries;
        }
        return new TimeSeries<Integer>();
    }

    /** Provides a defensive copy of the history of WORD. */
    public TimeSeries<Integer> countHistory(String word) {
        if (seriesTime.containsKey(word)) {
            TimeSeries<Integer> var = new TimeSeries<Integer>(seriesTime.get(word));
            return var;
        }
        return new TimeSeries<Integer>();
    }

    /** Provides the relative frequency of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> weightHistory(String word, int startYear, int endYear) {
        TimeSeries<Double> one;
        TimeSeries<Integer> two;
        TimeSeries<Long> three;
        two = countHistory(word, startYear, endYear);
        three = totalCountHistory();
        one = two.dividedBy(three);
        return one;
    }

    /** Provides the relative frequency of WORD. */
    public TimeSeries<Double> weightHistory(String word) {
        TimeSeries<Double> one;
        TimeSeries<Integer> two;
        TimeSeries<Long> three;
        two = countHistory(word);
        three = totalCountHistory();
        one = two.dividedBy(three);
        return one;      
    }

    /** Provides the summed relative frequency of all WORDS between
      * STARTYEAR and ENDYEAR. If a word does not exist, ignore it rather
      * than throwing an exception. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words, 
                              int startYear, int endYear) {
        TimeSeries<Double> series1 = new TimeSeries<Double>();
        for (String word : words) {
            series1 = series1.plus(weightHistory(word, startYear, endYear));
        }
        return series1;
    }

    /** Returns the summed relative frequency of all WORDS. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words) {
        TimeSeries<Double> series = new TimeSeries<Double>();
        for (String word : words) {
            series = series.plus(weightHistory(word));
        }
        return series;
    }

    /** Provides processed history of all words between STARTYEAR and ENDYEAR as processed
      * by YRP. */
    public TimeSeries<Double> processedHistory(int startYear, int endYear,
                                               YearlyRecordProcessor yrp) {
        TimeSeries<Double> tseries = new TimeSeries<Double>();
        for (int yr : wordFile.keySet()) {
            if (yr >= startYear && yr <= endYear) {
                tseries.put(yr, yrp.process(wordFile.get(yr)));
            }   
        }
        return tseries;
    }

    /** Provides processed history of all words ever as processed by YRP. */
    public TimeSeries<Double> processedHistory(YearlyRecordProcessor yrp) {
        TimeSeries<Double> t = new TimeSeries<Double>();
        for (int yr : wordFile.keySet()) {
            if (yr >= min() && yr <= max()) {
                t.put(yr, yrp.process(wordFile.get(yr)));
            }
        }
        return t;
    }
}
