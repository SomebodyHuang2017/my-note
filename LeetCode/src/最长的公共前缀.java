import org.junit.Test;

public class 最长的公共前缀 {
    public String longestCommonPrefix(String[] strs) {
        StringBuilder sb = new StringBuilder();
        Character ch = null;

        int minLength = Integer.MAX_VALUE;
        for (String str : strs) {
            if (str.length() < minLength) {
                minLength = str.length();
            }
        }

        for (int i = 0; i < minLength; i++) {
            for (int n = 0; n < strs.length; n++) {
                if (n == 0) {
                    ch = strs[n].charAt(i);
                }
                if (ch != strs[n].charAt(i)) {
                    return sb.toString();
                }
                if (n == strs.length - 1) {
                    sb.append(ch);
                }
            }
        }
        return sb.toString();
    }

    public String longestCommonPrefix2(String[] strs) {
        if (strs == null) return null;
        if (strs.length == 0) return "";

        String first = strs[0], last = strs[0];

        for (String str : strs) {
            if (str.compareTo(first) < 0)
                first = str;
            if (str.compareTo(last) > 0)
                last = str;
        }
        System.out.println(first + " " + last);

        int i = 0, len = Math.min(first.length(), last.length());

        while (i < len && first.charAt(i) == last.charAt(i))
            i++;

        return first.substring(0, i);
    }

    @Test
    public void test() {
        String[] strs = {"flower", "flow", "flight", "flalalalal", "fz"};
        String str = longestCommonPrefix(strs);
        System.out.println(str);
        longestCommonPrefix2(strs);
    }
}	
