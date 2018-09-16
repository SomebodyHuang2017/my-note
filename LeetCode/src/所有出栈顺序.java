import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class 所有出栈顺序 {


    public static LinkedList<String> allPermutation(String str) {
        if (str == null || str.length() == 0)
            return null;
        //保存所有的全排列
        LinkedList<String> listStr = new LinkedList<String>();

        allPermutation(str.toCharArray(), listStr, 0);

        //print(listStr);//打印全排列
        return listStr;
    }


    private static void allPermutation(char[] c, LinkedList<String> listStr, int start) {

        if (start == c.length - 1)
            listStr.add(String.valueOf(c));
        else {
            for (int i = start; i <= c.length - 1; i++) {
                //只有当没有重叠的字符 才交换
                if (!isSwap(c, start, i)) {
                    swap(c, i, start);//相当于: 固定第 i 个字符
                    allPermutation(c, listStr, start + 1);//求出这种情形下的所有排列
                    swap(c, start, i);//复位
                }
            }
        }
    }

    private static void swap(char[] c, int i, int j) {
        char tmp;
        tmp = c[i];
        c[i] = c[j];
        c[j] = tmp;
    }

    private static void print(LinkedList<String> listStr) {
        Collections.sort(listStr);//使字符串按照'字典顺序'输出
        for (String str : listStr) {
            System.out.println(str);
        }
        System.out.println("size:" + listStr.size());
    }

    //[start,end) 中是否有与 c[end] 相同的字符
    private static boolean isSwap(char[] c, int start, int end) {
        for (int i = start; i < end; i++) {
            if (c[i] == c[end])
                return true;
        }
        return false;
    }

    public static LinkedList<String> legalSequence(LinkedList<String> listStr) {
        Iterator<String> it = listStr.iterator();
        String currentStr;
        while (it.hasNext())//检查全排列中的每个序列
        {
            currentStr = it.next();
            if (!check(currentStr))
                it.remove();//删除不符合的出栈规律的序列
        }
        return listStr;
    }

    //检查出栈序列 str 是否 是合法的出栈 序列
    private static boolean check(String str) {
        boolean result = true;
        char[] c = str.toCharArray();
        char first;//当前数字.
        int k = 0;//记录 compare 数组中的元素个数
        char[] compare = new char[str.length()];
        for (int i = 0; i < c.length; i++) {
            first = c[i];
            //找出在 first 之后的，并且比 first 小的数字
            for (int j = i + 1; j < c.length; j++) {
                if (c[j] > first)
                    continue;
                else {
                    compare[k++] = c[j];//将比当前数字小的 所有数字 放在compare数组中
                }
            }
            if (k == 0)
                continue;
            else {
                for (int m = 0; m < k - 1; m++)//判断 compare 数组是否是 递减的顺序
                {
                    if (compare[m] < compare[m + 1]) {
                        result = false;//不符合递减顺序
                        return result;
                    }
                }
            }
            k = 0;
        }
        return result;
    }

    //hapjin test
    public static void main(String[] args) {
        String str = "12345";
        LinkedList<String> listStr = legalSequence(allPermutation(str));
        print(listStr);
    }
}
