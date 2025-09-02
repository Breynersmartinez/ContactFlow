package com.example.ContactFlow.controller;

import com.example.ContactFlow.entity.Contact;
import com.example.ContactFlow.service.EmailService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
public class ContactController {

    private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }


    @PostMapping
    public String sendContact(@RequestBody Contact form)
    {
        emailService.sendAndSave(form);
        return "Mensaje enviado y guardado correctamente";
    }

    @GetMapping
    public List<Contact> getMessages() {
        return emailService.getAllMessages(); // Método que devuelve todos los mensajes de la BD
    }
}
