package 日常练习题;

import java.util.Scanner;
import java.util.HashMap;

/**
 * 首先出现3次的英文字符
 * 
 */
public class 统计字符 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        HashMap<Character,Integer> map = new HashMap<>();
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(!isEnglishChar(c)){
                continue;
            }
            if(map.containsKey(c)){
                int val = map.get(c)+1;
                if(val == 3) {
                    System.out.println(c);
                    return;
                }
                map.put(c,val);
            } else {
                map.put(c,1);
            }
        }
    }
    public static boolean isEnglishChar(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
}