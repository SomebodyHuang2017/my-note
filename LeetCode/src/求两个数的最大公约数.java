
public class 求两个数的最大公约数 {
    public static int gbc(int a, int b) {
        while (a % b != 0) {
            int c = a % b;
            a = b;
            b = c;
        }
        return b;
    }

    public static void main(String[] args) {

        int y = gbc(144, 12);
        System.out.println(y);

    }
}
