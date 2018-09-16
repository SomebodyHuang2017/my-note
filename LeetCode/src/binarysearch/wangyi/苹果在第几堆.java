package binarysearch.wangyi;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

public class 苹果在第几堆{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] sums = new int[n];
        int before = 0;
        for(int i = 0; i < n; i++){
            before = sums[i] = before + sc.nextInt();
        }
        System.out.println(Arrays.toString(sums));
        
        int k = sc.nextInt();
        for(int i = 0; i < k; i++){
            int res = binarySearch(sums,sc.nextInt());
            System.out.println(res);
        }
    }
    
    private static int binarySearch(int[] sums, int target){
        int n = sums.length;
        int l = 0, h = n - 1;
        while (l <= h) {
            int m = l + (h - l) / 2;
            if (sums[m] <= target) {
                l = m + 1;
            } else {
                h = m - 1;
            }
        }
        return l+1;
    }
}