package br.edu.ifpb.assetmanagerapi.api.dto.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.edu.ifpb.assetmanagerapi.api.dto.output.ServidorDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RetiradaInsumoInputDTO {
	
	private String numeroChamadoSuap;
	
	private String linkChamadoSuap;
	
	private String observacoes;
	
	@Valid
	@NotNull
	private SetorIdInputDTO setor;
	
	private ServidorDTO expedidor;
	
	private ServidorDTO solicitante;
	
	@Positive
	private float quantidade;
	
}