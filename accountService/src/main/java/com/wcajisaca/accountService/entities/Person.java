package com.wcajisaca.accountService.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "persona", schema = "client_person")
@Immutable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -7879092307175797980L;
    @Id
    @Column(name = "persona_id")
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

