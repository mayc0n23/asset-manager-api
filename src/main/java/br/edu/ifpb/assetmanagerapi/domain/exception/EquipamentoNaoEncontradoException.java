package br.edu.ifpb.assetmanagerapi.domain.exception;

public class EquipamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public EquipamentoNaoEncontradoException(String mensagem) { super(mensagem); }

    public EquipamentoNaoEncontradoException(Long equipamentoId) {
        this(String.format("Equipamento de id '%d' não encontrado", equipamentoId));
    }
}
