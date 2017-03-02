package ChapterTwo;

import java.util.HashSet;
import java.util.Scanner;

public class ChapterTwo {
	public static void main(String[] args){
		System.out.println("Select a function to run: ");
		System.out.println("1. removeDuplicates: returns the head of the LinkedList with the duplicates removed (O(N) space and time)");
		System.out.println("2. spaceOptimizedRemoveDuplicates: does the same thing as 1, but with O(1) space and O(N^2) time");
		System.out.println("3. kthToLast: return the kth to last element of a singly linked list. Loops around if k > size of LinkedList");

		Scanner in = new Scanner(System.in);
		int choice = in.nextInt();
		String garbage = in.nextLine();
		if (choice == 1){
			System.out.println("Please enter the number of items you wish to add to the LinkedList");
			int n = in.nextInt();
			garbage = in.nextLine();
			System.out.println("Please enter " + n + " items to be added to the LinkedList");
			String[] arr = new String[n];
			for (int i = 0; i < n; i++){
				arr[i] = in.nextLine();
			}
			LinkedList<String> list = new LinkedList<>(arr);
			System.out.println("Original LinkedList: ");
			list.print();
			list.removeDuplicates();
			System.out.println("LinkedList without duplicates: ");
			list.print();
			
		} else if (choice == 2){
			System.out.println("Please enter the number of items you wish to add to the LinkedList");
			int n = in.nextInt();
			garbage = in.nextLine();
			System.out.println("Please enter " + n + " items to be added to the LinkedList");
			String[] arr = new String[n];
			for (int i = 0; i < n; i++){
				arr[i] = in.nextLine();
			}
			LinkedList<String> list = new LinkedList<>(arr);
			System.out.println("Original LinkedList: ");
			list.print();
			list.spaceOptimizedRemoveDuplicates();
			System.out.println("LinkedList without duplicates: ");
			list.print();
		} else if (choice == 3){
			System.out.println("Please enter the number of items you wish to add to the LinkedList");
			int n = in.nextInt();
			garbage = in.nextLine();
			System.out.println("Please enter " + n + " items to be added to the LinkedList");
			String[] arr = new String[n];
			for (int i = 0; i < n; i++){
				arr[i] = in.nextLine();
			}
			LinkedList<String> list = new LinkedList<>(arr);
			System.out.println("Please enter k for the kth to last element");
			int k = in.nextInt();
			System.out.println("Please enter 1 if you want to count the NULL tail as the last "
					+ "element, or 0 if you want to count the last non-null node as the last element");
			int l = in.nextInt();
			Node<String> kth = list.kthToLast(k, l);
			if (kth == null)
				System.out.println("NULL");
			else
				System.out.println(kth.toString());
		} /*else if (choice == 4){
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
		}*/ else {
			System.out.println("No other functions are supported at this time.");
		}
		in.close();
	}
	
	public static class LinkedList<T>{
		Node<T> head;
		
		public LinkedList(T[] items){
			head = new Node<T>(items[0]);
			head.next = new Node<T>(items[1]);
			Node<T> next = head.next;
			for (int i = 2; i < items.length; i++){
				next.next = new Node<>(items[i]);
				next = next.next;
			}
		}
		
		public LinkedList(T item){
			head = new Node<>(item);
		}
		
		public void removeNextNode (Node<T> priorNode){
			if (priorNode.next == null)
				return;
			Node<T> replacement = priorNode.next.next;
			priorNode.next = replacement;
		}
		
		public void print(){
			Node<T> next = head;
			StringBuilder sb = new StringBuilder();
			while (next != null){
				sb.append(next.item.toString());
				sb.append("-->");
				next = next.next;
			}
			sb.append("NULL");
			System.out.println(sb.toString());
		}
		
		public void removeDuplicates(){
			if (head == null)
				return;
			HashSet<T> set = new HashSet<>();
			Node<T> next = head;
			set.add(next.item);
			while (next.next != null){
				if (set.contains(next.next.item)){
					removeNextNode(next);
					if (next.next == null)
						break;
					continue;
				} else {
					set.add(next.next.item);
				}
				next = next.next;
			}
		}
		public void spaceOptimizedRemoveDuplicates(){
			Node<T> current = head;
			Node<T> checker = head;
			while (current != null){
				while (checker.next != null){
					if (current.item.equals(checker.next.item)){
						removeNextNode(checker);
						if (checker.next == null)
							return;
						continue;
					}
					checker = checker.next;
				}
				current = current.next;
				checker = current;
			}
		}
		public Node<T> kthToLast(int k, int lastIndex){
			Node<T> slow = head;
			Node<T> fast = head;
			int counter = 0;
			int stop = k - lastIndex;
			while (counter < k && fast != null){
				fast = fast.next;
				counter++;
				if (fast == null && counter < k - 1){
					fast = head;
					counter++;
				}
			}
			while (fast != null){
				fast = fast.next;
				slow = slow.next;
			}
			return slow;
			
		}
	}
	
	private static class Node<T>{
		T item;
		Node<T> next;
		
		public Node(T item){
			this.item = item;
			next = null;
		}
		
		public String toString(){
			return item.toString();
		}
		
	}
}