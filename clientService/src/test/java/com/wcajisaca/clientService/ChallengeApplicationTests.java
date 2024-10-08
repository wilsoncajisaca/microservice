package com.wcajisaca.clientService;

import com.wcajisaca.clientService.controllers.ClientController;
import com.wcajisaca.clientService.dto.request.ClientDTO;
import com.wcajisaca.clientService.entities.Client;
import com.wcajisaca.clientService.services.IClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(ClientController.class)
@ActiveProfiles("test")
class ChallengeApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IClientService clientService;
	private UUID clientId;

	@BeforeEach
	void setUp() {
		clientId = UUID.randomUUID();
	}

	@Test
	void testGetClientById() throws Exception {
		ClientDTO clientDTO = ClientDTO.builder()
				.clientId(clientId.toString())
				.name("Wilson Cajisaca")
				.identification("123456789")
				.gender("Masculino")
				.phone("0963521463")
				.address("Cuenca")
				.age(30)
				.build();
		when(clientService.findById(clientId)).thenReturn(clientDTO);
		mockMvc.perform(get("/clientes/{id}", clientId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.clientId").value(clientId.toString()))
				.andExpect(jsonPath("$.name").value("Wilson Cajisaca"))
				.andExpect(jsonPath("$.gender").value("Masculino"))
				.andExpect(jsonPath("$.identification").value("123456789"))
				.andExpect(jsonPath("$.phone").value("0963521463"))
				.andExpect(jsonPath("$.address").value("Cuenca"))
				.andExpect(jsonPath("$.age").value(30));
	}

	@Test
	void testDeleteClient() throws Exception {
		mockMvc.perform(delete("/clientes/{id}", clientId))
				.andExpect(status().isOk());

		verify(clientService).deleteById(clientId);
	}

	@Test
	void testClientEntity() {
		Client client = new Client();

		client.setClientId("wcajisaca");
		client.setPassword("password");
		client.setStatus(true);
		client.setName("Wilson Cajisaca");
		client.setGender("Masculino");
		client.setAge(30);
		client.setIdentification("0106254137");
		client.setAddress("Cuenca");
		client.setPhone("0963521463");

		assertThat(client.getClientId()).isEqualTo("wcajisaca");
		assertThat(client.getPassword()).isEqualTo("password");
		assertThat(client.getStatus()).isTrue();
		assertThat(client.getName()).isEqualTo("Wilson Cajisaca");
		assertThat(client.getGender()).isEqualTo("Masculino");
		assertThat(client.getAge()).isEqualTo(30);
		assertThat(client.getIdentification()).isEqualTo("0106254137");
		assertThat(client.getAddress()).isEqualTo("Cuenca");
		assertThat(client.getPhone()).isEqualTo("0963521463");
	}
}