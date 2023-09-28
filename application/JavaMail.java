package application;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;



public class JavaMail {

    public static void sendMail(String recipient) {
        System.out.println("Sending email...");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        final String myAccountEmail = "ScheduleSketcher@gmail.com";
        final String password = "mtimdixwewqywtqe";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        try {
            Message message = prepareMessage(session, myAccountEmail, recipient);
            if (message != null) {
                Transport.send(message);
                System.out.println("Message sent successfully!");
            } else {
                System.out.println("Failed to prepare the message.");
            }
        } catch (MessagingException e) {
            System.out.println("An error occurred while sending the email:");
            e.printStackTrace();
        }
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recipient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Test");
            message.setText("This is a test.");
            return message;
        } catch (Exception ex) {
            Logger.getLogger(JavaMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
