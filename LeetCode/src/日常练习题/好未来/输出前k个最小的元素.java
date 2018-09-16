package 日常练习题.好未来;

import java.util.Scanner;
import java.util.PriorityQueue;
/**
最小堆做法：
将所有元素创建一个最小堆，每次移除最小元素，此时堆会进行排序，移除k次即可

最大堆做法：
从输入的数字中取出前k个数字，创建一个大小为k的最大堆
然后每次获取堆顶元素，如果比对顶元素小的话就加进去，弹出最大元素
最后就只剩下前k个小元素了
*/
public class 输出前k个最小的元素 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        //最小堆做法
        String[] strings = sc.nextLine().split(" ");
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for(int i = 0; i < strings.length - 1; ++i){
            queue.add(Integer.valueOf(strings[i]));
        }
        int k = Integer.parseInt(strings[strings.length - 1]);
        for(int i =0; i < k; ++i){
            System.out.print(queue.poll());
            if(i != k - 1){
                System.out.print(" ");
            }
        }
    }
}