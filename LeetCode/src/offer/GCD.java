package offer;

import java.math.BigInteger;

public class GCD {
	/**
	 * 最大公约数
	 * @param a
	 * @param b
	 * @return
	 */
	public static int gcd(int a,int b) {
		int temp = Math.min(a,b);
		a = Math.max(a, b); //a存放大的
		b = temp; //b存放小的
		while(b != 0) { //直到余数为0
			temp = a % b; //取余数
			a = b; //将a作为被除数
			b = temp; //将余数作为除数
		}
		return a;
	}
	
	public static void main(String[] args) {
		System.out.println("最大公约数："+gcd(12,18));
		System.out.println("最小公倍数："+12*18/gcd(12,18));
		
//		BigInteger bigInteger = new BigInteger("123");
//		bigInteger.gcd(new BigInteger("12"));
		
	}
}
