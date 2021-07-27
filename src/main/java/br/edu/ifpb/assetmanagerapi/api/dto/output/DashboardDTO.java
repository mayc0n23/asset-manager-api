package br.edu.ifpb.assetmanagerapi.api.dto.output;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DashboardDTO {
	
	private List<EmprestimoDTO> emprestimos;
	
	private List<ServicoDTO> servicos;
	
	private List<EntradaInsumoDTO> entradasInsumos;
	
	private List<RetiradaInsumoDTO> retiradasInsumos;
	
}