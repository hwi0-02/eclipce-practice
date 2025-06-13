package mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MyEmail {

	private static MyEmail myobject;
	
	public boolean sendMail(MailData data) {
		//메일을 보낼 때 필요한 일반 사항(보내는 주소, 받는 주소, 메일내용)
				//정보를 한 번에 저장하는 객체
				Properties props = System.getProperties();
				props.put("mail.smtp.host", "smtp.naver.com");
				props.put("mail.smtp.port", "587");
				props.put("defaultEncoding", "utf-8");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.auth", "true");
				
				try {
				String userid="자신의 아이디";
				String userpw="자신의 비밀번호";
				
				//위의 정보를 이용하여 서버연결(session)
				//라이브러리 추가(javax activation, javax mail)
				//Session session = Session.getInstance(props, 인증관련객체);
				Session session = Session.getInstance(props, new Authenticator() {
						protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(userid, userpw);
						}	
				});
				
				/*
					Authenticator auth=new Authenticator() {
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(userid, userpw);
					}	
					};
					Session session = Session.getInstance(props,auth);
				*/
				
				//메일 보내기(보내는 주소, 받는 주소, 메일내용)
				String sendemail="자신의 이메일주소"; //고정
				String recvemail=data.getRecvemail();
				String subject=data.getSubject();
				String content=data.getContent();
				session.setDebug(false);
				
				Message msg=new MimeMessage(session); //연결이 설정된 객체주입
				
				msg.setFrom(new InternetAddress(sendemail));
//				msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recvmail));
				InternetAddress[] recvs = new InternetAddress[1];
				recvs[0]=new InternetAddress(recvemail);
				msg.setRecipients(Message.RecipientType.TO, recvs);
				msg.setSubject(subject);
				msg.setText(content);
				
				Transport.send(msg);
				return true;
			}catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		
	}

	public static MyEmail getInstance() {
		try {
		if(myobject == null) myobject=new MyEmail();
	}catch(Exception e) {
		e.printStackTrace();
	}
		return myobject;
	}

}
