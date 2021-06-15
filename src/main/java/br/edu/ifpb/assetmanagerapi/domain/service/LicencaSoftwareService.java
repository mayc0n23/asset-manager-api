package br.edu.ifpb.assetmanagerapi.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.assetmanagerapi.domain.exception.LicencaSoftwareNaoEncontradaException;
import br.edu.ifpb.assetmanagerapi.domain.exception.NegocioException;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.model.LicencaSoftware;
import br.edu.ifpb.assetmanagerapi.domain.model.TipoCategoria;
import br.edu.ifpb.assetmanagerapi.domain.repository.LicencaSoftwareRepository;

@Service
public class LicencaSoftwareService {
	
	@Autowired
	private LicencaSoftwareRepository licencaSoftwareRepository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Transactional
	public LicencaSoftware salvar(LicencaSoftware licenca) {
		licencaSoftwareRepository.detach(licenca);
		
		Optional<LicencaSoftware> licencaExistente = licencaSoftwareRepository.findByChaveAtivacao(licenca.getChaveAtivacao());
		if (licencaExistente.isPresent() && !licencaExistente.get().equals(licenca)) {
			throw new NegocioException(String.format("A licença de software com chave '%s' já existe", licenca.getChaveAtivacao()));
		}
		
		Categoria categoria = categoriaService.buscar(licenca.getCategoria().getId());
		if (!categoria.getTipoCategoria().equals(TipoCategoria.SOFTWARE)) {
			throw new NegocioException("A licença de software tem que estar na categoria de software");
		}
		
		licenca.setCategoria(categoria);
		return licencaSoftwareRepository.save(licenca);
	}
	
	public List<LicencaSoftware> listar() {
		return licencaSoftwareRepository.findAll();
	}
	
	public LicencaSoftware buscar(Long licencaSoftwareId) {
		return licencaSoftwareRepository.findById(licencaSoftwareId)
				.orElseThrow(() -> new LicencaSoftwareNaoEncontradaException(licencaSoftwareId));
	}
	
	public void deletar(Long licencaId) {
		LicencaSoftware licenca = buscar(licencaId);
		try {
			licencaSoftwareRepository.delete(licenca);
		} catch (DataIntegrityViolationException e) {
			throw new NegocioException("Essa licença de software está em uso");
		}
	}
	
}