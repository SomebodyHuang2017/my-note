package offer;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class 滑动窗口的最大值 {
    public ArrayList<Integer> maxInWindows(int [] num, int size)
    {
	    ArrayList<Integer> ret = new ArrayList<>();
	    if (size > num.length || size < 1)
	        return ret;
	    //最大堆
	    PriorityQueue<Integer> heap = new PriorityQueue<>((o1,o2)->o2-o1);
	    //先将窗口大小的组成一个堆
	    for(int i = 0; i < size; i++) {
	    	heap.add(num[i]);
	    }
	    ret.add(heap.peek());//添加到结果集中，这是第一个窗口的最大值
	    
	    //之后只需要维护一个size大小的堆就行了
	    // {2, 3, 4, 2, 6, 2, 5, 1} 
	    
	    // {[2,3,4],2,6,5,1}
	    // {2,[3,4,2],6,5,1}
	    // {2,3,[4,2,6],5,1}
	    // {2,3,4,[2,6,5],1}
	    // {2,3,4,2,[6,5,1]}
	    for(int i = 0; i < num.length - size; i++) {
	 		//移除堆中的元素
	    	heap.remove(num[i]);
	    	//向堆中添加元素
	    	heap.add(num[i + size]);
	    	ret.add(heap.peek());
	    }
	    return ret;
    }
}
