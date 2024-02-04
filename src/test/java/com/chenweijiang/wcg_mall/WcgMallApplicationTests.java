package com.chenweijiang.wcg_mall;

import com.chenweijiang.wcg_mall.utils.MailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WcgMallApplicationTests {
	@Autowired
	private MailUtils mailUtils;
	@Test
	void MailTest(){
		mailUtils.sendMail("1774532899@qq.com","123456789");
	}

}
