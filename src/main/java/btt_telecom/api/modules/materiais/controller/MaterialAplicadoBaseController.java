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
import btt_telecom.api.modules.materiais.model.MaterialAplicadoBase;
import btt_telecom.api.modules.materiais.repository.MaterialAplicadoBaseRepository;

@RestController
@RequestMapping(path = "/api/material/aplicado")
public class MaterialAplicadoBaseController extends AbstractMethods{

	@Autowired
	private MaterialAplicadoBaseRepository aplicadoBaseRepository;

	@GetMapping
	public ResponseEntity<List<MaterialAplicadoBase>> findAll() {
		return new ResponseEntity<>(aplicadoBaseRepository.findAll().stream().filter(x -> !x.isDelt_flg()).toList(), HttpStatus.OK);
	}
	
	@PostMapping(path = "/all/search")
	public ResponseEntity<List<MaterialAplicadoBase>> allToSearch(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);
			List<MaterialAplicadoBase> result = aplicadoBaseRepository.search(json.getString("value")).stream().filter(x -> !x.isDelt_flg()).toList();
			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/page")
	public ResponseEntity<Map<String, Object>> findAllWithPage(@RequestParam(name = "value", defaultValue = "") String value, @RequestParam(name = "size") Long size, @RequestParam(name = "page") Long page) {
		try {
			if(value.equals("")) {
				return new ResponseEntity<>(convertListToPage(aplicadoBaseRepository.findAll(), size, page), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(convertListToPage(aplicadoBaseRepository.search(value), size, page), HttpStatus.OK);
			}
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
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<HttpStatus> edit(@RequestBody MaterialAplicadoBase materialAplicadoBase) {
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
	
	@DeleteMapping(path = "/{id}")
	private ResponseEntity<HttpStatus> deleteMaterial(@PathVariable(name = "id") Long id) throws ApplicationException{
		try {
			if(aplicadoBaseRepository.existsById(id)) {
				MaterialAplicadoBase material = aplicadoBaseRepository.findById(id).get();
				material.setDelt_flg(true);
				if(aplicadoBaseRepository.save(material) != null) {
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
