package btt_telecom.api.modules.funcionario.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	private ResponseEntity<List<FuncionarioRubiList>> findAll(@RequestParam(name = "value", defaultValue = "") String value) throws SQLException{
		if(value.equals("")){
			List<FuncionarioRubiList> result = funcionarioDAO.findAll();
			return new ResponseEntity<>(result, HttpStatus.OK);
		}else {
			List<FuncionarioRubiList> result = funcionarioDAO.search(value);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
		
	@GetMapping(path = "/page")
	private ResponseEntity<Map<String, Object>> searchWithPage(@RequestParam(name = "value", defaultValue = "") String value, @RequestParam(name = "size") Long size, @RequestParam(name = "page") Long page) throws SQLException{
		try {
			if(value.equals("")) {			
				return new ResponseEntity<>(convertListToPage(funcionarioDAO.findAll(), size, page), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(convertListToPage(funcionarioDAO.search(value), size, page), HttpStatus.OK);
			}
		} catch (Exception e) {
			System.out.println(e);
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
	private ResponseEntity<FuncionarioRubi> efetuarLogin(@RequestBody String body) throws SQLException, Exception {
		try {
			json = new JSONObject(body);
			String username = json.getString("username");
			
			FuncionarioRubi funcRubi = funcionarioDAO.existsFuncionarioByUsername(username).get();

			if(funcRubi != null && funcRubi.getPermission().equals("Y")) {
				if(funcionarioRepository.existsByCpf(username)) {
					Funcionario func = funcionarioRepository.findByCpf(funcRubi.getCpf()).get();
					
					if(func.hasPassword()) {
						String password = json.getString("password");
						
						if(password.equals(func.getPassword())){
							return new ResponseEntity<>(funcRubi, HttpStatus.OK);
						} else {
							throw new Exception("Senha informada incorreta.");
						}
					} else {
						String cpf = json.getString("cpf");
						if(cpf.equals(func.getCpf())){
							return new ResponseEntity<>(funcRubi, HttpStatus.OK);
						} else {
							throw new Exception("CPF informado incorretamente.");
						}
					}
				} else {
					String cpf = json.getString("cpf");
					if(funcionarioDAO.login(cpf, username)) {
						return new ResponseEntity<>(funcRubi, HttpStatus.OK);
					} else {
						throw new Exception("CPF informado incorretamente.");
					}
				}
			} else {
				throw new UsernameNotFoundException("Status do funcionário [ " + funcRubi.getCpf() + " ] não está habilitado a utilizar o aplicativo.");
			}
		} catch (JSONException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/register/password")
	public ResponseEntity<HttpStatus> setPassword(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			Funcionario funcionario = new Funcionario();
			funcionario.setCpf(json.getString("cpf"));
			funcionario.setPassword(json.getString("password"));
			
			if(funcionarioRepository.existsByCpf(funcionario.getCpf())) {
				funcionario.setId(funcionarioRepository.findByCpf(funcionario.getCpf()).get().getId());
			}
			
			if(funcionarioRepository.save(funcionario) != null) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/reset/password")
	public ResponseEntity<HttpStatus> resetPassword(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			Funcionario funcionario = new Funcionario();
			funcionario.setCpf(json.getString("cpf"));
			
			if(funcionarioRepository.existsByCpf(funcionario.getCpf())) {
				funcionario.setId(funcionarioRepository.findByCpf(funcionario.getCpf()).get().getId());
				if(funcionario.getPassword().equals(json.getString("old_password"))) {
					funcionario.setPassword(json.getString("new_password"));
					if(funcionarioRepository.save(funcionario) != null) {
						return new ResponseEntity<>(HttpStatus.OK);
					} else {
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/validate/password")
	public ResponseEntity<HttpStatus> validatePasswordRegistered(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			Funcionario funcionario = new Funcionario();
			funcionario.setCpf(json.getString("cpf"));
			
			if(funcionarioRepository.existsByCpf(funcionario.getCpf())) {
				if(!funcionario.getPassword().equals("") || funcionario.getPassword() != null) {
					return new ResponseEntity<>(HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
	}
}
