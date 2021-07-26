package br.edu.ifpb.assetmanagerapi.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.edu.ifpb.assetmanagerapi.api.dto.input.EntradaInsumoInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.model.EntradaInsumo;
import br.edu.ifpb.assetmanagerapi.domain.model.Insumo;
import br.edu.ifpb.assetmanagerapi.domain.model.TipoCategoria;
import br.edu.ifpb.assetmanagerapi.domain.repository.CategoriaRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.EntradaInsumoRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.InsumoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class EntradaInsumoControllerTests {

	private final String initialUrl = "http://localhost:8081/insumos/";

	private final String finalUrl = "/entradas/";

	private final String auth = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjczODA3NTksInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiZjRiMDJlNmEtYTA4ZS00NWVjLWFjOWYtNDk3OWFhZDZkYjA2IiwiY2xpZW50X2lkIjoiYXNzZXQtbWFuYWdlci1hcHAiLCJzY29wZSI6WyJXUklURSIsIlJFQUQiXX0._vWCTeUbjwnDnkLsddtpr9vmYGkIDSuZz4rAPNpbhok";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private InsumoRepository insumoRepository;
	
	@Autowired
	private EntradaInsumoRepository entradaInsumoRepository;

	private Categoria categoriaCadastrada;

	private Insumo insumoCadastrado;
	
	private EntradaInsumo entradaCadastrada;
	
	@BeforeEach
	void setUp() {
		categoriaCadastrada = new Categoria();
		categoriaCadastrada.setNome("Cabos");
		categoriaCadastrada.setTipoCategoria(TipoCategoria.HARDWARE);
		categoriaRepository.save(categoriaCadastrada);
		
		insumoCadastrado = new Insumo();
		insumoCadastrado.setNome("Resma de papel");
		insumoCadastrado.setEstante('A');
		insumoCadastrado.setPrateleira((short) 2);
		insumoCadastrado.setQuantidadeMinima(1);
		insumoCadastrado.setQuantidadeAtual(10);
		insumoCadastrado.setUnidadeDeMedida("UND");
		insumoCadastrado.setCategoria(categoriaCadastrada);
		insumoRepository.save(insumoCadastrado);
		
		entradaCadastrada = new EntradaInsumo();
		entradaCadastrada.setData(LocalDateTime.now());
		entradaCadastrada.setDataValidade(LocalDateTime.now());
		entradaCadastrada.setQuantidade(20);
		entradaCadastrada.setInsumo(insumoCadastrado);
		entradaInsumoRepository.save(entradaCadastrada);
	}
	
	@AfterEach
	void tearDown() {
		entradaInsumoRepository.deleteAll();
		insumoRepository.delete(insumoCadastrado);
		categoriaRepository.delete(categoriaCadastrada);
	}
	
	@Test
	@DisplayName("Listando as entradas de um insumo válido")
	void listar() throws Exception {
		mockMvc.perform(get(initialUrl + insumoCadastrado.getId() + finalUrl)
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Listando as entradas de um insumo inválido (error 404)")
	void listarEntradaInsumoInvalido() throws Exception {
		mockMvc.perform(get(initialUrl + new Long(82748) + finalUrl)
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Buscando os dados da entrada de um insumo válido")
	void buscar() throws Exception {
		mockMvc.perform(get(initialUrl + insumoCadastrado.getId() + finalUrl + entradaCadastrada.getId())
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Buscando os dados da entrada de um insumo inválido")
	void buscarEntradaInsumoInvalido() throws Exception {
		mockMvc.perform(get(initialUrl + new Long(6347) + finalUrl + entradaCadastrada.getId())
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Buscando os dados da entrada de um insumo válido e entrada inválida")
	void buscarEntradaInvalido() throws Exception {
		mockMvc.perform(get(initialUrl + insumoCadastrado.getId() + finalUrl + new Long(9532))
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Cadastrando uma nova entrada")
	void cadastrar() throws Exception {
		EntradaInsumoInputDTO entrada = new EntradaInsumoInputDTO();
		entrada.setData(LocalDateTime.now());
		entrada.setDataValidade(LocalDateTime.now());
		entrada.setQuantidade(12);
		mockMvc.perform(post(initialUrl + insumoCadastrado.getId() + finalUrl)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(entrada)))
				.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Cadastrando uma nova entrada com dados faltando (error 400)")
	void cadastrarFaltandoDados() throws Exception {
		EntradaInsumoInputDTO entrada = new EntradaInsumoInputDTO();
		entrada.setData(LocalDateTime.now());
		entrada.setDataValidade(LocalDateTime.now());
		mockMvc.perform(post(initialUrl + insumoCadastrado.getId() + finalUrl)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(entrada)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastrando uma nova entrada em um insumo que n existe (error 404)")
	void cadastrarEmInsumoInexistente() throws Exception {
		EntradaInsumoInputDTO entrada = new EntradaInsumoInputDTO();
		entrada.setData(LocalDateTime.now());
		entrada.setDataValidade(LocalDateTime.now());
		entrada.setQuantidade(12);
		mockMvc.perform(post(initialUrl + new Long(5745) + finalUrl)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(entrada)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Deletando uma entrada cadastrada")
	void deletar() throws Exception {
		mockMvc.perform(delete(initialUrl + insumoCadastrado.getId() + finalUrl + entradaCadastrada.getId())
				.header("Authorization", auth))
				.andExpect(status().isNoContent());
	}
	
	@Test
	@DisplayName("Deletando uma entrada inexistente (error 404)")
	void deletarEntradaInexistente() throws Exception {
		mockMvc.perform(delete(initialUrl + insumoCadastrado.getId() + finalUrl + new Long(8356))
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Deletando uma entrada em um insumo inexistente (error 404)")
	void deletarEntradaInsumoInexistente() throws Exception {
		mockMvc.perform(delete(initialUrl + new Long(8356) + finalUrl + entradaCadastrada.getId())
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	public static String asJsonString(EntradaInsumoInputDTO entrada) {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			return objectMapper.writeValueAsString(entrada);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	
}