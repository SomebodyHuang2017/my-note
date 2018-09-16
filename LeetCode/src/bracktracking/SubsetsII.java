package bracktracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubsetsII {
    /**
     * For example,
     * If nums = [1,2,2], a solution is:
     * <p>
     * [
     * [2],
     * [1],
     * [1,2,2],
     * [2,2],
     * [1,2],
     * []
     * ]
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> subsets = new ArrayList<>();
        List<Integer> tempSubset = new ArrayList<>();
        boolean[] hasVisited = new boolean[nums.length];
        for (int size = 0; size <= nums.length; size++) {
            backtracking(0, tempSubset, subsets, hasVisited, size, nums); // 不同的子集大小
        }
        return subsets;
    }

    private void backtracking(int start, List<Integer> tempSubset, List<List<Integer>> subsets, boolean[] hasVisited,
                              final int size, final int[] nums) {

        if (tempSubset.size() == size) {
            subsets.add(new ArrayList<>(tempSubset));
            return;
        }
        for (int i = start; i < nums.length; i++) {
            if (i != 0 && nums[i] == nums[i - 1] && !hasVisited[i - 1]) {
                continue;
            }
            tempSubset.add(nums[i]);
            hasVisited[i] = true;
            backtracking(i + 1, tempSubset, subsets, hasVisited, size, nums);
            hasVisited[i] = false;
            tempSubset.remove(tempSubset.size() - 1);
        }
    }
}
