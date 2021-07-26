package br.edu.ifpb.assetmanagerapi.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.assetmanagerapi.domain.exception.EntradaInsumoNaoEncontradaException;
import br.edu.ifpb.assetmanagerapi.domain.model.EntradaInsumo;
import br.edu.ifpb.assetmanagerapi.domain.model.Insumo;
import br.edu.ifpb.assetmanagerapi.domain.repository.EntradaInsumoRepository;

@Service
public class EntradaInsumoService {
	
	@Autowired
	private EntradaInsumoRepository entradaInsumoRepository;
	
	@Autowired
	private InsumoService insumoService;
	
	@Transactional
	public EntradaInsumo salvar(Long insumoId, EntradaInsumo entrada) {
		Insumo insumo = insumoService.buscar(insumoId);
		insumo.setQuantidadeAtual(insumo.getQuantidadeAtual() + entrada.getQuantidade());
		entrada.setInsumo(insumo);
		return entradaInsumoRepository.save(entrada);
	}
	
	@Transactional
	public void deletar(Long insumoId, Long entradaId) {
		Insumo insumo = insumoService.buscar(insumoId);
		EntradaInsumo entrada = buscar(insumoId, entradaId);
		insumo.setQuantidadeAtual(insumo.getQuantidadeAtual() - entrada.getQuantidade());
		entradaInsumoRepository.delete(entrada);
	}
	
	public List<EntradaInsumo> listar(Long insumoId) {
		Insumo insumo = insumoService.buscar(insumoId);
		return entradaInsumoRepository.findByInsumoOrderByDataDesc(insumo);
	}
	
	public EntradaInsumo buscar(Long insumoId, Long entradaId) {
		Insumo insumo = insumoService.buscar(insumoId);
		return entradaInsumoRepository.findByIdAndInsumo(entradaId, insumo)
				.orElseThrow(() -> new EntradaInsumoNaoEncontradaException(entradaId));
	}
	
}