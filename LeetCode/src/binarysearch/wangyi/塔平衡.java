package binarysearch.wangyi;

import java.util.Scanner;
import java.util.ArrayList;
 
public class 塔平衡{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int s = sc.nextInt();
        int m = sc.nextInt();
         
        int[] nums = new int[s];
        for(int i = 0; i < s; i++){
            nums[i] = sc.nextInt();
        }
         
        //存放结果
        ArrayList<int[]> list = new ArrayList<>();
        int i = 0;
        int[] idx = findIndexForMinAndMax(nums);
        for(; i < m; i++){
            idx = findIndexForMinAndMax(nums);
            //如果最高和最低索引相等，或者最高的塔和最低的塔相差1那么跳出循环
            if(idx[0]==idx[1] || nums[idx[1]]-nums[idx[0]] == 1){
                break;
            } else {
                nums[idx[0]] += 1;
                nums[idx[1]] -= 1;
                list.add(new int[] {idx[1]+1,idx[0]+1});
            }
        }
        System.out.println((nums[idx[1]] - nums[idx[0]]) + " " + i);
        for(int[] item : list){
            System.out.println(item[0] +" "+ item[1]);
        }
    }
     
    public static int[] findIndexForMinAndMax(int[] nums){
        //0存放小，1存放大
        int[] res = new int[2];
        res[0] = 0;
        res[1] = 0;
        for(int i = 0; i < nums.length; i++){
            res[0] = nums[i] < nums[res[0]] ? i : res[0];
            res[1] = nums[i] > nums[res[1]] ? i : res[1];
        }
        return res;
    }
}