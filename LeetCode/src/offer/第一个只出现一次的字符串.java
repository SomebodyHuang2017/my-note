package offer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class 第一个只出现一次的字符串 {

	
    public int FirstNotRepeatingChar(String str) {
        HashMap<Character,Integer> map = new HashMap<Character,Integer>(10000);
        for(int i = 0; i < str.length(); ++i){
            if(map.containsKey(str.charAt(i))){
                int count = map.get(str.charAt(i));
                map.put(str.charAt(i),++count);
            } else {
                map.put(str.charAt(i),1);
            }
        }
        for(int i = 0; i < str.length(); ++i){
            if(map.get(str.charAt(i))==1){
                return i;
            }
        }
        return -1;
    }
}
