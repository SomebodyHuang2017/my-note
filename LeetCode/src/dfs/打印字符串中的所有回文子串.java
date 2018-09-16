package dfs;

import java.util.ArrayList;

/**
 给定一个字符串：aab
 
 返回结果：
   [
    ["aa","b"],
    ["a","a","b"]
  ]

 
 */
public class 打印字符串中的所有回文子串 {
		public ArrayList<ArrayList<String>> partition(String s) {
		        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
		        if (s == null || s.length() == 0)
		            return res;
		 
		        solve(s, 0, new ArrayList<String>(), res);
		        return res;
		    }
		 
	
		    private void solve(String s, int index, ArrayList<String> preList, ArrayList<ArrayList<String>> res) {
		        if (index == s.length()) {
		            res.add(new ArrayList<String>(preList));
		            return;
		        }
		        ArrayList<String> list = new ArrayList<String>(preList);
		        for (int i = index + 1; i <= s.length(); i++) {
		            if (isPalindrom(s.substring(index, i))) {
		                list.add(s.substring(index, i));
		                solve(s, i, list, res);
		                list.remove(list.size() - 1);
		            }
		        }
		    }
		 
		    private boolean isPalindrom(String s) {
		        if (s == null)
		            return false;
		 
		        int l = 0, r = s.length() - 1;
		        while (l < r) {
		            if (s.charAt(l++) != s.charAt(r--))
		                return false;
		        }
		        return true;
		 
		    }
}
