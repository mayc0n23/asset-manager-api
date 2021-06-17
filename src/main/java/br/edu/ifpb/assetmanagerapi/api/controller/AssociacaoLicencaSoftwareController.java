package br.edu.ifpb.assetmanagerapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.assetmanagerapi.api.assembler.EquipamentoAssembler;
import br.edu.ifpb.assetmanagerapi.api.dto.input.AssociacaoLicencaSoftwareInputDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.output.EquipamentoDTO;
import br.edu.ifpb.assetmanagerapi.domain.service.LicencaSoftwareAssociacaoService;

@RestController
@RequestMapping(value = "/licencas-software/{licencaSoftwareId}", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class AssociacaoLicencaSoftwareController {
	
	@Autowired
	private LicencaSoftwareAssociacaoService licencaSoftwareAssociacaoService;
	
	@Autowired
	private EquipamentoAssembler equipamentoAssembler;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/associacoes")
	public List<EquipamentoDTO> listarEquipamentosAssociados(@PathVariable Long licencaSoftwareId) {
		return equipamentoAssembler.toCollectionDTO(licencaSoftwareAssociacaoService.listarEquipamentosAssociados(licencaSoftwareId));
	}
	
	@PreAuthorize("isAuthenticated()")
	@PutMapping("/associar")
	public void associar(@PathVariable Long licencaSoftwareId, @RequestBody @Valid AssociacaoLicencaSoftwareInputDTO associacao) {
		licencaSoftwareAssociacaoService.associar(licencaSoftwareId, associacao.getEquipamentoId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/desassociar")
	public void desassociar(@PathVariable Long licencaSoftwareId, @RequestBody @Valid AssociacaoLicencaSoftwareInputDTO associacao) {
		licencaSoftwareAssociacaoService.desassociar(licencaSoftwareId, associacao.getEquipamentoId());
	}
	
}