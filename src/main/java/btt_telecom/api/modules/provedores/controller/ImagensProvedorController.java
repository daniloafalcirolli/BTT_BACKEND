package btt_telecom.api.modules.provedores.controller;

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

import btt_telecom.api.modules.provedores.model.ImagemProvedor;
import btt_telecom.api.modules.provedores.model.Provedor;
import btt_telecom.api.modules.provedores.repository.ImagensProvedorRepository;
import btt_telecom.api.modules.provedores.repository.ProvedorRepository;

@RestController
@RequestMapping(path = "/api/fotos/provedor")
public class ImagensProvedorController {

	@Autowired
	private ImagensProvedorRepository imagensProvedorRepository;
	
	@Autowired
	private ProvedorRepository provedorRepository;
	
	@GetMapping
	private ResponseEntity<List<ImagemProvedor>> findAll(){
		try {
			return new ResponseEntity<>(imagensProvedorRepository.findAll(), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping(path = "/all")
	private ResponseEntity<List<ImagemProvedor>> findAllWithoutPagination(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			return new ResponseEntity<>(imagensProvedorRepository.search(json.getString("value")), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/page")
	private ResponseEntity<Page<ImagemProvedor>> findAllWithPage(Pageable pageable){
		try {
			return new ResponseEntity<>(imagensProvedorRepository.findAll(pageable), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/search")
	private ResponseEntity<Page<ImagemProvedor>> search(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			List<ImagemProvedor> result = imagensProvedorRepository.search(json.getString("value"));
			Page<ImagemProvedor> page = new PageImpl<>(result);
			return new ResponseEntity<>(page, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/{id}")
	private ResponseEntity<ImagemProvedor> findById(@PathVariable(name = "id") Long id){
		try {
			if(imagensProvedorRepository.existsById(id)) {
				return new ResponseEntity<>(imagensProvedorRepository.findById(id).get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping
	private ResponseEntity<HttpStatus> save(@RequestBody ImagemProvedor imagemProvedor){
		try {
			if(imagensProvedorRepository.save(imagemProvedor) != null) {
				return new ResponseEntity<>(HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	private ResponseEntity<HttpStatus> edit(@RequestBody ImagemProvedor imagemProvedor){
		try {
			if(imagensProvedorRepository.existsById(imagemProvedor.getId())) {
				if(imagensProvedorRepository.save(imagemProvedor) != null) {
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
	private ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id){
		try {
			if(imagensProvedorRepository.existsById(id)) {
				provedorRepository.findAll().forEach(x -> {
					Provedor p = x;
					p.getImagens().remove(imagensProvedorRepository.findById(id).get());
					provedorRepository.save(p);
				});
				imagensProvedorRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
