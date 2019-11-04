/*

Assignment number : 9

File Name : LanguageModel.java

Name (First Last) : Gai Ashkenazy

Student ID : 204459127

Email : gaiashkenazy@gmail.com

*/

package languageModle;

import std.StdIn;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class LanguageModel {

	// The length of the moving window
	private int windowLength; 
	
	// The map for managing the (window, LinkedList) mappings 
	private HashMap<String, LinkedList<CharProb>> probabilities;

	// Random number generator:
	// Used by the getRandomChar method, initialized by the class constructors. 
	Random randomGenerator;
	
	/**
	 * Creates a new language model, using the given window length
	 * and a given (fixed) number generator seed.
	 * @param windowLength, the length chosen by the user
	 * @param seed, a testing propose number for generating controlled random numbers
	 */
	public LanguageModel(int windowLength, int seed) {
		this.randomGenerator = new Random(seed);
		this.windowLength = windowLength;
		probabilities = new HashMap<String, LinkedList<CharProb>>();
	}	
	
	/**
	 * Creates a new language model, using the given window length
	 * and a random number generator seed.
	 * @param windowLength, length chosen by the user
	 */
	public LanguageModel(int windowLength) {
		this.randomGenerator = new Random();
		this.windowLength = windowLength;
		probabilities = new HashMap<String, LinkedList<CharProb>>();
	}

	/**
	 * Builds a language model from the text in standard input (the corpus).
	 */
	public void train() {
		String window = "";
		char c;
		// Reads just enough characters to form the first window
		for (int i = 0; i < this.windowLength; i++) {
			window += StdIn.readChar();
		}

		// Processes the entire text, one character at a time
		while (!StdIn.isEmpty()) {
			// Gets the next character
			c = StdIn.readChar();
			//creates a new empty LinkedList of CharProbs
			LinkedList<CharProb> probs = new LinkedList<CharProb>();
			// Checks if the window is already in the map, if it is sets probs to the existing list
			if (probabilities.get(window) == null) {
				probabilities.put(window, probs);
			} else {
				probs = probabilities.get(window);
			}
			// Calculates the counts of the current character.
			calculateCounts(probs, c);
			// Advances the window, adds c to the window’s end, and deletes the window's first character.
			window = window.substring(1, windowLength) + c;
		}

		// The entire file has been processed, and all the characters have been counted.
		// Proceeds to compute and set the p and cp fields of all the CharProb objects
		// in each linked list in the map.
		for (LinkedList<CharProb> probs : probabilities.values()) {
			calculateProbabilities(probs);
		}
	}

	// Calculates the counts of the current character.
	private void calculateCounts(LinkedList<CharProb> probs, char c) {
		boolean flag = false;
		for (int i = 0; i < probs.size(); i++) {
			if ( probs.get(i).chr == c) {
				probs.get(i).count++;
				flag = true;
				break;
			}
		}
		if (!flag) {
			probs.add(new CharProb(c));
		}
	}

	// Computes and sets the probabilities (p and cp fields) of all the
	// characters in the given list. */
	private void calculateProbabilities(LinkedList<CharProb> probs) {
		double totalCount = 0.0;
		for (int i = 0; i < probs.size(); i++) {
			totalCount += probs.get(i).count;
		}
		// creates a double that contains the probability for a single char
		double evenShare = 1/totalCount;
		// runs a loop for creating the probabilities of each letter
		for (int i = 0; i < probs.size(); i++) {
			probs.get(i).p = probs.get(i).count * evenShare;
			if (i == 0) {
				probs.get(i).cp = probs.get(i).p;
			} else {
				probs.get(i).cp = probs.get(i-1).cp + probs.get(i).p;
			}
		}
	}	

	/**
	 * Returns a string representing the probabilities map.
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (String key : probabilities.keySet()) {
			LinkedList<CharProb> keyProb = probabilities.get(key);
				str.append(key + " : (");
				for (int i = 0; i < keyProb.size() ; i++) {
					str.append(keyProb.get(i).toString());
				}
				str.append(")");
				str.append("\n");
		}
		return str.toString();
	}	

	/**
	 * Generates a random text, based on the probabilities that were learned during training. 
	 * @param initialText - text to start. 
	 * @param textLength - the size of text to generate
	 * @return the generated text
	 */
	public String generate(String initialText, int textLength) {
		StringBuilder str = new StringBuilder();                  //the string that the program will return
		//sets the window to the last windowLength characters
		//of the initial text supplied by the user
		String window = initialText.substring(initialText.length()-windowLength, initialText.length());
		// if the entered text is shorter than the window length the program returns the entered string
		if (initialText.length() < windowLength ) return initialText;
		//starts the string by the given window
		str.append(window);
		//runs on the text file and generates the text
		while(!StdIn.isEmpty()) {
			if (probabilities.get(window) == null) {
				return str.toString();
			} else {
				char c = getRandomChar(probabilities.get(window));
				str.append(c);
				window = window.substring(1,window.length()) + c;
			}
			if (str.length() >= textLength) return str.toString();
		}
		return str.toString();
	}

	// Returns a random character from the given probabilities list.
	public char getRandomChar(LinkedList<CharProb> probs) {
		double random = randomGenerator.nextDouble();
		for (int i = 0; i < probs.size() ; i++) {
			if (random < probs.get(i).cp){
				return probs.get(i).chr;
			}
		}
		return ' ';
	}
	
	/**
	 * A Test of the LanguageModel class.
	 * Learns a given corpus (text file) and generates text from it.
	 */
	public static void main(String []args) {		
		int windowLength = Integer.parseInt(args[0]);  // window length
		String initialText = args[1];			      // initial text
		int textLength = Integer.parseInt(args[2]);	  // size of generated text
		boolean random = (args[3].equals("random") ? true : false);  // random / fixed seed
		
		LanguageModel lm;

		// Creates a language model with the given window length and random/fixed seed
		if (random) {
			// the generate method will use a random seed
			lm = new LanguageModel(windowLength);      
		} else {
			// the generate method will use a fixed seed = 20 (for testing purposes)
			lm = new LanguageModel(windowLength, 20); 
		}
		
		// Trains the model, creating the map.
		lm.train();
		
		// Generates text, and prints it.
		System.out.println(lm.generate(initialText,textLength));
	}
}