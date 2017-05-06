package ChapterThree;

import java.util.ArrayList;

public class ChapterThree {
	
	public static class StackMin<T extends Comparable<T>>{
		
		T[] back;
		int pos;
		ArrayList<Integer> mins;
		int minsSize;
		
		@SuppressWarnings("unchecked")
		public StackMin(){
			back = (T[]) new Object[8];
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
			if (mins.get(minsSize) == pos){
				mins.remove(minsSize--);
			}
			checkSize();
			return ret;
		}
		
		public T peek(){
			return back[pos - 1];
		}
		
		public T min(){
			return back[mins.get(minsSize)];
		}
		
		private void checkSize(){
			if (back.length > pos * 4){
				changeSize();
			} else if (pos >= back.length){
				changeSize();
			}
		}
		
		private void changeSize(){
			T[] temp = (T[]) new Object[pos * 2];
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
		
		
		public T popFromStack (T value, int stack) throws IllegalArgumentException{
			T ret = null;
			if (stack == 0){
				l -= 3;
				ret = back[l];
			} else if (stack == 1){
				m -= 3;
				ret = back[m];
			} else if (stack == r){
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
