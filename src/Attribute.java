/**
 * An interface that vaguely simulates the functionality of lambda functions. 
 * 
 * @author pmh6003
 */
public interface Attribute {
	
	/**
	 * Checks a string for some property.
	 * 
	 * @param s		The string to check
	 * @return	Whether the property applies to it
	 */
	boolean test(String s);
	
	/**
	 * Returns the name of the function.
	 * 
	 * @return	The name
	 */
	String name();
}

/**
 * Checks whether the common Dutch article "de" is in 
 * the string.
 */
class commonDutchWordDe implements Attribute {
	
	/**
	 * The test for "de"
	 */
	public boolean test( String s) {
		
		String[] arr = s.split("\\P{L}+");
		for(int i=0; i<arr.length; i++){
			if(arr[i].equals("de")){
				return true;
			}
		}
		return false;
	}

	/**
	 * The name of the function
	 */
	public String name() {
		return "Contains the most Common Dutch word, de.";
	}
}

/**
 * Checks whether the common Dutch article "te" is
 * in the string.
 */
class commonDutchWordTe implements Attribute {
	
	/**
	 * The test for "te"
	 */
	public boolean test( String s) {
		
		String[] arr = s.split("\\P{L}+");
		for(int i=0; i<arr.length; i++){
			if(arr[i].equals("te")){
				return true;
			}
		}
		return false;
	}

	/**
	 * The name of the function
	 */
	public String name() {
		return "Contains the most Common Dutch word, te.";
	}
}

/**
 * Checks whether the common English word "the" is
 * in the string.
 */
class commonEnglishWordThe implements Attribute {
	
	/**
	 * The test for "the"
	 */
	public boolean test( String s) {
		
		String[] arr = s.split("\\P{L}+");
		for(int i=0; i<arr.length; i++){
			if(arr[i].equals("the")){
				return true;
			}
		}
		return false;
	}

	/**
	 * The name of the function
	 */
	public String name() {
		return "Contains the most Common English word, the.";
	}
}

/**
 * Checks whether the string contains an 
 * accented character
 */
class containsAccents implements Attribute {
	
	/**
	 * The character test
	 */
	public boolean test( String s) {
		
		if(s.matches(".*[áéíóúàèëïöüÁÉÍÓÚÀÈËÏÖÜ].*")) {
			return true;
		}
		return false;
	}

	/**
	 * The name of the function
	 */
	public String name() {
		return "Contains an accented letter.";
	}
}

/**
 * Checks whether the string contains the unique Dutch
 * letter ĳ
 */
class containsIJ implements Attribute {
	
	/**
	 * The test for ĳ
	 */
	public boolean test( String s) {
		
		if(s.matches(".*[ĳĲ].*")) {
			return true;
		}
		return false;
	}

	/**
	 * The name of the function
	 */
	public String name() {
		return "Contains an ĳ or an Ĳ character.";
	}
}

/**
 * Checks whether the string has more than one 'z'
 */
class containsMoreThan1Z implements Attribute {
	
	/**
	 * The test for Z
	 */
	public boolean test( String s) {
		
		int numz = 0;
		for(int i=0; i<s.length(); i++) {
			if(s.charAt(i) == 'z' || s.charAt(i) == 'Z') {
				numz += 1;
			}
		}
		if(numz > 1) {
			return true;
		}
		return false;
	}

	/**
	 * The name of the function
	 */
	public String name() {
		return "Contains more than one z or Z.";
	}
}

/**
 * Checks whether the sentence has more than two 'k's
 */
class containsMoreThan2K implements Attribute {
	
	/**
	 * The test for k
	 */
	public boolean test( String s) {
		
		int numk = 0;
		for(int i=0; i<s.length(); i++) {
			if(s.charAt(i) == 'k' || s.charAt(i) == 'K') {
				numk += 1;
			}
		}
		if(numk > 2) {
			return true;
		}
		return false;
	}

	/**
	 * The name of the function
	 */
	public String name() {
		return "Contains more than two k or K.";
	}
}

/**
 * Checks whether the string contains a 'j' immediately
 * followed by a 'k'
 */
class containsJK implements Attribute {
	
	/**
	 * The 'j' 'k' test
	 */
	public boolean test( String s) {
		
		if(s.contains("jk")) {
			return true;
		}
		return false;
	}

	/**
	 * The name of the function
	 */
	public String name() {
		return "Contains a j followed immediately by a k.";
	}
}

/**
 * Checks whether the string contains a 'c' immediately
 * followed by a 'k'
 */
class containsCK implements Attribute {
	
	/**
	 * The 'c' 'k' test
	 */
	public boolean test( String s) {
		
		if(s.contains("ck")) {
			return true;
		}
		return false;
	}

	/**
	 * The name of the function
	 */
	public String name() {
		return "Contains a c followed immediately by a k.";
	}
}

/**
 * Checks whether the string has more than two 'v's
 */
class containsMoreThan2V implements Attribute {
	
	/**
	 * The test for 'v'
	 */
	public boolean test( String s) {
		
		int numv = 0;
		for(int i=0; i<s.length(); i++) {
			if(s.charAt(i) == 'v' || s.charAt(i) == 'V') {
				numv += 1;
			}
		}
		if(numv > 2) {
			return true;
		}
		return false;
	}

	/**
	 * The name of the function
	 */
	public String name() {
		return "Contains more than two v or V.";
	}
}
