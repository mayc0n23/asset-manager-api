package br.edu.ifpb.assetmanagerapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.assetmanagerapi.api.assembler.LicencaSoftwareAssembler;
import br.edu.ifpb.assetmanagerapi.api.dto.output.LicencaSoftwareDTO;
import br.edu.ifpb.assetmanagerapi.domain.service.LicencaSoftwareService;

@RestController
@RequestMapping(value = "/licencas-software", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
public class LicencaSoftwareController {
	
	@Autowired
	private LicencaSoftwareService licencaSoftwareService;
	
	@Autowired
	private LicencaSoftwareAssembler licencaSoftwareAssembler;
	
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
	
}