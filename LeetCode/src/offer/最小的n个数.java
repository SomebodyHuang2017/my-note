package offer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class 最小的n个数 {
	public static void main(String[] args) {
		int[] nums = {1,5,2,3,9,5,93,7,8};
		System.out.println(GetLeastNumbers_Solution(nums, 4));
	}
	
    public static ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        //创建一个大小为k的大顶堆，依次向大顶堆添加元素，每次移除对顶元素（移除当前堆中最大的元素）
        //返回这个大顶堆
         if (k > input.length || k <= 0)
            return new ArrayList<>();
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1,o2)->o2-o1);
        for(int num : input){
            queue.add(num);
            if(queue.size()>k){
                queue.poll();
            }
        }
        return new ArrayList<>(queue);
    }
}
