package br.edu.ifpb.assetmanagerapi.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.assetmanagerapi.domain.exception.LicencaSoftwareNaoEncontradaException;
import br.edu.ifpb.assetmanagerapi.domain.model.LicencaSoftware;
import br.edu.ifpb.assetmanagerapi.domain.repository.LicencaSoftwareRepository;

@Service
public class LicencaSoftwareService {
	
	@Autowired
	private LicencaSoftwareRepository licencaSoftwareRepository;
	
	public List<LicencaSoftware> listar() {
		return licencaSoftwareRepository.findAll();
	}
	
	public LicencaSoftware buscar(Long licencaSoftwareId) {
		return licencaSoftwareRepository.findById(licencaSoftwareId)
				.orElseThrow(() -> new LicencaSoftwareNaoEncontradaException(licencaSoftwareId));
	}
	
}