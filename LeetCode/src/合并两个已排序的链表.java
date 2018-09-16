import org.junit.Test;

/**
 * @author Somebody
 */
class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    @Override
    public String toString() {
        return "ListNode [val=" + val + "]";
    }

}

public class 合并两个已排序的链表 {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode head = new ListNode(-1);
        ListNode crt = head;

        while (l1 != null || l2 != null) {
            if (l1 == null) {
                crt.next = l2;
                return head.next;

            } else if (l2 == null) {
                crt.next = l1;
                return head.next;

            } else if (l1.val <= l2.val) {
                crt.next = l1;
                l1 = l1.next;
            } else {
                crt.next = l2;
                l2 = l2.next;
            }
            crt = crt.next;
        }
        return head.next;
    }

    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode pMergeHead = null;
        if (l1.val < l2.val) {
            pMergeHead = l1;
            pMergeHead.next = mergeTwoLists2(l1.next, l2);
        } else {
            pMergeHead = l2;
            pMergeHead.next = mergeTwoLists2(l1, l2.next);
        }
        return pMergeHead;
    }

    public ListNode generateListNode(int[] vals) {
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

    public void printListNode(ListNode head) {
        while (head != null) {
            System.out.print(head.val + ",");
            head = head.next;
        }
        System.out.println();
    }

    @Test
    public void test() {
        int[] nodes = {1, 3, 5, 7};
        int[] nodes2 = {2, 4, 6, 8};

        ListNode l1 = generateListNode(nodes);
        ListNode l2 = generateListNode(nodes2);
        System.out.print("链表1: ");
        printListNode(l1);
        System.out.print("链表2: ");
        printListNode(l2);
        ListNode result = mergeTwoLists(l1, l2);
        System.out.print("合并后: ");
        printListNode(result);

        int[] nodes3 = {1, 3, 5, 7};
        int[] nodes4 = {2, 4, 6, 8, 10};
        ListNode l3 = generateListNode(nodes3);
        ListNode l4 = generateListNode(nodes4);
        ListNode result2 = mergeTwoLists2(l3, l4);
        System.out.println("合并2后：");
        printListNode(result2);
    }

    @Test
    public void testSwapPairs() {
        int[] nodes = {1, 2, 3, 4, 5, 6, 7};
        ListNode head = generateListNode(nodes);
        ListNode result = swapPairs(head);
        printListNode(result);
    }


    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode h = new ListNode(0);
        ListNode rear = h;
        ListNode r, p = head, q = head.next;
        while (q != null) {
            r = q.next;
            q.next = p;
            rear.next = q;
            rear = rear.next;
            p.next = r;
            rear.next = p;
            rear = rear.next;
            p = p.next;
            if (p == null)
                break;
            q = p.next;
        }
        return h.next;
    }


}
