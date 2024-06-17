package com.chenweijiang.wcg_mall;

import com.chenweijiang.wcg_mall.utils.AlipayUtil;
import com.chenweijiang.wcg_mall.utils.MailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

//@SpringBootTest
class WcgMallApplicationTests {
	@Autowired
	private MailUtils mailUtils;
	@Autowired
	private AlipayUtil alipayUtil;

	@Test
	void contextLoads() {
		// 原始字符串
		String originalString = "我有一个字符串，里面是中文";

		// 使用toCharArray方法分割字符串
		char[] charArray = originalString.toCharArray();

		// 打印结果
		for (char c : charArray) {
			System.out.println(c);
		}
	}

}
