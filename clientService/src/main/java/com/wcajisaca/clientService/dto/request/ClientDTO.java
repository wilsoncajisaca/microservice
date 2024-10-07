package com.wcajisaca.clientService.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcajisaca.clientService.constants.Validations;
import com.wcajisaca.clientService.contraint.validator.EcuatorianDNI;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Tolerate;

import java.util.UUID;

/**
 * DTO for clients
 * @author wcajisaca
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {
    private UUID personId;
    private String clientId;
    @NotNull(message = "El nombre no puede estar vacio")
    @Size(min = 6,max = 100, message = "El nombre contener nombre y apellido")
    @Pattern(regexp = Validations.REGEX_NAME_VALIDATION, message = "Apellido no Valido")
    private String name;
    @NotNull(message = "El genero no puede estar vacio")
    private String gender;
    @NotNull(message = "La edad no puede estar vacia")
    private Integer age;
    @EcuatorianDNI
    private String identification;
    private String address;
    private String phone;
    @NotNull(message = "La contraseña no puede estar vacia")
    @With
    private String password;
    private Boolean status;
    private Boolean isNewClient;
    @Tolerate
    public ClientDTO() {
        super();
    }
}