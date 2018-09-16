import org.junit.Test;

public class PeakIndexInAMountainArray {
    public int peakIndexInMountainArray(int[] A) {
        for (int i = 0; i < A.length; ++i) {
            if (((i + 1) <= A.length) && (A[i + 1] <= A[i])) {
                return i;
            }
        }
        return 0;
    }

    @Test
    public void test() {
        int[] arr = {1, 2, 1, 0};
        System.out.println(peakIndexInMountainArray(arr));
    }
    
}
