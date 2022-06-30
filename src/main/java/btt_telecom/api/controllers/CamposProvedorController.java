package btt_telecom.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.models.CamposProvedor;
import btt_telecom.api.models.Provedor;
import btt_telecom.api.repositories.CamposProvedorRepository;
import btt_telecom.api.repositories.ProvedorRepository;

@RestController
@RequestMapping(path = "/api/campos/provedor")
public class CamposProvedorController {
	@Autowired
	private CamposProvedorRepository camposProvedorRepository;

	@Autowired
	private ProvedorRepository provedorRepository;
	
	@GetMapping
	private ResponseEntity<List<CamposProvedor>> findAll(){
		try {
			return new ResponseEntity<>(camposProvedorRepository.findAll(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/page")
	private ResponseEntity<Page<CamposProvedor>> findAllWithPage(Pageable pageable){
		try {
			return new ResponseEntity<>(camposProvedorRepository.findAll(pageable), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/{id}")
	private ResponseEntity<CamposProvedor> findById(@PathVariable(name = "id") Long id){
		try {
			if(camposProvedorRepository.existsById(id)) {
				return new ResponseEntity<>(camposProvedorRepository.findById(id).get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping
	private ResponseEntity<HttpStatus> save(@RequestBody CamposProvedor campo){
		if(camposProvedorRepository.save(campo) != null) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	private ResponseEntity<HttpStatus> edit(@RequestBody CamposProvedor campo){
		if(camposProvedorRepository.existsById(campo.getId())) {
			if(camposProvedorRepository.save(campo) != null) {
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	private ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id){
		try {
			if(camposProvedorRepository.existsById(id)) {
				provedorRepository.findAll().forEach(x -> {
					Provedor p = x;
					p.getCampos().remove(camposProvedorRepository.findById(id).get());
					provedorRepository.save(p);
				});
				camposProvedorRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
	}
}
