package btt_telecom.api.modules.materiais.controller;

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

import btt_telecom.api.config.exception.ApplicationException;
import btt_telecom.api.config.general.AbstractMethods;
import btt_telecom.api.modules.materiais.model.MaterialRetiradoBase;
import btt_telecom.api.modules.materiais.repository.MaterialRetiradoBaseRepository;

@RestController
@RequestMapping(path = "/api/material/retirado")
public class MaterialRetiradoBaseController extends AbstractMethods {
	
	@Autowired
	private MaterialRetiradoBaseRepository retiradoBaseRepository;
	
	@GetMapping
	public ResponseEntity<List<MaterialRetiradoBase>> findAll() {
		try {
			return new ResponseEntity<>(retiradoBaseRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/all/search")
	public ResponseEntity<List<MaterialRetiradoBase>> allToSearch(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);
			List<MaterialRetiradoBase> result = retiradoBaseRepository.search(json.getString("value"));
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/page")
	public ResponseEntity<Map<String, Object>> findAllWithPage(@RequestParam(name = "value", defaultValue = "") String value, @RequestParam(name = "size") Long size, @RequestParam(name = "page") Long page) {
		try {
			if(value.equals("")) {
				return new ResponseEntity<>(convertListToPage(retiradoBaseRepository.findAll(), size, page), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(convertListToPage(retiradoBaseRepository.search(value.toUpperCase()), size, page), HttpStatus.OK);
			}
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
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<HttpStatus> edit(@RequestBody MaterialRetiradoBase materialRetiradoBase) {
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
	
	@DeleteMapping(path = "/{id}")
	private ResponseEntity<HttpStatus> deleteMaterial(@PathVariable(name = "id") Long id) throws ApplicationException{
		try {
			if(retiradoBaseRepository.existsById(id)) {
				MaterialRetiradoBase material = retiradoBaseRepository.findById(id).get();
				material.setDelt_flg(true);
				if(retiradoBaseRepository.save(material) != null) {
					return new ResponseEntity<>(HttpStatus.OK);
				} else {
					throw new ApplicationException(HttpStatus.BAD_REQUEST, "Ocorreu um erro ao excluir o material.");
				}
			} else {
				throw new ApplicationException(HttpStatus.BAD_REQUEST, "Material não existente");
			}
		} catch (Exception e) {
			throw new ApplicationException(HttpStatus.BAD_REQUEST, "Ocorreu um erro.");
		}
	}

}
