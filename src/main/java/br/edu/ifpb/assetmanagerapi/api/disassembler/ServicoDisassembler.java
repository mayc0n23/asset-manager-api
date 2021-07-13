package br.edu.ifpb.assetmanagerapi.api.disassembler;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.input.ServicoInputDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import br.edu.ifpb.assetmanagerapi.domain.model.Servico;
import br.edu.ifpb.assetmanagerapi.domain.model.Setor;

@Component
public class ServicoDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Servico toDomainObject(ServicoInputDTO servicoInput) {
		return modelMapper.map(servicoInput, Servico.class);
	}
	
	public void copyToDomainObject(ServicoInputDTO servicoInput, Servico servico) {
		servico.setEquipamento(new Equipamento());
		servico.setSetor(new Setor());
		servico.setRetiradas(new ArrayList<>());
		modelMapper.map(servicoInput, servico);
	}
	
}