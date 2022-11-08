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
	private ResponseEntity<List<FuncionarioRubiList>> searchFindAll() throws SQLException{
		List<FuncionarioRubiList> result = funcionarioDAO.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(path = "/page")
	private ResponseEntity<Page<FuncionarioRubiList>> findAllWithPage(Pageable pageable) throws SQLException{
		Page<FuncionarioRubiList> page = convertListToPage(funcionarioDAO.findAll(), pageable);
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
	
	@GetMapping(path = "/page/search")
	private ResponseEntity<Page<FuncionarioRubiList>> searchWithPage(Pageable pageable) throws SQLException{
		FuncionarioDAO func = new FuncionarioDAO();
		List<FuncionarioRubiList> result = func.findAll();
		Page<FuncionarioRubiList> page = convertListToPage(result, pageable);
		return new ResponseEntity<>(page, HttpStatus.OK);
	}

	@GetMapping(path = "/{cpf}")
	private ResponseEntity<FuncionarioRubi> findById(@PathVariable(name = "cpf") String cpf) {
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
	
	@PostMapping(path = "/login")
	private ResponseEntity<Funcionario> efetuarLogin(@RequestBody String body) {
		try {
			json = new JSONObject(body);
			
			if(funcionarioRepository.findByUsernameCpf(json.getString("username"), json.getString("cpf")) != null) {
				if(funcionarioRepository.findByUsernameCpf(json.getString("username"), json.getString("cpf")).getStatus()){
					return new ResponseEntity<>(funcionarioRepository.findByUsernameCpf(json.getString("username"), json.getString("cpf")), HttpStatus.OK);
				}else{
					return new ResponseEntity<Funcionario>(HttpStatus.NOT_ACCEPTABLE);
				}
			}else{
				return new ResponseEntity<Funcionario>(HttpStatus.NOT_FOUND);
			}
		} catch (JSONException e) {
			return new ResponseEntity<Funcionario>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
