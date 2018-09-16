package offer;

public class 旋转数组的最小数字 {
	public int minNumberInRotateArray(int[] array) {
		//因为其基本有序，可以使用二分法
		int low = 0;
		int high = array.length - 1;
		//{3,4,5,1,2}
		while(low < high) {
			int mid = low + (high - low)/2;
			if(array[mid] > array[high]) {//说明小值在右边，上面数组为例子，5 < 2
				low = mid + 1;
			} else {
				high = mid;
			}
		}
		return array[low];		
	}
	
	public static void main(String[] args) {
		int[] arr = {3,4,5,1,2};
		int min = new 旋转数组的最小数字().minNumberInRotateArray(arr);
		System.out.println(min);
	}
}
