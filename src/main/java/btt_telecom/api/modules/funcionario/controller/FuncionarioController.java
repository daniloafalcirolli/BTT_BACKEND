package btt_telecom.api.modules.funcionario.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONException;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.modules.funcionario.dto.FuncionarioDTO;
import btt_telecom.api.modules.funcionario.dto.FuncionarioRubi;
import btt_telecom.api.modules.funcionario.external.FuncionarioDAO;
import btt_telecom.api.modules.funcionario.model.Funcionario;
import btt_telecom.api.modules.funcionario.repository.FuncionarioRepository;
import btt_telecom.api.repositories.CidadeRepository;
import btt_telecom.api.repositories.EmpresaRepository;

@RestController
@RequestMapping(path = "/api/funcionario")
public class FuncionarioController {
	private JSONObject json;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@GetMapping(path = "/teste")
	private ResponseEntity<Page<FuncionarioRubi>> findAllFromView(Pageable pageable) throws SQLException{
		FuncionarioDAO func = new FuncionarioDAO();
		List<FuncionarioRubi> result = func.findAll();
		Integer size = result.size();
		result = result.stream().skip(pageable.getPageSize() * pageable.getPageNumber()).limit(pageable.getPageSize()).collect(Collectors.toList());

		Page<FuncionarioRubi> page = new PageImpl<>(result, pageable, size);
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
	
	@GetMapping
	private ResponseEntity<List<FuncionarioDTO>> findAll(){
		try {
			List<Funcionario> result = funcionarioRepository.findAll();
			List<FuncionarioDTO> funcionarios = new ArrayList<>();
			result.forEach(x -> {
				funcionarios.add(new FuncionarioDTO(x));
			});
			
			return new ResponseEntity<>(funcionarios, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/all/search")
	private ResponseEntity<List<FuncionarioDTO>> findAll(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			List<Funcionario> result = funcionarioRepository.search(json.getString("value"));
			List<FuncionarioDTO> resultDTO = result.stream().map(x -> new FuncionarioDTO(x)).collect(Collectors.toList());
			return new ResponseEntity<>(resultDTO, HttpStatus.OK);
		} catch (Exception e) {
			
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/page")
	private ResponseEntity<Page<FuncionarioDTO>> findAllWithPage(Pageable pageable){
		try {
			Page<Funcionario> result = funcionarioRepository.findAll(pageable);
			Page<FuncionarioDTO> page = result.map(x -> new FuncionarioDTO(x));
			
			return new ResponseEntity<>(page, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/search")
	private ResponseEntity<Page<FuncionarioDTO>> filter(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			List<Funcionario> result = funcionarioRepository.search(json.getString("value"));
			List<FuncionarioDTO> resultDTO = result.stream().map(x -> new FuncionarioDTO(x)).collect(Collectors.toList());
			Page<FuncionarioDTO> page = new PageImpl<>(resultDTO);			
			return new ResponseEntity<>(page, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/{id}")
	private ResponseEntity<Funcionario> findById(@PathVariable(name = "id") Long id) {
		try {
			if(funcionarioRepository.existsById(id)) {
				return new ResponseEntity<>(funcionarioRepository.findById(id).get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
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
