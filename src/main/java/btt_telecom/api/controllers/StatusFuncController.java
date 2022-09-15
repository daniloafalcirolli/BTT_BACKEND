package btt_telecom.api.controllers;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import btt_telecom.api.models.StatusFunc;
import btt_telecom.api.repositories.StatusFuncRepository;

@RestController
@RequestMapping(path = "/api/status/func")
public class StatusFuncController {
	@Autowired
	private StatusFuncRepository statusFuncRepository;
	
	@GetMapping("/page")
	private ResponseEntity<Page<StatusFunc>> findAllWithPage(Pageable pageable){
		try {
			return new ResponseEntity<>(statusFuncRepository.findAll(pageable), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/search")
	private ResponseEntity<Page<StatusFunc>> search(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			List<StatusFunc> result = statusFuncRepository.search(json.getString("value"));
			Page<StatusFunc> page = new PageImpl<>(result);
			return new ResponseEntity<>(page, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/{id}")
	private ResponseEntity<StatusFunc> findById(@PathVariable(name = "id") Long id){
		try {
			if(statusFuncRepository.existsById(id)) {
				return new ResponseEntity<>(statusFuncRepository.findById(id).get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping
	private ResponseEntity<HttpStatus> save(@RequestBody StatusFunc StatusFunc){
		if(statusFuncRepository.save(StatusFunc) != null) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	private ResponseEntity<HttpStatus> edit(@RequestBody StatusFunc StatusFunc){
		if(statusFuncRepository.existsById(StatusFunc.getId())) {
			if(statusFuncRepository.save(StatusFunc) != null) {
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
			if(statusFuncRepository.existsById(id)) {
				statusFuncRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(path = "/status/{id}")
	private ResponseEntity<HttpStatus> alterarStatus(@PathVariable(name = "id") Long id){
		try {
			StatusFunc f = statusFuncRepository.findById(id).get();
			f.setSituacao(!f.isSituacao());	
			if(statusFuncRepository.save(f) != null) {
				return new ResponseEntity<HttpStatus>(HttpStatus.OK);
			}else {
				return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.PRECONDITION_FAILED);
		}
	}
}
