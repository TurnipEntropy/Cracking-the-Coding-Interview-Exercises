package ChapterOne;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
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
		System.out.println("7. rotateMatrix: rotates a randomly generated NxN matrix of 32-bit integers");
		System.out.println("8. zeroMatrix: nullifies any row/column with a 0 in it in an NxM matrix");
		System.out.println("9. stringRotation: checks to see if s2 is a rotation of s1");
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
		} else if (choice == 7) {
			System.out.println("Please enter N for the NxN matrix");
			int n = in.nextInt();
			rotateMatrix(n);
		} else if (choice == 8) {
			System.out.println("Please enter N for the NxM matrix");
			int n = in.nextInt();
			System.out.println("Please enter the M for the NxM matrix");
			int m = in.nextInt();
			zeroMatrix(n, m);
		} else if (choice == 9) {
			System.out.println("Please enter String 1");
			String s1 = in.nextLine();
			System.out.println("Please enter String 2");
			String s2 = in.nextLine();
			System.out.println(stringRotation(s1, s2));
		} else {
			System.out.println("No other functions are supported at this time.");
		}
	}
	
	public static boolean stringRotation(String s1, String s2){
		if (s2.length() != s1.length())
			return false;
		s2 += s2;
		return isSubstring(s1, s2);
	}
	
	public static void zeroMatrix(int N, int M){
		int[][] arr = new int[N][M];
		Random rand = new Random();
		System.out.println("Original matrix:");
		for (int i = 0; i < N; i++){
			for (int j = 0; j < M; j++){
				arr[i][j] = rand.nextInt(7);
				System.out.printf("%-4d", arr[i][j]);
			}
			System.out.println("");
		}
		
		boolean nullFirstCol = false, nullFirstRow = false;
		for (int i = 0; i < N; i++){
			if (arr[i][0] == 0) {
				nullFirstCol = true;
				break;
			}
		}
		for (int j = 0; j < M; j++){
			if (arr[0][j] == 0) {
				nullFirstRow = true;
				break;
			}
		}
		for (int i = 1; i < N; i++){
			for (int j = 1; j < M; j++){
				if (arr[i][j] == 0){
					arr[i][0] = 0;
					arr[0][j] = 0;
				}
			}
		}
		for (int i = 1; i < N; i++){
			if (arr[i][0] == 0){
				nullify(arr, i, true);
			}
		}
		for (int j = 1; j < M; j++){
			if (arr[0][j] == 0){
				nullify(arr, j, false);
			}
		}
		if (nullFirstCol)
			nullify(arr, 0, false);
		if (nullFirstRow)
			nullify(arr, 0, true);
		System.out.println("");
		System.out.println("Zeroed matrix: ");
		print2DSmall(arr);
	}
	
	
	public static void rotateMatrix(int N){
		if (N > 9){
			System.out.print("This will work... but it looks like crud, so not doing it");
			return;
		}
		System.out.println("Original matrix:");
		Random rand = new Random();
		int[][] arr = new int[N][N];
		for (int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				arr[i][j] = rand.nextInt();
				System.out.format("%-15d", arr[i][j]);
			}
			System.out.println("");
		}
		//xp = cos(t)x - sin(t)y
		//yp = sin(t)x + cos(t)y
		//all new xs = 0 - (y - center) = center - y + center
		//all new ys = x - center + center = x
		double centerC = (N - 1) / 2.0;
		int center = (N - 1) / 2;
		int centerX = N % 2 == 0 ? center : center - 1;
		for (int i = 0; i <= center; i++){
			for (int j = 0; j <= centerX; j++){
				int xp = (int)(centerC *2) - i;
				int xpp = (int)(centerC *2) - j;
				int xppp = (int)(centerC *2) - xp;
				int temp = arr[i][j];
				arr[i][j] = arr[xpp][xppp];
				arr[xpp][xppp] = arr[xp][xpp];
				arr[xp][xpp] = arr[j][xp];
				arr[j][xp] = temp;
			}
		}
		System.out.println("");
		System.out.println("rotated matrix:");
		print2DLarge(arr);
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
	
	private static void print2DSmall(int[][] arr){
		for (int i = 0; i < arr.length; i++){
			for (int j = 0; j < arr[0].length; j++){
				System.out.printf("%-4d", arr[i][j]);
			}
			System.out.println("");
		}
	}
	
	private static void print2DLarge(int[][] arr){
		for (int i = 0; i < arr.length; i++){
			for (int j = 0; j < arr[0].length; j++){
				System.out.printf("%-15d",  arr[i][j]);
			}
			System.out.println("");
		}
	}
	
	private static void nullify(int[][] arr, int index, boolean isRow){
		if (!isRow){
			//nullify the column
			for (int i = 0; i < arr.length; i++){
				arr[i][index] = 0;
			}
		} else if (isRow) {
			//nullify the row
			for (int j = 0; j< arr[0].length; j++){
				arr[index][j] = 0;
			}
		}
	}
	
	private static boolean isSubstring(String small, String large){
		return large.contains(small);
	}
}