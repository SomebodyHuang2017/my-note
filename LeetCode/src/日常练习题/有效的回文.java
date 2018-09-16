package 日常练习题;

public class 有效的回文 {
    public static boolean isPalindrome(String s) {
        if(s.isEmpty()) return true;
        String str = s.replaceAll("\\W", ""); // 使用正则去除非字符数字的字符
        str = str.toLowerCase();
        System.out.println(str);
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) != str.charAt(str.length() - i -1)) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
		String s = "A man, a plan, a canal: Panama";
		System.out.println(isPalindrome(s));
	}
}
