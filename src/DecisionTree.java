import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A program that creates a decision tree for determining whether given text samples
 * are in English or Dutch. It also uses adaptive boosting to create a weighted stump
 * array to predict the language of the given text samples.
 * 
 * @author pmh6003
 */
public class DecisionTree {
	
	/**
	 * The main function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		 * Use this to check available files if it says file not found
		File file = new File(".");
		for(String fileNames : file.list()) System.out.println(fileNames); */
		
		if(args.length != 2 && args.length != 1) {
			System.out.println("There must be one or two arguments.");
			usage();
		}
		
		// Get examples
		Scanner sc = null;
		try {
			sc = new Scanner(new File(args[0]));
		} catch (FileNotFoundException e) {
			System.out.println("Could not find specified examples file");
			usage();
		}
		ArrayList<String> lines = new ArrayList<String>();
		while (sc.hasNextLine()) {
		  lines.add(sc.nextLine());
		}

		String[] arr = lines.toArray(new String[0]);
		ArrayList<String> examples = new ArrayList<String>();
		for(int i=0;i<arr.length;i++) {
			examples.add(arr[i]);
		}

		ArrayList<String> predicts = new ArrayList<String>();
		// Get predicts
		if(args.length == 2) {
			try {
				sc = new Scanner(new File(args[1]));
			} catch (FileNotFoundException e) {
				System.err.println("Could not find specified predict file");
				usage();
			}
			ArrayList<String> lines2 = new ArrayList<String>();
			while (sc.hasNextLine()) {
			  lines2.add(sc.nextLine());
			}

			String[] arr2 = lines2.toArray(new String[0]);
			for(int i=0;i<arr2.length;i++) {
				predicts.add(arr2[i]);
			}
		}
		
		
		// Build attribute array for decision tree
		commonDutchWordDe a0 = new commonDutchWordDe();
		commonDutchWordTe a1 = new commonDutchWordTe();
		commonEnglishWordThe a2 = new commonEnglishWordThe();
		containsAccents a3 = new containsAccents();
		containsIJ a4 = new containsIJ();
		containsMoreThan1Z a5 = new containsMoreThan1Z();
		containsMoreThan2K a6 = new containsMoreThan2K();
		containsJK a7 = new containsJK();
		containsCK a8 = new containsCK();
		containsMoreThan2V a9 = new containsMoreThan2V();
		
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		attributes.add(new Attribute() { 
			public boolean test(String s) {return a0.test(s); }
			public String name() { return a0.name(); } });
		attributes.add(new Attribute() { 
			public boolean test(String s) {return a1.test(s); } 
			public String name() { return a1.name(); } });
		attributes.add(new Attribute() { 
			public boolean test(String s) {return a2.test(s); } 
			public String name() { return a2.name(); } });
		attributes.add(new Attribute() { 
			public boolean test(String s) {return a3.test(s); } 
			public String name() { return a3.name(); } }); 
		attributes.add(new Attribute() { 
			public boolean test(String s) {return a4.test(s); } 
			public String name() { return a4.name(); } });
		attributes.add(new Attribute() { 
			public boolean test(String s) {return a5.test(s); } 
			public String name() { return a5.name(); } });
		attributes.add(new Attribute() { 
			public boolean test(String s) {return a6.test(s); } 
			public String name() { return a6.name(); } });
		attributes.add(new Attribute() { 
			public boolean test(String s) {return a7.test(s); } 
			public String name() { return a7.name(); } });
		attributes.add(new Attribute() { 
			public boolean test(String s) {return a8.test(s); } 
			public String name() { return a8.name(); } });
		attributes.add(new Attribute() { 
			public boolean test(String s) {return a9.test(s); } 
			public String name() { return a9.name(); } }); 
		
		
		// Create decision tree and print it
		
		System.out.println("The decision tree created from the examples in " + args[0] + " :\n");
		
		Node tree = decisionTreeLearning(examples, attributes, null);
		printTree(tree, 1);
		
		if(args.length == 2) {
			System.out.println("\nThe above decision tree run on the samples in " + args[1] + " :\n");
			for(int i=0; i<predicts.size(); i++) {
				System.out.println("\tSample " + i + " : " + treePredict(tree, predicts.get(i)));
			}
		}
		
