package offer;

public class 斐波那契数列 {
	//从0开始，第0项是0
	public int fibonacci(int n) {
		if(n <= 1) {
			return n;
		}
		int first = 0;
		int second = 1;
		int third = 0;
		//这里
		for(int i = 2; i <= n; i++) {
			third = first + second;
			first = second;
			second = third;
		}
		return third;
	}
	
	/**
	 * 跳台阶
	 */
	public int jumpFloor(int target) {
		//从第一阶台阶开始
		//注意范围
		int first = 1;
		int second = 1;
		int third = 1;
		for(int i = 2; i <= target; i++) {
			third = first + second;
			first = second;
			second = third;
		}
		return third;
	}
}
