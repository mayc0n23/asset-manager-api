package br.edu.ifpb.assetmanagerapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.assetmanagerapi.api.assembler.ServicoAssembler;
import br.edu.ifpb.assetmanagerapi.api.disassembler.ServicoDisassembler;
import br.edu.ifpb.assetmanagerapi.api.dto.input.ServicoInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.output.ServicoDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Servico;
import br.edu.ifpb.assetmanagerapi.domain.service.ServicoService;

@RestController
@RequestMapping(value = "/servicos", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class ServicoController {
	
	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private ServicoAssembler servicoAssembler;
	
	@Autowired
	private ServicoDisassembler servicoDisassembler;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public List<ServicoDTO> listar() {
		return servicoAssembler.toCollectionDTO(servicoService.listar());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{servicoId}")
	public ServicoDTO buscar(@PathVariable Long servicoId) {
		return servicoAssembler.toDTO(servicoService.buscar(servicoId));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServicoDTO cadastrar(@RequestBody @Valid ServicoInputDTO servico) {
		return servicoAssembler.toDTO(servicoService.salvar(servicoDisassembler.toDomainObject(servico)));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PutMapping("/{servicoId}")
	public ServicoDTO editar(@PathVariable Long servicoId, @RequestBody @Valid ServicoInputDTO servicoInput) {
		Servico servicoAtual = servicoService.buscar(servicoId);
		servicoDisassembler.copyToDomainObject(servicoInput, servicoAtual);
		servicoAtual.setId(servicoId);
		return servicoAssembler.toDTO(servicoService.salvar(servicoAtual));
	}
	
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/{servicoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long servicoId) {
		servicoService.deletar(servicoId);
	}
	
}