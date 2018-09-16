package offer;

import java.util.Arrays;

public class 把数组排成最小的数 {

	public static void main(String[] args) {
		int[] nums = {3,32,321,4,78};
		System.out.println(PrintMinNumber(nums));
	}
	
	public static String PrintMinNumber(int[] numbers) {
	    if (numbers == null || numbers.length == 0)
	        return "";
	    int n = numbers.length;
	    String[] nums = new String[n];
	    for (int i = 0; i < n; i++)
	        nums[i] = numbers[i] + "";
	    Arrays.sort(nums, (s1, s2) -> (s1 + s2).compareTo(s2 + s1));
	    System.out.println(Arrays.toString(nums));
	    String ret = "";
	    for (String str : nums)
	        ret += str;
	    return ret;
	}
}
