package offer;

public class 反转链表 {
	/**
	 * 非递归方式反转链表
	 * @param head
	 * @return
	 */
	public ListNode reverseList(ListNode head) {
		if(head == null || head.next == null)
			return head;
		//1->2->3->4->5
        //1<-2<-3  4->5
		//head为当前结点
		ListNode pre = null;
		ListNode next = head.next;
		while(head != null) {
			next = head.next;//保存下一结点
			head.next = pre;//断链,且将当前结点的后继节点指向前一结点
			pre = head;//pre结点指向当前结点
			head = next;//当前结点指向后一节点
		}
		return pre;//前一结点为最终头节点
	}
	
	/**
	 * 递归方式
	 * @param head
	 * @return
	 */
	public ListNode ReverseList(ListNode head) {
		if(head == null || head.next == null)
			return head;
		ListNode next = head.next;//保存后继节点
		head.next = null;//断链操作
		ListNode newHead = ReverseList(next);
		next.next = head;
		return newHead;
	}
}
