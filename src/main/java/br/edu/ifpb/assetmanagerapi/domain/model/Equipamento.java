package br.edu.ifpb.assetmanagerapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column
    private String descricao;

    @Column
    private String sala;

    @Column
    private String bloco;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false)
    private String numeroSerie;

    @Embedded
    private Rede rede;

    @Enumerated(EnumType.STRING)
    private EstadoConservacao estadoConservacao;

    @ManyToOne(targetEntity = Categoria.class, fetch = FetchType.LAZY, optional = false)
    private Categoria categoria;
}
