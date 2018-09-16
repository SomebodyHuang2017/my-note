package bracktracking;

import java.util.ArrayList;
import java.util.List;

public class CombinationSumIII {
    /**
     * Input: k = 3, n = 9
     * <p>
     * Output:
     * <p>
     * [[1,2,6], [1,3,5], [2,3,4]]
     * 从 1-9 数字中选出 k 个数不重复的数，使得它们的和为 n。
     *
     * @param k
     * @param n
     * @return
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> combinations = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtracking(k, n, 1, path, combinations);
        return combinations;
    }

    private void backtracking(int k, int n, int start,
                              List<Integer> tempCombination, List<List<Integer>> combinations) {

        if (k == 0 && n == 0) {
            combinations.add(new ArrayList<>(tempCombination));
            return;
        }
        if (k == 0 || n == 0) {
            return;
        }
        for (int i = start; i <= 9; i++) {
            tempCombination.add(i);
            backtracking(k - 1, n - i, i + 1, tempCombination, combinations);
            tempCombination.remove(tempCombination.size() - 1);
        }
    }
}

