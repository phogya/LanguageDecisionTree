/**
 * A class that represents a branch on a decision tree.
 * 
 * @author pmh6003
 */
public class Branch implements Node{

	// Public variables
	Attribute attribute;
	Node childT;
	Node childF;
	
	/**
	 * Constructor
	 * 
	 * @param attribute		The decision at this node
	 * @param childT		The node's child if the decision returns true
	 * @param childF		The node's child if the decision returns false
	 */
	public Branch(Attribute attribute, Branch parent, Node childT, Node childF) {
		this.attribute = attribute;
		this.childF = childF;
		this.childT = childT;
	}
	
	/**
	 * Executes the branch's attribute's test function
	 * 
	 * @param s		The	string to test
	 * @return	The result of the test
	 */
	boolean doTest(String s){
		return attribute.test(s);
	}

	/**
	 * The type of the node
	 */
	public char type() {
		return 'B';
	}
}
