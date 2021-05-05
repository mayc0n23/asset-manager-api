package br.edu.ifpb.assetmanagerapi.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.assetmanagerapi.domain.exception.CategoriaNaoEncontradaException;
import br.edu.ifpb.assetmanagerapi.domain.exception.NegocioException;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Transactional
	public Categoria salvar(Categoria categoria) {
		categoriaRepository.detach(categoria);
		
		Optional<Categoria> categoriaExistente = categoriaRepository.findByNome(categoria.getNome());
		if (categoriaExistente.isPresent() && !categoriaExistente.get().equals(categoria)) {
			throw new NegocioException(String.format("A categoria de nome '%s' já existe", categoria.getNome()));
		}
		
		return categoriaRepository.save(categoria);
	}
	
	public Categoria buscar(Long categoriaId) {
		return categoriaRepository.findById(categoriaId)
				.orElseThrow(() -> new CategoriaNaoEncontradaException(categoriaId));
	}
	
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}

	public void deletar(Long categoriaId) {
		Categoria categoria = buscar(categoriaId);
		categoriaRepository.delete(categoria);
	}

}