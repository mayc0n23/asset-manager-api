package br.edu.ifpb.assetmanagerapi.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.assetmanagerapi.domain.exception.InsumoNaoEncontradoException;
import br.edu.ifpb.assetmanagerapi.domain.exception.NegocioException;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.model.Insumo;
import br.edu.ifpb.assetmanagerapi.domain.repository.InsumoRepository;

@Service
public class InsumoService {
	
	@Autowired
	private InsumoRepository insumoRepository;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Transactional
	public Insumo salvar(Insumo insumo) {
		insumoRepository.detach(insumo);
		
		Optional<Insumo> insumoExistente = insumoRepository.findByNome(insumo.getNome());
		if (insumoExistente.isPresent() && !insumoExistente.get().equals(insumo)) {
			throw new NegocioException(String.format("O insumo de nome '%s' já existe", insumo.getNome()));
		}
		validarQuantidade(insumo);
		Categoria categoria = categoriaService.buscar(insumo.getCategoria().getId());
		insumo.setCategoria(categoria);
		return insumoRepository.save(insumo);
	}
	
	public Insumo buscar(Long insumoId) {
		return insumoRepository.findById(insumoId)
				.orElseThrow(() -> new InsumoNaoEncontradoException(insumoId));
	}
	
	public List<Insumo> listar() {
		return insumoRepository
				.findAll()
				.stream()
				.filter(i -> i.isVisivel())
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void deletar(Long insumoId) {
		Insumo insumo = buscar(insumoId);
		insumo.setVisivel(false);
	}
	
	public void validarQuantidade(Insumo insumo) {
		if (insumo.getQuantidadeAtual() < insumo.getQuantidadeMinima()) {
			throw new NegocioException("A quantidade atual não pode ser inferior a quantidade mínima.");
		}
	}
	
}