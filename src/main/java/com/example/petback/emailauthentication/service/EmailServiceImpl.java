package com.example.petback.emailauthentication.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final MailAuthInfoService mailAuthInfoService;

    //이메일 전송 메서드
    @Override
    public void sendEmail(String email) throws Exception {
        // 새로운 코드를 생성
        String verificationCode = makeRandomCode();
        // 이메일에 새로운 코드를 포함한 MimeMessage 생성
        MimeMessage message = joinEmail(email, verificationCode);
        // 이메일 인증 정보를 저장
        mailAuthInfoService.setEmailVerificationCode(email, verificationCode);


        if (mailAuthInfoService.existData(email)) {
            mailAuthInfoService.deleteData(email);
        }
        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        mailAuthInfoService.setEmailVerificationCode(email, verificationCode);
    }

    // 인증 코드 검증

    @Override
    public boolean verificationCodeCheck(String email, String verificationCode) {
        String codeFindByEmail = mailAuthInfoService.getData(email);
        if (codeFindByEmail == null) {
            return false;
        }
        mailAuthInfoService.save("success", email);
        return mailAuthInfoService.getData(email).equals(verificationCode);
    }


    // 이메일 보낼 양식
    public MimeMessage joinEmail(String email, String verificationCode) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, email); //보내는 대상
        message.setSubject("Pet병 회원 복구 메일 입니다."); //이메일 제목

        String mailMsg = "";
        mailMsg += "<div>";
        mailMsg += "Pet병 회원 복구 이메일 인증 코드입니다";
        mailMsg += "<br>";
        mailMsg += "CODE : <strong>";
        mailMsg += verificationCode;
        mailMsg += "</strong></div>";

        message.setText(mailMsg, "utf-8", "html");
        message.setFrom(new InternetAddress("muvnelika@gamil.com", "Pet병 Admin"));
        return message;


    }

    //랜덤코드 생성
    private String makeRandomCode() {
        StringBuilder key = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) { // 6 자리 인증번호, 알파벳 섞이지 않게 한 개씩
            int index = random.nextInt(10); // 0~9까지 랜덤
            key.append(index);
        }
        return key.toString();
    }
}
