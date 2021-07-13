package br.edu.ifpb.assetmanagerapi.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

import br.edu.ifpb.assetmanagerapi.api.dto.input.EquipamentoIdInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.input.RetiradaInsumoInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.input.ServicoInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.input.SetorIdInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import br.edu.ifpb.assetmanagerapi.domain.model.EstadoConservacao;
import br.edu.ifpb.assetmanagerapi.domain.model.Insumo;
import br.edu.ifpb.assetmanagerapi.domain.model.RetiradaInsumo;
import br.edu.ifpb.assetmanagerapi.domain.model.Servico;
import br.edu.ifpb.assetmanagerapi.domain.model.Setor;
import br.edu.ifpb.assetmanagerapi.domain.model.TipoCategoria;
import br.edu.ifpb.assetmanagerapi.domain.model.TipoServico;
import br.edu.ifpb.assetmanagerapi.domain.repository.CategoriaRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.EquipamentoRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.InsumoRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.RetiradaInsumoRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.ServicoRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.SetorRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ServicoControllerTests {
	
	private final String url = "http://localhost:8081/servicos";
	
	private final String auth = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjYyNTg1NDIsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiNjZmNTUzMjQtYzE1MC00MTI2LThjODEtYzQ0NTA3ZjczYmM3IiwiY2xpZW50X2lkIjoiYXNzZXQtbWFuYWdlci1hcHAiLCJzY29wZSI6WyJXUklURSIsIlJFQUQiXX0.zO1tnmtRFscFJzHNnLReZwl_G7VqP25cs5XA2AmCR9w";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private EquipamentoRepository equipamentoRepository;
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private RetiradaInsumoRepository retiradaInsumoRepository;
	
	@Autowired
	private InsumoRepository insumoRepository;
	
	@Autowired
	private SetorRepository setorRepository;
	
	private Equipamento equipamentoCadastrado;
	
	private Categoria categoriaCadastrada;
	
	private Servico servicoCadastrado;
	
	private Setor setorCadastrado;
	
	private Insumo insumoCadastrado;
	
	@BeforeEach
	void setUp() {
		categoriaCadastrada = new Categoria();
		categoriaCadastrada.setNome("Computadores");
		categoriaCadastrada.setTipoCategoria(TipoCategoria.HARDWARE);
		categoriaRepository.save(categoriaCadastrada);
		
		equipamentoCadastrado = new Equipamento();
		equipamentoCadastrado.setDescricao("Notebook Pirata");
		equipamentoCadastrado.setBloco("A");
		equipamentoCadastrado.setSala("13");
		equipamentoCadastrado.setEstadoConservacao(EstadoConservacao.CONSERVADO);
		equipamentoCadastrado.setNumero(162);
		equipamentoCadastrado.setNumeroSerie("9883593h");
		equipamentoCadastrado.setCategoria(categoriaCadastrada);
		equipamentoRepository.save(equipamentoCadastrado);
		
		setorCadastrado = new Setor();
		setorCadastrado.setNome("CTI");
		setorCadastrado.setSigla("CTI");
		setorRepository.save(setorCadastrado);
		
		insumoCadastrado = new Insumo();
		insumoCadastrado.setNome("Resma de papel");
		insumoCadastrado.setEstante('A');
		insumoCadastrado.setPrateleira((short) 2);
		insumoCadastrado.setQuantidadeMinima(1);
		insumoCadastrado.setQuantidadeAtual(10);
		insumoCadastrado.setUnidadeDeMedida("UND");
		insumoCadastrado.setCategoria(categoriaCadastrada);
		
		insumoRepository.save(insumoCadastrado);
		
		servicoCadastrado = new Servico();
		servicoCadastrado.setTipoServico(TipoServico.CONSERTO);
		servicoCadastrado.setDataRetorno(LocalDateTime.now().plusDays(2));
		servicoCadastrado.setDataSaida(LocalDateTime.now());
		servicoCadastrado.setLinkChamadoSuap("link");
		servicoCadastrado.setEquipamento(equipamentoCadastrado);
		servicoCadastrado.setSetor(setorCadastrado);
		RetiradaInsumo retirada = new RetiradaInsumo();
		retirada.setDataSaida(LocalDateTime.now());
		retirada.setInsumo(insumoCadastrado);
		retirada.setQuantidade(2);
		retirada.setSetor(setorCadastrado);
		retirada.setLinkChamadoSuap("link");
		retiradaInsumoRepository.save(retirada);
		servicoCadastrado.getRetiradas().add(retirada);
		servicoRepository.save(servicoCadastrado);
	}
	
	@AfterEach
	void tearDown() {
		servicoRepository.deleteAll();
		retiradaInsumoRepository.deleteAll();
		insumoRepository.delete(insumoCadastrado);
		setorRepository.delete(setorCadastrado);
		equipamentoRepository.delete(equipamentoCadastrado);
		categoriaRepository.delete(categoriaCadastrada);
	}
	
	@Test
	@DisplayName("Listando os servicos")
	void listar() throws Exception {
		mockMvc.perform(get(url)
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Buscando um servico já cadastrado")
	void buscar() throws Exception {
		mockMvc.perform(get(url + "/" + servicoCadastrado.getId())
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Buscando um serviço não existente")
	void buscarServicoNaoCadastrado() throws Exception {
		mockMvc.perform(get(url + "/2000")
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Cadastrando um servico com os dados corretos e sem insumos")
	void cadastrarSemInsumos() throws Exception {
		EquipamentoIdInputDTO equipamento = new EquipamentoIdInputDTO();
		equipamento.setId(equipamentoCadastrado.getId());
		SetorIdInputDTO setor = new SetorIdInputDTO();
		setor.setId(setorCadastrado.getId());
		ServicoInputDTO servico = new ServicoInputDTO();
		servico.setEquipamento(equipamento);
		servico.setTipoServico(TipoServico.MANUTENCAO);
		servico.setSetor(setor);
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(servico)))
				.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Cadastrando um servico com os dados corretos e com insumos")
	void cadastrarComInsumos() throws Exception {
		EquipamentoIdInputDTO equipamento = new EquipamentoIdInputDTO();
		equipamento.setId(equipamentoCadastrado.getId());
		SetorIdInputDTO setor = new SetorIdInputDTO();
		setor.setId(setorCadastrado.getId());
		ServicoInputDTO servico = new ServicoInputDTO();
		servico.setEquipamento(equipamento);
		servico.setTipoServico(TipoServico.MANUTENCAO);
		servico.setSetor(setor);
		List<RetiradaInsumoInputDTO> retiradas = new ArrayList<>();
		RetiradaInsumoInputDTO retirada = new RetiradaInsumoInputDTO();
		retirada.setInsumoId(insumoCadastrado.getId());
		retirada.setQuantidade(3);
		retirada.setSetor(setor);
		retiradas.add(retirada);
		servico.setRetiradas(retiradas);
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(servico)))
				.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Cadastrando um servico com os dados corretos e com quantidade de insumo maior que a permitida")
	void cadastrarComInsumosQuantidadeInvalida() throws Exception {
		EquipamentoIdInputDTO equipamento = new EquipamentoIdInputDTO();
		equipamento.setId(equipamentoCadastrado.getId());
		SetorIdInputDTO setor = new SetorIdInputDTO();
		setor.setId(setorCadastrado.getId());
		ServicoInputDTO servico = new ServicoInputDTO();
		servico.setEquipamento(equipamento);
		servico.setTipoServico(TipoServico.MANUTENCAO);
		servico.setSetor(setor);
		List<RetiradaInsumoInputDTO> retiradas = new ArrayList<>();
		RetiradaInsumoInputDTO retirada = new RetiradaInsumoInputDTO();
		retirada.setInsumoId(insumoCadastrado.getId());
		retirada.setQuantidade(20);
		retirada.setSetor(setor);
		retiradas.add(retirada);
		servico.setRetiradas(retiradas);
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(servico)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastrando um servico faltando dados")
	void cadastrarFaltandoDados() throws Exception {
		EquipamentoIdInputDTO equipamento = new EquipamentoIdInputDTO();
		equipamento.setId(equipamentoCadastrado.getId());
		ServicoInputDTO servico = new ServicoInputDTO();
		servico.setEquipamento(equipamento);
		servico.setTipoServico(TipoServico.MANUTENCAO);
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(servico)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastrando um servico com equipamento inexistente")
	void cadastrarComEquipamentoInexistente() throws Exception {
		EquipamentoIdInputDTO equipamento = new EquipamentoIdInputDTO();
		equipamento.setId(1500L);
		SetorIdInputDTO setor = new SetorIdInputDTO();
		setor.setId(setorCadastrado.getId());
		ServicoInputDTO servico = new ServicoInputDTO();
		servico.setEquipamento(equipamento);
		servico.setTipoServico(TipoServico.MANUTENCAO);
		servico.setSetor(setor);
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(servico)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Editando um servico")
	void editar() throws Exception {
		EquipamentoIdInputDTO equipamento = new EquipamentoIdInputDTO();
		equipamento.setId(equipamentoCadastrado.getId());
		SetorIdInputDTO setor = new SetorIdInputDTO();
		setor.setId(setorCadastrado.getId());
		ServicoInputDTO servico = new ServicoInputDTO();
		servico.setEquipamento(equipamento);
		servico.setTipoServico(TipoServico.CONSERTO);
		servico.setSetor(setor);
		mockMvc.perform(put(url + "/" + servicoCadastrado.getId())
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(servico)))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("deletando um servico válido")
	void deletar() throws Exception {
		mockMvc.perform(delete(url + "/" + servicoCadastrado.getId())
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNoContent());
	}
	
	@Test
	@DisplayName("deletando um serviço inválido (inexistente)")
	void deletarServicoInvalido() throws Exception {
		mockMvc.perform(delete(url + "/16520")
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}
	
	public static String asJsonString(ServicoInputDTO servico) {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			return objectMapper.writeValueAsString(servico);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}