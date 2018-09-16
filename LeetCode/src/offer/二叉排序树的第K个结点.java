package offer;

public class 二叉排序树的第K个结点 {
	
    private TreeNode node;//保存返回值
    private int cnt = 0;//
    
    TreeNode KthNode(TreeNode pRoot, int k) {
        inOrder(pRoot, k);
        return node;
    }
    
    void inOrder(TreeNode pRoot, int k){
        if(pRoot == null){
            return;
        }
        inOrder(pRoot.left,k);
        cnt++;
        if(cnt==k){
            node = pRoot;
            return;
        }
        inOrder(pRoot.right,k);
    }
}

class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;
    }
}
