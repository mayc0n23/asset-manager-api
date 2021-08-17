package br.edu.ifpb.assetmanagerapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.assetmanagerapi.domain.model.Admin;
import br.edu.ifpb.assetmanagerapi.domain.repository.AdminRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Admin salvar(@RequestBody Admin admin) {
		return adminRepository.save(admin);
	}
	
}