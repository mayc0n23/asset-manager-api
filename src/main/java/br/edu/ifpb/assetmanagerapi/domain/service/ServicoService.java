package br.edu.ifpb.assetmanagerapi.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.assetmanagerapi.domain.exception.NegocioException;
import br.edu.ifpb.assetmanagerapi.domain.exception.ServicoNaoEncontradoException;
import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import br.edu.ifpb.assetmanagerapi.domain.model.RetiradaInsumo;
import br.edu.ifpb.assetmanagerapi.domain.model.Servico;
import br.edu.ifpb.assetmanagerapi.domain.model.Setor;
import br.edu.ifpb.assetmanagerapi.domain.repository.ServicoRepository;

@Service
public class ServicoService {
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private SetorService setorService;
	
	@Autowired
	private EquipamentoService equipamentoService;
	
	@Autowired
	private RetiradaInsumoService retiradaInsumoService;
	
	@Transactional
	public Servico salvar(Servico servico) {
		Setor setor = setorService.buscar(servico.getSetor().getId());
		servico.setSetor(setor);
		Equipamento equipamento = equipamentoService.buscar(servico.getEquipamento().getId());
		servico.setEquipamento(equipamento);
	
		List<RetiradaInsumo> retiradas = servico.getRetiradas();
		List<RetiradaInsumo> retiradasSalvas = new ArrayList<>();
		
		retiradas
			.forEach(retirada -> {
				retiradasSalvas.add(retiradaInsumoService.salvar(retirada.getInsumo().getId(), retirada));
		});
		
		servico.setRetiradas(retiradasSalvas);
		
		return servicoRepository.save(servico);
	}
	
	@Transactional
	public void deletar(Long servicoId) {
		Servico servico = buscar(servicoId);
		try {
			servicoRepository.delete(servico);
		} catch (DataIntegrityViolationException ex) {
			throw new NegocioException("O serviço está em uso");
		}
	}
	
	public List<Servico> listar() {
		return servicoRepository.findAll(Sort.by(Sort.Direction.DESC, "dataSaida"));
	}
	
	public Servico buscar(Long servicoId) {
		return servicoRepository.findById(servicoId)
				.orElseThrow(() -> new ServicoNaoEncontradoException(servicoId));
	}
	
}