/**
 * A class that represents a leaf on a decision tree
 * 
 * @author pmh6003
 */
public class Leaf implements Node {
	
	// Variables
	char c;
	
	/**
	 * Constructor
	 * 
	 * @param c		The language determined by branches above
	 */
	public Leaf(char c) {
		this.c = c;
	}

	/**
	 * The type of this node
	 */
	public char type() {
		return 'L';
	}
}
