package com.chenweijiang.wcg_mall;

import com.chenweijiang.wcg_mall.pojo.Alipay;
import com.chenweijiang.wcg_mall.pojo.Order;
import com.chenweijiang.wcg_mall.utils.AlipayUtil;
import com.chenweijiang.wcg_mall.utils.MailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WcgMallApplicationTests {
	@Autowired
	private MailUtils mailUtils;
	@Autowired
	private AlipayUtil alipayUtil;


	@Test
	void alipayTest() throws Exception{
		Alipay alipay = new Alipay();
		System.out.println(alipayUtil.pay(alipay));
	}

}
