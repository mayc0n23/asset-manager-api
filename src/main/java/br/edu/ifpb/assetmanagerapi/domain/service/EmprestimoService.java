package br.edu.ifpb.assetmanagerapi.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.assetmanagerapi.domain.exception.EmprestimoNaoEncontradoException;
import br.edu.ifpb.assetmanagerapi.domain.exception.NegocioException;
import br.edu.ifpb.assetmanagerapi.domain.model.Emprestimo;
import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import br.edu.ifpb.assetmanagerapi.domain.model.Setor;
import br.edu.ifpb.assetmanagerapi.domain.repository.EmprestimoRepository;

@Service
public class EmprestimoService {
	
	@Autowired
	private EmprestimoRepository emprestimoRepository;
	
	@Autowired
	private EquipamentoService equipamentoService;
	
	@Autowired
	private SetorService setorService;
	
	@Transactional
	public Emprestimo salvar(Emprestimo emprestimo) {
		Equipamento equipamento = equipamentoService.buscar(emprestimo.getEquipamento().getId());
		emprestimo.setEquipamento(equipamento);
		Setor setor = setorService.buscar(emprestimo.getSetor().getId());
		emprestimo.setSetor(setor);
		return emprestimoRepository.save(emprestimo);
	}
	
	@Transactional
	public void deletar(Long id) {
		Emprestimo emprestimo = buscar(id);
		try {
			emprestimoRepository.delete(emprestimo);
		} catch (DataIntegrityViolationException ex) {
			throw new NegocioException("O empréstimo está em uso");
		}
	}
	
	public Emprestimo buscar(Long id) {
		return emprestimoRepository.findById(id)
				.orElseThrow(() -> new EmprestimoNaoEncontradoException(id));
	}
	
	public List<Emprestimo> listar() {
		return emprestimoRepository.findAll();
	}
	
}