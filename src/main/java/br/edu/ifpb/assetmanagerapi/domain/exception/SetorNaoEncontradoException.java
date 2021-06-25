package br.edu.ifpb.assetmanagerapi.domain.exception;

public class SetorNaoEncontradoException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SetorNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public SetorNaoEncontradoException(Long setorId) {
		this(String.format("Setor de id '%d' não encontrado", setorId));
	}

}