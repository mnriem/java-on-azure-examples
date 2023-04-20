package certsendemail;

import com.azure.security.keyvault.jca.KeyVaultLoadStoreParameter;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Map;
import java.util.Properties;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.PrivateKeyDetails;
import org.apache.http.ssl.PrivateKeyStrategy;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This REST controller shows you how you would send an email using a
 * client-side certificate when communicating with your SMTP server.
 *
 * @author Manfred Riem (manfred.riem@microsoft.com)
 */
@RestController
public class CertSendEmailController {

    /**
     * Stores the certificate name.
     */
    @Value("${keyVaultCertificateName}")
    private String certificateName;

    /**
     * Stores the key vault URI.
     */
    @Value("${keyVaultUri}")
    private String keyVaultUri;
    
    /**
     * Stores the tenant ID.
     */
    @Value("${keyVaultTenantId}")
    private String tenantId;
    
    /**
     * Stores the client ID.
     */
    @Value("${keyVaultClientId}")
    private String clientId;
    
    /**
     * Stores the client secret.
     */
    @Value("${keyVaultClientSecret}")
    private String clientSecret;

    /**
     * This PrivateKeyStrategy determines which certificate to send to the
     * server. In this particular case the certificate with the 'self-signed'
     * alias is used.
     */
    private class ClientPrivateKeyStrategy implements PrivateKeyStrategy {

        /**
         * Choose which alias to use for the client certificate.
         *
         * @param map the map of aliases.
         * @param socket the socket.
         * @return the alias to use.
         */
        @Override
        public String chooseAlias(Map<String, PrivateKeyDetails> map, Socket socket) {
            return certificateName;
        }
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setProtocol("smtps");
        mailSender.setHost("localhost");
        mailSender.setPort(465);
        mailSender.setUsername("user01@james.local");
        mailSender.setPassword("1234");
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");
        SocketFactory socketFactory = getSocketFactory();
        if (socketFactory != null) {
            properties.put("mail.smtp.socketFactory", socketFactory);
            properties.put("mail.smtp.ssl.socketFactory", socketFactory);
        }
        return mailSender;
    }

    /**
     * Get the Socket factory.
     *
     * @return the Socket factory.
     */
    public SocketFactory getSocketFactory() {
        SocketFactory socketFactory = null;
        try {
            KeyStore ks = getKeyStore();
            SSLContext sslContext = SSLContexts.custom()
                    .loadKeyMaterial(ks, "".toCharArray(), new ClientPrivateKeyStrategy())
                    .loadTrustMaterial(ks, new TrustSelfSignedStrategy())
                    .build();
            socketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return socketFactory;
    }

    /**
     * Get the default AzureKeyVault KeyStore.
     *
     * <p>
     * If you want to be able to use multiple KeyVaults use a
     * KeyVaultLoadStoreParameter instead of passing null into the load method.
     * </p>
     *
     * @return the KeyStore, or null if initialization failed.
     */
    private KeyStore getKeyStore() {
        KeyStore ks = null;

        try {
            ks = KeyStore.getInstance("AzureKeyVault");
//            KeyVaultLoadStoreParameter parameter = new KeyVaultLoadStoreParameter(keyVaultUri);
            KeyVaultLoadStoreParameter parameter = new KeyVaultLoadStoreParameter(
                    keyVaultUri,
                    tenantId,
                    clientId,
                    clientSecret);
            ks.load(parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ks;
    }

    /**
     * Call the external server that requires a client-certificate.
     *
     * @return 'Hello mTLS' or 'Oops' when the call failed.
     */
    @RequestMapping("/sendEmail")
    public String sendEmail() {
        String result = "Sent!";
        try {
            JavaMailSender sender = getJavaMailSender();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("no-reply@example.com");
            message.setTo("you@example.com");
            message.setSubject("Test");
            message.setText("This is a test");
            sender.send(message);
        } catch(Exception e) {
            result = e.getMessage();
        }
        return result;
    }
}