		// Build attribute array for weighted stumps
		ArrayList<Attribute> attributes2 = new ArrayList<Attribute>();
		attributes2.add(new Attribute() { 
			public boolean test(String s) {return a0.test(s); }
			public String name() { return a0.name(); } });
		attributes2.add(new Attribute() { 
			public boolean test(String s) {return a1.test(s); } 
			public String name() { return a1.name(); } });
		attributes2.add(new Attribute() { 
			public boolean test(String s) {return a2.test(s); } 
			public String name() { return a2.name(); } });
		attributes2.add(new Attribute() { 
			public boolean test(String s) {return a3.test(s); } 
			public String name() { return a3.name(); } }); 
		attributes2.add(new Attribute() { 
			public boolean test(String s) {return a4.test(s); } 
			public String name() { return a4.name(); } });
		attributes2.add(new Attribute() { 
			public boolean test(String s) {return a5.test(s); } 
			public String name() { return a5.name(); } });
		attributes2.add(new Attribute() { 
			public boolean test(String s) {return a6.test(s); } 
			public String name() { return a6.name(); } });
		attributes2.add(new Attribute() { 
			public boolean test(String s) {return a7.test(s); } 
			public String name() { return a7.name(); } });
		attributes2.add(new Attribute() { 
			public boolean test(String s) {return a8.test(s); } 
			public String name() { return a8.name(); } });
		attributes2.add(new Attribute() { 
			public boolean test(String s) {return a9.test(s); } 
			public String name() { return a9.name(); } }); 
				
		// Create weighted decision stumps and print it
		
		System.out.println("\nThe array of weighted decision stumps created from the examples in " + args[0] + " :\n");
		
		WeightedStump[] stumps = adaBoost(examples, attributes2, 12);
		printStumps(stumps);
		
		if(args.length == 2) {
			System.out.println("\nThe weighted stumps run on the samples in " + args[1] + " :\n");
			for(int i=0; i<predicts.size(); i++) {
				System.out.println("\tSample " + i + " : " + stumpPredict(stumps, predicts.get(i)));
			}
		}
	}
	

