package br.edu.ifpb.assetmanagerapi.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class Rede {

    @Column
    private String hostname;

    @Column
    private String enderecoIP;

    @Column
    private String enderecoMAC;
}
