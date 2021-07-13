package br.edu.ifpb.assetmanagerapi.api.dto.output;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RetiradaInsumoDTO {
	
	private Long id;
	
	private LocalDateTime dataSaida;
	
	private String numeroChamadoSuap;
	
	private String linkChamadoSuap;
	
	private String observacoes;
	
	private InsumoDTO insumo;
	
	private SetorDTO setor;
	
	private ServidorDTO expedidor;
	
	private ServidorDTO solicitante;
	
	private float quantidade;
	
}