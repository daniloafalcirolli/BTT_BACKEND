package btt_telecom.api.modules.provedores.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.config.general.AbstractMethods;
import btt_telecom.api.modules.provedores.model.CategoriaServicoProvedor;
import btt_telecom.api.modules.provedores.repository.CategoriaServicoProvedorRepository;

@RestController
@RequestMapping(path = "/api/categoria/servico/provedor")
public class CategoriaServicoProvedorController extends AbstractMethods{
	@Autowired
	private CategoriaServicoProvedorRepository categoriaRepository;
	
	@GetMapping
	private ResponseEntity<List<CategoriaServicoProvedor>> findAll(){
		try {
			return new ResponseEntity<>(categoriaRepository.findAll(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/page")
	private ResponseEntity<Map<String, Object>> findAllWithPage(@RequestParam(name = "value", defaultValue = "") String value, @RequestParam(name = "size") Long size, @RequestParam(name = "page") Long page){
		try {
			if(value.equals("")) {
				return new ResponseEntity<>(convertListToPage(categoriaRepository.findAll(), size, page), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(convertListToPage(categoriaRepository.search(value), size, page), HttpStatus.OK);
			}	
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/{id}")
	private ResponseEntity<CategoriaServicoProvedor> findById(@PathVariable(name = "id") Long id){
		try {
			if(categoriaRepository.existsById(id)) {
				return new ResponseEntity<>(categoriaRepository.findById(id).get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping
	private ResponseEntity<HttpStatus> save(@RequestBody CategoriaServicoProvedor categoriaServicoProvedor){
		if(categoriaRepository.save(categoriaServicoProvedor) != null) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	private ResponseEntity<HttpStatus> edit(@RequestBody CategoriaServicoProvedor categoriaServicoProvedor){
		if(categoriaRepository.existsById(categoriaServicoProvedor.getId())) {
			if(categoriaRepository.save(categoriaServicoProvedor) != null) {
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
			if(categoriaRepository.existsById(id)) {
				categoriaRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
	}
}
