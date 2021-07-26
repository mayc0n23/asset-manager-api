package br.edu.ifpb.assetmanagerapi.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.assetmanagerapi.api.dto.output.DashboardDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.output.EmprestimoDTO;
import br.edu.ifpb.assetmanagerapi.api.dto.output.ServicoDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Emprestimo;
import br.edu.ifpb.assetmanagerapi.domain.model.Servico;
import br.edu.ifpb.assetmanagerapi.domain.repository.EmprestimoRepository;
import br.edu.ifpb.assetmanagerapi.domain.repository.ServicoRepository;

@Service
public class DashboardService {
	
	@Autowired
	private EmprestimoRepository emprestimoRepository;
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public DashboardDTO dashboard() {
		List<Emprestimo> emprestimos = emprestimoRepository.findTop20ByOrderByDataSaidaDesc();
		List<Servico> servicos = servicoRepository.findTop20ByOrderByDataSaidaDesc();
		DashboardDTO dashboard = new DashboardDTO();
		
		dashboard.setEmprestimos(emprestimos.stream()
				.map(emprestimo -> modelMapper.map(emprestimo, EmprestimoDTO.class))
				.collect(Collectors.toList()));
		
		dashboard.setServicos(servicos.stream()
				.map(servico -> modelMapper.map(servico, ServicoDTO.class))
				.collect(Collectors.toList()));
		
		return dashboard;
	}
	
}