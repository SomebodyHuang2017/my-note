package bfs;

public class 朋友圈 {
    private static int n;

    public static int findCircleNum(int[][] M) {
        n = M.length;
        int circleNum = 0;
        boolean[] hasVisited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!hasVisited[i]) {
                dfs(M, i, hasVisited);
                circleNum++;
            }
        }
        return circleNum;
    }

    private static void dfs(int[][] M, int i, boolean[] hasVisited) {
        hasVisited[i] = true;
        for (int k = 0; k < n; k++) {
            if (M[i][k] == 1 && !hasVisited[k]) {
                dfs(M, k, hasVisited);
            }
        }
    }

    public static void main(String[] args) {
        int[][] friendsTable =
                {{0, 1, 0},
                        {1, 0, 1},
                        {0, 1, 0}};
        //01
        //10
        //12
        //21
        int count = findCircleNum(friendsTable);
        System.out.println(count);
    }
}
