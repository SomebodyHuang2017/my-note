package 排列组合;

/**
 * @author "shihuc"
 * @date   2016年12月1日
 */
  
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author chengsh05
 * 
 * 组合算法实现，支持产品列表页的筛选模块实现全静态化。
 * 
 * 给定m个不同的数，从中选择出n个数的不同选择方法有多少种？
 * 答案：一共有 n!/(n-m)!*m!
 * 
 * 存储计算出来的组合结构，组合元素之间用逗号分隔
 *         例如1,2,3的全组合：
 *         "1,", "2,", "3,","1,2,", "1,3,", "2,3,", "1,2,3,"
 */
public class Combination {
    
    /**
     * 从m个元素中取出n个元素，获取排序好了的组合数列表，同一个组合中的元素按从小到大排序。
     * 
     * @param m 组合的元素基数
     * @param n 组合的被选元素个数
     * @return 排序好后的组合列表
     * @throws Exception 
     */
    public ArrayList<String> getCombinations(int m, int n) throws Exception{
        
        if(m < n){
            throw new IllegalCombinationInputException();
        }
        
        ArrayList<String> resCom = calcCombination(m, n);
        
        return resCom;
    }
    
    /**
     * 通过移位方式，计算给定m个数中取出n个数的组合列表。
     * 
     * 具体思路：
     * 一个bit位（boolean）数组中，初始化全为0(false), 然后给左边的n个位初始化为1(true)。
     * <1> 从左向右找第一个10的位置，将10换位程01，然后将这个01左边的所有的1全都 移位到数组的最左边，此时得到的1所在位置下标对应的序列即为一个组合数。 
     * <2> 循环重复上面的<1>步骤的操作，直到所有的1都移动到最右边为止。
     * 
     * @param m 输入的基数个数
     * @param n 组合被选元素格式
     * @return 原始组合数列表，即没有排序的组合
     */
    private ArrayList<String> calcCombination(int m, int n){
        boolean base[] = new boolean[m];
        Arrays.fill(base, false);
        for(int i=0; i<n; i++){
            base[i] = true;
        }
        return combination(base,m,n);
    }
    
    private ArrayList<String> combination(boolean a[], int m, int n){
        ArrayList<String> combination = new ArrayList<String>();        
        while(!isAllZeroLeft(a, m, n)){
            for(int i=0; i<m-1; i++){
                if(a[i] == true && a[i+1] == false){
                    String elem = calcElement(a);    //计算出一个组合元素
                    //System.out.println(elem);
                    combination.add(elem);
                    oneZeroSwap(a, i, i+1);          //完成10/01换位                    
                    moveFrontOneToLeft(a, i);        //完成剩余左边的1全向最左边搬移操作                    
                    break;
                }
            }
        }
        
        //最后一个元素也是组合的一个，即所有的1(true)都到了数组的最右边
        combination.add(calcElement(a));
        return combination;
    }
    
    /**
     * 异或操作实现不开辟新的存储空间完成两个数的位置交换。
     * 
     * @param a 待操作数所在的数组
     * @param x 待交换位置的第一个数在数组中的下标
     * @param y 待交换位置的第二个数在数组中的下标
     */
    private void oneZeroSwap(boolean a[], int x, int y){
        a[x] = a[x] ^ a[y];
        a[y] = a[y] ^ a[x];
        a[x] = a[x] ^ a[y];
    }
    
    /**
     * 判断10作01交换位置后，是否实现了数组a中右端的n个元素全为1(true),这个结果作为10/01换位结束标识
     * 
     * @param a 10/01换位的输入数组
     * @param m 计算组合的元数据个数
     * @param n 计算组合的被选取元素个数
     * @return true表示10/01换位结束，false表示还可以继续
     */
    private boolean isAllZeroLeft(boolean a[], int m, int n){
        int gap = m - n;
        for(int i=0; i<gap; i++){
            if(a[i]){
                return false;
            }
        }
        return true;
    }
    
    /**
     * 将10/01换位之后数组左边的全部1都搬移到数组的最左边。
     * 
     * @param a 待操作的组合数组
     * @param end 指明搬移操作的范围，在end数组下标左边的进行搬移, 这个end的值小于数组的长度
     */
    private void moveFrontOneToLeft(boolean a[], int end){
        int oneCnt = 0;
        for(int i=0; i<end; i++){
            if(a[i]){
                oneCnt++;
                a[i] = false;
            }
        }
        for(int i=0; i<oneCnt; i++){
            a[i] = true;
        }
    }
    
    /**
     * 计算当前数组中的组合元素的值，数组元素为1(true)的对应的下标的全集，即为所需的一个组合元素值
     * 
     * @param a 待操作的组合数组
     * @return 一个组合的值, 去掉了最后的一个逗号分隔符
     */
    private String calcElement(boolean a[]){        
        String elem = "";
        for(int i=0; i<a.length; i++){
            if(a[i]){
                elem += (i+1) + ",";
            }
        }
        return elem.substring(0, elem.length() - 1);
    }
        
    
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        int m = 5, n = 2;
        Combination combination = new Combination();
        ArrayList<String> coms = combination.getCombinations(m, n);
        for(int i = 0; i<coms.size(); i++){
            System.out.println(coms.get(i));
        }
    }
}

/**
 * @author "shihuc"
 * @date   2016年12月1日
 */

/**
 * @author chengsh05
 *
 */
class IllegalCombinationInputException extends Exception{

    private static final long serialVersionUID = 678024281707796100L;
    
    public IllegalCombinationInputException(){
        super("The combination base number should be not less than the selection number.");  
    }

}