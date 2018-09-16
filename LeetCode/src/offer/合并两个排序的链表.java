package offer;

public class 合并两个排序的链表 {
	public ListNode merge(ListNode list1,ListNode list2) {
		if(list1 == null) //有一条链表为空则把对应链表返回，结束
			return list2;
		if(list2 == null)
			return list1;
		
		ListNode head = null;
		if(list1.val < list2.val) {
			head = list1;
			head.next = merge(list1.next,list2);
		} else {
			head = list2;
			head.next = merge(list1, list2.next);
		}
		return head;
	}
}
