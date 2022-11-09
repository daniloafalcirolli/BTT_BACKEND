package btt_telecom.api.modules.funcionario.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.config.general.AbstractMethods;
import btt_telecom.api.modules.funcionario.external.StatusFuncionarioDAO;
import btt_telecom.api.modules.funcionario.model.StatusFunc;
import btt_telecom.api.modules.funcionario.repository.StatusFuncRepository;

@RestController
@RequestMapping(path = "/api/status/func")
public class StatusFuncController extends AbstractMethods{
	@Autowired
	private StatusFuncRepository statusFuncRepository;
	
	private StatusFuncionarioDAO statusDAO = new StatusFuncionarioDAO();
	
	@GetMapping("/rubi")
	private ResponseEntity<List<StatusFunc>> findAllFromRubiWithoutPage(){
		try {
			return new ResponseEntity<>(statusDAO.findAll(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/rubi/page")
	private ResponseEntity<Page<StatusFunc>> findAllFromRubiWithPage(Pageable pageable){
		try {
			return new ResponseEntity<>(convertListToPage(statusDAO.findAll(), pageable) , HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/page")
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
	
	@PostMapping
	private ResponseEntity<HttpStatus> save(@RequestBody StatusFunc status){
		try {
			if(statusFuncRepository.save(status) != null) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
			}
		} catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
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
}
