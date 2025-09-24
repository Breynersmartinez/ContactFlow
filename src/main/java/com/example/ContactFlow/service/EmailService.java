package com.example.ContactFlow.service;

import com.example.ContactFlow.entity.Contact;
import com.example.ContactFlow.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
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
    private SimpleMailMessage templateMessage;


    public EmailService(JavaMailSender mailSender, ContactRepository contactRepository, SimpleMailMessage templateMessage) {
        this.mailSender = mailSender;
        this.templateMessage = templateMessage;
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

        //Envio del corrreo de confirmacion al usuario que envia el mensaje al propietario del sitio
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(form.getEmail());
        msg.setText(
                "Hola " + form.getName() + ",\n\n" +
                        "Muchas gracias por tu mensaje. Te responderemos lo más pronto posible.\n\n" +
                        "Tu mensaje fue:\n" + form.getMessage() + "\n\n" +
                        "También puedes comunicarte con nosotros a través de nuestro WhatsApp empresarial: https://wa.me/573103212753"
        );
        try {
            this.mailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }


        //Enviar Email al propietario del sitio, para informar sobre un nuevo  comentario registrado
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
