package offer;

public class 链表中倒数第k个结点 {
	public ListNode findKthToTail(ListNode head, int k) {
		//思路：双指针，快指针先走k步，随后两个指针一起走，直到快指针走到链表尾部
		if(head == null || k <= 0)
			return null;
		ListNode slow = head;
		ListNode fast = head;
		for(int i = 1; i < k; i++) {
			if(fast.next != null) {
				fast = fast.next;
			} else {
				return null;
			}
		}
		while(fast.next != null) {
			slow = slow.next;
			fast = fast.next;
		}
		return slow;		
	}
}
