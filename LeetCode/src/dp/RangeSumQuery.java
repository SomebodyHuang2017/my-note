package dp;

public class RangeSumQuery {
    /**
     * Given nums = [-2, 0, 3, -5, 2, -1]
     * <p>
     * sumRange(0, 2) -> 1
     * sumRange(2, 5) -> -1
     * sumRange(0, 5) -> -3
     * 求区间 i ~ j 的和，可以转换为 sum[j] - sum[i-1]，其中 sum[i] 为 0 ~ i 的和。
     */

    private static int[] sums;

    public static void numArray(int[] nums) {
        sums = new int[nums.length + 1];
        for (int i = 1; i <= nums.length; i++) {
            sums[i] = sums[i - 1] + nums[i - 1];
        }
    }

    public static int sumRange(int i, int j) {
        return sums[j + 1] - sums[i];
    }

    public static void main(String[] args) {
        int[] nums = {-2, 0, 3, -5, 2, -1};
        numArray(nums);
        System.out.println(sumRange(2, 5));
    }
}
