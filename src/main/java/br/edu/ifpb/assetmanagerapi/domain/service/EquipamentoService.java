package br.edu.ifpb.assetmanagerapi.domain.service;

import br.edu.ifpb.assetmanagerapi.domain.exception.EquipamentoNaoEncontradoException;
import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import br.edu.ifpb.assetmanagerapi.domain.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Transactional
    public Equipamento salvar(Equipamento equipamento) {
        equipamentoRepository.detach(equipamento);
        return equipamentoRepository.save(equipamento);
    }

    public Equipamento buscar(Long equipamentoId) {
        return equipamentoRepository.findById(equipamentoId)
                .orElseThrow(() -> new EquipamentoNaoEncontradoException(equipamentoId));
    }

    public List<Equipamento> listar() {
        return equipamentoRepository.findAll();
    }

    public void deletar(Equipamento categoria) {
        equipamentoRepository.delete(categoria);
    }
}
