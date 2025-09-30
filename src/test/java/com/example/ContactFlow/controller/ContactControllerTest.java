package com.example.ContactFlow.controller;

import com.example.ContactFlow.entity.Contact;
import com.example.ContactFlow.repository.ContactRepository;
import com.example.ContactFlow.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Evitamos correo real y BD real
    @MockBean
    private EmailService emailService;

    @MockBean
    private ContactRepository contactRepository;

    @Test
    void deberiaEnviarContactoYResponderOk() throws Exception {
        String json = """
        {
          "name": "Breiner",
          "email": "breinersmartinezmunoz@gmail.com",
          "message": "Hola, quiero más info"
        }
        """;

        mockMvc.perform(post("/api/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Mensaje enviado y guardado correctamente"));
    }

    @Test
    void deberiaListarContactos() throws Exception {
        Contact contacto = new Contact();
        contacto.setName("Gloria");
        contacto.setEmail("gloriamparomunozsoscue@gmail.com");
        contacto.setMessage("Prueba de mensaje");

        when(emailService.getAllMessages()).thenReturn(List.of(contacto));

        mockMvc.perform(get("/api/contact"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gloria"))
                .andExpect(jsonPath("$[0].email").value("gloriamparomunozsoscue@gmail.com"));
    }
}
