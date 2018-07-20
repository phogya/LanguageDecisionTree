/**
 * A class that represents a leaf on a decision tree
 * 
 * @author pmh6003
 */
public class Leaf implements Node {
	
	// Variables
	char c;
	float p;
	
	/**
	 * Constructor
	 * 
	 * @param c		The language determined by branches above
	 * @param p		The probability of the determined language based on examples seen
	 */
	public Leaf(char c, float p) {
		this.c = c;
		this.p = p;
	}

	/**
	 * The type of this node
	 */
	public char type() {
		return 'L';
	}
}
