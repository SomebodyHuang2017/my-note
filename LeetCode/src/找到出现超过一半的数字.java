import java.util.Arrays;

import org.junit.Test;

public class 找到出现超过一半的数字 {
	
	//先排序
	//如果出现超过一半，那么中间数字一定是要找的数
    public int findOverMiddleNumber(int[] nums) {
        Arrays.sort(nums);
//		System.out.println(Arrays.toString(nums));
        return nums[nums.length >> 1];
    }

    @Test
    public void test() {
        int[] nums = {1, 2, 3, 2, 2, 2, 5, 6, 4, 2, 6, 6, 6, 6, 6, 6, 6, 6};
        int result = findOverMiddleNumber(nums);
        System.out.println(result);
//		int[][] A = new int[3][];
//		int a = A[0].length;
    }
}
