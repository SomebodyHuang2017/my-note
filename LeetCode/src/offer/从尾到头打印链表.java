package offer;

import java.util.ArrayList;

public class 从尾到头打印链表 {
	public ArrayList<Integer> printListFromTailToHead(ListNode listNode){
		ArrayList<Integer> list = new ArrayList<>();
		
		addToList(listNode, list);
		return list;
	}
	
	public void addToList(ListNode head,ArrayList<Integer> list) {
		if(head == null)
			return;
		addToList(head.next, list);
		if(head != null)
			list.add(head.val);
	}
}
