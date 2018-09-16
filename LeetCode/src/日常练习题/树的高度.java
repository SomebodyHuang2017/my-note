package 日常练习题;

import java.util.HashMap;
import java.util.Scanner;
 /*

5
0 1
0 2
1 3
1 4

*/
public class 树的高度 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String result = "";
        // 树的深度Map、节点孩子数量Map
        HashMap<Integer, Integer> deep = new HashMap<>();
        HashMap<Integer, Integer> childNum = new HashMap<>();
        deep.put(0, 1);
        childNum.put(0, 0);
        // 默认树的深度为1
        int max = 1;
        int myDeep = 0;
        for (int i = 0; i < n - 1; i++) {
        	int parent = scanner.nextInt();
            int num = scanner.nextInt();
            // 不包含父节点或者孩子数目超过两个，则跳过
            if (!deep.containsKey(parent) || childNum.get(parent) >= 2) {
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
        System.out.println(max);
    }
}
