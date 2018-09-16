import java.util.Arrays;

import org.junit.Test;

public class RemoveDuplicatesFromSortedArray {
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int single = nums[0];
        int len = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == single) continue;

            if (nums[i] > single) {
                len++;
                single = nums[i];
                int temp = nums[len];
                nums[len] = nums[i];
                nums[i] = temp;
            }
        }
        return len + 1;
    }

    @Test
    public void test() {
        int[] nums = {0, 0, 1, 1, 1, 2, 3};
        int len = removeDuplicates(nums);
        for (int i = 0; i < len; i++) {
            System.out.print(nums[i] + " ");
        }
    }
}
