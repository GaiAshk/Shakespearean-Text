package languagemodel;

import java.util.HashMap;
import java.util.LinkedList;
import std.StdIn;

public class LanguageModel {

	// The length of the moving window
	private int windowLength; 
	// The map where we manage the (window, LinkedList) mappings 
	private HashMap<String, LinkedList<CharProb>> probabilities;

	/**
	 * Creates a new language model, using the given window length.
	 * @param windowLength
	 */
	public LanguageModel(int windowLength) {
		this.windowLength = windowLength;
		probabilities = new HashMap<String, LinkedList<CharProb>>();
	}

	/**
	 * Builds a language model from the text in standard input (the corpus).
	 */
	public void train() {
	    String window = "";
		char c;
		// Complete the implementation of this method.
	}
		
	// If the given character is found in the given list, increments its count;
    // Otherwise, constructs a new CharProb object and adds it to the given list.
	private void calculateCounts(LinkedList<CharProb> probs, char c) {
		// Put your code here
	}
	
	// Calculates and sets the probabilities (p and cp fields) of all the
	// characters in the given list.
	private void calculateProbabilities(LinkedList<CharProb> probs) {				
		// Put your code here
	}	

	/**
	 * Returns a string representing the probabilities map.
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (String key : probabilities.keySet()) {
			LinkedList<CharProb> keyProbs = probabilities.get(key);
			// Complete the code
		}
		return str.toString();
	}		
	
	// Learns the text that comes from standard input,
	// using the window length given in args[0],
	// and prints the resulting map. 
	public static void main(String[] args) {
	   int windowLength = Integer.parseInt(args[0]);
	   // Constructs a learning model
	   LanguageModel lm = new LanguageModel(windowLength);
	   // Builds the language model
	   lm.train();
	   // Prints the resulting map
	   System.out.println(lm);
	}
}
