package btt_telecom.api.controllers;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.models.Empresa;
import btt_telecom.api.repositories.EmpresaRepository;

@RestController
@RequestMapping(path = "/api/empresa")
public class EmpresaController {
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@GetMapping("/page")
	private ResponseEntity<Page<Empresa>> findAllWithPage(Pageable pageable){
		try {
			return new ResponseEntity<>(empresaRepository.findAll(pageable), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/search")
	private ResponseEntity<Page<Empresa>> search(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			List<Empresa> result = empresaRepository.search(json.getString("value"));
			Page<Empresa> page = new PageImpl<>(result);
			return new ResponseEntity<>(page, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
