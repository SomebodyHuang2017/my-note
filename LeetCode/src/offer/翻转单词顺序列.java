package offer;

public class 翻转单词顺序列 {
	/**
	 * Input:
"I am a student."

Output:
"student. a am I"
	 */
	
	public static String ReverseSentence(String str) {
	    int n = str.length();
	    char[] chars = str.toCharArray();
	    int i = 0, j = 0;
	    while (j <= n) {
	        if (j == n || chars[j] == ' ') {
	            reverse(chars, i, j - 1);
	            i = j + 1;
	        }
	        j++;
	    }
	    reverse(chars, 0, n - 1);
	    return new String(chars);
	}

	private static void reverse(char[] c, int i, int j) {
	    while (i < j)
	        swap(c, i++, j--);
	}

	private static void swap(char[] c, int i, int j) {
	    char t = c[i];
	    c[i] = c[j];
	    c[j] = t;
	}
	
	public static void main(String[] args) {
		String s = "I am a student.";
		System.out.println(ReverseSentence(s) );
	}
}
