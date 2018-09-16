package offer;

import java.util.ArrayList;
import java.util.Arrays;

public class 和为S的两个数字 {
    public ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
        ArrayList<Integer> res = new ArrayList<>();
        if(array==null||array.length==0)
            return res;
        Arrays.sort(array);
        int i = 0;
        int j = array.length - 1;
        while(i < j){
            int s = array[i] + array[j];
            if(s==sum){
                res.add(array[i]);
                res.add(array[j]);
                return res;
            } else if(s > sum){
                j--;
            } else {
                i++;
            }
        }
        return res;
    }
}
