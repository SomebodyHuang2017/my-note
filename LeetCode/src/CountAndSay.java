import org.junit.Test;

public class CountAndSay {
    public String countAndSay(int n) {
        if (n == 1) return "1";

        char[] before = countAndSay(n - 1).toCharArray();
        int same = 1;
        char ch = before[0];

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < before.length; ++i) {
            if (ch == before[i]) {
                ++same;
            } else {
                sb.append(same);
                sb.append(ch);
                ch = before[i];
                same = 1;
            }
        }
        sb.append(same);
        sb.append(ch);
        return sb.toString();
    }

    //1
    //11
    //21
    //1211
    //111221

    public String countAndSay2(int n) {
        String s = "1";

        for (int i = 1; i < n; ++i) {
            int count = 1;
            StringBuilder sb = new StringBuilder();
            for (int j = 1; j < s.length(); ++j) {
                if (s.charAt(j) == s.charAt(j - 1))
                    ++count;
                else {
                    sb.append(count).append(s.charAt(j - 1));
                    count = 1;
                }
            }
            s = sb.append(count).append(s.charAt(s.length() - 1)).toString();
        }
        return s;
    }

    @Test
    public void test() {
        long start2 = System.currentTimeMillis();
        String str2 = countAndSay2(28);
        long end2 = System.currentTimeMillis();
        System.out.println(str2);
        System.out.println(end2 - start2);

        long start = System.currentTimeMillis();
        String str = countAndSay(28);
        long end = System.currentTimeMillis();
        System.out.println(str);
        System.out.println(end - start);
    }
}
