package com.example.ContactFlow.entity;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Table (name = "CONTACTOS")
public class Contact {

    @Id
    @GeneratedValue(strategy =   GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column (name = "NOMBRE")
    private String name;
    @Column (name = "EMAIL")
    private String email;
    @Column (name = "MENSAJE")
    private String message;

    public Contact()
    {

    }

    public Contact(String name, Integer id, String email, String message) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.message = message;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
