package btt_telecom.api.modules.materiais.controller;

import org.springframework.data.domain.Pageable;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.modules.materiais.model.MaterialRetiradoBase;
import btt_telecom.api.modules.materiais.repository.MaterialRetiradoBaseRepository;

@RestController
@RequestMapping(path = "/api/material/retirado")
public class MaterialRetiradoController {
	
	@Autowired
	private MaterialRetiradoBaseRepository retiradoBaseRepository;
	
	@GetMapping(path = "/page")
	public ResponseEntity<Page<MaterialRetiradoBase>> findAllWithPage(Pageable pageable) {
		try {
			return new ResponseEntity<>(retiradoBaseRepository.findAll(pageable), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	

	@PostMapping(path = "/search")
	public ResponseEntity<Page<MaterialRetiradoBase>> search(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);
			List<MaterialRetiradoBase> result = retiradoBaseRepository.search(json.getString("value"));
			Page<MaterialRetiradoBase> page = new PageImpl<>(result);
			return new ResponseEntity<>(page, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<MaterialRetiradoBase> findById(@PathVariable Long id) {
		return new ResponseEntity<>(retiradoBaseRepository.findById(id).get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<HttpStatus> save(@RequestBody MaterialRetiradoBase materialRetiradoBase) {
		retiradoBaseRepository.save(materialRetiradoBase);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(path = "/status/{id}")
	private ResponseEntity<HttpStatus> alterarStatus(@PathVariable(name = "id") Long id) {
		try {
			MaterialRetiradoBase material = retiradoBaseRepository.findById(id).get();
			material.setHas_serial(!material.isHas_serial());
			if (retiradoBaseRepository.save(material) != null) {
				return new ResponseEntity<HttpStatus>(HttpStatus.OK);
			} else {
				return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.PRECONDITION_FAILED);
		}
	}


}
