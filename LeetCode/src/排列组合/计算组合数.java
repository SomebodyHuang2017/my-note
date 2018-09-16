package 排列组合;

import java.math.BigInteger;

public class 计算组合数 {
	public static void main(String[] args) {
		int m = 3;
		int n = 10;
		BigInteger bi = computeZuhe(n, m);
		System.out.println(bi);
	}
	
	public static BigInteger computeZuhe(int n, int m) {
		BigInteger fenzi = new BigInteger("1");
		for(int i = 2; i <= n; i++) {
			fenzi = fenzi.multiply(new BigInteger(String.valueOf(i)));
		}
		
		BigInteger fenmu1 = new BigInteger("1");
		for(int i = 2; i <= m; i++) {
			fenmu1 = fenmu1.multiply(new BigInteger(String.valueOf(i)));
		}
		
		int k = n - m;
		BigInteger fenmu2 = new BigInteger("1");
		for(int i = 2; i <= k; i++) {
			fenmu2 = fenmu2.multiply(new BigInteger(String.valueOf(i)));
		}
		
		return fenzi.divide(fenmu1.multiply(fenmu2));
	}
}	
