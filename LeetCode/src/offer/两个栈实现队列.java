package offer;

import java.util.Stack;

public class 两个栈实现队列 {
	Stack<Integer> in = new Stack<>();
	Stack<Integer> out = new Stack<>();
	public void push(int node) {
		in.push(node);
	}
	
	public int pop() {
		//out不为空，直接出栈
		if(!out.empty())
			return out.pop();

		while(!in.empty()) {
			out.push(in.pop());
		}
		
		if(out.empty())
			throw new RuntimeException("队列为空！");

		return out.pop();
	}
}	
