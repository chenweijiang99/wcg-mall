package com.chenweijiang.wcg_mall.utils;

import com.chenweijiang.wcg_mall.constant.MailConstant;
import com.sun.mail.util.MailSSLSocketFactory;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 邮件工具类
 * @author 枳枳
 */
@Component
@RequiredArgsConstructor
public class MailUtils  {

	private final JavaMailSender javaMailSender;


	public void sendAcactivateMail(String code,String to){
		// 解决本地DNS未配置 ip->域名场景下，邮件发送太慢的问题
		System.getProperties().setProperty("mail.mime.address.usecanonicalhostname", "false");
		// 获取 MimeMessage
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		Session session = mimeMessage.getSession();
		// 设置 日志打印控制器
		session.setDebug(true);
		//  解决本地DNS未配置 ip->域名场景下，邮件发送太慢的问题
		session.getProperties().setProperty("mail.smtp.localhost", "myComputer");
		String text = MailConstant.ACTIVATE_MAIL + code+"\n";
		String title = MailConstant.ACTIVATE_TITLE;
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(MailConstant.FROM);
		message.setSubject(title);
		message.setTo(to);
		message.setText(text);
		javaMailSender.send(message);
	}

	public void sendResetPwdMail(String code,String to){
		// 解决本地DNS未配置 ip->域名场景下，邮件发送太慢的问题
		System.getProperties().setProperty("mail.mime.address.usecanonicalhostname", "false");
		// 获取 MimeMessage
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		Session session = mimeMessage.getSession();
		// 设置 日志打印控制器
		session.setDebug(true);
		//  解决本地DNS未配置 ip->域名场景下，邮件发送太慢的问题
		session.getProperties().setProperty("mail.smtp.localhost", "myComputer");
		String text = MailConstant.RESET_MAIL + code+"\n" + MailConstant.VALID_TIME;
		String title = MailConstant.RESET_TITLE;
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(MailConstant.FROM);
		message.setSubject(title);
		message.setTo(to);
		message.setText(text);
		javaMailSender.send(message);
	}
}
