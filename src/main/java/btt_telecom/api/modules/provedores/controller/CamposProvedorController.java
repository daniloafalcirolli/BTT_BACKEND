package btt_telecom.api.modules.provedores.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
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
import btt_telecom.api.modules.provedores.model.CamposProvedorBase;
import btt_telecom.api.modules.provedores.model.Provedor;
import btt_telecom.api.modules.provedores.repository.CamposProvedorBaseRepository;
import btt_telecom.api.modules.provedores.repository.ProvedorRepository;

@RestController
@RequestMapping(path = "/api/campos/provedor")
public class CamposProvedorController extends AbstractMethods {
	@Autowired
	private CamposProvedorBaseRepository camposRepository;

	@Autowired
	private ProvedorRepository provedorRepository;
	
	@GetMapping
	private ResponseEntity<List<CamposProvedorBase>> findAll(){
		try {
			return new ResponseEntity<>(camposRepository.findAll(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/all/search")
	private ResponseEntity<List<CamposProvedorBase>> findAll(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			List<CamposProvedorBase> result = camposRepository.search(json.getString("value"));
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/page")
	private ResponseEntity<Map<String, Object>> findAllWithPage(@RequestParam(name = "value", defaultValue = "") String value, @RequestParam(name = "size") Long size, @RequestParam(name = "page") Long page){
		try {
			if(value.equals("")) {
				return new ResponseEntity<>(convertListToPage(camposRepository.findAll(), size, page), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(convertListToPage(camposRepository.search(value), size, page), HttpStatus.OK);
			}			}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/{id}")
	private ResponseEntity<CamposProvedorBase> findById(@PathVariable(name = "id") Long id){
		try {
			if(camposRepository.existsById(id)) {
				return new ResponseEntity<>(camposRepository.findById(id).get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping
	private ResponseEntity<HttpStatus> save(@RequestBody CamposProvedorBase campo){
		if(camposRepository.save(campo) != null) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	private ResponseEntity<HttpStatus> edit(@RequestBody CamposProvedorBase campo){
		if(camposRepository.existsById(campo.getId())) {
			if(camposRepository.save(campo) != null) {
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
			if(camposRepository.existsById(id)) {
				provedorRepository.findAll().forEach(x -> {
					Provedor p = x;
					p.getCampos().remove(camposRepository.findById(id).get());
					provedorRepository.save(p);
				});
				camposRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
	}
}
