package com.wcajisaca.clientService.mapper;


import com.wcajisaca.clientService.dto.request.ClientDTO;
import com.wcajisaca.clientService.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper
 * @author wcajisaca
 */
@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    // Convertir de un record ClientDTO a una entidad Client
    Client toEntity(ClientDTO clientDTO);

    // Convertir de una entidad Client a un record ClientDTO
    ClientDTO toDTO(Client client);
}