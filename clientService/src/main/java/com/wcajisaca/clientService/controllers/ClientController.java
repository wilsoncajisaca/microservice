package com.wcajisaca.clientService.controllers;

import com.wcajisaca.clientService.dto.request.ClientDTO;
import com.wcajisaca.clientService.dto.response.BaseResponse;
import com.wcajisaca.clientService.services.IClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("clientes")
public class ClientController {
    @Autowired
    private IClientService clientService;

    @GetMapping("/kafka")
    @ResponseStatus(HttpStatus.OK)
    public void testKafka() {
        clientService.testKafka();
    }

    @GetMapping
    public ResponseEntity<?> getAllClients() {
        return ResponseEntity
                .ok(BaseResponse.builder().data(clientService.findAll()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable UUID id) {
        return ResponseEntity
                .ok(BaseResponse.builder().data(clientService.findById(id)).build());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createClient(@Valid @RequestBody ClientDTO client) {
        ClientDTO clientDTO = client.withIsNewClient(Boolean.TRUE);
        ClientDTO obj = this.clientService.save(clientDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.personId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@PathVariable UUID id, @RequestBody ClientDTO client) {
        ClientDTO clientDtoUpd = clientService.findById(id);
        ClientDTO clientDTO = client.withPersonId(clientDtoUpd.personId())
                .withIsNewClient(Boolean.FALSE);
        return ResponseEntity
                .ok(BaseResponse.builder().data(clientService.save(clientDTO)).build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteClient(@PathVariable UUID id) {
        clientService.deleteLogicById(id);
    }
}