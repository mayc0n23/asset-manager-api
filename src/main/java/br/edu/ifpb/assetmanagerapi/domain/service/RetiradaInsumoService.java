package br.edu.ifpb.assetmanagerapi.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.assetmanagerapi.domain.exception.NegocioException;
import br.edu.ifpb.assetmanagerapi.domain.exception.RetiradaInsumoNaoEncontradoException;
import br.edu.ifpb.assetmanagerapi.domain.model.Insumo;
import br.edu.ifpb.assetmanagerapi.domain.model.RetiradaInsumo;
import br.edu.ifpb.assetmanagerapi.domain.model.Setor;
import br.edu.ifpb.assetmanagerapi.domain.repository.RetiradaInsumoRepository;

@Service
public class RetiradaInsumoService {
	
	@Autowired
	private RetiradaInsumoRepository retiradaInsumoRepository;
	
	@Autowired
	private InsumoService insumoService;
	
	@Autowired
	private SetorService setorService;
	
	@Transactional
	public RetiradaInsumo salvar(Long insumoId, RetiradaInsumo retirada) {
		Insumo insumo = insumoService.buscar(insumoId);
		if ((insumo.getQuantidadeAtual() >= retirada.getQuantidade()) 
				&& (insumo.getQuantidadeAtual() - retirada.getQuantidade() >= insumo.getQuantidadeMinima())) {
			
			Setor setor = setorService.buscar(retirada.getSetor().getId());
			retirada.setSetor(setor);
			
			insumo.setQuantidadeAtual(insumo.getQuantidadeAtual() - retirada.getQuantidade());
			retirada.setInsumo(insumo);
			return retiradaInsumoRepository.save(retirada);
		} else {
			throw new NegocioException("A quantidade que você quer retirar não pode ser maior que a quantidade atual ou ultrapassa a quantidade minima");
		}
	}
	
	@Transactional
	public void deletar(Long insumoId, Long entradaId) {
		Insumo insumo = insumoService.buscar(insumoId);
		RetiradaInsumo retirada = buscar(insumoId, entradaId);
		insumo.setQuantidadeAtual(insumo.getQuantidadeAtual() + retirada.getQuantidade());
		retiradaInsumoRepository.delete(retirada);
	}
	
	public List<RetiradaInsumo> listar(Long insumoId) {
		Insumo insumo = insumoService.buscar(insumoId);
		return retiradaInsumoRepository.findByInsumoOrderByDataSaidaDesc(insumo);
	}
	
	public RetiradaInsumo buscar(Long insumoId, Long retiradaId) {
		Insumo insumo = insumoService.buscar(insumoId);
		return retiradaInsumoRepository.findByIdAndInsumo(retiradaId, insumo)
				.orElseThrow(() -> new RetiradaInsumoNaoEncontradoException(retiradaId));
	}
	
}