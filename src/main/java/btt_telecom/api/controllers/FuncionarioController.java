package btt_telecom.api.controllers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import btt_telecom.api.dto.FuncionarioDTO;
import btt_telecom.api.models.Funcionario;
import btt_telecom.api.repositories.CidadeRepository;
import btt_telecom.api.repositories.EmpresaRepository;
import btt_telecom.api.repositories.FuncionarioRepository;

@RestController
@RequestMapping(path = "/api/funcionario")
public class FuncionarioController {
	private JSONObject json;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
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
	
	@PostMapping(path = "/cadastro")
	private ResponseEntity<HttpStatus> efetuarCadastro(@RequestBody String body){	
		try {
			json = new JSONObject(body);
			Funcionario func = new Funcionario();
			
			func.setPrimeiro_nome(json.getString("primeiro_nome"));
			func.setUltimo_nome(json.getString("ultimo_nome"));
			func.setUsername(json.getString("username"));
			func.setCpf(json.getString("cpf"));
			func.setRg(json.getString("rg"));
			func.setTelefone(json.getString("telefone"));
			func.setEndereco(json.getString("endereco"));
			func.setKilometragem_por_litro(json.getString("km_por_litro"));
			func.setPlaca(json.getString("placa"));
			func.setLatitude(json.getString("latitude"));
			func.setLongitude(json.getString("longitude"));
			func.setStatus(true);
			if(json.has("id_cidade")) {
				func.setCidade(cidadeRepository.findById(json.getLong("id_cidade")).get());
			}
			
			if(json.has("id_empresa")) {
				func.setEmpresa(empresaRepository.findById(json.getLong("id_empresa")).get());
			}
			
			if(funcionarioRepository.save(func) != null) {
				return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
			}else {
				return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
			}
		}catch(JSONException e) {
			System.out.println(e);
			return new ResponseEntity<HttpStatus>(HttpStatus.PRECONDITION_FAILED);
		}
	}
	
	@PutMapping(path = "/{id}")
	private ResponseEntity<HttpStatus> alterar(@RequestBody String body, @PathVariable(name = "id") Long id){
		try {
			json = new JSONObject(body);
			Funcionario f = funcionarioRepository.findById(id).get();
			if(f != null) {
				f.setPrimeiro_nome(json.getString("primeiro_nome"));
				f.setUltimo_nome(json.getString("ultimo_nome"));
				f.setUsername(json.getString("username"));
				f.setCpf(json.getString("cpf"));
				f.setRg(json.getString("rg"));
				f.setTelefone(json.getString("telefone"));
				f.setEndereco(json.getString("endereco"));
				f.setKilometragem_por_litro(json.getString("km_por_litro"));
				f.setPlaca(json.getString("placa"));
				f.setLatitude(json.getString("latitude"));
				f.setLongitude(json.getString("longitude"));
				f.setStatus(true);
				
				if(json.has("id_cidade")) {
					f.setCidade(cidadeRepository.findById(json.getLong("id_cidade")).get());
				}
				
				if(json.has("id_empresa")) {
					f.setEmpresa(empresaRepository.findById(json.getLong("id_empresa")).get());
				}
				
				funcionarioRepository.save(f);
				return new ResponseEntity<HttpStatus>(HttpStatus.OK);
			}else {
				System.out.println("bad");
				return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
			}
		}catch(JSONException e) {
			System.out.println(e);
			return new ResponseEntity<HttpStatus>(HttpStatus.PRECONDITION_FAILED);
		}
	}
	
	@PutMapping(path = "/status/{id}")
	private ResponseEntity<HttpStatus> alterarStatus(@PathVariable(name = "id") Long id){
		try {
			Funcionario f = funcionarioRepository.findById(id).get();
			f.setStatus(!f.getStatus());	
			if(funcionarioRepository.save(f) != null) {
				return new ResponseEntity<HttpStatus>(HttpStatus.OK);
			}else {
				return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.PRECONDITION_FAILED);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	private ResponseEntity<HttpStatus> excluir(@PathVariable(name = "id") Long id){
		if(funcionarioRepository.existsById(id)) {
			funcionarioRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}else {
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}
	}
}
