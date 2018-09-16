package offer;

public class 二叉树的下一节点 {

	/**
	 * 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。
	 * 注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
	 */
	
	
	//分析：
	//如果该结点有右孩子，那么该节点的下一节点为：以右孩子为根节点的子树的最左结点
	//如果该节点没有右孩子，也没有父节点，那么这个结点就是根节点，下一节点是 null
	//如果有父节点，且该结点是父节点的左孩子，那么下一节点就是父节点
	//如果有父节点，且该结点是父节点的右孩子，那么下一节点是该父节点的子树根节点的父节点
	
    public TreeLinkNode GetNext(TreeLinkNode pNode)
    {
        //如果该节点的右孩子不为空，那么下一节点就是该右孩子下的子树的最左节点
        if(pNode.right!=null){
            TreeLinkNode pChild = pNode.right;
            while(pChild.left!=null){
                pChild = pChild.left;
            }
            return pChild;
        } else {
            while(pNode.next!=null){
                TreeLinkNode pParent = pNode.next;
                if(pParent.left==pNode)
                    return  pParent;
                pNode = pNode.next;
            }
        }
        return null;
    }
}

class TreeLinkNode {
    int val;
    TreeLinkNode left = null;
    TreeLinkNode right = null;
    TreeLinkNode next = null;

    TreeLinkNode(int val) {
        this.val = val;
    }
}
