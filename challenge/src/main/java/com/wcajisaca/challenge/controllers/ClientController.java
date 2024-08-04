package com.wcajisaca.challenge.controllers;

import com.wcajisaca.challenge.constants.Commons;
import com.wcajisaca.challenge.dto.request.ClientDTO;
import com.wcajisaca.challenge.exception.RequestValidationException;
import com.wcajisaca.challenge.services.IClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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
    public void createClient(@Valid @RequestBody ClientDTO client,
                                          Errors errors) throws RequestValidationException {
        Commons.validateFieldRequest(errors);
        this.clientService.save(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable UUID id, @RequestBody ClientDTO client,
                                                  Errors errors) throws RequestValidationException {
        Commons.validateFieldRequest(errors);
        ClientDTO clientDto = clientService.findById(id);
        client.setClientId(clientDto.getClientId());
        return ResponseEntity.ok(clientService.save(client));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteClient(@PathVariable UUID id) {
        clientService.deleteById(id);
    }
}