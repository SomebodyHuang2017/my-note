package offer;

public class 二叉树的最大高度 {
	public static int getMaxDepth(TreeNode root) {
		if(root == null)
			return 0;
		int left = getMaxDepth(root.left);
		int right = getMaxDepth(root.right);
		return Math.max(left, right) + 1;
	}
	
	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.left.left = new TreeNode(3);
		root.left.left.right = new TreeNode(4);
		root.right = new TreeNode(5);
		root.right.left = new TreeNode(6);
		root.right.right = new TreeNode(7);
		
		/*
		 1
		/  \ 
	   2    5
	  /    / \
     3    6   7
      \ 
       4
		 * */
		System.out.println(getMaxDepth(root));
	}
}
