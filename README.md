# GoogleTrends

For full project outline, please visit http://cs61b.ug/sp15/materials/proj/proj1/proj1.html

This project seeks to see the trends of words, and their related terms throughout history. For that we use the Google NGram dataset. Sample input files can be found under the directory wordnet.

The bulk of the project can be found in WordNet, TimeSeries, YearlyRecord and NGramMap (all can be found inside ngordnet). WordNet is a datastructure that creates a tree of the relationship of words using a dictograph. TimeSeries is an extension of a treeMap that has added functionality to track frequencies of words over years. YearlyRecord tracks words and their frequency. 

NGramMap uses TimeSeries and YearlyRecord to produce the data that can be passed into the plotter NgordnetUI.

This project uses the princeton standard library: http://introcs.cs.princeton.edu/java/stdlib/javadoc/