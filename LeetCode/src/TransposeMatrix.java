import java.util.Arrays;

import org.junit.Test;

public class TransposeMatrix {
    public int[][] transpose(int[][] A) {
        if (A == null || A.length == 0) return null;

        int[][] result = new int[A[0].length][];
        for (int row = 0; row < A[0].length; ++row) {
            result[row] = new int[A.length];
            for (int col = 0; col < A.length; ++col) {
                result[row][col] = A[col][row];
            }
        }
        return result;
    }

    @Test
    public void test() {
        int[][] A = {{1, 2, 3}, {4, 5, 6}};
        int[][] result = transpose(A);
        for (int[] row : result) {
            System.out.println(Arrays.toString(row));
        }
        //取反
        int a = 5;//00000101
        System.out.println(~1);
    }
}
