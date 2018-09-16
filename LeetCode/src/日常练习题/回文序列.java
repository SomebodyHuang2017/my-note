package 日常练习题;

import java.util.Scanner;
import java.util.LinkedList;

public class 回文序列 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        LinkedList<Integer> list = new LinkedList<>();
        while(sc.hasNextInt()){
            list.add(sc.nextInt());
        }
        int count = 0;
        while(list.size() > 1){
            int left = list.getFirst();
            int right = list.getLast();
            if(left == right){
                list.removeFirst();
                list.removeLast();
            } else {
                if(left < right){
                    list.addFirst(list.removeFirst()+list.removeFirst());
                } else {
                    list.addLast(list.removeLast()+list.removeLast());
                }
                count++;
            }
        }
        System.out.println(count);
    }
}