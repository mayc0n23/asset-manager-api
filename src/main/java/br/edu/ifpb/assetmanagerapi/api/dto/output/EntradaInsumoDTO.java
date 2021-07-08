package br.edu.ifpb.assetmanagerapi.api.dto.output;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EntradaInsumoDTO {
	
	private Long id;
	
	private LocalDateTime data;
	
	private LocalDateTime dataValidade;
	
	private float quantidade;
	
	private InsumoDTO insumo;
	
}