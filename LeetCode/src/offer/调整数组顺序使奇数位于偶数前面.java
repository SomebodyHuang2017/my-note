package offer;

public class 调整数组顺序使奇数位于偶数前面 {
	public void reOrderArray(int[] a) {
		//计算奇数的个数
		int evenStart = 0;
		for(int i = 0; i < a.length; i++) {
			if((a[i] & 1) == 1)
				++evenStart;
		}
		
		//将顺序调整
		int[] clone = a.clone();
		int oddStart = 0;
		for(int i = 0; i < clone.length; i++) {
			if((clone[i] & 1) == 1) {
				a[oddStart++] = clone[i];
			} else {
				a[evenStart++] = clone[i];
			}
		}
	}
}
