package offer;

public class 二进制中1的个数 {
	public int numberOf1(int n) {
		//5中1的个数
		//101
		//101 & 100 -> 100
		//
		//100 & 011 -> 000
		
		//每次减一再和本生相与都会少一个1
		int count = 0;
		while(n != 0) {
			++count;
			n = n & (n - 1);
		}
		return count;
	}
}
