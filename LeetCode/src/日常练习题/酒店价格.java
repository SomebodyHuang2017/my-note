package 日常练习题;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Arrays;
import java.util.Iterator;

public class 酒店价格 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TreeMap<Integer,TreeSet<Integer>> map = new TreeMap<>();
        while(sc.hasNextInt()){
            int from = sc.nextInt();
            int to = sc.nextInt();
            int money = sc.nextInt();
            
            TreeSet<Integer> set = null;
            if(map.containsKey(money)){
                set = map.get(money);
            } else {
                set = new TreeSet<>();
            }
            set.add(from);
            set.add(to);
            map.put(money,set);
        }
        
		Iterator<Map.Entry<Integer,TreeSet<Integer>>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<Integer,TreeSet<Integer>> entry = iterator.next();
			int[] res = new int[3];
            res[2] = entry.getKey();
            Integer[] arr = entry.getValue().toArray(new Integer[0]);
            res[0] = arr[0];
            res[1] = arr[arr.length - 1];
            System.out.print(Arrays.toString(res));
            if(iterator.hasNext()){
                System.out.print(",");
            }
		}
        
    }
}