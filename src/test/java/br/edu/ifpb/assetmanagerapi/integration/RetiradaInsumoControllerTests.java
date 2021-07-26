package br.edu.ifpb.assetmanagerapi.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.edu.ifpb.assetmanagerapi.api.dto.input.RetiradaInsumoInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.input.SetorIdInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.model.Insumo;
import br.edu.ifpb.assetmanagerapi.domain.model.RetiradaInsumo;
import br.edu.ifpb.assetmanagerapi.domain.model.Setor;
import br.edu.ifpb.assetmanagerapi.domain.model.TipoCategoria;
import br.edu.ifpb.assetmanagerapi.domain.repository.CategoriaRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.InsumoRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.RetiradaInsumoRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.SetorRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RetiradaInsumoControllerTests {
	
	private final String initialUrl = "http://localhost:8081/insumos/";

	private final String finalUrl = "/retiradas/";
	
	private final String auth = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjczODA3NTksInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiZjRiMDJlNmEtYTA4ZS00NWVjLWFjOWYtNDk3OWFhZDZkYjA2IiwiY2xpZW50X2lkIjoiYXNzZXQtbWFuYWdlci1hcHAiLCJzY29wZSI6WyJXUklURSIsIlJFQUQiXX0._vWCTeUbjwnDnkLsddtpr9vmYGkIDSuZz4rAPNpbhok";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private InsumoRepository insumoRepository;
	
	@Autowired
	private RetiradaInsumoRepository retiradaInsumoRepository;
	
	@Autowired
	private SetorRepository setorRepository;
	
	private Categoria categoriaCadastrada;

	private Insumo insumoCadastrado;
	
	private RetiradaInsumo retiradaCadastrada;
	
	private Setor setorCadastrado;
	
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
		
		setorCadastrado = new Setor();
		setorCadastrado.setNome("Biblioteca");
		setorCadastrado.setSigla("BBT");
		setorRepository.save(setorCadastrado);
		
		retiradaCadastrada = new RetiradaInsumo();
		retiradaCadastrada.setSetor(setorCadastrado);
		retiradaCadastrada.setInsumo(insumoCadastrado);
		retiradaCadastrada.setQuantidade(3);
		retiradaInsumoRepository.save(retiradaCadastrada);
	}
	
	@AfterEach
	void tearDown() {
		retiradaInsumoRepository.deleteAll();
		setorRepository.delete(setorCadastrado);
		insumoRepository.delete(insumoCadastrado);
		categoriaRepository.delete(categoriaCadastrada);
	}
	
	@Test
	@DisplayName("Listando as retiradas de um insumo válido")
	void listar() throws Exception {
		mockMvc.perform(get(initialUrl + insumoCadastrado.getId() + finalUrl)
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Listando as retiradas de um insumo inválido (error 404)")
	void listarRetiradaInsumoInvalido() throws Exception {
		mockMvc.perform(get(initialUrl + new Long(82748) + finalUrl)
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Buscando os dados da retirada de um insumo válido")
	void buscar() throws Exception {
		mockMvc.perform(get(initialUrl + insumoCadastrado.getId() + finalUrl + retiradaCadastrada.getId())
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Buscando os dados da retirada de um insumo inválido")
	void buscarRetiradaInsumoInvalido() throws Exception {
		mockMvc.perform(get(initialUrl + new Long(6347) + finalUrl + retiradaCadastrada.getId())
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Buscando os dados da retirada de um insumo válido e retirada inválida")
	void buscarEntradaInvalido() throws Exception {
		mockMvc.perform(get(initialUrl + insumoCadastrado.getId() + finalUrl + new Long(9532))
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Cadastrando uma nova retirada")
	void cadastrar() throws Exception {
		RetiradaInsumoInputDTO retirada = new RetiradaInsumoInputDTO();
		SetorIdInputDTO setor = new SetorIdInputDTO();
		setor.setId(setorCadastrado.getId());
		retirada.setSetor(setor);
		retirada.setQuantidade(2);
		mockMvc.perform(post(initialUrl + insumoCadastrado.getId() + finalUrl)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(retirada)))
				.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Cadastrando uma nova retirada com dados faltando (error 400)")
	void cadastrarFaltandoDados() throws Exception {
		RetiradaInsumoInputDTO retirada = new RetiradaInsumoInputDTO();
		SetorIdInputDTO setor = new SetorIdInputDTO();
		setor.setId(setorCadastrado.getId());
		retirada.setSetor(setor);
		mockMvc.perform(post(initialUrl + insumoCadastrado.getId() + finalUrl)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(retirada)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastrando uma nova retirada em um insumo que n existe (error 404)")
	void cadastrarEmInsumoInexistente() throws Exception {
		RetiradaInsumoInputDTO retirada = new RetiradaInsumoInputDTO();
		SetorIdInputDTO setor = new SetorIdInputDTO();
		setor.setId(setorCadastrado.getId());
		retirada.setSetor(setor);
		retirada.setQuantidade(2);
		mockMvc.perform(post(initialUrl + new Long(5745) + finalUrl)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(retirada)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Cadastrando uma nova retirada em um setor que n existe (error 404)")
	void cadastrarEmSetorInexistente() throws Exception {
		RetiradaInsumoInputDTO retirada = new RetiradaInsumoInputDTO();
		SetorIdInputDTO setor = new SetorIdInputDTO();
		setor.setId(new Long(6375));
		retirada.setSetor(setor);
		retirada.setQuantidade(2);
		mockMvc.perform(post(initialUrl + insumoCadastrado.getId() + finalUrl)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(retirada)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Deletando uma retirada cadastrada")
	void deletar() throws Exception {
		mockMvc.perform(delete(initialUrl + insumoCadastrado.getId() + finalUrl + retiradaCadastrada.getId())
				.header("Authorization", auth))
				.andExpect(status().isNoContent());
	}
	
	@Test
	@DisplayName("Deletando uma retirada inexistente (error 404)")
	void deletarRetiradaInexistente() throws Exception {
		mockMvc.perform(delete(initialUrl + insumoCadastrado.getId() + finalUrl + new Long(8356))
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Deletando uma retirada em um insumo inexistente (error 404)")
	void deletarRetiradaInsumoInexistente() throws Exception {
		mockMvc.perform(delete(initialUrl + new Long(8356) + finalUrl + retiradaCadastrada.getId())
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	public static String asJsonString(RetiradaInsumoInputDTO retirada) {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			return objectMapper.writeValueAsString(retirada);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
}