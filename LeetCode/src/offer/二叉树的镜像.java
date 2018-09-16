package offer;

public class 二叉树的镜像 {
	public void mirror(TreeNode root) {
		if(root == null)
			return;
		//交换左右子节点
		TreeNode temp = root.left;
		root.left = root.right;
		root.right = temp;
		
		mirror(root.left);
		mirror(root.right);
	}
}
