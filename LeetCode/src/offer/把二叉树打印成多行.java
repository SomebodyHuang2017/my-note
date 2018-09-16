package offer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class 把二叉树打印成多行 {

	
    private ArrayList<ArrayList<Integer>> Print(TreeNode pRoot){
    	  ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
          
          if(pRoot == null) {
              return ret;
          }
         
          Queue<TreeNode> queue = new LinkedList<>();
          queue.add(pRoot);

          while (!queue.isEmpty()) {
              ArrayList<Integer> list = new ArrayList<>();
              int cnt = queue.size();
              while (cnt-- > 0) {
                  TreeNode node = queue.poll();
                  list.add(node.val);
                  
                  if(node.left != null)
                      queue.add(node.left);
                  if(node.right != null)
                      queue.add(node.right);
              }
              ret.add(list);
          }
          return ret;
    }
}
