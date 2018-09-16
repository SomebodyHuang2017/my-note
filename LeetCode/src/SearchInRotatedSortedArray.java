import java.util.Arrays;

import org.junit.Test;

public class SearchInRotatedSortedArray {
    /**
     * @deprecated 感觉不行
     */
    public int search(int[] nums, int target) {
        //思路：
        //1、用二分查找确定最大数字的下标
        //2、调用API的二分查找查找可能存在target的段
        //例如：
        //nums = [4,5,6,7,0,1,2], target = 3
        //二分查找，最大值7所在的下标为4
        //target比较位置0，4，nums.length - 1上的数字
        //可能存在某一部份内则调用二分查找

        int low = 0;
        int high = nums.length - 1;


        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = nums[mid];
            if (mid - 1 >= 0 && mid + 1 <= nums.length - 1) {
                if (nums[mid - 1] < nums[mid] && nums[mid] > nums[mid + 1]) {
                    continue;
                }

            }

//            if (midVal < key)
//                low = mid + 1;
//            else if (midVal > key)
//                high = mid - 1;
//            else
//                return mid; // key found
        }


        //Arrays.binarySearch(a, key)

        return 0;
    }

    @Test
    public void test() {
        int[] arr = {4, 5, 6, 7, 0, 1, 2};
        //int index = search(arr, 5);
        int index = search3(arr, 3);
        System.out.println(index);
    }

    public int search3(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[left] <= nums[mid]) { //left-mid part is monotonic
                if (nums[left] <= target && target <= nums[mid]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            } else { //mid-right part is monotonic
                if (nums[mid] <= target && target <= nums[right]) {
                    left = mid;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    public int search2(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        return search(nums, target, low, high);
    }

    //	int[] arr = {4,5,6,7,0,1,2};
    private int search(int[] nums, int target, int low, int high) {
        int mid = (low + high) >>> 1;

        // key not present
        if (low > high) {
            return -1;
        }

        // Key is found
        if (nums[mid] == target) {
            return mid;
        }

        // if left half is sorted
        if (nums[low] <= nums[mid]) {
            // if key is present in left half.
            if (nums[low] <= target && nums[mid] >= target) {
                return search(nums, target, low, mid - 1);
            } else {
                // if key is not present in left half. Search the right half
                return search(nums, target, mid + 1, high);
            }
        }
        // if right half is sorted
        else {

            // if key is present in right half.
            if (nums[mid] <= target && nums[high] >= target) {
                return search(nums, target, mid + 1, high);
            } else {
                // if key is not present in right half. Search the left half
                return search(nums, target, low, mid - 1);
            }
        }
    }
}
