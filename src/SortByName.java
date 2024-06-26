// Gilbert Wang
// January 28th, 2023
// SortByName
// This program sort Challenges by name.

import java.util.Comparator;
// Comparator
public class SortByName implements Comparator <challenges>{
	// Compare method
	// compares challenges by name
	public int compare(challenges c1, challenges c2) {
		return c1.getKey().compareTo(c2.getKey());
	}
}
