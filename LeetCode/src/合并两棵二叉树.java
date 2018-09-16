import org.junit.Test;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class 合并两棵二叉树 {
    //	public TreeNode generateTrees(int[] before,int[] middle) {
//		
//	}
//	
    //合并两棵二叉树
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        TreeNode head = new TreeNode(t1.val + t2.val);
        head.left = mergeTrees(t1.left, t2.left);
        head.right = mergeTrees(t1.right, t2.right);
        return head;
    }

    /**
     * 先序遍历树，打印
     *
     * @param head
     */
    public void printTree(TreeNode head) {
        if (head == null) {
            //System.out.print("null,");
            return;
        }
        System.out.print(head.val + ",");
        printTree(head.left);
        printTree(head.right);
    }

    @Test
    public void test() {
        //TreeNode tree = mergeTrees(null,null);

        TreeNode t1 = new TreeNode(1);
        TreeNode mNode = new TreeNode(3);
        TreeNode mNode2 = new TreeNode(2);
        mNode.left = new TreeNode(5);
        t1.left = mNode;
        t1.right = mNode2;

        TreeNode t2 = new TreeNode(2);
        TreeNode nNode = new TreeNode(1);
        TreeNode nNode2 = new TreeNode(3);
        nNode.right = new TreeNode(4);
        nNode2.right = new TreeNode(7);
        t2.left = nNode;
        t2.right = nNode2;


        TreeNode result = mergeTrees(t1, t2);
        printTree(t1);
        System.out.println();
        printTree(t2);
        System.out.println();
        System.out.println("合并后：");
        printTree(result);
        System.out.println("\r\n++++++++++++++++++++++++++++++++");


        TreeNode m = new TreeNode(1);
        TreeNode mleft = new TreeNode(2);
        mleft.left = new TreeNode(3);
        m.left = mleft;

        TreeNode n = new TreeNode(1);
        TreeNode nRight = new TreeNode(2);
        nRight.right = new TreeNode(3);
        n.right = nRight;

        TreeNode result2 = mergeTrees(m, n);
        printTree(result2);

        //System.out.println(3%0);
    }
}
