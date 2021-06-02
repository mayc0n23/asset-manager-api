package br.edu.ifpb.assetmanagerapi.infrastructure.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.util.FileCopyUtils;

import br.edu.ifpb.assetmanagerapi.domain.service.FileStorageService;

public class LocalFileStorageService implements FileStorageService {

	@Override
	public InputStream recuperar(String nomeArquivo) {
		try {
			Path path = Paths.get("C:/Users/mayco/Desktop/files", nomeArquivo);
			return Files.newInputStream(path);
		} catch (IOException e) {
			throw new StorageException("Não foi possivel recuperar o arquivo", e);
		}
	}

	@Override
	public void armazenar(File file) {
		try {
			Path arquivoPath = Paths.get("C:/Users/mayco/Desktop/files", file.getNomeArquivo());
			FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(arquivoPath));
		} catch (IOException e) {
			throw new StorageException("Não foi possivel armazenar o arquivo", e);
		}
	}

	@Override
	public void remover(String nomeArquivo) {
		try {
			Path path = Paths.get("C:/Users/mayco/Desktop/files", nomeArquivo);
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new StorageException("Não foi possivel excluir o arquivo", e);
		}
	}

}