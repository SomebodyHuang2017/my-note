package dp;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class 一百分 {
	/**
	 * 小明出试卷，找到了一些题目，从中挑选一些题目，让这些题目总分恰好是100份。
	 * 
	 * 输入描述： 输入第一行一个正整数n(1 <= n <= 10)，表示小明找打了n道题。 下面是n行，其中第i行是一个正整数A[i] (1 <= A[i]
	 * <= 100)， 表示第i道题目的分数是A[i]
	 * 
	 * 输出描述： 输入的第一行一个正整数m（1<= m <= n），表示小明需要选择的题目数量
	 * 下面是m行，每行一个正整数，表示小明选择的题目编号，编号的取值范围是[1,n]， 编号应该按照从小到大的顺序输出。
	 */
	/**
	 * 
	 * 输入： 7 1 2 4 8 16 32 64
	 */

	/**
	 * 输出： 3 3 6 7
	 */

	static int SUM = 100;
	
	public static void main(String[] args) {
		int n = 7;
		int[] scores = { 0, 1, 2, 4, 8, 16, 32, 64 };

		boolean[][] r = new boolean[n + 1][SUM + 1];
		markResults(n, scores, r);
		printResults(n, scores, r);
	}

	/*
	 * 3 3 6 7
	 */

	private static void printResults(int n, int[] scores, boolean[][] r) {
		Deque<Integer> stack = new ArrayDeque<>(n);

		printResults(n, scores, r, stack, 1, SUM);
	}

	private static void printResults(int n, int[] scores, boolean[][] r, Deque<Integer> stack, int index, int sum) {
		if (sum == 0) {
			printStack(stack);
		}

		if (index == n) {
			if (r[index][sum]) {
				stack.push(index);
				printStack(stack);
				stack.pop();
			}
			return;
		}

		if (r[index][sum]) {
			if (r[index + 1][sum]) {
				printResults(n, scores, r, stack, index + 1, sum);
			}
			int division = sum - scores[index];
			if (division >= 0 && r[index + 1][division]) {
				stack.push(index);
				printResults(n, scores, r, stack, index + 1, division);
				stack.pop();
			}
		}
	}

	private static void printStack(Deque<Integer> stack) {
		if (stack.size() == 0) {
			return;
		}
		System.out.println(stack.size());
		Iterator<Integer> iterator = stack.descendingIterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	private static void markResults(int n, int[] scores, boolean[][] r) {
		for (int j = 1; j <= SUM; j++) {
			r[n][j] = false;
		}
		r[n][0] = true;
		r[n][scores[n]] = true;

		for (int i = n - 1; i > 0; i--) {
			for (int j = 0; j <= SUM; j++) {
				if (r[i + 1][j]) {
					r[i][j] = true;
					if (j <= SUM - scores[i]) {
						r[i][j + scores[i]] = true;
					}
				}
			}
		}

	}

}
