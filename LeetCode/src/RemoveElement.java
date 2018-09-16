import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;

public class RemoveElement {
    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) return 0;

        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            if (nums[start] != val && nums[end] != val) {
                start++;
            } else if (nums[start] == val && nums[end] != val) {
                int temp = nums[start];
                nums[start] = nums[end];
                nums[end] = temp;
                start++;
                end--;
            } else if (nums[start] == val && nums[end] == val) {
                end--;
            } else {
                end--;
            }
        }
        int len = nums.length - 1;
        while (len >= 0 && nums[len] == val) {
            len--;
        }
        return len + 1;
    }

    @Test
    public void test() {
        int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
        //int[] nums = {3,2,2,3};
        System.out.println(Arrays.toString(nums));
        int len = removeElement(nums, 2);
        System.out.println(len);
        System.out.println(Arrays.toString(nums));
    }
}
