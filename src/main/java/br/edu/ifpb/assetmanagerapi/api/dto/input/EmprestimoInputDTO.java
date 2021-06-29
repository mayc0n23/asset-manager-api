package br.edu.ifpb.assetmanagerapi.api.dto.input;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

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
	
	private LocalDateTime dataPrevistaRetorno;
	
	@NotNull
	private StatusEmprestimo status;
	
	@NotNull
	private Long equipamentoId;
}