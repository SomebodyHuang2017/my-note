
public class 删除链表的所有重复结点 {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 3, 4, 4, 5};
        ListNode list = generateListNode(arr);
        ListNode removed = deleteDuplication(list);
        printListNode(removed);
    }

    public static void printListNode(ListNode head) {
        while (head != null) {
            System.out.print(head.val + ",");
            head = head.next;
        }
        System.out.println();
    }

    public static ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null)
            return null;

        ListNode pPrev = null;
        ListNode pNode = pHead;
        ListNode pNext = pHead.next;

        boolean flag = false;
        while (pNext != null) {
            if (pNode.val == pNext.val) {
                pNext = pNext.next;
                flag = true;
                continue;
            }
            if (flag) {
                pPrev.next = pNext;
                pNode = pPrev;
                flag = false;
            }
            pPrev = pNode;
            pNode = pNext;
            pNext = pNext.next;
        }
        return pHead;
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
