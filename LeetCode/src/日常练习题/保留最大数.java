package 日常练习题;


/**
 * 
 * 给定一个十进制的正整数number，选择从里面去掉一部分数字，希望保留下来的数字组成的正整数最大。
 * @author Somebody
 *
 */
/*
 输入为两行内容，第一行是正整数number，1 ≤ length(number) ≤ 50000。第二行是希望去掉的数字数量cnt 1 ≤ cnt < length(number)。
输出描述:
输出保留下来的结果。
示例1
输入
复制
325 1
输出
复制
35
 */
import java.util.Scanner;

public class 保留最大数{
     
    public static void main(String[] args) {
    	//思路：删除的数肯定是该位的数比后一位小的数字 。比如，325 删除的2 比 后一位的5小，所以删除之。
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()) {
            StringBuilder sb = new StringBuilder();
            sb.append(sc.next());
            int cnt = sc.nextInt();
             
            for(int i = 0;i < cnt;i++) {
                int j = 0;
                while(j + 1< sb.length() && sb.charAt(j) >= sb.charAt(j+1)) {
                    j++;
                }
                sb.deleteCharAt(j);
            }
            System.out.println(sb.toString());
        }
    }
}
