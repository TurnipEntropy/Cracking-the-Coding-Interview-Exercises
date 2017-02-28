package ChapterOne;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class ChapterOne {
	
	public static void main(String[] args){
		
		System.out.println("Select a function to run: ");
		System.out.println("1. isUnique: check if a String is comprised only of unique characters");
		System.out.println("2. checkPermutation: check if two strings are permutations");
		System.out.println("3. URLify: Replace spaces with %20");
		System.out.println("4. palindromePermutation: Check if the string is a permutation of a palindrome");
		System.out.println("5. oneAway: Check if two strings are off by 1 edit");
		System.out.println("6. compress: compress a string");
		
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
		} else if (choice == 3){
			System.out.println("Please enter the String to be URLified");
			System.out.println("Do note that as a part of the assumption in this question,");
			System.out.println("there have to be 2x as many spaces at the end of the String");
			System.out.println("as there are within the String");
			String s = in.nextLine();
			System.out.println("Enter the true length of the string(i.e. minus the extra spaces");
			int l = in.nextInt();
			System.out.println(URLify(s, l));
		} else if (choice == 4){
			System.out.println("Please enter the String to be checked");
			String s = in.nextLine();
			System.out.println(palindromePermutation(s));
		} else if (choice == 5) {
			System.out.println("Please enter first String");
			String s1 = in.nextLine();
			System.out.println("Please enter second String");
			String s2 = in.nextLine();
			System.out.println(oneAway(s1, s2));
		} else if (choice == 6){
			System.out.println("Please enter String to be compressed");
			String s = in.nextLine();
			System.out.println(compress(s));
		} else {
			System.out.println("No other functions are supported at this time.");
		}
	}
	
	public static String compress(String s){
		//question states that only alphabetic chars are allowed
		StringBuilder sb = new StringBuilder();
		int i = 0;
		char c = 'a';
		while (i < s.length()){
			int nums = 0;
			c = s.charAt(i);
			while (i <s.length() && s.charAt(i) == c){
				nums++;
				i++;
			}
			sb.append(c);
			sb.append(nums);
			
		}
		return s.length() > sb.length() ? sb.toString() : s;
		
	}
	public static boolean oneAway(String one, String two){
		//implement the dynamic programming solution to this for practice later.
		if (Math.abs(one.length() - two.length()) >= 2)
			return false;
		if (one.length() != two.length()){
			String temp = one.length() < two.length() ? one : two;
			one = one.length() < two.length() ? two : one;
			two = temp;
			int i = 0, j = 0;
		
			while (i < one.length() && j < two.length()){
				if (one.codePointAt(i) != two.codePointAt(j)){
					i++;
					continue;
				}
				i++;
				j++;
			}
			return i == j + 1;
		} else {
			int difs = 0;
			int i = 0;
			while (i < one.length()){
				if (one.codePointAt(i) != two.codePointAt(i)){
					difs++;
				}
				if (difs > 1)
					return false;
				i++;
			}
			return true;
		}
	}
	
	public static boolean palindromePermutation(String s){
		s = s.toLowerCase();
		HashSet<Integer> set = new HashSet<>();
		for (int i = 0; i < s.length(); i++){
			if (s.charAt(i) == ' ')
				continue;
			Integer c = s.codePointAt(i);
			if (set.contains(c))
				set.remove(c);
			else
				set.add(c);
		}
		if (set.size() > 1){
			return false;
		}
		return true;
	}
	
	public static String URLify(String s, int trueLength){
		//question states that we can assume there is enough space at the end of the string
		//to hold the extra characters needed
		char[] arr = s.toCharArray();
		int end = arr.length - 1;
		for (int i = trueLength - 1; i >= 0; i--){
			if (arr[i] != ' '){
				arr[end--] = arr[i];
			} else {
				arr[end--] = '0';
				arr[end--] = '2';
				arr[end--] = '%';
			}
		}
		return new String(arr);
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