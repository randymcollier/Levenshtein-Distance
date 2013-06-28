package levenshteinDistance;
//Randy Collier
//CSci 433
//4-29-13
//This program will calculate the edit distance of two user inputted strings.

import java.util.Scanner;

public class LevenshteinDistance {

	public static void main(String[] args) {
		// read in two strings from keyboard
		System.out.println("This program will calculate the edit distance of two strings.");
		System.out.print("Enter the string to start with: ");
		Scanner scan = new Scanner(System.in);
		String first = scan.nextLine();
		System.out.print("Enter the string to finish with: ");
		String second = scan.nextLine();
		
		// calculate the edit distance of the two inputted strings
		EditDistance(first, second);
	}
	
	public static void EditDistance(String s, String t) {
		
		// transform each string to a char array
		  char[] s_Array = s.toCharArray();
		  char[] t_Array = t.toCharArray();
		
		// degenerate cases
	    if (s.equals(t)) System.out.println("\nThe edit distance is 0. \nBoth words are the same.");
	    else if (s.length() == 0) {
	    	System.out.println("\nThe edit distance is " + t.length() + ".");
	    	System.out.println("\nSequence of operations:");
	    	for (int i = 0; i < t.length(); i++) {
	    		System.out.println("Inserted " + t_Array[i]);
	    	}
	    }
	    else if (t.length() == 0) {
	    	System.out.println("\nThe edit distance is " + s.length() + ".");
	    	System.out.println("\nSequence of operations:");
	    	for (int i = 0; i < s.length(); i++) {
	    		System.out.println("Deleted " + s_Array[i]);
	    	}
	    }
	    else {
		// for all i and j, d[i,j] will hold the Levenshtein distance between
	  // the first i characters of s and the first j characters of t;
	  // note that d has (m+1)x(n+1) values
	  int[][] d = new int[s.length() + 1][t.length() + 1];
	  
	  // backtrace array
	  String[][] backtrace = new String[s.length() + 1][t.length() +1];

	  // set each element to zero
	  for (int i = 0; i <= s.length(); i++) {
		  for (int j = 0; j <= t.length(); j++) {
			  d[i][j] = 0;
		  }
	  }

	  // source prefixes can be transformed into empty string by
	  // dropping all characters 
	  for (int i = 0; i <= s.length(); i++) {
		  d[i][0] = i;                    
	  }

	  // target prefixes can be reached from empty source prefix
	  // by inserting every characters
	  for (int i = 0; i <= t.length(); i++) {
		  d[0][i] = i;                    
	  }
	  
		  for (int i = 1; i < s.length(); i++)
			  backtrace[i][0] = "Deleted " + s_Array[i-1];
		  for (int i = 1; i < t.length(); i++)
			  backtrace[0][i] = "Inserted " + t_Array[i-1];
		  backtrace[0][0] = "Same";
		  backtrace[s.length()][0] = "Deleted " + s_Array[s.length() - 1];
		  backtrace[0][t.length()] = "Inserted " + t_Array[t.length() - 1];

	  for (int j = 1; j <= s.length(); j++) {
	    for (int i = 1; i <= t.length(); i++) {
	      if (s_Array[j - 1] == t_Array[i- 1]) {
	    	  d[j][i] = d[j-1][i-1];
	    	  backtrace[j][i] = "Same";
	      }
	      else {
	        d[j][i] = min(d[j-1][i] + 1, d[j][i-1] + 1, d[j-1][i-1] + 1);
	        backtrace[j][i] = min2(d[j-1][i] + 1, d[j][i-1] + 1, d[j-1][i-1] + 1);
	        if (backtrace[j][i].equals("Inserted"))
		       	backtrace[j][i] += " " + t_Array[i - 1];
		    else if (backtrace[j][i].equals("Deleted"))
		       	backtrace[j][i] += " " + s_Array[j - 1];
		    else
		       	backtrace[j][i] += " " + s_Array[j - 1] + " with " + t_Array[i - 1];
	        
	      }
	      
	    }
	  }

	  System.out.println("\nThe edit distance of '" + s + "' and '" + t + "' is " + d[s.length()][t.length()] + ".");
	  
		  String[] operations = new String[d[s.length()][t.length()]];
		  int i = t.length(), j = s.length(), a = d[s.length()][t.length()] - 1;
			  while (a >= 0) {
				  if (backtrace[j][i].contains("Same")) {
					  if (i == 0 && j == 0) {
						  i = t.length();
						  j = s.length();
					  }
					  else if (i == 0) {
						  i = t.length();
						  j--;
					  }
					  else if (j == 0) {
						  j = s.length();
						  i--;
					  }
					  else {
						  i--;
						  j--;
					  }
				  }
				  else if (backtrace[j][i].contains("Replaced")) {
					  operations[a] = backtrace[j][i];
					  a--;
					  
					  if (i == 0 && j == 0) {
						  i = t.length();
						  j = s.length();
					  }
					  else if (i == 0) {
						  i = t.length();
						  j--;
					  }
					  else if (j == 0) {
						  j = s.length();
						  i--;
					  }
					  else {
						  i--;
						  j--;
					  }
				  }
				  else if (backtrace[j][i].contains("Deleted")) {
					  if (i==0) {
						  operations[a] = backtrace[j][i];
						  a--;
						  j--;
					  }
					  else {
					  
					  operations[a] = backtrace[j][i];
					  a--;
					  if (j == 0)
						  j = s.length();
					  else
						  j--;
					  }
				  }
				  else if (backtrace[j][i].contains("Inserted")) {
					  if (j==0) {
						  operations[a] = backtrace[j][i];
						  a--;
						  i--;
					  }
					  else {
					  
					  operations[a] = backtrace[j][i];
					  a--;
					  if (i == 0)
						  i = t.length();
					  else
						  i--;
					  }
				  }
			  }
		  System.out.println("\nSequence of operations:");
		  for (int k = 0; k < operations.length; k++)
			  System.out.println(operations[k]);
	    }
	    
	}
	
	public static int min(int first, int second, int third) {
		return Math.min(Math.min(first, second), third);
	}
	
	public static String min2(int first, int second, int third) {
		int minimum = first;
		String m = null;
		if (second <= minimum) minimum = second;
		if (third <= minimum) minimum = third;
		if (minimum == first) m = "Deleted";
		else if (minimum == second) m = "Inserted";
		else if (minimum == third) m = "Replaced";
		return m;
	}
	
	public static int max(int first, int second) {
		int max = first;
		if (second > max)
			max = second;
		return max;
	}

}
