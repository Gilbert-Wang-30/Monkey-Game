// Gilbert Wang
// challenges
// This class is for challenges object
// January 28th, 2023

import java.util.*;

public class challenges implements Comparable<challenges>{
	// instance variables
	private String key;
	private int number;
	
	// Constructor
	public challenges(String key, int number) {
		this.key = key;
		this.number = number;
	}

	// change
	// This method adds the number of word by 1
	public void change() {
		this.number++;
	}
	
	// compareTo
	// Compare by number from big to small
	public int compareTo(challenges o) {
		return o.number-this.number;
	}
	
	// Getters
	// get key
	public String getKey() {
		return this.key;
	}
	// get number
	public int getNumber() {
		return this.number;
	}
	
	// toString
	// toString method
	public String toString() {
		return String.format("%-10s %s", key, number);
	}


}

