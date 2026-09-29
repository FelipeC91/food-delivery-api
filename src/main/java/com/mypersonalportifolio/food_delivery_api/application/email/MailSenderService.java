package com.mypersonalportifolio.food_delivery_api.application.email;

public interface MailSenderService {

    void sendMail(MailMessageSource messageSource);
}
