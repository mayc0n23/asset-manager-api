package br.edu.ifpb.assetmanagerapi.domain.exception;

public class EntradaInsumoNaoEncontradaException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntradaInsumoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public EntradaInsumoNaoEncontradaException(Long entradaId) {
		this(String.format("Não foi encontrado nenhuma entrada de id '%d'", entradaId));
	}

}