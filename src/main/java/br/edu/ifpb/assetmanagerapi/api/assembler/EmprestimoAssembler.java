package br.edu.ifpb.assetmanagerapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.output.EmprestimoDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Emprestimo;

@Component
public class EmprestimoAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public EmprestimoDTO toDTO(Emprestimo emprestimo) {
		return modelMapper.map(emprestimo, EmprestimoDTO.class);
	}
	
	public List<EmprestimoDTO> toCollectionDTO(List<Emprestimo> emprestimos) {
		return emprestimos.stream()
				.map(emprestimo -> toDTO(emprestimo))
				.collect(Collectors.toList());
	}
	
}