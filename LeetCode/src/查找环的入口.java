
public class 查找环的入口 {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        ListNode list = generateListNode(arr);
        ListNode last = list.next.next.next.next.next.next.next;
        //System.out.println(last);
        ListNode entry = list.next.next;
        last.next = entry;
        //System.out.println(entry);
        ListNode result = EntryNodeOfLoop(list);
        System.out.println(result);
    }

    public static ListNode EntryNodeOfLoop(ListNode pHead) {
        //判断是否存在环
        ListNode meetingNode = meetingNode(pHead);
        if (meetingNode == null) return null;

        //找出环的大小
        int count = 1;
        ListNode searchNode = meetingNode;
        while (searchNode.next != meetingNode) {
            searchNode = searchNode.next;
            ++count;
        }

        //两个指针，pNode1先走count步，之后pNode2和pNode1一起走
        ListNode pNode1 = pHead;
        ListNode pNode2 = pHead;
        for (int i = 0; i < count; ++i)
            pNode1 = pNode1.next;

        while (pNode1 != pNode2) {
            pNode1 = pNode1.next;
            pNode2 = pNode2.next;
        }

        return pNode1;
    }

    public static ListNode meetingNode(ListNode pHead) {
        if (pHead == null)
            return null;

        ListNode slow = pHead.next;
        if (slow == null)
            return null;

        ListNode fast = slow.next;
        while (fast != null && slow != null) {
            if (fast == slow)
                return fast;

            slow = slow.next;

            fast = fast.next;
            if (fast != null)
                fast = fast.next;
        }
        return null;
    }

    public static ListNode generateListNode(int[] vals) {
        if (vals == null || vals.length == 0) return null;

        ListNode head = new ListNode(vals[0]);
        ListNode list = head;
        if (vals.length > 1) {
            for (int i = 1; i < vals.length; i++) {
                list.next = new ListNode(vals[i]);
                list = list.next;
            }
        }
        return head;
    }
}
