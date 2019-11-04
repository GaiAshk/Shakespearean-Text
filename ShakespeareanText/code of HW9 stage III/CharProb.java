/*

Assignment number : 9

File Name : CharProb.java

Name (First Last) : Gai Ashkenazy

Student ID : 204459127

Email : gaiashkenazy@gmail.com

*/

package languageModle;

/** Represents a character probability. Used only in the context of character probability lists
 *  of the form ((char_0,count_0,p_0,cp_0), (char_1,count_1,p_1,cp_1), ... (char_n,count_n,p_n,cp_n)),
 *  where each list element is a CharProb object. The field count_i stores the number of times
 *  that char_i appears in a given string that exists outside this class. The field p_i stores the
 *  probability of drawing at random char_i from the given string, and the field cp_i stores the cumulative
 *  probability of drawing either char_0, or char_1, or ... or char_i from the string.
 *  Note that in order to compute p and cp we need to know how many characters exist in the string,
 *  and that this information is available only outside this class.
 */
class CharProb {

    char chr;    // character
    int count;	 // how many times the character appears
    double p;    // the probability of drawing this character from a given string
    // that exists outside this class.
    double cp;   // the cumulative probability of drawing this character or any other
    // character that precedes it in the character probabilities list.
    // This list exists outside this class.

    /**
     * Creates a character probability object.
     */
    public CharProb(char chr) {
        this.chr = chr;
        this.count = 1;
        this.p = 0;
        this.cp = 0;
    }

    /**
     * Calculates the probability (p) of observing this character, given the total number of characters.
     */
    public void CharProb(int totalCount) {
        this.p = (double) this.count / totalCount;
    }

    // The commulative probability (cp) of this character is calculated and set by the class that
    // manages the list of character probabilities (outside this class). Thus there is no method
    // for doing this calculation in this class.

    /**
     * Returns a textual representation of this character probability.
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("( " + chr + " " + count + " " + p + " " + cp + " )");
        return str.toString();
    }
}
