package br.edu.ifpb.assetmanagerapi.api.dto.input;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpb.assetmanagerapi.api.dto.output.ServidorDTO;
import br.edu.ifpb.assetmanagerapi.domain.model.TipoServico;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServicoInputDTO {
	
	private String numeroChamadoSuap;
	
	private String linkChamadoSuap;
	
	private String observacoes;
	
	private LocalDateTime dataRetorno;
	
	private String descricao;
	
	@NotNull
	private TipoServico tipoServico;
	
	@Valid
	@NotNull
	private EquipamentoIdInputDTO equipamento;
	
	@Valid
	@NotNull
	private SetorIdInputDTO setor;
	
	private ServidorDTO expedidor;
	
	private ServidorDTO solicitante;
	
	@Valid
	private List<RetiradaInsumoInputDTO> retiradas = new ArrayList<>();
	
}