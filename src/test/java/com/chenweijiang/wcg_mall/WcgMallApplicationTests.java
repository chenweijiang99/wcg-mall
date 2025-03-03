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


}
