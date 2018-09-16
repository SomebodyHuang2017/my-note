package offer;

public class 替换空格 {
	public String replaceSpace(StringBuffer str) {
		int index = -1;
		while((index = str.indexOf(" ")) != -1) {
			str.replace(index, index+1, "%20");
		}
		return str.toString();
	}
}
