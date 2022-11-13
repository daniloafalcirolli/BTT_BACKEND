package btt_telecom.api.modules.funcionario.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/page")
	private ResponseEntity<Map<String, Object>> findAllWithPage(@RequestParam(name = "value", defaultValue = "") String value, @RequestParam(name = "size") Long size, @RequestParam(name = "page") Long page){
		try {
			if(value.equals("")) {
				return new ResponseEntity<>(convertListToPage(statusFuncRepository.findAll(), size, page) , HttpStatus.OK);			
			} else {
				return new ResponseEntity<>(convertListToPage(statusFuncRepository.search(value.toUpperCase()), size, page) , HttpStatus.OK);			
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/rubi")
	private ResponseEntity<List<StatusFunc>> findAllFromRubiWithoutPage(){
		try {
			return new ResponseEntity<>(statusDAO.findAll(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/rubi/page")
	private ResponseEntity<Map<String, Object>> findAllFromRubiWithPage(@RequestParam(name = "value", defaultValue = "") String value, @RequestParam(name = "size") Long size, @RequestParam(name = "page") Long page){
		try {
			if(value.equals("")) {
				return new ResponseEntity<>(convertListToPage(statusDAO.findAll(), size, page) , HttpStatus.OK);
			} else {
				return new ResponseEntity<>(convertListToPage(statusDAO.search(value), size, page) , HttpStatus.OK);
			}
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
