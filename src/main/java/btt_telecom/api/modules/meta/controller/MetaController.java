package btt_telecom.api.modules.meta.controller;


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

import btt_telecom.api.modules.meta.model.Meta;
import btt_telecom.api.modules.meta.repository.MetaRepository;

@RestController
@RequestMapping(path = "/api/meta")
public class MetaController {
	
	@Autowired
	private MetaRepository metaRepository;

	@GetMapping(path = "/page")
	private ResponseEntity<Page<Meta>> findAllWithPage(Pageable pageable){
		try {
			return new ResponseEntity<>(metaRepository.findAll(pageable), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/search")
	private ResponseEntity<Page<Meta>> search(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			List<Meta> result = metaRepository.search(json.getString("value"));
			Page<Meta> page = new PageImpl<Meta>(result);
			return new ResponseEntity<>(page, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}
	}
	
	@PostMapping
	private ResponseEntity<HttpStatus> save(@RequestBody Meta meta){
		try {
			if(metaRepository.save(meta) != null) {
				return new ResponseEntity<>(HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/{id}")
	private ResponseEntity<Meta> getById(@PathVariable(name = "id") Long id){
		try {
			if(metaRepository.existsById(id)) {
				return new ResponseEntity<>(metaRepository.findById(id).get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/key/{key}")
	private ResponseEntity<Meta> getByKey(@PathVariable(name = "key") String key){
		try {
			if(metaRepository.existsByMetaKey(key)) {
				return new ResponseEntity<>(metaRepository.findByMetaKey(key).get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	private ResponseEntity<HttpStatus> edit(@RequestBody Meta meta){
		try {
			if(metaRepository.existsById(meta.getId())) {
				if(metaRepository.save(meta) != null) {
					return new ResponseEntity<>(HttpStatus.OK);
				}else {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	private ResponseEntity<HttpStatus> deleteById(@PathVariable(name = "id") Long id){
		try {
			if(metaRepository.existsById(id)) {
				metaRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
