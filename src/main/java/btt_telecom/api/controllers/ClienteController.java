package btt_telecom.api.controllers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.comparator.Comparators;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.external.Geocoder;
import btt_telecom.api.models.Cliente;
import btt_telecom.api.repositories.ClienteRepository;

@RestController
@RequestMapping(path = "/api/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping
	private ResponseEntity<List<Cliente>> findAll(){
		try {
			return new ResponseEntity<>(clienteRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/page")
	private ResponseEntity<Page<Cliente>> findAllWithPage(Pageable pageable) {
		try {
			return new ResponseEntity<>(clienteRepository.findAll(pageable), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable(name = "id") Long id){
		try {
			if(clienteRepository.existsById(id)) {
				return new ResponseEntity<>(clienteRepository.findById(id).get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping
	private ResponseEntity<Cliente> save(@RequestBody Cliente cliente){
		try {
			if(clienteRepository.save(cliente) != null) {
				return new ResponseEntity<>(cliente, HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/import")
	private ResponseEntity<HttpStatus> importCsv(@RequestBody String body){
		try {
			JSONArray jsonarray = new JSONArray(body);
			
			Geocoder g = new Geocoder();
			for(int i = 0; i < jsonarray.length(); i++) {
				JSONObject json = new JSONObject(jsonarray.get(i).toString());
				System.out.println(jsonarray.get(i).toString());
				Cliente cliente = new Cliente();
				cliente.setCnpj(json.getString("cnpj"));
				cliente.setCpf(json.getString("cpf"));
				cliente.setNome(json.getString("nome").toUpperCase());
				cliente.setEndereco(json.getString("endereco").toUpperCase());
				cliente.setContrato(json.getString("contrato"));
				JSONObject j = g.GeocodeSync(json.getString("endereco"));
				cliente.setLatitude(j.getString("lat"));
				cliente.setLongitude(j.getString("lng"));
				clienteRepository.save(cliente);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/validate")
	private ResponseEntity<Cliente> existsByCpfOrCnpj(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			if(json.has("cpf") && json.has("cnpj")) {
				if(clienteRepository.existsByCpf(json.getString("cpf")) || clienteRepository.existsByCnpj(json.getString("cnpj"))) {
					return new ResponseEntity<>(clienteRepository.findByCpf(json.getString("cpf")),HttpStatus.FOUND);
				}else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			}else if(json.has("cpf")) {
				if(clienteRepository.existsByCpf(json.getString("cpf"))) {
					return new ResponseEntity<>(clienteRepository.findByCpf(json.getString("cpf")), HttpStatus.FOUND);
				}else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			}else if(json.has("cnpj")) {
				if(clienteRepository.existsByCnpj(json.getString("cnpj"))) {
					return new ResponseEntity<>(clienteRepository.findByCnpj(json.getString("cnpj")), HttpStatus.FOUND);
				}else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	private ResponseEntity<HttpStatus> put(@RequestBody Cliente cliente){
		try {
			if(clienteRepository.existsById(cliente.getId())) {
				clienteRepository.save(cliente);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	private ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id){
		try {
			if(clienteRepository.existsById(id)){
				clienteRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
}
