package com.chenweijiang.wcg_mall.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class MailUtils  {
 	@Autowired
	private JavaMailSender javaMailSender;

	 public static final String FROM = "1774532899@qq.com";
	 public void sendAcactivateMail(String to){
		 String text = "这是一封激活邮件,激活请点击以下链接：\n" +
				 "http://localhost:8080/user/user/activate/"+to+"\n" +
				 "激活账户。";
		 String titile = "欢迎注册WCG商城";
		 SimpleMailMessage message = new SimpleMailMessage();
		 message.setFrom(MailUtils.FROM);
		 message.setSubject(titile);
		 message.setTo(to);
		 message.setText(text);
		 javaMailSender.send(message);
	 }

	public void sendResetPwdMail(String to){
		String text = "这是一封重置邮件,激活请点击以下链接：\n" +
				"http://localhost:8080/user/user/resetPwd"+to+"\nz" +
				"重置密码。";
		String titile = "重置密码";
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(MailUtils.FROM);
		message.setSubject(titile);
		message.setTo(to);
		message.setText(text);
		javaMailSender.send(message);
	}
}
