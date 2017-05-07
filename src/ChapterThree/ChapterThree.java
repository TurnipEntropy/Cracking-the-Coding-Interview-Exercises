package ChapterThree;

import java.util.ArrayList;
import java.util.Scanner;

import ChapterTwo.ChapterTwo.LinkedList;

public class ChapterThree {
	
	public static void main(String[] args){
		System.out.println("Select a function to run: ");
		System.out.println("1. Implements 3 stacks in a single stack");
		System.out.println("2. Implements a stack with an additional method: min");
		System.out.println("3. Stack of Plates: splits the stack into mulitple stacks when too large");
		System.out.println("4. Stack implementation of a Queue");
		System.out.println("5. Sort a stack using only an additional temporary stack");
		System.out.println("6. Animal Shelter problem... see the book");
		Scanner in = new Scanner(System.in);
		int choice = in.nextInt();
		String garbage = in.nextLine();
		if (choice == 1){
			System.out.println("API documentation: pushToStack(T item, int stackNumber)\n"
							+ "popFromStack(int stackNumber)\npeekAtStack(int stackNumber)\n\n"
					        + "Please enter commands you'd like to use (type of Stack will "
					        + "be String for ease)\n. Type \"exit\" to exit\n");
			ThreeInOneStack<String> stack = new ThreeInOneStack<String>();
			String l = in.nextLine();
			while (!l.toLowerCase().equals("exit")){
				String[] sides = l.split("\\(");
				if (sides[0].equals("pushToStack")){
					String[] params = sides[1].split(",");
					int stackNum = Integer.parseInt(params[1].trim().substring(0, 1));
					stack.pushToStack(params[0], stackNum);
				} else if (sides[0].equals("popFromStack")){
					System.out.println(
							stack.popFromStack(Integer.parseInt(sides[1].trim().substring(0, 1))));
				} else if (sides[0].equals("peekAtStack")){
					System.out.println(
							stack.peekAtStack(Integer.parseInt(sides[1].trim().substring(0, 1))));
				}
				l = in.nextLine();
			}
			
		} else if (choice == 2){
			System.out.println("API documentation: push(T item)\n"
					+ "pop()\npeek()\nmin()\n\n"
			        + "Please enter commands you'd like to use (type of Stack will "
			        + "be String for ease)\n. Type \"exit\" to exit\n");
			
			StackMin<String> stack = new StackMin<>();
			String l = in.nextLine();
			while (!l.toLowerCase().equals("exit")){
				String[] call = l.split("\\(");
				if (call[1].charAt(call[1].length() - 1) != ')'){
					System.out.println("Try again, this time without a typo");
					continue;
				}
				if (call[0].equals("push")){
					String item = call[1].substring(0, call[1].length() - 1);
					stack.push(item);
				} else if (call[0].equals("pop")){
					System.out.println(stack.pop());
				} else if (call[0].equals("peek")){
					System.out.println(stack.peek());
				} else if (call[0].equals("min")){
					System.out.println(stack.min());
				}
				l = in.nextLine();
			}
		}
			
	}
	public static class Stack<T extends Comparable<T>> implements Comparable<Stack>{
		T[] back;
		int pos;
		@SuppressWarnings("unchecked")
		public Stack(){
			back = (T[]) new Comparable[8];
			pos = 0;
		}
		
		public boolean isEmpty(){
			return pos == 0;
		}
		public void push(T item){
			checkSize();
			back[pos++] = item;
		}
		
		public T pop(){
			if (isEmpty())
				return null;
			checkSize();
			return back[--pos];
		}
		
		public T peek(){
			if (isEmpty())
				return null;
			return back[pos - 1];
		}
		
		private void checkSize(){
			if (pos * 4 <= 12 && pos < back.length)
				return;
			if(pos >= back.length || pos * 4 <= back.length)
				resize();
		}
		
		@SuppressWarnings("unchecked")
		private void resize(){
			T[] temp = (T[]) new Comparable[pos * 2];
			System.arraycopy(back, 0, temp, 0, pos);
			back = temp;
		}
		
		@Override
		public int compareTo(Stack other){
			return Integer.compare(this.pos, other.pos);
		}
	}
	public static class StackMin<T extends Comparable<T>>{
		
		T[] back;
		int pos;
		ArrayList<Integer> mins;
		int minsSize;
		
