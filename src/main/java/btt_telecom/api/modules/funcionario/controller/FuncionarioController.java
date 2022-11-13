package btt_telecom.api.modules.funcionario.controller;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.config.general.AbstractMethods;
import btt_telecom.api.modules.funcionario.dto.FuncionarioRubi;
import btt_telecom.api.modules.funcionario.dto.FuncionarioRubiList;
import btt_telecom.api.modules.funcionario.external.FuncionarioDAO;
import btt_telecom.api.modules.funcionario.model.Funcionario;
import btt_telecom.api.modules.funcionario.repository.FuncionarioRepository;


@RestController
@RequestMapping(path = "/api/funcionario")
public class FuncionarioController extends AbstractMethods{
	private JSONObject json;
	
	private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@GetMapping
	private ResponseEntity<List<FuncionarioRubiList>> findAll() throws SQLException{
		List<FuncionarioRubiList> result = funcionarioDAO.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping(path = "/search")
	private ResponseEntity<List<FuncionarioRubiList>> searchFindAll(@RequestBody String body) throws SQLException{
		try {
			JSONObject json = new JSONObject(body);
			List<FuncionarioRubiList> result = funcionarioDAO.search(json.getString("value"));
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/page")
	private ResponseEntity<Page<FuncionarioRubiList>> findAllWithPage(Pageable pageable) throws SQLException{
		try {
			Page<FuncionarioRubiList> page = convertListToPage(funcionarioDAO.findAll(), pageable);
			return new ResponseEntity<>(page, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/page/search")
	private ResponseEntity<Page<FuncionarioRubiList>> searchWithPage(@RequestParam(name = "value") String value, @RequestParam(name = "size") int size, @RequestParam(name = "page") int page) throws SQLException{
		try {
			List<FuncionarioRubiList> result = funcionarioDAO.search(value);
			Page<FuncionarioRubiList> pageResult = convertListToPage(result, size, page);
			return new ResponseEntity<>(pageResult, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/{cpf}")
	private ResponseEntity<FuncionarioRubi> findByCpf(@PathVariable(name = "cpf") String cpf) {
		try {
			if(funcionarioDAO.existsFuncionarioByCpf(cpf)) {
				return new ResponseEntity<>(funcionarioDAO.findByCpf(cpf), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping
	private ResponseEntity<HttpStatus> editAndSave(@RequestBody Funcionario funcionario){
		try {
			if(funcionarioRepository.existsByCpf(funcionario.getCpf())) {
				funcionario.setId(funcionarioRepository.findByCpf(funcionario.getCpf()).get().getId());
			}

			if(funcionarioRepository.save(funcionario) != null) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/login")
	private ResponseEntity<FuncionarioRubi> efetuarLogin(@RequestBody String body) throws SQLException {
		try {
			json = new JSONObject(body);
			String cpf = json.getString("cpf");
			String username = json.getString("username").toLowerCase();
			
			if(funcionarioDAO.login(cpf, username)) {
				return new ResponseEntity<>(funcionarioDAO.findByCpf(cpf), HttpStatus.OK);
			} else{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (JSONException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