// Decision Tree Functions
	
	/**
	 * Creates a decision tree using the given attributes based on the given
	 * examples.
	 * 
	 * @param examples			Labeled examples to learn from
	 * @param attributes		Attributes with boolean outputs
	 * @param parent_examples	Used for recursion
	 * @return	The root of the tree. Always a branch but recursion requires 
	 * the type to be a Node
	 */
	static Node decisionTreeLearning(ArrayList<String> examples, ArrayList<Attribute> attributes, ArrayList<String> parent_examples) {
		
		if(examples.isEmpty()) {
			return pluralityValue(parent_examples);
			
		} else {
			
			boolean same = true;
			char c = examples.get(0).charAt(examples.get(0).length() -1);
			
			for(int i=1; i<examples.size(); i++) {
				if(examples.get(i).charAt(examples.get(i).length() -1) != c) {
					same = false;
				}
			}
			
			// All examples are the same
			if(same == true) {
				return (new Leaf(c, 1));
			
			} else if (attributes.isEmpty()) {
				return pluralityValue(examples);
				
			} else {
				
				double max = 0;
				double current = 0;
				int mindex = 0;
				for(int i=0; i<attributes.size(); i++) {
					current = importance(attributes.get(i), examples);
					if(current > max ) {
						max = current;
						mindex = i;
					}
				}
				
				// If unable to differentiate examples end tree to prevent becoming needlessly larger
				if( max == 0) {
					return pluralityValue(parent_examples);
				}
				
				Attribute A = attributes.get(mindex);
				Branch root = new Branch(A, null, null, null);
				
				// For vk = true
				ArrayList<String> exsT = new ArrayList<String>();
				String curr;
				for(int i=0; i<examples.size(); i++) {
					curr = examples.get(i);
					if(A.test(curr)) {
						exsT.add(curr);
					}
				}
				attributes.remove(A);
				root.childT = decisionTreeLearning(exsT, attributes, examples);
				
				// For vk = false
				ArrayList<String> exsF = new ArrayList<String>();
				for(int i=0; i<examples.size(); i++) {
					curr = examples.get(i);
					if(!(A.test(curr))) {
						exsF.add(curr);
					}
				}
				root.childF = decisionTreeLearning(exsF, attributes, examples);
				
				return root;
			}
		}
	}
	
	/**
	 * The most common type of the given examples with 'E' always being selected if 
	 * there is a tie.
	 * 
	 * @param examples	The examples to tabulate
	 * @return	A leaf node containing the most common language and the probability
	 */
	public static Node pluralityValue(ArrayList<String> examples) {
		
		// nums[0] is number of E appearances
		// nums[1] is number of D appearances
		// nums[2] is number of I appearances (for use if italian is implemented)
		
		int[] nums = new int [3];
		
		for(int i=0; i< examples.size(); i++) {
			
			char current = examples.get(i).charAt(examples.get(i).length() -1);
			if(current == 'E') {
				nums[0] += 1;
			}
			if(current == 'D') {
				nums[1] += 1;
			}
			if(current == 'I') {
				nums[2] += 1;
			}
		}
		
		
		// E is most common or there is a tie with either D or I
		if( nums[0] >= nums[1] && nums[0] >= nums[2]) {
			return new Leaf('E', (float) nums[0] / (float) (nums[0] + nums[1] + nums[2]));
		}
		
		// D is most comman or there is a tie with I
		if( nums[1] >= nums[0] && nums[1] >= nums[2]) {
			return new Leaf ('D', (float) nums[1] / (float) (nums[0] + nums[1]+ nums[2]));
		}
		
		// I is most common
		if( nums[2] >= nums[0] && nums[2] >= nums[1]) {
			return new Leaf ('I', (float) nums[2] / (float) (nums[0] + nums[1]+ nums[2]));
		}
		
		// Should never reach here
		return null;
	}
	
	/**
	 * The most common type of the given examples with 'E'
	 * always being selected if there is a tie.
	 * 
	 * @param examples	The examples to tabulate
	 * @return	The most common type of the examples
	 */
	/* public static char pluralityValue(ArrayList<String> examples) {
		
		// nums[0] is number of E appearances
		// nums[1] is number of D appearances
		// nums[2] is number of I appearances (for use if italian is implemented)
		
		int[] nums = new int [3];
		
		for(int i=0; i< examples.size(); i++) {
			
			char current = examples.get(i).charAt(examples.get(i).length() -1);
			if(current == 'E') {
				nums[0] += 1;
			}
			if(current == 'D') {
				nums[1] += 1;
			}
			if(current == 'I') {
				nums[2] += 1;
			}
		}
		
		// E is most common or there is a tie with either D or I
		if( nums[0] >= nums[1] && nums[0] >= nums[2]) {
			return 'E';
		}
		
		// D is most comman or there is a tie with I
		if( nums[1] >= nums[0] && nums[1] >= nums[2]) {
			return 'D';
		}
		
		// I is most common
		if( nums[2] >= nums[0] && nums[2] >= nums[1]) {
			return 'I';
		}
		
		// Should never reach here
		return 0;
	} */
	
	/**
	 * The information gain of the given attribute over the given examples.
	 * 
	 * @param attribute		The attribute to examine
	 * @param examples		The examples to apply the attribute to
	 * @return	The numeric value of the information gain
	 */
	static double importance(Attribute attribute, ArrayList<String> examples) {
		
		double p = 0;
		double n = 0;
		
		for(int i=0; i<examples.size(); i++) {
			if(examples.get(i).charAt(examples.get(i).length() -1) == 'E') {
				p += 1;
			} else {
				n += 1;
			}
		}
		double pkT = 0;
		double nkT = 0;
		
		double pkF = 0;
		double nkF = 0;
		
		for(int i=0; i<examples.size(); i++) {
			if(attribute.test(examples.get(i))) {
				if(examples.get(i).charAt(examples.get(i).length() -1) == 'E') {
					pkT += 1;
				} else {
					nkT += 1;
				}
				
			} else {
				if(examples.get(i).charAt(examples.get(i).length() -1) == 'E') {
					pkF += 1;
				} else {
					nkF += 1;
				}
			}
		}
		/*
		for(int i=0; i<examples.size(); i++) {
			if(attribute.test(examples.get(i))) {
				if(examples.get(i).charAt(examples.get(i).length() -1) == 'E') {
					pkT += 1;
				} else {
					nkT += 1;
				}
				
			} else {
				if(examples.get(i).charAt(examples.get(i).length() -1) == 'E') {
					pkF += 1;
				} else {
					nkF += 1;
				}
			}
		} */
		return (1 - 
				((((pkT + nkT) / (p + n)) * entropy(pkT / (pkT + nkT))) +
				(((pkF + nkF) / (p + n)) * entropy(pkF / (pkF + nkF)))
				));
	}
	
	/**
	 * Predicts the language of a sample using the given tree.
	 * 
	 * @param current	The tree to start at, used for recursion as well
	 * @param sample	The sample to predict
	 * @return	The language, 'E' for English, 'D' for Dutch
	 */
	static char treePredict(Node current, String sample) {
		
		if(current.type() == 'B') {
			Branch b = (Branch) current;
			
			if(b.doTest(sample) == true) {
				return treePredict(b.childT, sample);
			} else {
				return treePredict(b.childF, sample);
			}
		} else {
			Leaf l = (Leaf) current;
			return l.c;
		}
	}
	
