package btt_telecom.api.modules.materiais.controller;

import java.util.List;
import org.springframework.data.domain.Pageable;

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

import btt_telecom.api.modules.materiais.model.MaterialAplicadoBase;
import btt_telecom.api.modules.materiais.repository.MaterialAplicadoBaseRepository;

@RestController
@RequestMapping(path = "/api/material/aplicado")
public class MaterialAplicadoBaseController {

	@Autowired
	private MaterialAplicadoBaseRepository aplicadoBaseRepository;

	@GetMapping(path = "/all")
	public ResponseEntity<List<MaterialAplicadoBase>> findAll() {
		return new ResponseEntity<>(aplicadoBaseRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/page")
	public ResponseEntity<Page<MaterialAplicadoBase>> findAllWithPage(Pageable pageable) {
		try {
			return new ResponseEntity<>(aplicadoBaseRepository.findAll(pageable), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/search")
	public ResponseEntity<Page<MaterialAplicadoBase>> search(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);
			List<MaterialAplicadoBase> result = aplicadoBaseRepository.search(json.getString("value"));
			Page<MaterialAplicadoBase> page = new PageImpl<>(result);
			return new ResponseEntity<>(page, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<MaterialAplicadoBase> findById(@PathVariable Long id) {
		return new ResponseEntity<>(aplicadoBaseRepository.findById(id).get(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<HttpStatus> save(@RequestBody MaterialAplicadoBase materialAplicadoBase) {
		aplicadoBaseRepository.save(materialAplicadoBase);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(path = "/status/{id}")
	private ResponseEntity<HttpStatus> alterarStatus(@PathVariable(name = "id") Long id) {
		try {
			MaterialAplicadoBase material = aplicadoBaseRepository.findById(id).get();
			material.setHas_serial(!material.isHas_serial());
			if (aplicadoBaseRepository.save(material) != null) {
				return new ResponseEntity<HttpStatus>(HttpStatus.OK);
			} else {
				return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.PRECONDITION_FAILED);
		}
	}

}
