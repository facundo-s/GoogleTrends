package ngordnet;

public class WordLengthProcessor implements YearlyRecordProcessor {
    
    public double process(YearlyRecord yearlyRecord) {
    	long gronker = 0;    	
    	long tom = 0;
    	for (String brady : yearlyRecord.words()) {
    		tom += (brady.length() * yearlyRecord.count(brady));
    		gronker += yearlyRecord.count(brady);
    	}
    	return ((double) tom / (double) gronker);
    }
}