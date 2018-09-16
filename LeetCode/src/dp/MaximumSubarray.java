package dp;

public class MaximumSubarray {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int preSum = nums[0];
        int maxSum = preSum;
        for (int i = 1; i < nums.length; i++) {
            preSum = preSum > 0 ? preSum + nums[i] : nums[i];
            maxSum = Math.max(maxSum, preSum);
        }
        return maxSum;
    }
    
    public static void main(String[] args) {
		System.out.println(new MaximumSubarray().maxSubArray(new int[] {3,1,2,-3,4,5,-2,9}));
	}
}
