package 排列组合;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class 计算组合数2 {
	public static long binomial(int n, int k) {
	    assert n >= k && k >= 0;
	    if (k == 0 || k == n) {
	        return 1;
	    }

	    long[] dp = new long[k + 1];
	    dp[0] = 1;
	    for (int i = 1; i <= n; i++) {
	        if (i <= k) {
	            dp[i] = 1;
	            for (int j = i - 1; j >= 1; j--) {
	                dp[j] += dp[j - 1];
	            }
	        } else {
	            for (int j = k; j >= 1; j--) {
	                dp[j] += dp[j - 1];
	            }
	        }
	    }
	    return dp[k];
	}

	public static void startTest() throws Exception {
	    int n = 8;
	    //计算C(8,i)的组合数
	    for (int k = 0; k <= n; k++) {
	        System.out.print(binomial(n, k));
	        if (k != n) {
	            System.out.print(", ");
	        }
	    }
	    
	    System.out.println("\n+++++++++++++++++++++++++++++++");
	    
	    System.out.println(binomial(10, 3));
	}
	
	public static void main(String[] args) {
		Pattern p = Pattern.compile("^[0-9]$");
		System.out.println(p.matcher("2345").matches());
		HashMap<Integer,HashSet<Integer>> map = new HashMap<>();
		try {
			startTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