		@SuppressWarnings("unchecked")
		public StackMin(){
			back = (T[]) new Comparable[8];
			pos = 0;
			mins = new ArrayList<>();
			minsSize = -1;
		}
		
		public boolean isEmpty(){
			return pos == 0;
		}
		
		public void push(T item){
			
			checkSize();
			if (mins.isEmpty()){
				mins.add(pos);
				minsSize++;
			} else if (item.compareTo(back[mins.get(minsSize)]) <= 0){
				mins.add(pos);
				minsSize++;
			}
			back[pos++] = item;
			
		}
		
		public T pop(){
			T ret = back[--pos];
			if (isEmpty())
				return null;
			if (mins.get(minsSize) == pos){
				mins.remove(minsSize--);
			}
			checkSize();
			return ret;
		}
		
		public T peek(){
			if (isEmpty())
				return null;
			return back[pos - 1];
		}
		
		public T min(){
			return back[mins.get(minsSize)];
		}
		
		private void checkSize(){
			if (back.length > pos * 4 && back.length > 8){
				changeSize();
			} else if (pos >= back.length){
				changeSize();
			}
		}
		
		private void changeSize(){
			T[] temp = (T[]) new Comparable[pos * 2];
			System.arraycopy(back, 0, temp, 0, Math.min(temp.length, back.length));
			back = temp;
		}
		
	}
	
	public static class ThreeInOneStack<T>{
		
		public static final String IAE = "Only three stacks allowed. They are 0 indexed";
		T[] back;
		int l, m, r;
		
		@SuppressWarnings("unchecked")
		public ThreeInOneStack(){
			
			back = (T[])new Object[24];
			l = 0;
			m = 1;
			r = 2;
			
		}
		
		public boolean allAreEmpty(){
			return stackIsEmpty(0) && stackIsEmpty(1) && stackIsEmpty(2);
		}
		
		public boolean stackIsEmpty(int stack) throws IllegalArgumentException{
			if (stack == 0){
				return l == 0;
			} else if (stack == 1){
				return m == 1;
			} else if (stack == 2){
				return r == 2;
			} else {
				throw new IllegalArgumentException(IAE);
			}
		}
		public void pushToStack (T value, int stack) throws IllegalArgumentException{
			checkSpace(stack);
			if (stack == 0){
				back[l] = value;
				l += 3;
			} else if (stack == 1){
				back[m] = value;
				m += 3;
			} else if (stack == 2){
				back[r] = value;
				r += 3;
			} else {
				throw new IllegalArgumentException(IAE);
			}
		}
		
		
		public T popFromStack (int stack) throws IllegalArgumentException{
			T ret = null;
			if (stackIsEmpty(stack))
				return null;
			if (stack == 0){
				l -= 3;
				ret = back[l];
			} else if (stack == 1){
				m -= 3;
				ret = back[m];
			} else if (stack == 2){
				r -= 3;
				ret = back[r];
			} else {
				throw new IllegalArgumentException(IAE);
			}
			
			checkStorage();
			return ret;
			
		}
		
		public T peekAtStack(int stack) throws IllegalArgumentException{
			T ret = null;
			if (stackIsEmpty(stack))
				return null;
			if (stack == 0){
				ret = back[l - 3];
			} else if(stack == 1){
				ret = back[m - 3];
			} else if (stack == 2){
				ret = back[r - 3];
			} else {
				throw new IllegalArgumentException(IAE);
			}
			return ret;
		}
		
		private void checkStorage(){
			int largest = Math.max(l,  Math.max(m, r));
			if (largest <= back.length * 0.25) {
				//can safely change the size to half of its current size
				adjustSize(back.length / 2);
			}
		}
		private void checkSpace(int stack) throws IllegalArgumentException{
			if (stack == 0){
				if (l >= back.length){
					adjustSize(2 * l);
				}
			} else if (stack == 1){
				if (m >= back.length){
					adjustSize(2 * m);
				}
			} else if (stack == 2){
				if (r >= back.length){
					adjustSize(2 * r);
				}
			} else {
				throw new IllegalArgumentException(IAE);
			}
		}
		
		private void adjustSize(int size){
			@SuppressWarnings("unchecked")
			T[] temp = (T[]) new Object[size];
			System.arraycopy(back, 0, temp, 0, Math.min(temp.length, back.length));
			back = temp;
		}
	}
}
