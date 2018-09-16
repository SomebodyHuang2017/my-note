package bracktracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LetterCombinations {
    /**
     * Backtracking
     * Backtracking（回溯）属于 DFS。
     * <p>
     * 普通 DFS 主要用在 可达性问题 ，这种问题只需要执行到特点的位置然后返回即可。
     * 而 Backtracking 主要用于求解 排列组合 问题，例如有 { 'a','b','c' } 三个字符，求解所有由这三个字符排列得到的字符串，这种问题在执行到特定的位置返回之后还会继续执行求解过程。
     * 因为 Backtracking 不是立即就返回，而要继续求解，因此在程序实现时，需要注意对元素的标记问题：
     * <p>
     * 在访问一个新元素进入新的递归调用时，需要将新元素标记为已经访问，这样才能在继续递归调用时不用重复访问该元素；
     * 但是在递归返回时，需要将元素标记为未访问，因为只需要保证在一个递归链中不同时访问一个元素，可以访问已经访问过但是不在当前递归链中的元素。
     */

    private static final String[] KEYS = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    public static List<String> letterCombinations(String digits) {
        List<String> combinations = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return combinations;
        }
        doCombination(new StringBuilder(), combinations, digits);
        return combinations;
    }

    private static void doCombination(StringBuilder prefix, List<String> combinations, final String digits) {
        if (prefix.length() == digits.length()) {
            combinations.add(prefix.toString());
            return;
        }
        int curDigits = digits.charAt(prefix.length()) - '0';
        String letters = KEYS[curDigits];
        for (char c : letters.toCharArray()) {
            prefix.append(c);                         // 添加
            doCombination(prefix, combinations, digits);
            prefix.deleteCharAt(prefix.length() - 1); // 删除
        }
    }

    public static void main(String[] args) {
        String digits = "942";
//		List<Integer> list = Arrays.asList(2,3,4,2,6,2,5,1);
//		list.subList(6, 6 + 3);
//		list.stream().max(Integer::compare);
        System.out.println(letterCombinations(digits));
    }
}
