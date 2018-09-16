import org.junit.Test;

public class LengthOfLastWord {
    public int lengthOfLastWord2(String s) {
        //方式1
        String str = s.trim();
        if (str.length() == 0)
            return 0;
        return str.length() - 1 - str.lastIndexOf(' ');
    }

    public int lengthOfLastWord(String s) {
        //方式2
        int count = 0;
        int end = s.length() - 1;
        while (end >= 0 && s.charAt(end) == ' ') --end;

        while (end >= 0 && s.charAt(end) != ' ') {
            ++count;
            --end;
        }
        return count;
    }

    @Test
    public void test() {
        int count = lengthOfLastWord("Hello Woaaarld   ");
        System.out.println(count);
    }
}
