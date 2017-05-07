package ChapterTwo;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class ChapterTwo {
	public static void main(String[] args){
		System.out.println("Select a function to run: ");
		System.out.println("1. removeDuplicates: returns the head of the LinkedList with the duplicates removed (O(N) space and time)");
		System.out.println("2. spaceOptimizedRemoveDuplicates: does the same thing as 1, but with O(1) space and O(N^2) time");
		System.out.println("3. kthToLast: return the kth to last element of a singly linked list. Loops around if k > size of LinkedList");
		System.out.println("4. deleteMiddleNode: delete the given node from the list");
		System.out.println("5. partition: partition the linked list around given value");
		System.out.println("6. sumList: sum two linked list represetnations of numbers (reverse)");
		System.out.println("7. sumListForward: sum two linked list representations of numbers (forward)");
		System.out.println("8. isPalindrome: check if LinkedList is a palindrome.");
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
		} else if (choice == 4) {
			Random rand = new Random();
			Integer[] items = new Integer[6];
			for (int i = 0; i < 6; i++){
				items[i] = rand.nextInt(10);
			}
			LinkedList<Integer> list = new LinkedList<>(items);
			list.print();
			System.out.println("choose the index of the node you'd like to delete");
			int index = in.nextInt();
			list.deleteMiddleNode(list.getNode(index));
			list.print();
			
		} else if (choice == 5) {
			
		} else {
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
		
		public Node<T> getNode(int index){
			Node<T> runner = head;
			int counter = 0;
			while (counter < index){
				runner = runner.next;
			}
			return runner;
		}
		
		public void deleteMiddleNode(Node<T> node){
			node.item = node.next.item;
			node.next = node.next.next;
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