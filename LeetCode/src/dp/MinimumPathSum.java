package dp;

public class MinimumPathSum {
	
	/*  [[1,3,1],
		 [1,5,1],
		 [4,2,1]]
		Given the above grid map, return 7. Because the path 1→3→1→1→1 minimizes the sum.
		题目描述：求从矩阵的左上角到右下角的最小路径和，每次只能向右和向下移动。
	 * */

    public static int minPathSum(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {//遍历row
            for (int j = 0; j < grid[0].length; j++) {//遍历column
                if (i == 0 && j != 0) {//如果是第一排
                    grid[i][j] = grid[i][j] + grid[i][j - 1];
                } else if (i != 0 && j == 0) {//如果是第一列
                    grid[i][j] = grid[i][j] + grid[i - 1][j];
                } else if (i == 0 && j == 0) {//第一格比较特殊，因为不能-1，单独处理一下
                    grid[i][j] = grid[i][j];
                } else {//如果都不是以上的话，可以向右或向下走，取之前最小值
                    grid[i][j] = grid[i][j] + Math.min(grid[i][j - 1], grid[i - 1][j]);
                }
            }
        }
        return grid[grid.length - 1][grid[0].length - 1];//返回最后一格
    }

    public static void main(String[] args) {
        int[][] grid = {{1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}};
        System.out.println(minPathSum(grid));
    }
}
