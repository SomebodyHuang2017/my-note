package dp;

import java.util.Set;

public class 分词 {

	/**
Given a string s and a dictionary of words dict, determine if s can be segmented into a space-separated sequence of one or more dictionary words.

For example, given
s ="leetcode",
dict =["leet", "code"].

Return true because"leetcode"can be segmented as"leet code".


	 */

	/**
	       * 方法二：
	       * 状态转移方程：
	       * f(i) 表示s[0,i]是否可以分词
	       * f(i) = f(j) && f(j+1,i); 0 <= j < i;
	       *
	       */
	      public boolean wordBreak2(String s, Set<String> dict){
	        int len = s.length();
	        boolean[] arrays = new boolean[len+1];
	        arrays[0] = true;
	        for (int i = 1; i <= len; ++i){
	          for (int j = 0; j < i; ++j){
	            if (arrays[j] && dict.contains(s.substring(j, i))){
	              arrays[i] = true;
	              break;
	            }
	          }
	        }
	        return arrays[len];
	      }
}