// Adaptive Boosting Functions
	
	/**
	 * Adaptive boosting algorithm that gets an array of weighted stumps from a series of
	 * attributes and examples to apply those attributes to in order to predict the 
	 * language of a piece of text
	 * 
	 * @param examples		The training examples
	 * @param attributes	The potential hypotheses
	 * @param k				The number of hypotheses to use
	 * @return				An array of hypotheses with their weights
	 */
	static WeightedStump[] adaBoost(ArrayList<String> examples, ArrayList<Attribute> attributes, int k) {
		
		WeightedStump[] wt = new WeightedStump[k];
		
		double N = examples.size();
		double[] weights = new double[(int) N];
		Arrays.fill(weights, (1/N));
		
		Branch[] hypotheses = new Branch[k];
		double[] hypoWeights = new double[k];
		
		// Used to convert boolean values from attributes to chars for comparison with examples;
		char bool;
		
		// Used to determine which language a stump tilts the results towards
		char tilt;
		int numTilt = 0;
		
		double error;
		for(int i=0; i<k; i++) {
			hypotheses[i] = adaLearning(examples, attributes, weights);
			error = 0;
			
			for(int j=0; j<N; j++) {
				
				// Setup bool char
				if(hypotheses[i].doTest(examples.get(j)) == true) {
					bool = 'E';
				} else {
					bool = 'D';
				}
				
				if( bool != examples.get(j).charAt(examples.get(j).length() -1)) {
					numTilt += 1;
					error += weights[j];
				}

			}
			for(int j=0; j<N; j++) {
				
				// Setup bool char
				if(hypotheses[i].doTest(examples.get(j)) == true) {
					bool = 'E';
				} else {
					bool = 'D';
				}
				
				if( bool == examples.get(j).charAt(examples.get(j).length() -1)) {
					numTilt -= 1;
					weights[j] = weights[j] * error / (1 - error);
				}
			}
			
			if(numTilt > 0) {
				tilt = 'D';
			} else {
				tilt = 'E';
			}
			
			weights = normalize(weights);
			hypoWeights[i] = Math.log(1 - error) / error;
			wt[i] = new WeightedStump(hypotheses[i], hypoWeights[i], tilt);
			numTilt = 0;
		}
		return wt;
	}
	
	/**
	 * Helper function for adaBoost, selects the highest information gain stump from
	 * attributes based on the given weights
	 * 
	 * @param examples
	 * @param attributes
	 * @param weights
	 * @return
	 */
	static Branch adaLearning(ArrayList<String> examples, ArrayList<Attribute> attributes, double[] weights) {
		
		double max = 0;
		double current = 0;
		int mindex = 0;
		for(int i=0; i<attributes.size(); i++) {
			current = adaImportance(attributes.get(i), examples, weights);
			if(current > max ) {
				max = current;
				mindex = i;
			}
		}
		
		Attribute A = attributes.get(mindex);
		Branch root = new Branch(A, null, null, null);

		return root;
	}
	
	/**
	 * The information gain of the given attributes over the given examples
	 * where each example is weighted according to the weights argument.
	 * 
	 * @param attribute The attribute to examine
	 * @param examples	The examples to apply the attribute to
	 * @param weights	The weights of the given examples
	 * @return	A numeric value for the information gain
	 */
	static double adaImportance(Attribute attribute, ArrayList<String> examples, double[] weights) {
		
		double p = 0;
		double n = 0;
		
		
		for(int i=0; i<examples.size(); i++) {
			if(examples.get(i).charAt(examples.get(i).length() -1) == 'E') {
				p += weights[i];
			} else {
				n += weights[i];
			}
		}
		double pkT = 0;
		double nkT = 0;
		
		double pkF = 0;
		double nkF = 0;
		
		for(int i=0; i<examples.size(); i++) {
			if(attribute.test(examples.get(i))) {
				if(examples.get(i).charAt(examples.get(i).length() -1) == 'E') {
					pkT += weights[i];
				} else {
					nkT += weights[i];
				}
				
			} else {
				if(examples.get(i).charAt(examples.get(i).length() -1) == 'E') {
					pkF += weights[i];
				} else {
					nkF += weights[i];
				}
			}
		}
		return (1 - 
				((((pkT + nkT) / (p + n)) * entropy(pkT / (pkT + nkT))) +
				(((pkF + nkF) / (p + n)) * entropy(pkF / (pkF + nkF)))
				));
	}
	
	/**
	 * Normalizes an array of doubles so that all doubles maintain
	 * their proportions but all summed equal 1.
	 * 
	 * @param weights	The array of doubles
	 * @return	The normalized array of doubles
	 */
	static double[] normalize(double[] weights) {
		
		double denominator = 0;
		for(int i=0; i<weights.length; i++) {
			denominator += weights[i];
		}
		for(int i=0; i<weights.length; i++) {
			weights[i] = weights[i] / denominator;
		}
		return weights;
	}
	
	/**
	 * Predicts the language of an example using the given weighted stumps
	 * 
	 * @param current	The weighted stumps to use
	 * @param example	The example to predict
	 * @return	The language, 'E' for English, 'D' for Dutch
	 */
	static char stumpPredict(WeightedStump[] stumps, String sample) {
		
		double weightE = 0;
		double weightD = 0;
		
		for(int i=0; i<stumps.length; i++) {
			if(stumps[i].getStump().doTest(sample) == true) {
				if(stumps[i].getTilt() == 'E') {
					weightE += stumps[i].getWeight();
				} else {
					weightD += stumps[i].getWeight();
				}
			}
		}
		if(weightD < weightE) {
			return 'D';
		} else {
			return 'E';
		}
	}
	
