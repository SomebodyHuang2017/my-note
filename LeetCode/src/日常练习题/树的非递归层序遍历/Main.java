package 日常练习题.树的非递归层序遍历;

import java.util.Stack;

public class Main {
	public static void main(String[] args) {
		/**
		 3
	   /   \
	  2     5
	    \  / \
	    1 4   6
	   / \
	  -1  0
		 */
		TreeNode root = new TreeNode(3);
		TreeNode left = new TreeNode(2);
		TreeNode right = new TreeNode(5);
		root.left = left;
		root.right = right;
		TreeNode leftChild =  new TreeNode(1);
		leftChild.right = new TreeNode(0);
		leftChild.left = new TreeNode(-1);
		left.right  = leftChild;
		right.left = new TreeNode(4);
		right.right = new TreeNode(6);
		
		preOrder(root);
		System.out.println();
		inOrder(root);
		System.out.println();
		postOrder(root);
		
		
	}
	
	public static void preOrder(TreeNode root) {
		if(root==null) return;
		//先根，再左右
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		while(!stack.empty()) {
			TreeNode node = stack.pop();
			System.out.print(node.val + " ");
			if(node.right != null) {
				stack.push(node.right);
			}
			if(node.left != null) {
				stack.push(node.left);
			}
		}
	}
	/**
	 3
  /   \
 2     5
   \  / \
   1 4   6
   /\
 -1  0
	 */
	private static void inOrder(TreeNode root) {
		if(root==null) return;
		//先左，再根，再右
		Stack<TreeNode> stack = new Stack<>();
		TreeNode p = root;
		while(p != null || !stack.empty()) {
			//左子树持续入栈
			while(p != null) {
				stack.push(p);
				p = p.left;
			}
			p = stack.pop();
			System.out.print(p.val + " ");
			if(p.right != null) {
				p = p.right;
			} else {
				p = null;
			}
		}
	}
	/**
	   3
	 /   \
	2     5
	  \  / \
	  1 4   6
	  /\
	-1  0
		 */
	private static void postOrder(TreeNode root) {
		if(root==null) return;
		//先左，再右，再根
		Stack<TreeNode> stack = new Stack<>();
		TreeNode p = root;
		boolean isVisited = false;
		while(p != null || !stack.empty()) {
			while(p != null) {
				stack.push(p);
				p = p.left;
			}
			p = stack.peek();
			if(!isVisited && p.right != null) {
				p = p.right;
				isVisited = false;
				continue;
			} else {
				p = stack.pop();	
				System.out.print(p.val + " ");		
				if(!stack.empty() && p == stack.peek().left) {
					p = stack.peek().right;
				} else {
					p = null;
					isVisited = true;
				}
			}

		}
	}
}
