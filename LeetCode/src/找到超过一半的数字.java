
public class 找到超过一半的数字 {
    public static void main(String[] args) {
        int[] nums = {1, 2, 34, 5, 3, 2, 2, 2, 2, 2, 2, 2, 4, 5, 6};
        int a = moreThanHalfNum_Solution(nums);
        System.out.println(a);
    }

    public static int moreThanHalfNum_Solution(int[] nums) {
        int majority = nums[0];
        for (int i = 1, cnt = 1; i < nums.length; i++) {
            cnt = nums[i] == majority ? cnt + 1 : cnt - 1;
            if (cnt == 0) {
                majority = nums[i];
                cnt = 1;
            }
        }
        int cnt = 0;
        for (int val : nums)
            if (val == majority)
                cnt++;
        return cnt > nums.length / 2 ? majority : 0;
    }
}
