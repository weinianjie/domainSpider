package com.spider;

public class DomainCharacter {
	// 密码可能会包含的字符集合
	static char[] charSource = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9' };
	static int sLength = charSource.length; // 字符集长度
	static int maxLength = 3; // 设置可能最长的密码长度
	static int counter = 2000;// 计数器，多线程时可以对其加锁，当然得先转换成Integer类型。

	public static synchronized String nextDomain() {
		StringBuilder buider = new StringBuilder(maxLength * 2);
		int _counter = counter;
		while (_counter >= sLength) {// 10进制转换成26进制
			buider.insert(0, charSource[_counter % sLength]);// 获得低位
			_counter = _counter / sLength;
			_counter--;// 精髓所在，处理进制体系中只有10没有01的问题，在穷举里面是可以存在01的
		}
		buider.insert(0, charSource[_counter]);// 最高位
		counter++;
		
		return buider.toString().length() <= maxLength? buider.toString() : null;
	}
}
