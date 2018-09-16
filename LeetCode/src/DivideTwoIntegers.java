import org.junit.Test;

public class DivideTwoIntegers {
    public boolean isPositive(int dividend, int divisor) {
        return (dividend < 0 && divisor < 0)
                || (dividend > 0 && divisor > 0);
    }

    public int divide(int dividend, int divisor) {
        int absDividend = Math.abs(dividend);
        int absDivisor = Math.abs(divisor);

        if (absDivisor == 1) {
            if (dividend == Integer.MIN_VALUE) {
                return isPositive(dividend, divisor) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            } else {
                return isPositive(dividend, divisor) ? absDividend : -absDividend;
            }
        }

        int result = 0;
        while ((absDividend -= absDivisor) >= 0) {
            result++;
        }
        return isPositive(dividend, divisor) ? result : -result;
    }

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        int result = divide(-2147483648, 2);
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
//    	System.out.println(Integer.MAX_VALUE);
//    	System.out.println(Integer.MIN_VALUE);
//    	System.out.println(Math.abs(Integer.MIN_VALUE));
        System.out.println(result);
    }
}	
