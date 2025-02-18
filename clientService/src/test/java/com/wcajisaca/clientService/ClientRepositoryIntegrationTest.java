package com.wcajisaca.clientService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcajisaca.clientService.dto.request.ClientDTO;
import com.wcajisaca.clientService.entities.Client;
import com.wcajisaca.clientService.repositories.IClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.wcajisaca.clientService.dto.request.ClientDTO.clientDTOForTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClientRepositoryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IClienteRepository clientRepository;
    ClientDTO clientDTO;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();
        clientDTO = clientDTOForTest("wcajisaca");
    }

    @Test
    void testCreateClient() throws Exception {
        // Realizar la solicitud POST al endpoint de creación
        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(clientDTO)))
                .andExpect(status().isCreated());

        List<Client> clients = clientRepository.findAll();
        assertEquals(1, clients.size());
        Client createdClient = clients.get(0);
        assertEquals("wcajisaca", createdClient.getClientId());
        assertEquals("Wilson Cajisaca", createdClient.getName());
        assertEquals("0106146137", createdClient.getIdentification());
        assertEquals("Masculino", createdClient.getGender());
        assertEquals(30, createdClient.getAge());
        assertEquals("Cuenca", createdClient.getAddress());
        assertEquals("0963521463", createdClient.getPhone());
        assertEquals(Boolean.TRUE, createdClient.getStatus());
        assertTrue(createdClient.getStatus());
    }
}
