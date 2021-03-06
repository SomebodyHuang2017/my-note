package offer;

public class 左旋转字符串 {

	/**
	 * 汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串模拟这个指令的运算结果。
	 * 对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。例如，字符序列S=”abcXYZdef”,
	 * 要求输出循环左移3位后的结果，即“XYZdefabc”。是不是很简单？OK，搞定它！
	 */
	
    public String LeftRotateString(String str,int n) {

        int len = str.length();
       if(len == 0) return "";
       n = n % len;
       str += str;
       return str.substring(n, n+len);
       
       /*
       if(str==null||str.length()==0){
           return "";
       }
       Queue<Character> queue = new LinkedList<>();
       for(int i = 0; i < str.length(); i++){
           queue.add(str.charAt(i));
       }
       for(; n > 0; n--){
           queue.add(queue.poll());
       }
       StringBuilder sb = new StringBuilder();
       while(!queue.isEmpty()){
           sb.append(queue.poll());
       }
       return sb.toString();*/
   }
}
