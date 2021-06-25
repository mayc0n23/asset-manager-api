package br.edu.ifpb.assetmanagerapi.api.dto.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SetorInputDTO {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String sigla;
	
}