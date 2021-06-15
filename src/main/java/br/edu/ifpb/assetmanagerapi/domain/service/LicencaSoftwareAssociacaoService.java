package br.edu.ifpb.assetmanagerapi.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.assetmanagerapi.domain.model.Equipamento;
import br.edu.ifpb.assetmanagerapi.domain.model.LicencaSoftware;

@Service
public class LicencaSoftwareAssociacaoService {
	
	@Autowired
	private LicencaSoftwareService licencaSoftwareService;
	
	@Autowired
	private EquipamentoService equipamentoService;
	
	public List<Equipamento> listarEquipamentosAssociados(Long licencaSoftwareId) {
		LicencaSoftware licenca = licencaSoftwareService.buscar(licencaSoftwareId);
		return licenca.getEquipamentosAssociados();
	}
	
	@Transactional
	public void associar(Long licencaSoftwareId, Long equipamentoId) {
		LicencaSoftware licenca = licencaSoftwareService.buscar(licencaSoftwareId);
		Equipamento equipamento = equipamentoService.buscar(equipamentoId);
		licenca.getEquipamentosAssociados().add(equipamento);
	}
	
	@Transactional
	public void desassociar(Long licencaSoftwareId, Long equipamentoId) {
		LicencaSoftware licenca = licencaSoftwareService.buscar(licencaSoftwareId);
		Equipamento equipamento = equipamentoService.buscar(equipamentoId);
		licenca.getEquipamentosAssociados().remove(equipamento);
	}
	
}