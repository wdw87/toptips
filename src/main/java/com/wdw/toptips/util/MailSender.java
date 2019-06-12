package com.wdw.toptips.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: Wudw
 * @Date: 2019/6/11 16:41
 * @Version 1.0
 */
@Service
public class MailSender implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

    private JavaMailSenderImpl mailSender;

    @Autowired
    private Configuration configuration;

    public boolean sendWithHtmlTemplate(String to, String subject, String templateName, Map<String, Object> model) {
        try {
            //使用@Autowired注入后，可以在application.properties中进行配置
            //Configuration configuration = new Configuration(Configuration.getVersion());
            //configuration.setDefaultEncoding("utf-8");
            //configuration.setClassForTemplateLoading(MailSender.class, "/templates");
            Template template = configuration.getTemplate(templateName);
            String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            String nick = MimeUtility.encodeText("toptips");
            InternetAddress from = new InternetAddress(nick + "<1406024115@st.nuc.edu.cn>");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(htmlText);
            mailSender.send(mimeMessage);
            return true;

        } catch (Exception e) {
            logger.error("发送邮件异常 " + e.getMessage());
            return false;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl();

        // 请输入自己的邮箱和密码，用于发送邮件
        mailSender.setUsername("1406024115@st.nuc.edu.cn");
        mailSender.setPassword("3346535");
        mailSender.setHost("smtp.exmail.qq.com");
        // 请配置自己的邮箱和密码

        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", true);
        mailSender.setJavaMailProperties(javaMailProperties);
    }
}
