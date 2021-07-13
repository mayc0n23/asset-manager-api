package br.edu.ifpb.assetmanagerapi.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.ifpb.assetmanagerapi.api.dto.output.ServicoDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.Servico;

@Component
public class ServicoAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ServicoDTO toDTO(Servico servico) {
		return modelMapper.map(servico, ServicoDTO.class);
	}
	
	public List<ServicoDTO> toCollectionDTO(List<Servico> servicos) {
		return servicos.stream()
				.map(servico -> toDTO(servico))
				.collect(Collectors.toList());
	}
	
}