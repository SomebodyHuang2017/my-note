import org.junit.Test;

public class HammingDistance {
    public int hammingDistance(int x, int y) {
        int n = x ^ y;
        int distance = 0;
        while (n > 0) {
            ++distance;
            n = n & (n - 1);
        }
        return distance;
    }

    public int hammingDistance2(int x, int y) {
        return Integer.bitCount(x ^ y);
    }

    @Test
    public void test() {
        int[][] a = new int[0][];

        int x = 1;
        int y = 4;
        int distance = hammingDistance(x, y);
        int distance2 = hammingDistance2(x, y);
        System.out.println(distance);
        System.out.println(distance2);
    }
}
