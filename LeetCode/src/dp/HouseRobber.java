package dp;

public class HouseRobber {
    /**
     * 题目描述：抢劫一排住户，但是不能抢邻近的住户，求最大抢劫量。
     * <p>
     * 定义 dp 数组用来存储最大的抢劫量，其中 dp[i] 表示抢到第 i 个住户时的最大抢劫量。
     * <p>
     * 由于不能抢劫邻近住户，因此如果抢劫了第 i 个住户那么只能抢劫 i - 2 或者 i - 3 的住户，所以
     */
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return nums[0];
        }
        int pre3 = 0, pre2 = 0, pre1 = 0;
        for (int i = 0; i < n; i++) {
            int cur = Math.max(pre2, pre3) + nums[i];
            pre3 = pre2;
            pre2 = pre1;
            pre1 = cur;
        }
        return Math.max(pre1, pre2);
    }


}
