package br.edu.ifpb.assetmanagerapi.api.dto.input;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpb.assetmanagerapi.api.dto.output.ServidorDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.StatusEmprestimo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmprestimoInputDTO {
	
	private String numeroChamadoSuap;
	
	private String linkChamadoSuap;
	
	private String observacoes;
	
	private LocalDateTime dataRetorno;
	
	@NotNull
	private LocalDateTime dataPrevistaRetorno;
	
	@NotNull
	private StatusEmprestimo status;
	
	@Valid
	@NotNull
	private EquipamentoIdInputDTO equipamento;
	
	@Valid
	@NotNull
	private SetorIdInputDTO setor;
	
	private ServidorDTO expedidor;
	
	private ServidorDTO solicitante;
}