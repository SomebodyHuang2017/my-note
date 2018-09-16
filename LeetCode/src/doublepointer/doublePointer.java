package doublepointer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

public class doublePointer {
    /**
     * 题目描述：在有序数组中找出两个数，使它们的和为 target。
     * <p>
     * 使用双指针，一个指针指向值较小的元素，一个指针指向值较大的元素。指向较小元素的指针从头向尾遍历，指向较大元素的指针从尾向头遍历。
     * <p>
     * 如果两个指针指向元素的和 sum == target，那么得到要求的结果；
     * 如果 sum > target，移动较大的元素，使 sum 变小一些；
     * 如果 sum < target，移动较小的元素，使 sum 变大一些。
     */

    public int[] twoSum(int[] nums, int target) {
        //如果没有排序需要排序
//		Arrays.sort(nums);
        int i = 0;
        int j = nums.length - 1;
        while (i < j) {
            int sum = nums[i] + nums[j];
            if (sum == target) {
                return new int[]{i + 1, j + 1};
            } else if (sum < target) {
                i++;
            } else {
                j--;
            }
        }
        return null;
    }

    @Test
    public void testTwoSum() {
        int[] nums = {2, 7, 11, 15};
        int[] result = twoSum(nums, 9);
        System.out.println(Arrays.toString(result));
    }

    /**
     * 633. Sum of Square Numbers (Easy)
     * <p>
     * Input: 5
     * Output: True
     * Explanation: 1 * 1 + 2 * 2 = 5
     * 题目描述：判断一个数是否为两个数的平方和，例如 5 = 12 + 22。
     */

    public boolean judgeSquareSum(int c) {
        int i = 0;
        int j = (int) Math.sqrt(c);
        while (i <= j) {
            int powSum = i * i + j * j;
            if (powSum == c) {
                return true;
            } else if (powSum > c) {
                j--;
            } else {
                i++;
            }
        }
        return false;
    }

    @Test
    public void testJudgeSquareSum() {
        int c = 5;
        System.out.println(judgeSquareSum(c));
    }

    /**
     * 反转字符串中的元音字符
     * <p>
     * 345. Reverse Vowels of a String (Easy)
     * <p>
     * Given s = "leetcode", return "leotcede".
     * 使用双指针，指向待反转的两个元音字符，一个指针从头向尾遍历，一个指针从尾到头遍历。
     */

    private final static HashSet<Character> vowels = new HashSet<>(
            Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U')
    );

    public String reverseVowels(String s) {
        int i = 0;
        int j = s.length() - 1;
        char[] result = new char[s.length()];
        while (i <= j) {
            char ci = s.charAt(i);
            char cj = s.charAt(j);
            if (!vowels.contains(ci)) {
                result[i++] = ci;
            } else if (!vowels.contains(cj)) {
                result[j--] = cj;
            } else {
                result[i++] = cj;
                result[j--] = ci;
            }
        }
        return new String(result);
    }

    @Test
    public void testReverseVowles() {
        System.out.println(reverseVowels("leetcode"));
    }

    /**
     * 回文字符串
     * <p>
     * 680. Valid Palindrome II (Easy)
     * <p>
     * Input: "abca"
     * Output: True
     * Explanation: You could delete the character 'c'.
     * 题目描述：可以删除一个字符，判断是否能构成回文字符串。
     */

    public boolean validPalindrome(String s) {
        int i = -1;
        int j = s.length();
        while (++i < --j) {
            if (s.charAt(i) != s.charAt(j)) {
                return isPalindrome(s, i, j - 1) || isPalindrome(s, i + 1, j);
            }
        }
        return true;
    }

    private boolean isPalindrome(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i++) != s.charAt(j--)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 88. Merge Sorted Array (Easy)
     * <p>
     * Input:
     * nums1 = [1,2,3,0,0,0], m = 3
     * nums2 = [2,5,6],       n = 3
     * <p>
     * Output: [1,2,2,3,5,6]
     * 题目描述：把归并结果存到第一个数组上。
     * <p>
     * 需要从尾开始遍历，否则在 nums1 上归并得到的值会覆盖还未进行归并比较的值。
     */

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int index1 = m - 1;
        int index2 = n - 1;
        int indexMerge = m + n - 1;
        while (index1 >= 0 || index2 >= 0) {
            if (index1 < 0) {
                nums1[indexMerge--] = nums2[index2--];
            } else if (index2 < 0) {
                nums1[indexMerge--] = nums1[index1--];
            } else if (nums1[index1] > nums2[index2]) {
                nums1[indexMerge--] = nums1[index1--];
            } else {
                nums1[indexMerge--] = nums2[index2--];
            }
        }
    }

    @Test
    public void testMerge() {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 4, 5};
        merge(nums1, 3, nums2, nums2.length);
        System.out.println(Arrays.toString(nums1));
    }

    /**
     * 判断链表是否存在环
     * <p>
     * 141. Linked List Cycle (Easy)
     * <p>
     * 使用双指针，一个指针每次移动一个节点，一个指针每次移动两个节点，如果存在环，那么这两个指针一定会相遇。
     */

    public boolean hasCycle(ListNode head) {
        if (head == null)
            return false;
        ListNode l1 = head, l2 = head.next;
        while (l1 != null && l2 != null && l2.next != null) {
            if (l1 == l2) {
                return true;
            }
            l1 = l1.next;
            l2 = l2.next.next;
        }
        return false;
    }

    /**
     * 最长子序列
     * <p>
     * 524. Longest Word in Dictionary through Deleting (Medium)
     * <p>
     * Input:
     * s = "abpcplea", d = ["ale","apple","monkey","plea"]
     * <p>
     * Output:
     * "apple"
     * 题目描述：删除 s 中的一些字符，使得它构成字符串列表 d 中的一个字符串，找出能构成的最长字符串。如果有多个相同长度的结果，返回字典序的最大字符串。
     */

    public String findLongestWord(String s, List<String> d) {
        String longestWord = "";
        for (String target : d) {
            int l1 = longestWord.length(), l2 = target.length();
            if (l1 > l2 || (l1 == l2 && longestWord.compareTo(target) < 0)) {
                continue;
            }
            if (isValid(s, target)) {
                longestWord = target;
            }
        }
        return longestWord;
    }

    private boolean isValid(String s, String target) {
        int i = 0, j = 0;
        while (i < s.length() && j < target.length()) {
            if (s.charAt(i) == target.charAt(j)) {
                j++;
            }
            i++;
        }
        return j == target.length();
    }
}

class ListNode {
    int val;
    ListNode next;

    @Override
    public String toString() {
        return val + "->";
    }

}
