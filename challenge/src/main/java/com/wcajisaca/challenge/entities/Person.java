package com.wcajisaca.challenge.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "persona", schema = "client_person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public class Person implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "pk_persona",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "persona_id",updatable = false, nullable = false)
    private UUID personId;

    @Column(name = "nombre")
    private String name;
    @Column(name = "genero")
    private String gender;
    @Column(name = "edad")
    private Integer age;
    @Column(name = "identificacion")
    private String identification;
    @Column(name = "direccion")
    private String address;
    @Column(name = "telefono")
    private String phone;
}
