package offer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static java.util.Collections.reverse;

public class 之字形打印二叉树 {

	
	 public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot){
	        ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
	        Queue<TreeNode> queue = new LinkedList<>();
	        queue.add(pRoot);
	        boolean reverse = false;
	        while (!queue.isEmpty()) {
	            ArrayList<Integer> list = new ArrayList<>();
	            int cnt = queue.size();
	            while (cnt-- > 0) {
	                TreeNode node = queue.poll();
	                if (node == null)
	                    continue;
	                list.add(node.val);
	                queue.add(node.left);
	                queue.add(node.right);
	            }
	            if (reverse)
	                reverse(list);
	            reverse = !reverse;
	            if (list.size() != 0)
	                ret.add(list);
	        }
	        return ret;
	    }
	
}
