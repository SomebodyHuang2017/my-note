import org.junit.Test;

public class HashTest {
    @Test
    public void print1To100HashCode() {
        for (int i = 1; i <= 100; ++i) {
            System.out.println(Integer.hashCode(i));
        }
    }

    /**
java.lang.Double.doubleToRawLongBits() 方法返回根据IEEE754浮点“双精度格式”位布局，不是非数字(NaN)值，返回指定浮点值的表示。它包括以下要点：

如果参数为正无穷大，其结果是 0x7ff0000000000000L.
如果参数为负无穷大，其结果是 0xfff0000000000000L.
如果参数为NaN，那么结果是长整型表示实际NaN值。doubleToLongBits方法不同，doubleToRawLongBits不垮所有的位模式NaN编码为一个单一的“规范”NaN值。
     */
    @Test
    public void print1_0To100_0HashCode() {
        System.out.println(Double.doubleToRawLongBits(1.0));
        System.out.println(Double.doubleToLongBits(1.0));

        for (double i = 0; i < 100; ++i) {
            System.out.println(Double.hashCode(i));
        }
    }
}
