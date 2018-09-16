package 日常练习题.好未来;

import java.util.Scanner;

public class 查找最大数字字串 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String result = "";
        int len = str.length();
        int start = 0, end = 0;
        for(; start < len;){
            if(!isDigital(str.charAt(start))){
                start++;
                continue;
            }
            end = start + 1;
            while(end < len && isDigital(str.charAt(end)))
                end++;
            String temp = str.substring(start,end);
            result = temp.length() > result.length() ? temp : result;
            start = end + 1;
        }
        
        System.out.println(result);
    }
    public static boolean isDigital(char c){
        return c >= '0' && c <= '9';
    }
}