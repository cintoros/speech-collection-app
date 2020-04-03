package ch.fhnw.speech_collection_app.features.email;

import ch.fhnw.speech_collection_app.config.SpeechCollectionAppConfig;
import ch.fhnw.speech_collection_app.jooq.enums.VerificationTokenTokenType;
import ch.fhnw.speech_collection_app.jooq.tables.records.UserRecord;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static ch.fhnw.speech_collection_app.jooq.Tables.VERIFICATION_TOKEN;

public interface EmailSenderService {
    /**
     * send a reset password link in case the feature is enabled
     */
    void sendResetPassword(UserRecord userRecord);

    /**
     * send a confirmation email in case the feature is enabled else it is just activated by default.
     */
    void sendEmailConfirmation(UserRecord userRecord);

    @Service
    @ConditionalOnProperty(value = "speech-collection-app.features.email-integration", havingValue = "true")
    class EmailSenderServiceImpl implements EmailSenderService {
        private final JavaMailSender javaMailSender;
        private final DSLContext dslContext;
        private final Configuration freemarkerConfig;
        private final Logger logger = LoggerFactory.getLogger(getClass());
        private final SpeechCollectionAppConfig speechCollectionAppConfig;

        @Autowired
        public EmailSenderServiceImpl(JavaMailSender javaMailSender, DSLContext dslContext, Configuration freemarkerConfig, SpeechCollectionAppConfig speechCollectionAppConfig) {
            this.javaMailSender = javaMailSender;
            this.dslContext = dslContext;
            this.freemarkerConfig = freemarkerConfig;
            this.speechCollectionAppConfig = speechCollectionAppConfig;
        }

        @Override
        public void sendResetPassword(UserRecord userRecord) {
            sendEmail(userRecord, false);
        }

        @Override
        public void sendEmailConfirmation(UserRecord userRecord) {
            sendEmail(userRecord, true);
        }

        private void sendEmail(UserRecord userRecord, boolean emailMode) {
            var token = UUID.randomUUID().toString();
            var verificationToken = dslContext.newRecord(VERIFICATION_TOKEN);
            verificationToken.setTokenType(emailMode ? VerificationTokenTokenType.EMAIL : VerificationTokenTokenType.PASSWORD);
            verificationToken.setToken(token);
            verificationToken.setUserId(userRecord.getId());
            verificationToken.store();
            try {
                var email = new MimeMessageHelper(javaMailSender.createMimeMessage());
                email.setTo(userRecord.getEmail());
                email.setSubject((emailMode ? "Email Confirmation" : "Password Reset") + " Speech Collection App");
                email.setFrom(speechCollectionAppConfig.getFromEmail());
                var template = freemarkerConfig.getTemplate(emailMode ? "email-confirmation.ftl" : "password-reset.ftl");
                var url = speechCollectionAppConfig.getBaseUrl() + "token" + "?mode=" + (emailMode ? "email" : "password") + "&token=" + token;
                var text = FreeMarkerTemplateUtils.processTemplateIntoString(template, Map.of("link", url));
                email.setText(text, true);
                javaMailSender.send(email.getMimeMessage());
            } catch (IOException | TemplateException | MessagingException e) {
                logger.error("unexpected exception:", e);
            }
        }
    }

    @Service
    @ConditionalOnProperty(value = "speech-collection-app.features.email-integration", havingValue = "false")
    class EmailSenderServiceDummyImpl implements EmailSenderService {
        @Override
        public void sendResetPassword(UserRecord userRecord) {
        }

        @Override
        public void sendEmailConfirmation(UserRecord userRecord) {
            userRecord.setEnabled(true);
            userRecord.store();
        }
    }
}


