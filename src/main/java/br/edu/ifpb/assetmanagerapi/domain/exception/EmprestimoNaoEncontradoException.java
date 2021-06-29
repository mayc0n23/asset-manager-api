package br.edu.ifpb.assetmanagerapi.domain.exception;

public class EmprestimoNaoEncontradoException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmprestimoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public EmprestimoNaoEncontradoException(Long id) {
		this(String.format("Emprestimo de id '%d' não encontrado", id));
	}

}