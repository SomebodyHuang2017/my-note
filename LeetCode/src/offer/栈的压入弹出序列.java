package offer;

import java.util.Stack;

public class 栈的压入弹出序列 {
	
	public static boolean isPopOrder(int[] pushA, int[] popA) {
		//模拟栈的压入和弹出
		//压入顺序：1、2、3、4、5
		//弹出顺序：4、3、5、1、2
		Stack<Integer> stack = new Stack<>();
		int pushIndex = 0;
		int popIndex = 0;
		while(pushIndex < pushA.length) {
			stack.push(pushA[pushIndex]);//先入栈
			
			while(!stack.empty() && popA[popIndex]==stack.peek()) {
				stack.pop();
				popIndex++;
			}
			pushIndex++;
		}
		
		return stack.empty();
	}
	
	public static void main(String[] args) {
		int[] pushA = {1,2,3,4,5};
		int[] popA = {4,5,3,2,1};
		boolean result = isPopOrder(pushA, popA);
		System.out.println(result);
	}
}
