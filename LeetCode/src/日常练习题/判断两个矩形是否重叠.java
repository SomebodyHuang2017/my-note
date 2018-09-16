package 日常练习题;

import org.junit.Test;

public class 判断两个矩形是否重叠 {
    // 逆向思维，不重叠情况
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        if (rec2[0] >= rec1[2] || rec2[3] <= rec1[1]
                || rec2[2] <= rec1[0] || rec2[1] >= rec1[3])
            return false;
        return true;
    }

    @Test
    public void testIsRectanle() {
        int[] rec1 = {0, 0, 1, 1};
        //int[] rec2 = {1,1,3,3};
        int[] rec3 = {1, 0, 2, 1};
        boolean result = isRectangleOverlap(rec1, rec3);
        System.out.println(result);
    }
}
