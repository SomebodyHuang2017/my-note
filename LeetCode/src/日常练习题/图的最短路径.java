package 日常练习题;

import java.util.HashMap;
import java.util.Scanner;

/*
 
5
0 1
0 2
1 3
1 4

 * */
public class 图的最短路径 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] arr = new int[n - 1][2];
        int j = 0;
        while(sc.hasNextInt()){
        	arr[j][0] = sc.nextInt();
        	arr[j][1] = sc.nextInt();
        	j++;
        }
        
        

        // 树的深度Map、节点孩子数量Map
        HashMap<Integer, Integer> deep = new HashMap<>();
        HashMap<Integer, Integer> childNum = new HashMap<>();
        deep.put(1, 1);
        childNum.put(1, 0);
        // 默认树的深度为1
        int max = 1;
        int myDeep = 0;
        for (int i = 0; i < n - 1; i++) {
        	int parent = arr[i][0];
            int num =  arr[i][1];
            // 不包含父节点 则跳过
            if (!deep.containsKey(parent)) {
                continue;
            }
            // 树的深度加一
            myDeep = deep.get(parent) + 1;
            // 子节点和树的深度
            deep.put(num, myDeep);
            // 存父节点，其子节点的数量加一
            childNum.put(parent, (childNum.get(parent) + 1));
            // 存子节点，其子节点数量为0
            childNum.put(num, 0);
            if (myDeep > max) {
                max = myDeep;
            }
        }
        System.out.println(2*(n - 1 - max) + max);
    }
}
