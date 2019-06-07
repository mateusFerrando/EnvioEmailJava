package br.com.mateus.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EnviarEmail {

    // Email e senha de quem vai enviar os emails
    private static String email = "seuemail@seuemail.com";
    private static String senha = "suasenha";

    public static void sendEmailAll(List<String> emails, String assunto, String mensagem) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, senha);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));

            for (String email : emails) {
                message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            }
            message.setSubject(assunto);
            message.setText(mensagem);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) {
        List<String> email = new ArrayList<>(0);
        email.add("email@email.com");

        sendEmailAll(email, "Envio de e-mail", "Envio de e-mail com Java");
    }

}