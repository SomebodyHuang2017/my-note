import java.util.Stack;

public class 判断是否是栈的出栈序列 {
    public static void main(String[] args) {
        int[] pushA = {1, 2, 3, 4, 5};
        int[] popA = {4, 5, 3, 2, 1};
        boolean result = isPopOrder(pushA, popA);
        System.out.println(result);
    }

    public static boolean isPopOrder(int[] pushA, int[] popA) {
        Stack<Integer> s = new Stack<Integer>();
        int popIndex = 0;
        //入栈
        //如果是当前值则出栈
        //最后检查栈是否为空
        for (int i = 0; i < popA.length; ++i) {
            s.push(pushA[i]);
            while (!s.empty() && s.peek() == popA[popIndex]) {
                s.pop();
                popIndex++;
            }
        }
        return s.empty();
    }
}
