package offer;

import java.util.Arrays;

public class 二叉搜索树转换为双向链表 {

	
	private TreeNode pre = null;
	private TreeNode head = null;

	public TreeNode Convert(TreeNode root) {
	    inOrder(root);
	    return head;
	}

	private void inOrder(TreeNode node) {
	    if (node == null)
	        return;
	    inOrder(node.left);
	    node.left = pre;
	    if (pre != null)
	        pre.right = node;
	    pre = node;
	    if (head == null)
	        head = node;
	    inOrder(node.right);
	}
	
	public static void main(String[] args) {
		TreeNode root = new TreeNode(2);
		root.left = new TreeNode(1);
		root.right = new TreeNode(3);
		二叉搜索树转换为双向链表 实体 = new 二叉搜索树转换为双向链表();
		TreeNode head = 实体.Convert(root);
		System.out.println(head);
	}
}
