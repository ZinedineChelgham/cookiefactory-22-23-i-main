package fr.unice.services;


import fr.unice.model.Bill;
import fr.unice.enums.MailMessageType;
import fr.unice.enums.MailSubjectsType;

/**
 * Contains all the functionality to send mails to customers
 */
public interface MailService {

    /**
     * Sends a mail to the given mail with the given content
     * @param email target mail address
     * @param subject content of the subject line in the mail
     * @param message message of the mail
     * @param bill bill to put in the attachments
     */
    String sendMail(String email, MailSubjectsType subject, MailMessageType message, Bill bill);
}



