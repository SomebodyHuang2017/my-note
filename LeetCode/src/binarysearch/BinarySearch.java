package binarysearch;

import org.junit.Test;

public class BinarySearch {
    //普通的二分查找
    public int binarySearch(int[] nums, int key) {
        int l = 0, h = nums.length - 1;
        while (l <= h) {
            //最好采用这种方式计算中间值
            //m = (l + h) /2可能产生加法溢出
            int m = l + (h - l) / 2;
            if (nums[m] == key) {
                return m;
            } else if (nums[m] > key) {
                h = m - 1;
            } else {
                l = m + 1;
            }
        }
        return -1;
    }

    /**
     * 实现自己的二分查找，MySqrt()
     */
    public int mySqrt(int x) {
        if (x <= 1) {
            return x;
        }
        int l = 1, h = x;
        while (l <= h) {
            int mid = l + (h - l) / 2;
            int sqrt = x / mid;
            if (sqrt == mid) {
                return mid;
            } else if (mid > sqrt) {
                h = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return h;
    }

    /**
     * 大于给定元素的最小元素
     * <p>
     * 744. Find Smallest Letter Greater Than Target (Easy)
     * <p>
     * Input:
     * letters = ["c", "f", "j"]
     * target = "d"
     * Output: "f"
     * <p>
     * Input:
     * letters = ["c", "f", "j"]
     * target = "k"
     * Output: "c"
     * 题目描述：给定一个有序的字符数组 letters 和一个字符 target，要求找出 letters 中大于 target 的最小字符，如果找不到就返回第 1 个字符。
     */

    public static char nextGreatestLetter(char[] letters, char target) {
        int n = letters.length;
        int l = 0, h = n - 1;
        while (l <= h) {
            int m = l + (h - l) / 2;
            if (letters[m] <= target) {
                l = m + 1;
            } else {
                h = m - 1;
            }
        }
        return l < n ? letters[l] : letters[0];
    }
    
    public static void main(String[] args) {
		char[] sums = {'c','f','j'};
		System.out.println(nextGreatestLetter(sums,'d'));
	}

    /**
     * 有序数组的 Single Element
     * <p>
     * 540. Single Element in a Sorted Array (Medium)
     * <p>
     * Input: [1,1,2,3,3,4,4,8,8]
     * Output: 2
     * 题目描述：一个有序数组只有一个数不出现两次，找出这个数。要求以 O(logN) 时间复杂度进行求解。
     * <p>
     * 令 index 为 Single Element 在数组中的位置。如果 m 为偶数，并且 m + 1 < index，那么 nums[m] == nums[m + 1]；m + 1 >= index，那么 nums[m] != nums[m + 1]。
     * <p>
     * 从上面的规律可以知道，如果 nums[m] == nums[m + 1]，那么 index 所在的数组位置为 [m + 2, h]，此时令 l = m + 2；如果 nums[m] != nums[m + 1]，那么 index 所在的数组位置为 [l, m]，此时令 h = m。
     * <p>
     * 因为 h 的赋值表达式为 h = m，那么循环条件也就只能使用 l < h 这种形式。
     */

    public int singleNonDuplicate(int[] nums) {
        int l = 0;
        int h = nums.length - 1;
        while (l < h) {
            int m = l + (h - l) / 2;
            if (m % 2 == 1) {
                m--;
            }
            if (nums[m] == nums[m + 1]) {
                l = m + 2;
            } else {
                h = m;
            }
        }
        return nums[l];
    }

    /**
     * 旋转数组中的最小数字
     */
    public int findMin(int[] nums) {
        int l = 0;
        int h = nums.length - 1;
        while (l < h) {
            int m = l + (h - l) / 2;
            if (nums[m] <= nums[h]) {
                h = m;
            } else {
                l = m + 1;
            }
        }
        return nums[l];
    }

    @Test
    public void testFindMin() {
        System.out.println(findMin(new int[]{4, 5, 6, 1, 2, 3}));
    }

    /**
     * 查找区间
     * <p>
     * Input: nums = [5,7,7,8,8,10], target = 8
     * Output: [3,4]
     * <p>
     * Input: nums = [5,7,7,8,8,10], target = 6
     * Output: [-1,-1]
     */
    public int[] searchRange(int[] nums, int target) {
        int first = binarySearch(nums, target);
        int last = binarySearch(nums, target + 1) - 1;
        if (first == nums.length || nums[first] != target) {
            return new int[]{-1, -1};
        } else {
            return new int[]{first, Math.max(first, last)};
        }
    }

    private int binarySearch1(int[] nums, int target) {
        int l = 0, h = nums.length; // 注意 h 的初始值
        while (l < h) {
            int m = l + (h - l) / 2;
            if (nums[m] >= target) {
                h = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

}
