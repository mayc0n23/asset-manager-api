package br.edu.ifpb.assetmanagerapi.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ifpb.assetmanagerapi.api.dto.input.SetorInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Setor;
import br.edu.ifpb.assetmanagerapi.domain.repository.SetorRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class SetorControllerTests {
	
	private final String url = "http://localhost:8081/setores";
	
	private final String auth = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjQ2NzIxNjYsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiNDI3ZDc2ZmYtNDQ5MC00MDc1LTlkNmQtNDE0Nzg5NWRiNGY3IiwiY2xpZW50X2lkIjoiYXNzZXQtbWFuYWdlci1hcHAiLCJzY29wZSI6WyJXUklURSIsIlJFQUQiXX0.buDPf4Wtq8LOBpNtdJiddQQjNxLpe_-5vY6xuiR3vmU";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private SetorRepository setorRepository;
	
	private Setor setorCadastrado;
	
	@BeforeEach
	void setUp() {
		setorCadastrado = new Setor();
		setorCadastrado.setNome("Biblioteca");
		setorCadastrado.setSigla("BBT");
		setorRepository.save(setorCadastrado);
	}
	
	@AfterEach
	void tearDown() {
		setorRepository.deleteAll();
	}
	
	@Test
	@DisplayName("Listando os setores")
	void listar() throws Exception {
		mockMvc.perform(get(url)
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Buscando um setor já cadastrado")
	void buscar() throws Exception {
		mockMvc.perform(get(url + "/" + setorCadastrado.getId())
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Buscando um setor não existente")
	void buscarSetorNaoCadastrado() throws Exception {
		mockMvc.perform(get(url + "/2000")
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Cadastrando um novo setor")
	void cadastrar() throws Exception {
		SetorInputDTO setor = new SetorInputDTO();
		setor.setNome("Restaurante");
		setor.setSigla("RS");
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(setor)))
				.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Cadastrando um novo setor sem informar o nome")
	void cadastrarComDadosFaltando() throws Exception {
		SetorInputDTO setor = new SetorInputDTO();
		setor.setSigla("RS");
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(setor)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastrando um setor já existente")
	void cadastrarSetorExistente() throws Exception {
		SetorInputDTO setor = new SetorInputDTO();
		setor.setNome("Biblioteca");
		setor.setSigla("BBT");
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(setor)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Editando setor")
	void editar() throws Exception {
		SetorInputDTO setor = new SetorInputDTO();
		setor.setNome("Restaurante");
		setor.setSigla("RS");
		mockMvc.perform(put(url + "/" + setorCadastrado.getId())
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(setor)))
				.andExpect(status().isOk());
	}
	
	public static String asJsonString(SetorInputDTO setor) {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(setor);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
}