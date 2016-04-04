package ngordnet;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Set;
import java.util.ArrayList;


public class TimeSeries<T extends Number> extends TreeMap<Integer, T> {    
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR. */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {
        super(ts.subMap(startYear, true, endYear, true));
    }

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
        super(ts);
    }

    /** Returns the quotient of this time series divided by the relevant value in ts.
      * If ts is missing a key in this time series, return an IllegalArgumentException. */
    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> quotient = new TimeSeries<Double>();
        Set<Integer> setKeys = this.keySet();
        for (int key : setKeys) {
            if (ts.containsKey(key)) {
                quotient.put(key, (this.get(key).doubleValue() / ts.get(key).doubleValue()));
            } else {
                throw new IllegalArgumentException();
            }
        }
        return quotient;
    }

    /** Returns the sum of this time series with the given ts. The result is a 
      * a Double time series (for simplicity). */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> sum = new TimeSeries<Double>();
        Set<Integer> set = this.keySet();
        for (int key : set) {
            if (ts.containsKey(key)) {
                sum.put(key, (this.get(key).doubleValue() + ts.get(key).doubleValue()));
            } else {
                sum.put(key, this.get(key).doubleValue());
            }
        }
        for (int key : ts.keySet()) {
            if (!sum.containsKey(key)) {
                sum.put(key, ts.get(key).doubleValue());
            }
        }
        return sum;
    }

    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
        return new ArrayList<Number>(this.keySet());
    }

    /** Returns all data for this time series. 
      * Must be in the same order as years(). */
    public Collection<Number> data() {
        return new ArrayList<Number>(this.values());
    }
}
