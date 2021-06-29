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

import br.edu.ifpb.assetmanagerapi.api.assembler.EmprestimoAssembler;
import br.edu.ifpb.assetmanagerapi.api.disassembler.EmprestimoDisassembler;
import br.edu.ifpb.assetmanagerapi.api.dto.input.EmprestimoInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.output.EmprestimoDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Emprestimo;
import br.edu.ifpb.assetmanagerapi.domain.service.EmprestimoService;

@RestController
@RequestMapping(value = "/emprestimos", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class EmprestimoController {
	
	@Autowired
	private EmprestimoService emprestimoService;
	
	@Autowired
	private EmprestimoAssembler emprestimoAssembler;
	
	@Autowired
	private EmprestimoDisassembler emprestimoDisassembler;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public List<EmprestimoDTO> listar() {
		return emprestimoAssembler.toCollectionDTO(emprestimoService.listar());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{id}")
	public EmprestimoDTO buscar(@PathVariable Long id) {
		return emprestimoAssembler.toDTO(emprestimoService.buscar(id));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EmprestimoDTO cadastrar(@RequestBody @Valid EmprestimoInputDTO emprestimoInput) {
		return emprestimoAssembler.toDTO(emprestimoService.salvar(emprestimoDisassembler.toDomainObject(emprestimoInput)));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PutMapping("/{id}")
	public EmprestimoDTO editar(@PathVariable Long id, @RequestBody @Valid EmprestimoInputDTO emprestimoInput) {
		Emprestimo emprestimoAtual = emprestimoService.buscar(id);
		emprestimoDisassembler.copyToDomainObject(emprestimoInput, emprestimoAtual);
		emprestimoAtual.setId(id);
		return emprestimoAssembler.toDTO(emprestimoService.salvar(emprestimoAtual));
	}
	
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		emprestimoService.deletar(id);
	}
	
}