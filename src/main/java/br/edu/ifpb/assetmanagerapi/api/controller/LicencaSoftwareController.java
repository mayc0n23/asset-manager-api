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

import br.edu.ifpb.assetmanagerapi.api.assembler.LicencaSoftwareAssembler;
import br.edu.ifpb.assetmanagerapi.api.disassembler.LicencaSoftwareDisassembler;
import br.edu.ifpb.assetmanagerapi.api.dto.input.LicencaSoftwareInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.output.LicencaSoftwareDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.LicencaSoftware;
import br.edu.ifpb.assetmanagerapi.domain.service.LicencaSoftwareService;

@RestController
@RequestMapping(value = "/licencas-software", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class LicencaSoftwareController {
	
	@Autowired
	private LicencaSoftwareService licencaSoftwareService;
	
	@Autowired
	private LicencaSoftwareAssembler licencaSoftwareAssembler;
	
	@Autowired
	private LicencaSoftwareDisassembler licencaSoftwareDisassembler;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public List<LicencaSoftwareDTO> listar() {
		return licencaSoftwareAssembler.toCollectionDTO(licencaSoftwareService.listar());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{licencaSoftwareId}")
	public LicencaSoftwareDTO buscar(@PathVariable Long licencaSoftwareId) {
		return licencaSoftwareAssembler.toDTO(licencaSoftwareService.buscar(licencaSoftwareId));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LicencaSoftwareDTO cadastrar(@RequestBody @Valid LicencaSoftwareInputDTO licenca) {
		return licencaSoftwareAssembler.toDTO(licencaSoftwareService.salvar(licencaSoftwareDisassembler.toDomainObject(licenca)));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PutMapping("/{licencaSoftwareId}")
	public LicencaSoftwareDTO editar(@PathVariable Long licencaSoftwareId, @RequestBody @Valid LicencaSoftwareInputDTO licencaInput) {
		LicencaSoftware licencaAtual = licencaSoftwareService.buscar(licencaSoftwareId);
		licencaSoftwareDisassembler.copyToDomainObject(licencaInput, licencaAtual);
		licencaAtual.setId(licencaSoftwareId);
		return licencaSoftwareAssembler.toDTO(licencaSoftwareService.salvar(licencaAtual));
	}
	
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/{licencaSoftwareId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long licencaSoftwareId) {
		licencaSoftwareService.deletar(licencaSoftwareId);
	}
	
}