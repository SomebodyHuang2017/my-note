import org.junit.Test;

public class 跳过泥潭 {
    public boolean canJump(int[] nums) {
        int max = 0;

        for (int i = 0; i < nums.length && i <= max; i++) {
            if (nums[i] + i > max) {
                max = nums[i] + i;
            }
            if (max >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void test() {
        //{2,3,1,0,0,0,0}
        int[] nums = {3, 2, 1, 0, 4};//{2,5,0,0};//{2,0};//{1,2};//{3,2,1,0,4};//{2,3,1,1,4};
        System.out.println(canJump(nums));
    }
}	
