package br.edu.ifpb.assetmanagerapi.api.dto.input;

import br.edu.ifpb.assetmanagerapi.domain.model.EstadoConservacao;
import br.edu.ifpb.assetmanagerapi.domain.model.Rede;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class EquipamentoInputDTO {

    private String descricao;

    private String sala;

    private String bloco;

    @NotBlank
    private Integer numero;

    @NotBlank
    private String numeroSerie;

    private Rede rede;

    @NotNull
    private EstadoConservacao estadoConservacao;

    @NotNull
    private Long categoriaId;
}
