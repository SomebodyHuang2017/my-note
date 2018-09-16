package offer;

public class 二叉树的最小高度 {
    public int run(TreeNode root) {
        if(root==null) return 0;
        
        if(root.left == null){
            return run(root.right) + 1;
        }
        if(root.right == null){
            return run(root.left) + 1;
        }
        int leftDepth = run(root.left);
        int rightDepth = run(root.right);
        return (leftDepth > rightDepth) ? (rightDepth+1) : (leftDepth+1);
    }
}
