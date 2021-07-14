package br.edu.ifpb.assetmanagerapi.api.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LicencaSoftwareInputDTO {
	
	@Positive
	private int numero;
	
	@NotBlank
	private String software;
	
	@NotBlank
	private String chaveAtivacao;
	
	@NotNull
	private boolean ativacoesInfinitas;
	
	private int maximoAtivacoes;
	
	@NotNull
	private Long categoriaId;
	
}