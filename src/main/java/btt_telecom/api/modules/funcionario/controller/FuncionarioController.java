package btt_telecom.api.modules.funcionario.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import btt_telecom.api.modules.funcionario.response.ResponseLogin;


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
	private ResponseLogin efetuarLogin(@RequestBody String body) throws SQLException, Exception {
		ResponseLogin response = new ResponseLogin();

		try {
			json = new JSONObject(body);
			String username = json.getString("username");

			FuncionarioRubi funcRubi = funcionarioDAO.existsFuncionarioByUsername(username);

			if(funcRubi != null) {
				if(funcRubi.getPermission().equals("Y")) {
					if(funcionarioRepository.existsByCpf(funcRubi.getCpf())) {
						Funcionario func = funcionarioRepository.findByCpf(funcRubi.getCpf()).get();
						
						if(func.getPassword() != null && !func.getPassword().equals("")) {
							if(json.has("password")) {
								String password = json.getString("password");
								
								if(func.getPassword().equals(password)){
									response = new ResponseLogin(200l, "Login realizado com sucesso", funcRubi);
									return response;
								} else {
									response = new ResponseLogin(403l, "Senha informada incorretamente.");
									return response;
								}
							} else {
								response = new ResponseLogin(403l, "Use a senha para realizar o login.");
							}
							
						} else {
							if(json.has("cpf")) {
								String cpf = json.getString("cpf");
								if(cpf.equals(func.getCpf())){
									response = new ResponseLogin(200l, "Login realizado com sucesso", funcRubi);
									return response;
								} else {
									response = new ResponseLogin(403l, "CPF informado incorretamente.");
									return response;
								}
							} else {
								response = new ResponseLogin(403l, "Use o cpf para realizar o login.");
							}
						}
					} else {
						if(json.has("cpf")) {
						String cpf = json.getString("cpf");
							if(funcionarioDAO.login(cpf, username)) {
								response = new ResponseLogin(200l, "Login realizado com sucesso", funcRubi);
							} else {
								response = new ResponseLogin(403l, "CPF informado incorretamente.");
								return response;
							}
						} else {
							response = new ResponseLogin(403l, "Use o cpf para realizar o login.");
						}
					}
				} else {
					response = new ResponseLogin(403l, "Status do funcionário [ " + funcRubi.getNome() + " ] não está habilitado a entrar no aplicativo.");
					return response;
				}
			} else {
				response = new ResponseLogin(403l, "Username do funcionário não encontrado");
				return response;
			}
		} catch (JSONException e) {
			System.out.println(e);
			response = new ResponseLogin(500l, "Ocorreu um erro.");
			return response;
		}
	
		return response;
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
			String cpf = json.getString("cpf");
			Funcionario funcionario = new Funcionario();
			
			if(funcionarioRepository.existsByCpf(cpf)) {
				funcionario = funcionarioRepository.findByCpf(cpf).get();
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
			e.printStackTrace();
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
