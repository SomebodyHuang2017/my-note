package 网格走法数目;

import java.util.Scanner;
/**
 * 在  格点 上走，从左上角走到右下角。多少种走法？
 * @author Somebody
 * 如输入：3 2
 * 那么有4行3列，表格线
 */
public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt() + 1;
        int y = sc.nextInt() + 1;
        //动态规划，dp数组存放上行的路径条数
        int[] dp = new int[x];
        for(int i = 0; i < x; i++){
            for(int j = 0; j < y; j++){
                if(i == 0 || j == 0){
                    dp[j] = 1;
                } else {
                    dp[j] = dp[j - 1] + dp[j];
                }
            }
        }
        
        System.out.println(dp[x - 1]);
    }
}
