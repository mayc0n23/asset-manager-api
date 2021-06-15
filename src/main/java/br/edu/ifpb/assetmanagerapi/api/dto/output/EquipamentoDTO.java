package br.edu.ifpb.assetmanagerapi.api.dto.output;

import br.edu.ifpb.assetmanagerapi.domain.model.EstadoConservacao;
import br.edu.ifpb.assetmanagerapi.domain.model.Rede;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EquipamentoDTO {

    private Long id;

    private String descricao;

    private String sala;

    private String bloco;

    private Integer numero;

    private String numeroSerie;
    
    private String nomeArquivo;

    private Rede rede;

    private EstadoConservacao estadoConservacao;

    private CategoriaDTO categoria;
}