package com.execom.pomodoro.service;

import com.execom.pomodoro.controller.UserController;
import com.execom.pomodoro.domain.Invitation;
import org.apache.commons.lang3.CharEncoding;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    private JavaMailSender javaMailSender;
    private SpringTemplateEngine templateEngine;

    private static Logger log = Logger.getLogger(UserController.class);

    @Autowired
    public MailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendMail(String toWho, String fromWho, String subject, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeHelper = new MimeMessageHelper(message, false, CharEncoding.UTF_8);
        mimeHelper.setFrom(fromWho);
        mimeHelper.setTo(toWho);
        mimeHelper.setSubject(subject);
        mimeHelper.setText(content, true);

        javaMailSender.send(message);

    }

    public void sendInvitationMail(String fromWhichUser, Invitation invitation) {
        log.info("-------------------------------------------------------------------------------");
        log.info("usao u mail service, send invitation");
        Context context = new Context();
        context.setVariable("fromWhichUser", fromWhichUser);
        context.setVariable("invitation", invitation);

        String content = templateEngine.process("sendInvitation", context);
        try {
            log.info("-------------------------------------------------------------------------------");
            log.info("usao u try");
            sendMail(invitation.getUsername(), "olivera", "link to activate invitation", content);
        } catch (MessagingException e) {
            log.info("eeee-------------------------------------------------------------------------------");
            log.info(e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
