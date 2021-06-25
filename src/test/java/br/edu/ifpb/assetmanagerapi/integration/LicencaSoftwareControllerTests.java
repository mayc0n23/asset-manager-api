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

import br.edu.ifpb.assetmanagerapi.api.dto.input.AssociacaoLicencaSoftwareInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.input.LicencaSoftwareInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import br.edu.ifpb.assetmanagerapi.domain.model.EstadoConservacao;
import br.edu.ifpb.assetmanagerapi.domain.model.LicencaSoftware;
import br.edu.ifpb.assetmanagerapi.domain.model.TipoCategoria;
import br.edu.ifpb.assetmanagerapi.domain.repository.CategoriaRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.EquipamentoRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.LicencaSoftwareRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class LicencaSoftwareControllerTests {
	
	private final String url = "http://localhost:8081/licencas-software";
	
	private final String auth = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MjQ1MzEwNzYsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiNzUyMGVmNWUtNjQ1Zi00YjhjLWEyNTYtYWQ5OWY3MTgxODBhIiwiY2xpZW50X2lkIjoiYXNzZXQtbWFuYWdlci1hcHAiLCJzY29wZSI6WyJXUklURSIsIlJFQUQiXX0.chSU45jtqS6fQXi4d_BAlF5NEMdpjQBEGSF0GwvwYLw";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private EquipamentoRepository equipamentoRepository;
	
	@Autowired
	private LicencaSoftwareRepository licencaSoftwareRepository;
	
	private LicencaSoftware licencaCadastrada;
	
	private Equipamento equipamentoCadastrado;
	
	private Equipamento equipamentoHardware;
	
	private Equipamento equipamentoMobilia;
	
	private Categoria categoria;
	
	private Categoria categoriaMobiliario;
	
	private Categoria categoriaHardware;
	
	@BeforeEach
	void setUp() {
		categoria = new Categoria();
		categoria.setNome("Antivirus");
		categoria.setTipoCategoria(TipoCategoria.SOFTWARE);
		categoriaRepository.save(categoria);
		
		categoriaMobiliario = new Categoria();
		categoriaMobiliario.setNome("Antivirus");
		categoriaMobiliario.setTipoCategoria(TipoCategoria.MOBILIARIO);
		categoriaRepository.save(categoriaMobiliario);
		
		categoriaHardware = new Categoria();
		categoriaHardware.setNome("Notebooks diversos");
		categoriaHardware.setTipoCategoria(TipoCategoria.HARDWARE);
		categoriaRepository.save(categoriaHardware);
		
		licencaCadastrada = new LicencaSoftware();
		licencaCadastrada.setCategoria(categoria);
		licencaCadastrada.setChaveAtivacao("UHLK-08953");
		licencaCadastrada.setMaximoAtivacoes(5);
		licencaCadastrada.setNumero(541);
		licencaCadastrada.setQuantidadeUsada(1);
		licencaCadastrada.setSoftware("Avast pirata");
		licencaSoftwareRepository.save(licencaCadastrada);
		
		equipamentoCadastrado = new Equipamento();
		equipamentoCadastrado.setDescricao("Notebook Pirata");
		equipamentoCadastrado.setBloco("A");
		equipamentoCadastrado.setSala("13");
		equipamentoCadastrado.setEstadoConservacao(EstadoConservacao.CONSERVADO);
		equipamentoCadastrado.setNumero(162);
		equipamentoCadastrado.setNumeroSerie("9883593h");
		equipamentoCadastrado.setCategoria(categoria);
		equipamentoRepository.save(equipamentoCadastrado);
		
		equipamentoMobilia = new Equipamento();
		equipamentoMobilia.setDescricao("Cadeira estofada");
		equipamentoMobilia.setBloco("A");
		equipamentoMobilia.setSala("13");
		equipamentoMobilia.setEstadoConservacao(EstadoConservacao.CONSERVADO);
		equipamentoMobilia.setNumero(182);
		equipamentoMobilia.setNumeroSerie("98a83593h");
		equipamentoMobilia.setCategoria(categoriaMobiliario);
		equipamentoRepository.save(equipamentoMobilia);
		
		equipamentoHardware = new Equipamento();
		equipamentoHardware.setDescricao("Notebook HPP");
		equipamentoHardware.setBloco("A");
		equipamentoHardware.setSala("13");
		equipamentoHardware.setEstadoConservacao(EstadoConservacao.CONSERVADO);
		equipamentoHardware.setNumero(182);
		equipamentoHardware.setNumeroSerie("98a83593h");
		equipamentoHardware.setCategoria(categoriaHardware);
		equipamentoRepository.save(equipamentoHardware);
	}
	
	@AfterEach
	void tearDown() {
		licencaSoftwareRepository.deleteAll();
		equipamentoRepository.delete(equipamentoCadastrado);
		equipamentoRepository.delete(equipamentoMobilia);
		equipamentoRepository.delete(equipamentoHardware);
		categoriaRepository.delete(categoria);
		categoriaRepository.delete(categoriaMobiliario);
		categoriaRepository.delete(categoriaHardware);
	}
	
	
	@Test
	@DisplayName("Listando as licencas")
	void listar() throws Exception {
		mockMvc.perform(get(url)
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Buscando uma licença já cadastrada")
	void buscar() throws Exception {
		mockMvc.perform(get(url + "/" + licencaCadastrada.getId())
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Buscando uma licença não existente")
	void buscarLicencaNaoCadastrada() throws Exception {
		mockMvc.perform(get(url + "/2000")
				.header("Authorization", auth))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Cadastrando uma licença")
	void cadastrar() throws Exception {
		LicencaSoftwareInputDTO licenca = new LicencaSoftwareInputDTO();
		licenca.setCategoriaId(categoria.getId());
		licenca.setChaveAtivacao("UHJL94864");
		licenca.setMaximoAtivacoes(4);
		licenca.setNumero(521);
		licenca.setSoftware("AVG");
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(licenca)))
				.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Cadastrando uma licença com o número maximo de ativações igual a 0")
	void cadastrarComMaximoAtivacoesInvalido() throws Exception {
		LicencaSoftwareInputDTO licenca = new LicencaSoftwareInputDTO();
		licenca.setCategoriaId(categoria.getId());
		licenca.setChaveAtivacao("UHJL94864");
		licenca.setMaximoAtivacoes(0);
		licenca.setNumero(521);
		licenca.setSoftware("AVG");
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(licenca)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastrando uma licença sem informar dados obrigatórios")
	void cadastrarComDadosFaltando() throws Exception {
		LicencaSoftwareInputDTO licenca = new LicencaSoftwareInputDTO();
		licenca.setCategoriaId(categoria.getId());
		licenca.setMaximoAtivacoes(0);
		licenca.setNumero(521);
		licenca.setSoftware("AVG");
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(licenca)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastrando uma licença com chave de ativação existente")
	void cadastrarComMesmaChaveAtivacao() throws Exception {
		LicencaSoftwareInputDTO licenca = new LicencaSoftwareInputDTO();
		licenca.setCategoriaId(categoria.getId());
		licenca.setChaveAtivacao(licencaCadastrada.getChaveAtivacao());
		licenca.setMaximoAtivacoes(0);
		licenca.setNumero(521);
		licenca.setSoftware("AVG");
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(licenca)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Cadastrando uma licença com uma categoria que não existe")
	void cadastrarComCategoriaInexistente() throws Exception {
		LicencaSoftwareInputDTO licenca = new LicencaSoftwareInputDTO();
		licenca.setCategoriaId(new Long(1000));
		licenca.setChaveAtivacao("UHJL94864");
		licenca.setMaximoAtivacoes(0);
		licenca.setNumero(521);
		licenca.setSoftware("AVG");
		mockMvc.perform(post(url)
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(licenca)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Editando uma licença de software")
	void editar() throws Exception {
		LicencaSoftwareInputDTO licenca = new LicencaSoftwareInputDTO();
		licenca.setCategoriaId(categoria.getId());
		licenca.setChaveAtivacao("UHJL94864d5sd2");
		licenca.setMaximoAtivacoes(5);
		licenca.setNumero(521);
		licenca.setSoftware("Windows Defender");
		mockMvc.perform(put(url + "/" + licencaCadastrada.getId())
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(licenca)))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Associando uma licença a um equipamento")
	void associar() throws Exception {
		AssociacaoLicencaSoftwareInputDTO associacao = new AssociacaoLicencaSoftwareInputDTO();
		associacao.setEquipamentoId(equipamentoHardware.getId());
		mockMvc.perform(put(url + "/" + licencaCadastrada.getId() + "/associar")
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(associacao)))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Associando uma licença inexistente a um equipamento")
	void associarComLicencaInexistente() throws Exception {
		AssociacaoLicencaSoftwareInputDTO associacao = new AssociacaoLicencaSoftwareInputDTO();
		associacao.setEquipamentoId(equipamentoCadastrado.getId());
		mockMvc.perform(put(url + "/" + "5558985" + "/associar")
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(associacao)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Associando uma licença a um equipamento inexistente")
	void associarComEquipamentoInexistente() throws Exception {
		AssociacaoLicencaSoftwareInputDTO associacao = new AssociacaoLicencaSoftwareInputDTO();
		associacao.setEquipamentoId(new Long(3651));
		mockMvc.perform(put(url + "/" + licencaCadastrada.getId() + "/associar")
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(associacao)))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Associando uma licença a um equipamento que não é da categoria de hardware")
	void associarComEquipamentoQueNaoEhHardware() throws Exception {
		AssociacaoLicencaSoftwareInputDTO associacao = new AssociacaoLicencaSoftwareInputDTO();
		associacao.setEquipamentoId(equipamentoMobilia.getId());
		mockMvc.perform(put(url + "/" + licencaCadastrada.getId() + "/associar")
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(associacao)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Desassociando uma licença de um equipamento")
	void desassociar() throws Exception {
		AssociacaoLicencaSoftwareInputDTO associacao = new AssociacaoLicencaSoftwareInputDTO();
		associacao.setEquipamentoId(equipamentoCadastrado.getId());
		mockMvc.perform(put(url + "/" + licencaCadastrada.getId() + "/desassociar")
				.header("Authorization", auth)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(associacao)))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Listando os equipamentos que a licença de software está associada")
	void listarAssociacoesDeUmaLicenca() throws Exception {
		mockMvc.perform(get(url + "/" + licencaCadastrada.getId() + "/associacoes")
				.header("Authorization", auth))
				.andExpect(status().isOk());
	}
	
	public static String asJsonString(LicencaSoftwareInputDTO licenca) {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(licenca);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public static String asJsonString(AssociacaoLicencaSoftwareInputDTO associacao) {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(associacao);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
}