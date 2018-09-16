package bfs;

import java.util.Scanner;
/**
 * 给定一个m行n列的二维地图, 初始化每个单元都是水.
操作addLand 把单元格(row,col)变成陆地.
岛屿定义为一系列相连的被水单元包围的陆地单元, 横向或纵向相邻的陆地称为相连(斜对角不算).
在一系列addLand的操作过程中, 给出每次addLand操作后岛屿的个数.
二维地图的每条边界外侧假定都是水.

输入描述:
多组测试数据，请参考例题处理 每组数据k+3行, k表示addLand操作次数 第一行:表示行数m 第二行:表示列数n 第三行:表示addLand操作次数k 第4~k+3行:row col 表示addLand的坐标。注意超过边界的坐标是无效的。


输出描述:
一行,k个整数, 表示每次addLand操作后岛屿的个数, 用空格隔开，结尾无空格

输入例子1:
3
3
4
0 0
0 1
1 2
2 1

输出例子1:
1 1 2 3
 */
public class 几个岛升级版迅雷 {
    static int m = 0;
    static int n = 0;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        m =  sc.nextInt();
        n = sc.nextInt();
        
        int[][] map = new int[m][n];
        int k = sc.nextInt();
        int[][] point = new int[k][2];
        
        for(int i = 0; i < k; i++){
            point[i][0] = sc.nextInt();
            point[i][1] = sc.nextInt();
        }
        
        int before = 0;
        for(int i = 0; i < k; i++){
            if(isValidPoint(point[i][0],point[i][1])){
                map[point[i][0]][point[i][1]] = 1;
                int nums = numIslands(map);
                before = nums;
                System.out.print(nums);
                //恢复标记为2的变为标记为1
                recoverMap(map);
            } else {//否则输出上一次的值
            	System.out.print(before);
            }
            if(i != k) {
            	System.out.print(" ");
            }
        }
        
    }
    
    private static void recoverMap(int[][] map) {
		for(int i = 0; i < m; i++) {
			for(int j = 0; j < n; j++) {
				if(map[i][j]==2) {
					map[i][j] = 1;
				}
			}
		}
	}

	public static boolean isValidPoint(int i, int j){
        return i >= 0 && i < m && j >= 0 && j < n;
    }
    
    public static int numIslands(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int islandsNum = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
            	//改动，等于1才是真正的没有访问过的小岛
                if (grid[i][j] == 1) {
                    dfs(grid, i, j);
                    islandsNum++;
                }
            }
        }
        return islandsNum;
    }

    private static void dfs(int[][] grid, int i, int j) {
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] != 1) {
            return;
        }
        //先把岛标记为2，之后再去恢复。
        //因为这个dfs会将之前的独立小岛设置为0，所以这里改为设置成2，让其之后能恢复
        grid[i][j] = 2;
        dfs(grid, i, j - 1);
        dfs(grid, i + 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i - 1, j);
    }
}
