package br.edu.ifpb.assetmanagerapi.api.dto.output;

import java.time.LocalDateTime;

import br.edu.ifpb.assetmanagerapi.domain.model.StatusEmprestimo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmprestimoDTO {
	
	private Long id;
	
	private LocalDateTime dataSaida;
	
	private String numeroChamadoSuap;
	
	private String linkChamadoSuap;
	
	private String observacoes;
	
	private LocalDateTime dataPrevistaRetorno;
	
	private LocalDateTime dataRetorno;
	
	private StatusEmprestimo status;
	
	private EquipamentoDTO equipamento;
	
	private SetorDTO setor;
	
	private ServidorDTO expedidor;
	
	private ServidorDTO solicitante;
	
}