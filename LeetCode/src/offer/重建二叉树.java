package offer;

public class 重建二叉树 {
	
	public static void main(String[] args) {
		int[] pre = {1 ,2,4,7 ,3,5,6,8};
		int[] in = {4,7,2, 1 ,5,3,8,6};
		TreeNode root = reConstructBinaryTree(pre, in);
		
	}
	
	//前序遍历序列{1 ,2,4,7 ,3,5,6,8}
    //中序遍历序列{4,7,2, 1 ,5,3,8,6}
	public static TreeNode reConstructBinaryTree(int[] pre, int[] in) {
		TreeNode root = reConstructBinaryTree(pre, 0, pre.length - 1, in, 0, in.length - 1);
		return root;
	}
	
	public static TreeNode reConstructBinaryTree(int[] pre, int preStart, int preEnd, int[] in, int inStart,int inEnd) {
		if(preStart > preEnd || inStart > inEnd) 
			return null;
		TreeNode root = new TreeNode(pre[0]);
		
		for(int i=inStart; i <= inEnd; i++) {
			if(in[i] == pre[preStart]) {
				// preStart + 1 
				root.left = reConstructBinaryTree(pre, preStart + 1, preStart + i - inStart, in, inStart, i - 1);
				root.right = reConstructBinaryTree(pre, i - inStart + preStart + 1, preEnd, in, i + 1, inEnd);
				break;
			}
		}
		return root;
	}
}
