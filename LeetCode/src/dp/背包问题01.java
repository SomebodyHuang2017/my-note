package dp;

public class 背包问题01 {
/**
 * 有编号为 a，b，c，d，e 的5件宝物在山洞，它们的重量分别是2、2、6、5、4，它们的价值分别是6、3、5、4、6，
 * 现在给你一个承重为 10 的背包。请问怎么装背包，才能带走最多的财富?
 */
	
	// weight[0] 和 price[0] 无意义
	// 宝物对应的索引为 1-N
	public static int packTreasure(int[] weight, int[] price,
	        int capacity, int N) {
	    int[] c = new int[capacity + 1];
	    c[0] = 0;
	    for (int y = 1; y <= capacity; y++) {
	        c[y] = (y < weight[N]) ? 0 : price[N];
	    }
	    for (int i = N - 1; i >= 1; i--) {
	        for (int y = capacity; y >= 1; y--) {
	            if (y >= weight[i]) {
	                int tmp = c[y - weight[i]] + price[i];
	                if (tmp > c[y]) {
	                    c[y] = tmp;
	                }
	            }
	        }
	    }
	    return c[capacity];
	}

	public static void startTest() {
	    int N = 5;
	    int[] weight = {0, 2, 2, 6, 5, 4};
	    int[] price = {0, 6, 3, 5, 4, 6};
	    int capacity = 10;
	    System.out.println(packTreasure(weight, price, capacity, N));
	}
	// 15
	
	public static void main(String[] args) {
		startTest();
	}
}
