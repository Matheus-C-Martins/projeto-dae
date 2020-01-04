package ejbs;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Stateless(name = "EmailEJB")
public class EmailBean {
    private Properties props = new Properties();
    @Resource(name = "java:/jboss/mail/gmail")
    private Session mailSession;
    public void send(String to, String subject, String text) throws MessagingException {
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        mailSession = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("taes.grupox@gmail.com","grupoxtaes");
                    }
                });
        Message message = new MimeMessage(mailSession);
        try {
            // Adjust the recipients. Here we have only one recipient.
            // The recipient's address must be an object of the InternetAddress class.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to, false));
            // Set the message's subject
            message.setSubject(subject);
            // Insert the message's body
            message.setText(text);
            // Adjust the date of sending the message
            Date timeStamp = new Date();
            message.setSentDate(timeStamp);
            // Use the 'send' static method of the Transport class to send the message
            Transport.send(message);
        } catch (MessagingException e) {
            throw e;
        }
    }
}
