package com.wcajisaca.clientService.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class Client extends Person implements Serializable {
    @Column(name = "cliente_id", nullable = false, unique = true)
    private String clientId;
    @Column(name = "contrasenia")
    private String password;
}