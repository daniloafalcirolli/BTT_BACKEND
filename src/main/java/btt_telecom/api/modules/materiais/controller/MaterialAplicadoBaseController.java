package btt_telecom.api.modules.materiais.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.modules.materiais.model.MaterialAplicado;
import btt_telecom.api.modules.materiais.model.MaterialAplicadoBase;
import btt_telecom.api.modules.materiais.repository.MaterialAplicadoBaseRepository;
import btt_telecom.api.modules.materiais.repository.MaterialAplicadoRepository;
import btt_telecom.api.modules.provedores.model.Provedor;
import btt_telecom.api.modules.provedores.repository.ProvedorRepository;

@RestController
@RequestMapping(path = "/api/material/aplicado")
public class MaterialAplicadoBaseController {
	@Autowired
	private MaterialAplicadoBaseRepository repository;
	
	@Autowired
	private ProvedorRepository provedorRepository;
	
	@Autowired
	private MaterialAplicadoRepository aplicadoRepository;
	
	@GetMapping(path = "/all")
	public ResponseEntity<List<MaterialAplicadoBase>>  findAll(){
		return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<MaterialAplicadoBase> findById(@PathVariable Long id){
		return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id){
		MaterialAplicadoBase materialAplicado = repository.findById(id).get();
		
		List<Provedor> provedores = provedorRepository.findByMaterialAplicado(id);
		if(!provedores.isEmpty()) {
			provedores.forEach(x -> {
				x.getMateriais_aplicados().remove(materialAplicado);
				provedorRepository.save(x);
			});
		}
		
//		List<MaterialAplicado> materiaisAplicados = aplicadoRepository.findByMaterialId(id);
//		if(!materiaisAplicados.isEmpty()) {
//			aplicadoRepository.deleteAll(materiaisAplicados);
//		}
		
//		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<HttpStatus> save(@RequestBody MaterialAplicadoBase materialAplicadoBase){
		repository.save(materialAplicadoBase);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
