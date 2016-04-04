package ngordnet;
import java.util.HashMap;
import java.util.Collection;
import java.util.Map;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class YearlyRecord {
    
    /** Creates a new empty YearlyRecord. */
    private HashMap<String, Integer> recordMap;
    private boolean sorted;
    private ArrayList<Map.Entry<String, Integer>> sortedEntries;
    private HashMap<String, Integer> rankMap;
    
    YearlyRecord(YearlyRecord rcrd) {
        this(rcrd.recordMap);
    }

    public YearlyRecord() {
        this.recordMap = new HashMap<String, Integer>();
        sorted = false;
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        this.recordMap = new HashMap<String, Integer>(otherCountMap);
        sorted = false;
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        if (recordMap.containsKey(word)) {
            return recordMap.get(word);
        } else {
            return 0;
        }
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        recordMap.put(word, count);
        sorted = false;
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return recordMap.size();
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        sort();
        Collection<String> keys = new ArrayList<String>();
        for (Map.Entry<String, Integer> element : sortedEntries) {
            keys.add(element.getKey());
        }
        return keys;
    }

    private void sort() {
        sortedEntries = new ArrayList<Map.Entry<String, Integer>>(recordMap.entrySet());
        Collections.sort(sortedEntries, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
                return a.getValue().compareTo(b.getValue());
            }
        });
        sorted = true;
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        sort();
        Collection<Number> keys = new ArrayList<Number>();
        for (Map.Entry<String, Integer> element : sortedEntries) {
            keys.add(element.getValue());
        }
        return keys;
    }

    /** Returns rank of WORD. Most common word is rank 1. 
    */

    private void rankMapConstr() {
        int var = words().size();
        rankMap = new HashMap<String, Integer>();
        for (Iterator iter = words().iterator(); iter.hasNext(); ) {
            rankMap.put((String) iter.next(), var);
            var--;
        }
    }
    public int rank(String word) {
        if (!sorted) {
            rankMapConstr();          
        }
        return rankMap.get(word);
    }
}
