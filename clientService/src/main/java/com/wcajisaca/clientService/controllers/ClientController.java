package com.wcajisaca.clientService.controllers;

import com.wcajisaca.clientService.constants.Commons;
import com.wcajisaca.clientService.dto.request.ClientDTO;
import com.wcajisaca.clientService.exception.RequestValidationException;
import com.wcajisaca.clientService.services.IClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
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
    public List<ClientDTO> getAllClients() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable UUID id) {
        return ResponseEntity.ok()
                .body(clientService.findById(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createClient(@Valid @RequestBody ClientDTO client,
                                          Errors errors) throws RequestValidationException {
        Commons.validateFieldRequest(errors);
        ClientDTO clientDTO = client.withIsNewClient(Boolean.TRUE);
        ClientDTO obj = this.clientService.save(clientDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.personId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable UUID id, @RequestBody ClientDTO client,
                                                  Errors errors) throws RequestValidationException {
        Commons.validateFieldRequest(errors);
        ClientDTO clientDtoUpd = clientService.findById(id);
        ClientDTO clientDTO = client.withPersonId(clientDtoUpd.personId())
                .withIsNewClient(Boolean.FALSE);
        return ResponseEntity.ok(clientService.save(clientDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteClient(@PathVariable UUID id) {
        clientService.deleteLogicById(id);
    }
}