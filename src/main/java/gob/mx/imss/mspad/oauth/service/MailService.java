package gob.mx.imss.mspad.oauth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
public class MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    private JavaMailSender mailSender;

    private MailProperties mailProperties;

    @Autowired
    public MailService(JavaMailSender mailSender, MailProperties mailProperties) {
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
    }

    /**
     * Construye y envía el correo electrónico
     *
     * @param recipientsMail   Direcciones de correo electrónico de los destinatarios
     * @param subject          Asunto del correo electrónico
     * @param body             Contenido del correo electrónico
     * @param attachmentName   Nombre del archivo adjunto
     * @param attachmentBase64 Cadena en base64 del archivo adjunto
     * @throws IOException 
     */
    public void sendMail(List<String> recipientsMail, String subject, String body, String attachmentName, String attachmentBase64, String nombre, String correo) throws IOException {

        try {
            LOGGER.info("Enviando correo a {}", String.join(", ", recipientsMail));

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
////            messageHelper.addAttachment("logo.png", new ClassPathResource("IMSS-Logo.png"));
//            String inlineImage = "<img src=\"cid:logo.png\"></img><br/>";
//
//            messageHelper.setText(body, true);
//            messageHelper.setSubject(subject);
//           
//            messageHelper.setFrom(mailProperties.getUsername());
//            messageHelper.setTo(recipientsMail.toArray(new String[recipientsMail.size()]));
//
//            if (null != attachmentBase64) {
//
//                messageHelper.addAttachment(attachmentName, new ByteArrayDataSource(Base64
//                        .getDecoder().decode(attachmentBase64), MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE));
//            }
//    
//            
//            FileSystemResource res = new FileSystemResource(new File("C:\\Users\\ibenriquez\\Documents\\workspace\\logo1.png"));
//            messageHelper.addInline("DATA_IMG", res);
            
            
            
      

			ClassPathResource image = new ClassPathResource("logoimss.png");
			ClassPathResource image2 = new ClassPathResource("linea.png");


          messageHelper.setText(body, true);
          messageHelper.setSubject(subject);
         
          messageHelper.setFrom(mailProperties.getUsername());
          messageHelper.setTo(recipientsMail.toArray(new String[recipientsMail.size()]));
          messageHelper.addInline("DATA_IMG", image);
          messageHelper.addInline("DATA_BORDE", image2);
         
            
            
            mailSender.send(message);
            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {

            LOGGER.error("Se produjo un error al enviar el correo a {} - "
                    + e.getMessage(), String.join(", ", recipientsMail));
        }
    }
    
    BodyPart createInlineImagePart(byte[] base64EncodedImageContentByteArray) throws MessagingException {
    	 InternetHeaders headers = new InternetHeaders();
    	 headers.addHeader("Content-Type", "image/png");
    	 headers.addHeader("Content-Transfer-Encoding", "base64");
    	 MimeBodyPart imagePart = new MimeBodyPart(headers, base64EncodedImageContentByteArray);
    	 imagePart.setDisposition(MimeBodyPart.INLINE);
    	 imagePart.setContentID("&lt;DATA_IMG&gt;");
    	 imagePart.setFileName("image.png");
    	 return imagePart;
    }
    
    String generateContentId(String prefix) {
        return String.format("%s-%s", prefix, UUID.randomUUID());
   }
}
