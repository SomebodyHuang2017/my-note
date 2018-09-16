package dfs;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * 输入两个整数 n 和 m，
 * 从数列1，2，3.......n 中随意取几个数,使其和等于 m ,
 * 要求将其中所有的可能组合列出来
 * @author Somebody
 *
 */
public class 好未来求和 {
    public static void main(String[] args) {
//    	args.clone();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); int m = sc.nextInt();
        List<List<Integer>> listall = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        helper(listall, list, m, 1, n);
        for(List<Integer> tmp : listall) {
            String s = "";
            for(int i : tmp) {
                s += i + " ";
            }
            s = s.trim();
            System.out.println(s);
        }
    }
     
    public static void helper(List<List<Integer>> listall, 
                              List<Integer> list, int dest, int bottom, int n) {
        if(dest < 0) return;
        else if(dest == 0) {
            listall.add(new ArrayList<>(list));
            return;
        } else {
            for(int i = bottom; i <= n && i <= dest; i++) {
                list.add(i);
                helper(listall, list, dest - i, i + 1, n);
                list.remove(list.size() - 1);
            }
        }
    }
}
