package br.edu.ifpb.assetmanagerapi.domain.exception;

public class InsumoNaoEncontradoException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InsumoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public InsumoNaoEncontradoException(Long insumoId) {
		this(String.format("Insumo de id '%d' não encontrado", insumoId));
	}

}