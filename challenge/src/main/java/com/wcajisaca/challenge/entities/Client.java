package com.wcajisaca.challenge.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Client extends Person implements Serializable {
    @Column(name = "cliente_id", nullable = false, unique = true)
    private String clientId;
    @Column(name = "contrasenia")
    private String password;
    @Column(name = "estado")
    private Boolean status;
}