package br.edu.ifpb.assetmanagerapi.api.dto.input;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EntradaInsumoInputDTO {
	
	@NotNull
	private LocalDateTime data;
	
	@NotNull
	private LocalDateTime dataValidade;
	
	@Positive
	private float quantidade;
	
}