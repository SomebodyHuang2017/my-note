import java.util.LinkedList;

public class 重建二叉树 {
    /**
     * 分层打印
     *
     * @param root
     * @return
     */
    public static void printTreeByFloor(TreeNode root) {
        //层序遍历，发现一层中某个节点没有子节点（叶子节点），则该层就是该数的最小深度
        //层序遍历需要用到队列。
        if (root == null) return;

        LinkedList<TreeNode> deque = new LinkedList<TreeNode>();

        int nextNode = 0;
        int toBePrint = 1;
        deque.addLast(root);
        while (!deque.isEmpty()) {
            TreeNode p = deque.removeFirst();
            --toBePrint;
            System.out.print(p.val + " ");
            if (p.left != null) {
                nextNode++;
                deque.addLast(p.left);
            }
            if (p.right != null) {
                nextNode++;
                deque.addLast(p.right);
            }
            if (toBePrint == 0) {
                System.out.println();
                toBePrint = nextNode;
            }

        }
    }

    public static int run(TreeNode root) {
        if (root == null) return 0;

        if (root.left == null) {
            return run(root.right) + 1;
        }
        if (root.right == null) {
            return run(root.left) + 1;
        }
        int leftDepth = run(root.left);
        int rightDepth = run(root.right);
        return (leftDepth > rightDepth) ? (rightDepth + 1) : (leftDepth + 1);
    }

    public static void main(String[] args) {
        int[] pre = {1, 2, 4, 7, 3, 5, 6, 8};
        int[] in = {4, 7, 2, 1, 5, 3, 8, 6};
        TreeNode root = reConstructBinaryTree(pre, in);
        printTree(root);
        System.out.println();
        printTreeByFloor(root);
        System.out.println();
        int depth = run(root);
        System.out.println(depth);
    }

    /**
     * 先序遍历树，打印
     *
     * @param head
     */
    public static void printTree(TreeNode head) {
        if (head == null) {
            return;
        }
        System.out.print(head.val + ",");
        printTree(head.left);
        printTree(head.right);
    }

    public static TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        TreeNode root = reConstructBinaryTree(pre, 0, pre.length - 1, in, 0, in.length - 1);
        return root;
    }

    //前序遍历{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}
    private static TreeNode reConstructBinaryTree(int[] pre, int startPre, int endPre, int[] in, int startIn, int endIn) {
        if (startPre > endPre || startIn > endIn)
            return null;
        TreeNode root = new TreeNode(pre[startPre]);
        for (int i = startIn; i <= endIn; i++)
            if (in[i] == pre[startPre]) {
                root.left = reConstructBinaryTree(pre, startPre + 1, startPre + i - startIn, in, startIn, i - 1);
                root.right = reConstructBinaryTree(pre, i - startIn + startPre + 1, endPre, in, i + 1, endIn);
                break;
            }
        return root;
    }
}
