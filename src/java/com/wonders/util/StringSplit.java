package com.wonders.util;

public class StringSplit {
	public static void main(String[] args) throws Exception {
		String ss = "a很bc你好";
		//System.out.println(splitString(ss, 9));
		System.out.println("a".getBytes("UTF-8").length);
	}

	public static String splitString(String str, int byteLength)
			throws Exception {
		//如果字符串为空，直接返回
		if(str == null || "".equals(str)) {
			return str;
		}
		//用于统计这个字符串中有几个中文字符
		int wordCount = 0;
		//统一按照gbk编码来得到他的字节数组，因为不同的编码字节数组是不一样的。
		byte[] strBytes = str.getBytes("GBK");
		
		//如果只截取一位，而且第一位是中文字符时的处理
		if (byteLength == 1) {
			if (strBytes[0] < 0) {
				return str.substring(0, 1);
			}
		}
		//字符串中的一个中文会使得wordCount 加两次
//如果你这个字节取出来的是一个汉字也就是两个字节当中的一个的话val的值为负数
		for (int i = 0; i < byteLength; i++) {
			int val = strBytes[i];
			if (val < 0) {
				wordCount++;
			}
		}
		
		//如果传递的这个截取的位数没有截到半个中文上面，那么就按照byteLength - (wordCount / 2个长度进行截取
		if (wordCount % 2 == 0) {
			return str.substring(0, (byteLength - (wordCount / 2)));
		}
		//否则，我们就舍弃多出来的这一位 所以  -1 
		return str.substring(0, (byteLength - (wordCount / 2) - 1));

	}
}