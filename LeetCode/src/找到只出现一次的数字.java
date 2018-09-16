
public class 找到只出现一次的数字 {
    public static void main(String[] args) {
        int[] a = {2, 2, 2, 3, 1, 3, 3};
        int dif = 0;
        for (int i : a) {
            dif ^= i;
            dif &= i;
        }
        System.out.println(dif);
        int[] nums = {1, 1, 2, 2, 4, 5, 5, 6, 6, 7};
        int[] num1 = new int[1];
        int[] num2 = new int[1];
        findNumsAppearOnce(nums, num1, num2);
        System.out.println(num1[0] + " " + num2[0]);
    }

    //利用异或和与运算
    public static void findNumsAppearOnce(int[] nums, int num1[], int num2[]) {
        int diff = 0;
        for (int num : nums)
            diff ^= num;
        diff &= -diff;
        for (int num : nums) {
            if ((num & diff) == 0)
                num1[0] ^= num;
            else
                num2[0] ^= num;
        }
    }
}
