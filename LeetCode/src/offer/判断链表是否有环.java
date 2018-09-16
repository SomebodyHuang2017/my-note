package offer;

public class 判断链表是否有环 {
	public boolean hasLoop(ListNode head) {
		if(head == null)
			return false;
		
		ListNode slow = head;
		ListNode fast = slow.next;
		
		while(fast != null) {
			if(slow == fast)
				return true;
			slow = slow.next;
			fast = fast.next;
			if(fast != null)
				fast = fast.next;
		}		
		return false;
	}
}
