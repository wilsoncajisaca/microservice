package com.wcajisaca.clientService.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcajisaca.clientService.constants.Validations;
import com.wcajisaca.clientService.contraint.validator.EcuatorianDNI;
import jakarta.validation.constraints.NotBlank;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClientDTO (
        @With
        UUID personId,
        @NotBlank(message = "El nombre de usuario no puede estar vacio")
        String clientId,
        @NotNull(message = "El nombre no puede estar vacio")
        @Size(min = 6,max = 100, message = "El nombre contener nombre y apellido")
        @Pattern(regexp = Validations.REGEX_NAME_VALIDATION, message = "Apellido no Valido")
        String name,
        @NotNull(message = "El genero no puede estar vacio")
        String gender,
        @NotNull(message = "La edad no puede estar vacia")
        Integer age,
        @EcuatorianDNI
        String identification,
        String address,
        String phone,
        @NotNull(message = "La contraseña no puede estar vacia")
        @NotBlank(message = "La contraseña no puede estar vacia")
        @With
        String password,
        Boolean status,
        @With
        Boolean isNewClient
){
    public static ClientDTO clientDTOForTest(String clientId) {
        return new ClientDTO(null, clientId, "Wilson Cajisaca", "Masculino",30,
                "0106146137", "Cuenca", "0963521463", "12345", Boolean.TRUE, null);
    }
}