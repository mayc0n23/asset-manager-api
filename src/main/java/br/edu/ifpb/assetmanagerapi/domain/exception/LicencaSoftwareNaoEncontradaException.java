package br.edu.ifpb.assetmanagerapi.domain.exception;

public class LicencaSoftwareNaoEncontradaException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LicencaSoftwareNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public LicencaSoftwareNaoEncontradaException(Long licencaSoftwareId) {
		this(String.format("Licença de software de id '%d' não encontrada", licencaSoftwareId));
	}

}