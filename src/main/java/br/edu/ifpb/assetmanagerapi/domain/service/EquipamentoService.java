package br.edu.ifpb.assetmanagerapi.domain.service;

import br.edu.ifpb.assetmanagerapi.domain.exception.EquipamentoNaoEncontradoException;
import br.edu.ifpb.assetmanagerapi.domain.exception.NegocioException;
import br.edu.ifpb.assetmanagerapi.domain.model.Categoria;
import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import br.edu.ifpb.assetmanagerapi.domain.repository.EquipamentoRepository;
import br.edu.ifpb.assetmanagerapi.domain.service.FileStorageService.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository equipamentoRepository;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private CategoriaService categoriaService;

    @Transactional
    public Equipamento salvar(Equipamento equipamento) {
        equipamentoRepository.detach(equipamento);
        Optional<Equipamento> equipamentoExistente = equipamentoRepository.findByDescricao(equipamento.getDescricao());
        if (equipamentoExistente.isPresent() && !equipamentoExistente.get().equals(equipamento)) {
        	throw new NegocioException(String.format("O equipamento de descrição '%s' já existe.", equipamento.getDescricao()));
        }
        Categoria categoria = categoriaService.buscar(equipamento.getCategoria().getId());
        equipamento.setCategoria(categoria);
        return equipamentoRepository.save(equipamento);
    }

    public Equipamento buscar(Long equipamentoId) {
        return equipamentoRepository.findById(equipamentoId)
                .orElseThrow(() -> new EquipamentoNaoEncontradoException(equipamentoId));
    }

    public List<Equipamento> listar() {
        return equipamentoRepository.findAll();
    }

    public void deletar(Long equipamentoId) {
    	Equipamento equipamento = buscar(equipamentoId);
        equipamentoRepository.delete(equipamento);
    }
    
    @Transactional
    public void inserirArquivo(Long equipamentoId, File file) {
    	System.out.println("Entrei no service de inserir o arquivo");
    	Equipamento equipamento = buscar(equipamentoId);
    	fileStorageService.armazenar(file);
    	equipamento.setNomeArquivo(file.getNomeArquivo());
    	equipamentoRepository.save(equipamento);
    	System.out.println("Tudo OK");
    }
    
    @Transactional
    public void editarArquivo(Long equipamentoId, File file) {
    	Equipamento equipamento = buscar(equipamentoId);
    	fileStorageService.substituir(equipamento.getNomeArquivo(), file);
    	equipamento.setNomeArquivo(file.getNomeArquivo());
    	equipamentoRepository.save(equipamento);
    }
    
    @Transactional
    public void removerArquivo(Long equipamentoId) {
    	Equipamento equipamento = buscar(equipamentoId);
    	fileStorageService.remover(equipamento.getNomeArquivo());
    	equipamento.setNomeArquivo(null);
    	equipamentoRepository.save(equipamento);
    }
    
    public InputStream recuperarArquivo(Long equipamentoId) {
    	Equipamento equipamento = buscar(equipamentoId);
    	return fileStorageService.recuperar(equipamento.getNomeArquivo());
    }
    
}