package ChapterOne;

import java.util.BitSet;
import java.util.Scanner;

public class ChapterOne {
	
	public static void main(String[] args){
		
		System.out.println("Select a function to run: ");
		System.out.println("1. isUnique: check if a String is comprised only of unique characters");
		System.out.println("2. checkPermutation: check if two strings are permutations");
		Scanner in = new Scanner(System.in);
		int choice = in.nextInt();
		String garbage = in.nextLine();
		if (choice == 1){
			System.out.println("Please enter a String");
			String s = in.nextLine();
			System.out.println("Please enter the number of chars in the charset used");
			int numChar = in.nextInt();
			System.out.println("Please enter the lowerst integer representation of the chars in this char set");
			int  lowestChar = in.nextInt();
			System.out.println(isUnique(s, numChar, lowestChar));
		} else if (choice == 2){
			System.out.println("Please enter a String");
			String s = in.nextLine();
			System.out.println("Please enter a String");
			String s2 = in.nextLine();
			System.out.println("Please enter the number of chars in the charset used");
			int numChar = in.nextInt();
			System.out.println("Please enter the lowerst integer representation of the chars in this char set");
			int  lowestChar = in.nextInt();
			garbage = in.nextLine();
			System.out.println("Do you want the permutations to be case sensitive?");
			String caseSensitive = in.nextLine();
			System.out.println(checkPermutation(s, s2, numChar, lowestChar, caseSensitive));
		} else {
			System.out.println("No other functions are supported at this time.");
		}
	}
	
	public static boolean checkPermutation(String one, String two, int numChar, int lowestChar,
											String caseSensitive){
		
		if (caseSensitive.equals("No") || caseSensitive.equals("n") || 
				caseSensitive.equals("N") || caseSensitive.equals("no") ||
				caseSensitive.equals("Nope")){
			one = one.toLowerCase();
			two = two.toLowerCase();
		}
		int n = one.length();
		int m = two.length();
		if (n != m)
			return false;
		//BitSet won't work as well for this one sadly.
		int[] counts = new int[numChar];
		int sum = 0;
		for (int i = 0; i < n; i++){
			counts[one.codePointAt(i) - lowestChar]++;
			sum++;
		}
		for (int i = 0; i < n; i++){
			counts[two.codePointAt(i) - lowestChar]--;
			if (counts[two.codePointAt(i) - lowestChar] < 0)
				return false;
			sum--;
		}
		if (sum > 0)
			return false;
		return true;
	}
	
	public static boolean isUnique(String s, int numChar, int lowestChar){
		
		
		//need a bitSet that can hold all of the numChar in String s
		BitSet bitSet = new BitSet(numChar);
		for (int i = 0; i < s.length(); i++){
			int loc = s.codePointAt(i) - lowestChar;
			bitSet.flip(loc);
			if (bitSet.get(loc) == false)
				return false;
		}
		
		return true;
	}
}