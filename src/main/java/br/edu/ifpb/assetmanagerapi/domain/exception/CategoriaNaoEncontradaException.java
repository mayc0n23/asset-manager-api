package br.edu.ifpb.assetmanagerapi.domain.exception;

public class CategoriaNaoEncontradaException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoriaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CategoriaNaoEncontradaException(Long categoriaId) {
		this(String.format("Categoria de id '%d' não encontrado", categoriaId));
	}

}