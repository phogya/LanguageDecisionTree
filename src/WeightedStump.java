/**
 * A class that provides a pair object for branches and doubles.
 * Used by the adaBoost function to return a weighted-majority
 * function.
 * 
 * @author pmh6003
 */
public class WeightedStump {

	/**
	 * Private variables
	 */
	private final Branch stump;
	private final double weight;
	private final char tilt;	// The language the stump weights towards
	
	/**
	 * Constructor
	 * 
	 * @param stump		The decision stump
	 * @param weight	The weight of the stump
	 */
	public WeightedStump(Branch stump, double weight, char tilt) {
		this.stump = stump;
		this.weight = weight;
		this.tilt = tilt;
	}
	
	/**
	 * Getter for stump
	 * 
	 * @return	the stump
	 */
	public Branch getStump() {
		return stump;
	}
	
	/**
	 * Getter for weight
	 * 
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * Getter for tilt
	 * 
	 * @return	the tilt
	 */
	public char getTilt() {
		return tilt;
	}
}
