import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Test;

public class MultiplyStrings {
    public int[] changeToNumberArray(String str) {
        if (str == null || str.length() == 0)
            return new int[0];

        char[] arr = str.toCharArray();
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            result[i] = arr[i] - '0';
        }
        return result;
    }

    public String multiply(String num1, String num2) {
        if (num1.charAt(0) == '0' || num2.charAt(0) == '0')
            return "0";

        int[] arr1 = changeToNumberArray(num1);
        int[] arr2 = changeToNumberArray(num2);

        int[] result = new int[arr1.length + arr2.length];
        int z = 0;

        int count = 0;
        for (int i = arr1.length - 1; i >= 0; --i, ++count) {
            int[] temp = new int[arr1.length + arr2.length];
            int t = temp.length - 1;
            for (int j = arr2.length - 1; j >= 0; --j, --t) {
                int r = arr1[i] * arr2[j] + z;
                z = 0;
                if (r >= 10) {
                    z = r / 10;
                    r = r % 10;
                }
                temp[t - count] = r;
            }
            temp[t - count] = z;

            z = 0;
            for (int n = result.length - 1; n >= 0; --n) {
                int r = result[n] + temp[n] + z;
                z = 0;
                if (r >= 10) {
                    z = r / 10;
                    r = r % 10;
                }
                result[n] = r;
            }
        }
        StringBuilder sb = new StringBuilder();
        int begin = 0;
        while (result[begin] == 0) ++begin;
        for (; begin < result.length; begin++) {
            sb.append(result[begin]);
        }
        return sb.toString();
    }

    @Test
    public void test() {
        String s = multiply("27893", "1234");
        System.out.println(s);
    }
}
