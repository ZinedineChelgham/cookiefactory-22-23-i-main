package fr.unice.services;


import fr.unice.enums.MailMessageType;
import fr.unice.enums.MailSubjectsType;
import fr.unice.model.Bill;
import org.springframework.stereotype.Component;

@Component
public class BasicMailService implements MailService {

    @Override
    public String sendMail(String email, MailSubjectsType subject, MailMessageType message, Bill bill) {
        return "Sending mail to " + email + " with subject " + subject + " and message " + message + " and bill " + bill;
    }
}
