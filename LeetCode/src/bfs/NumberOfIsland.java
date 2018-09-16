package bfs;

public class NumberOfIsland {
    private int m, n;

//    [[1,0,0,1],
//     [0,1,1,0],
//     [0,1,1,1],
//     [1,0,1,1]]
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        m = grid.length;
        n = grid[0].length;
        int islandsNum = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != '0') {
                    dfs(grid, i, j);
                    islandsNum++;
                }
            }
        }
        return islandsNum;
    }

    private void dfs(char[][] grid, int i, int j) {
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        dfs(grid, i, j - 1);
        dfs(grid, i + 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i - 1, j);
    }
    
    public static void main(String[] args) {
		char[][] map = 
			    {{1,0,0,1},
			     {0,1,1,0},
			     {0,1,1,1},
			     {1,0,1,1}};
		System.out.println(new NumberOfIsland().numIslands(map));
	}
}
