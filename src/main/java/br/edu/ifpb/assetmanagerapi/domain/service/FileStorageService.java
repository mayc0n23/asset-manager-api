package br.edu.ifpb.assetmanagerapi.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FileStorageService {
	
	InputStream recuperar(String nomeArquivo);
	
	void armazenar(File file);
	
	void remover(String nomeArquivo);
	
	default void substituir(String nomeArquivoAntigo, File file) {
		this.armazenar(file);
		if (nomeArquivoAntigo != null) {
			this.remover(nomeArquivoAntigo);
		}
	}
	
	default String gerarNomeArquivo(String nomeArquivo) {
		return UUID.randomUUID() + "_" + nomeArquivo;
	}
	
	@Builder
	@Getter
	class File {
		private String nomeArquivo;
		private String contentType;
		private InputStream inputStream;
	}
	
}