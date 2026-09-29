package com.mypersonalportifolio.food_delivery_api.infrastructure.email;

import com.mypersonalportifolio.food_delivery_api.application.email.MailMessageSource;
import com.mypersonalportifolio.food_delivery_api.application.email.MailSenderService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;


@Component
public class SmtpMailSenderService implements MailSenderService {

    private final JavaMailSender mailSender;
    private final Configuration freemarkerConfig;
    private final String DEFAULT_CHARSET = "UTF-8";

    @Value("${dev.application.mail.sandbox-address}")
    private String sandBoxMailAddress;

    @Autowired
    public SmtpMailSenderService(JavaMailSender mailSender, Configuration freemarkerConfig) {
        this.mailSender = mailSender;
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public void sendMail(MailMessageSource messageSource) {
        System.out.println("----------------------------------------------------");
        System.out.println("sending mail");
        System.out.println("---------------");
        var messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), DEFAULT_CHARSET);

        try {
            var templateAsBody = processTemplate(messageSource);
            var to = Objects.isNull(sandBoxMailAddress) ? messageSource.getTo().toArray(String[]::new) : Set.of(sandBoxMailAddress).toArray(String[]::new);

            messageHelper.setFrom("Mailgun Sandbox <postmaster@sandbox37aeac34c64a44239c022e8050ad1ca3.mailgun.org>");
            messageHelper.setTo(to); //sandBox email
            messageHelper.setSubject(messageSource.getSubject());
            messageHelper.setText(templateAsBody, true);

            mailSender.send(messageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String processTemplate(MailMessageSource messageSource) {
        try {
           var template = freemarkerConfig.getTemplate(messageSource.getBody(), Locale.of("pt", "BR"), DEFAULT_CHARSET);

           return FreeMarkerTemplateUtils.processTemplateIntoString(template, messageSource.getModelVariables());

        } catch (IOException | TemplateException e) {
            throw new FailOnMailProcessingException(e);
        }
    }

}
