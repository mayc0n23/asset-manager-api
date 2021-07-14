package br.edu.ifpb.assetmanagerapi.api.dto.output;

import java.time.LocalDateTime;
import java.util.List;

import br.edu.ifpb.assetmanagerapi.domain.model.TipoServico;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServicoDTO {
		
	private Long id;
	
	private LocalDateTime dataSaida;
	
	private String numeroChamadoSuap;
	
	private String linkChamadoSuap;
	
	private String observacoes;
	
	private LocalDateTime dataRetorno;
	
	private String descricao;
	
	private TipoServico tipoServico;
	
	private EquipamentoDTO equipamento;
	
	private ServidorDTO expedidor;
	
	private ServidorDTO solicitante;
	
	private SetorDTO setor;
	
	private List<InsumoDTO> insumos;
	
}