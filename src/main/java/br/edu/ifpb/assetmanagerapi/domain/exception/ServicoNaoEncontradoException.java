package br.edu.ifpb.assetmanagerapi.domain.exception;

public class ServicoNaoEncontradoException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServicoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public ServicoNaoEncontradoException(Long servicoId) {
		this(String.format("Serviço de id '%d' não encontrado", servicoId));
	}

}