// Utility and Printing Functions
	
	/**
	 * The entropy of the given probability. The log functions
	 * fail when q is 0 or 1 so there is a special case catch.
	 * 
	 * @param q		The given probability
	 * @return	The entropy
	 */
	static double entropy(double q) {
		if( q == 0 || q == 1) {
			return 0;
		}
		return (-(q * (Math.log10(q) / Math.log10(2.)) + ((1 - q) * (Math.log10(1 - q) / Math.log10(2.)))));
	}
	
	/**
	 * Prints a tree starting at the given node. The int argument
	 * allows it to work recursively without a helper function, 
	 * it should always be given as 1 if called.
	 * 
	 * @param node	The starting node of the tree
	 * @param n		Recursive argument
	 */
	private static void printTree(Node node, int n) {
		if(node.type() == 'B') {
			Branch b = (Branch) node;
			System.out.println("\tAttribute " + n + " : " + b.attribute.name());
			System.out.print("\t" + n + "-T: ");
			printTree(b.childT, n+1);
			System.out.print("\t" + n + "-F: ");
			printTree(b.childF, n+1);
		} else {
			Leaf l = (Leaf) node;
			System.out.println("\tOutput: " + l.c + ", Probability: " + l.p);
		}
	}
	
	/**
	 * Prints the weighted stumps output of an adaboost program
	 * 
	 * @param stumps	The array of stumps
	 */
	private static void printStumps(WeightedStump[] stumps) {
		for(int i=0; i<stumps.length; i++) {
			System.out.println("\tAttribute: " + stumps[i].getStump().attribute.name() + " Weight: " + stumps[i].getWeight() + " tilted towards " + stumps[i].getTilt());
		}
	}
	
	/**
	 * Prints a usage message and exits
	 */
	private static void usage() {
		System.out.println("java DecisionTree ExamplesFileName PredictFileName");
		System.exit(1);
	}
}
