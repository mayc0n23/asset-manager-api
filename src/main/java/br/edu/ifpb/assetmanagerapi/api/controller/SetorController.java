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

import br.edu.ifpb.assetmanagerapi.api.assembler.SetorAssembler;
import br.edu.ifpb.assetmanagerapi.api.disassembler.SetorDisassembler;
import br.edu.ifpb.assetmanagerapi.api.dto.input.SetorInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.output.SetorDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Setor;
import br.edu.ifpb.assetmanagerapi.domain.service.SetorService;

@RestController
@RequestMapping(value = "/setores", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class SetorController {

	@Autowired
	private SetorService setorService;
	
	@Autowired
	private SetorAssembler setorAssembler;
	
	@Autowired
	private SetorDisassembler setorDisassembler;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public List<SetorDTO> listar() {
		return setorAssembler.toCollectionDTO(setorService.listar());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{setorId}")
	public SetorDTO buscar(@PathVariable Long setorId) {
		return setorAssembler.toDTO(setorService.buscar(setorId));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SetorDTO cadastrar(@RequestBody @Valid SetorInputDTO setorInput) {
		return setorAssembler.toDTO(setorService.salvar(setorDisassembler.toDomainObject(setorInput)));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PutMapping("/{setorId}")
	public SetorDTO editar(@PathVariable Long setorId, @RequestBody @Valid SetorInputDTO setorInput) {
		Setor setorAtual = setorService.buscar(setorId);
		setorDisassembler.copyToDomainObject(setorInput, setorAtual);
		setorAtual.setId(setorId);
		return setorAssembler.toDTO(setorService.salvar(setorAtual));
	}
	
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/{setorId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long setorId) {
		setorService.deletar(setorId);
	}
	
}