import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TwoSum {
    public int[] twoSum(int[] numbers, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < numbers.length; i++) {
            if (map.containsKey(target - numbers[i])) {
                result[1] = i;
                result[0] = map.get(target - numbers[i]);
                return result;
            }
            map.put(numbers[i], i);
        }
        return result;
    }

    @Test
    public void test() {
        int[] arr = {2, 4, 6, 7, 11, 15};
        int[] result = twoSum(arr, 9);
        System.out.println(Arrays.toString(result));
    }
}
