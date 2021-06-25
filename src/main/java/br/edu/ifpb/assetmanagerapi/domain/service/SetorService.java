package br.edu.ifpb.assetmanagerapi.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.assetmanagerapi.domain.exception.NegocioException;
import br.edu.ifpb.assetmanagerapi.domain.exception.SetorNaoEncontradoException;
import br.edu.ifpb.assetmanagerapi.domain.model.Setor;
import br.edu.ifpb.assetmanagerapi.domain.repository.SetorRepository;

@Service
public class SetorService {
	
	@Autowired
	private SetorRepository setorRepository;
	
	@Transactional
	public Setor salvar(Setor setor) {
		setorRepository.detach(setor);
		
		Optional<Setor> setorExistente = setorRepository.findBySigla(setor.getSigla());
		setorExistente.ifPresent(setorAtual -> {
			if (!setorAtual.equals(setor)) {
				throw new NegocioException(String.format("O setor de sigla '%s' já existe", setor.getSigla()));
			}
		});
		
		return setorRepository.save(setor);
	}
	
	@Transactional
	public void deletar(Long setorId) {
		Setor setor = buscar(setorId);
		try {
			setorRepository.delete(setor);
		} catch (DataIntegrityViolationException ex) {
			throw new NegocioException("Esse setor está em uso");
		}
	}
	
	public Setor buscar(Long setorId) {
		return setorRepository.findById(setorId)
				.orElseThrow(() -> new SetorNaoEncontradoException(setorId));
	}
	
	public List<Setor> listar() {
		return setorRepository.findAll();
	}
	
}