import java.util.Arrays;

import org.junit.Test;

public class FlippingAnImage {
    public int[][] flipAndInvertImage(int[][] A) {


        int rows = A.length;//总行数
        int cols = A[0].length;//总列数
        int mid = cols >> 1;//行中间值索引
        int[][] result = new int[rows][];


        for (int row = 0; row < rows; row++) {
            result[row] = new int[cols];

            if ((cols & 1) == 1) {//长度为奇数
                result[row][mid] = A[row][mid] ^ 1;//取反
            }
            /*
             0 0 变
             1 1 变
             
             0 1 不变
             1 0 不变
             * */
            for (int col = 0; col < mid; col++) {
                if ((A[row][col] == A[row][cols - 1 - col])) {
                    result[row][col] = A[row][col] ^ 1;
                    result[row][cols - 1 - col] = A[row][col] ^ 1;
                } else {
                    result[row][col] = A[row][col];
                    result[row][cols - 1 - col] = A[row][cols - 1 - col];
                }
            }
        }
        return result;
    }

    public int[][] flipAndInvertImage2(int[][] A) {
        int C = A[0].length;
        for (int[] row : A)
            for (int i = 0; i < (C + 1) / 2; ++i) {
                int tmp = row[i] ^ 1;
                row[i] = row[C - 1 - i] ^ 1;
                row[C - 1 - i] = tmp;
            }

        return A;
    }

    @Test
    public void test() {
        int[][] B = {{1, 1, 0}, {1, 0, 1}, {0, 0, 0}};
        int[][] A = {{1, 1, 0, 0}, {1, 0, 0, 1}, {0, 1, 1, 1}, {1, 0, 1, 0}};
        for (int[] row : B) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("++++++++++++++++++++++++++++++++++");
        int[][] result = flipAndInvertImage(B);
        for (int[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    }
}
