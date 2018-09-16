package bracktracking;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BinaryTreePaths {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if (root == null) {
            return paths;
        }
        List<Integer> values = new ArrayList<>();
        backtracking(root, values, paths);
        return paths;
    }

    private void backtracking(TreeNode node, List<Integer> values, List<String> paths) {
        if (node == null) {
            return;
        }
        values.add(node.val);
        if (isLeaf(node)) {
            paths.add(buildPath(values));
        } else {
            backtracking(node.left, values, paths);
            backtracking(node.right, values, paths);
        }
        values.remove(values.size() - 1);
    }

    private String buildPath(List<Integer> values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i));
            if (i != values.size() - 1) {
                sb.append("->");
            }
        }
        return sb.toString();
    }

    private boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }

    @Test
    public void test() {
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        TreeNode childLeft = new TreeNode(5);
        left.right = childLeft;
        root.left = left;
        root.right = right;

        System.out.println(binaryTreePaths(root));
    }
}
