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
import btt_telecom.api.modules.provedores.model.BasesProvedor;
import btt_telecom.api.modules.provedores.repository.BasesProvedorRepository;

@RestController
@RequestMapping(path = "/api/bases")
public class BasesProvedorController extends AbstractMethods{
	@Autowired
	private BasesProvedorRepository basesProvedorRepository;
	
	@GetMapping
	private ResponseEntity<List<BasesProvedor>> findAll() {
		try {
			return new ResponseEntity<>(basesProvedorRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/page")
	private ResponseEntity<Map<String, Object>> findAllWithPage(@RequestParam(name = "value", defaultValue = "") String value, @RequestParam(name = "size") Long size, @RequestParam(name = "page") Long page) {
		try {
			if(value.equals("")) {
				return new ResponseEntity<>(convertListToPage(basesProvedorRepository.findAll(), size, page), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(convertListToPage(basesProvedorRepository.search(value), size, page), HttpStatus.OK);
			}	
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<BasesProvedor> findById(@PathVariable(name = "id") Long id){
		try {
			if(basesProvedorRepository.existsById(id)) {
				return new ResponseEntity<>(basesProvedorRepository.findById(id).get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping
	private ResponseEntity<BasesProvedor> save(@RequestBody BasesProvedor base){
		try {
			if(basesProvedorRepository.save(base) != null) {
				return new ResponseEntity<>(base, HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	private ResponseEntity<HttpStatus> put(@RequestBody BasesProvedor base){
		try {
			if(basesProvedorRepository.existsById(base.getId())) {
				basesProvedorRepository.save(base);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	private ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id){
		try {
			if(basesProvedorRepository.existsById(id)){
				basesProvedorRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
}
