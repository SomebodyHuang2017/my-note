import org.junit.Test;

public class TestCharAtAndToCharArray {

    //测试结果：charAt效率更高
    @Test
    public void test() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100_000_000; ++i) {
            sb.append('a');
        }
        String str = sb.toString();

        long start = System.currentTimeMillis();
        for (int i = 0; i < str.length(); ++i) {
            str.charAt(i);
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");

        System.out.println("++++++++++++++++++++++++++++++++++++++");

        long start2 = System.currentTimeMillis();
        char[] arr = str.toCharArray();
        for (char c : arr) {

        }
        long end2 = System.currentTimeMillis();
        System.out.println((end2 - start2) + "ms");

    }
}
