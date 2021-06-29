package br.edu.ifpb.assetmanagerapi.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.input.EmprestimoInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Emprestimo;
import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;

@Component
public class EmprestimoDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Emprestimo toDomainObject(EmprestimoInputDTO emprestimoInput) {
		return modelMapper.map(emprestimoInput, Emprestimo.class);
	}
	
	public void copyToDomainObject(EmprestimoInputDTO emprestimoInput, Emprestimo emprestimo) {
		emprestimo.setEquipamento(new Equipamento());
		modelMapper.map(emprestimoInput, emprestimo);
	}
	
}