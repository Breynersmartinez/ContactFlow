package com.example.ContactFlow.service;

import com.example.ContactFlow.entity.Contact;
import com.example.ContactFlow.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private String mail;

    private final JavaMailSender mailSender;
    private final ContactRepository contactRepository;
    public EmailService(JavaMailSender mailSender, ContactRepository contactRepository) {
        this.mailSender = mailSender;
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllMessages()
    {
        return contactRepository.findAll();
    }

    public void sendAndSave(Contact form)
    {
      //  guardar en bd
        contactRepository.save(form);

        //Envio del corrreo
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setSubject(" Nuevo mensaje de tu sitio web ");
        message.setText(
                "Nombre: " + form.getName() + "\n" +
                        "Email: " + form.getEmail() + "\n" +
                        "Mensaje: " + form.getMessage()
        );

mailSender.send(message);



    }
}
