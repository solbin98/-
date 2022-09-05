package com.project.member.join;

import com.project.dao.EmailAuthDao;
import com.project.dto.EmailAuthDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class MailService {
    @Autowired
    JavaMailSenderImpl mailSender;
    @Autowired
    AuthKeyMaker authKeyMaker;
    @Autowired
    EmailAuthDao emailAuthDao;
    @Autowired
    MessageSource messageSource;

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Locale locale = Locale.KOREA;

    public Map<String, Object> sendAuthMail(String email) {
        Map<String,Object> resultMap = new HashMap<>();

        String authKey = authKeyMaker.getKey(6);
        // 인증 코드 발급/재발급 파트
        try{
            EmailAuthDto emailAuthDto = getEmailAuthByEmail(email);
            LocalDateTime newExpireDateTime = LocalDateTime.now().plusSeconds(120);
            if(emailAuthDto == null) addEmailAuth(new EmailAuthDto(email, authKey, newExpireDateTime, false));
            else{
                if(isExpiredCode(emailAuthDto)) { // 만료된 코드인 경우에, 이메일 재 발송 요청을 수행
                    updateEmailAuth(new EmailAuthDto(email, authKey, newExpireDateTime, false));
                }
                else { // 아직 만료되지 않은 코드인 경우에, 에러 메시지 반환
                    resultMap.put("message", messageSource.getMessage("join.email.retry", null, locale) + emailAuthDto.getExpire_date());
                    resultMap.put("result", false);
                    return resultMap;
                }
            }
        }
        catch (Exception e){
            log.debug("error message : {}",e);
            resultMap.put("message", messageSource.getMessage("join.email.fail", null, locale));
            resultMap.put("result", false);
        }

        // 이메일 전송 파트
        MimeMessage mail = mailSender.createMimeMessage();
        String mailContent = "<h1>[Brogrammers 이메일 인증]</h1><br><p>아래 인증번호를 입력 하시면 이메일 인증이 완료됩니다.</p>" + "인증번호 : " + authKey;

        try {
            mail.setSubject("회원가입 이메일 인증 ", "utf-8");
            mail.setText(mailContent, "utf-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        resultMap.put("message", messageSource.getMessage("join.email.success", null, locale));
        return resultMap;
    }

    public boolean checkAuthCode(String email, String code) throws Exception {
        EmailAuthDto result = getEmailAuthByEmailAndCode(email, code);
        if(result != null && !isExpiredCode(result)) {
            updateEmailAuth(new EmailAuthDto(email, code, LocalDateTime.now(), true));
            return true;
        }
        else return false;
    }

    public boolean isExpiredCode(EmailAuthDto emailAuthDto){
        LocalDateTime expireDateTime = emailAuthDto.getExpire_date();
        LocalDateTime nowTime = LocalDateTime.now();
        return (nowTime.isAfter(expireDateTime) == true);
    }

    public EmailAuthDto getEmailAuthByEmail(String email){
        return emailAuthDao.selectByEmail(email);
    }

    public EmailAuthDto getEmailAuthByEmailAndCode(String email, String code){
        return emailAuthDao.selectByEmailAndCode(email, code);
    }

    public boolean getAuthByEmail(String email){
        return emailAuthDao.selectAuthByEmail(email);
    }

    public void addEmailAuth(EmailAuthDto emailAuthDto) throws Exception{
        emailAuthDao.insert(emailAuthDto);
    }

    public void updateEmailAuth(EmailAuthDto emailAuthDto) throws Exception{
        emailAuthDao.update(emailAuthDto);
    }

}

