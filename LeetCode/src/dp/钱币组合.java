package dp;

public class 钱币组合 {
	/**
	 * 给你六种面额的纸币 {1、5、10、20、50、100元}，假设每种币值的数量都足够多，
	 * 编写程序求组成 N 元（N为 0-10000 的非负整数）的不同组合的个数。
	 */
	public long numOfCombine(int N) {
	    if (N < 0) throw new IllegalArgumentException();
	    if (N == 0) return 0;

	    int[] p = {1, 5, 10, 20, 50, 100};
	    long[] a = new long[N + 1];

	    for (int j = 0; j <= N; j++){
	        a[j] = 1;
	    }

	    for (int i = 1; i <= 5; i++) {
	        for (int j = 1; j <= N; j++) {
	            if (j >= p[i]) {
	                a[j] += a[j - p[i]];
	            }
	        }
	    }

	    return a[N];
	}
	
	
}
