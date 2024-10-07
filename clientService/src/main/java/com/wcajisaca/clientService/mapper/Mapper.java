package com.wcajisaca.clientService.mapper;


import com.wcajisaca.clientService.dto.request.ClientDTO;
import com.wcajisaca.clientService.entities.Client;

/**
 * Mapper
 * @author wcajisaca
 */
public class Mapper {

    public static Client convertToClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setPersonId(clientDTO.getPersonId());
        client.setClientId(clientDTO.getClientId());
        client.setName(clientDTO.getName());
        client.setGender(clientDTO.getGender());
        client.setAge(clientDTO.getAge());
        client.setIdentification(clientDTO.getIdentification());
        client.setAddress(clientDTO.getAddress());
        client.setPhone(clientDTO.getPhone());
        client.setPassword(clientDTO.getPassword());
        client.setStatus(clientDTO.getStatus());
        return client;
    }

    public static ClientDTO convertToDTO(Client client) {
        return ClientDTO.builder()
                .personId(client.getPersonId())
                .name(client.getName())
                .gender(client.getGender())
                .age(client.getAge())
                .identification(client.getIdentification())
                .address(client.getAddress())
                .phone(client.getPhone())
                .status(client.getStatus())
                .build();
    }

}
