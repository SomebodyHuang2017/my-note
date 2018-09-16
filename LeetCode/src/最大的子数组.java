import org.junit.Test;

public class 最大的子数组 {
    public int maxSubArray2(int[] a) {

        int result = Integer.MIN_VALUE;
        int sum = 0;

        for (int i = 0; i < a.length; i++) {

            if (sum <= 0) {
                sum = a[i];
            } else {
                sum += a[i];
            }

            result = Math.max(sum, result);
        }
        return result;
    }

    public int maxSubArray(int[] nums) {
        int acc = 0, max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            acc = Math.max(acc, 0);//忽略负数的累加结果
            acc += nums[i];
            max = Math.max(max, acc);
        }
        return max;
    }

    @Test
    public void test() {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
//    	int max = maxSubArray(nums);
        int max = maxSubArray2(nums);
        System.out.println(max);
    }
}
