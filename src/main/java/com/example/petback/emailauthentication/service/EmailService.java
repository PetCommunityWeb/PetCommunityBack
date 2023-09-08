package com.example.petback.emailauthentication.service;

public interface EmailService  {

void sendEmail(String email) throws Exception;

boolean verificationCodeCheck(String email, String verificationCode);
}
