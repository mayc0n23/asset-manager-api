package br.edu.ifpb.assetmanagerapi.domain.exception;

public class RetiradaInsumoNaoEncontradoException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RetiradaInsumoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public RetiradaInsumoNaoEncontradoException(Long retiradaId) {
		this(String.format("Não foi encontrado nenhuma retirada de id '%d'", retiradaId));
	}

}