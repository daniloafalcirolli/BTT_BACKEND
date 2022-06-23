package btt_telecom.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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

import btt_telecom.api.dto.ProvedorDTO;
import btt_telecom.api.models.CamposProvedor;
import btt_telecom.api.models.Provedor;
import btt_telecom.api.models.ServicoProvedor;
import btt_telecom.api.repositories.CamposProvedorRepository;
import btt_telecom.api.repositories.ProvedorRepository;
import btt_telecom.api.repositories.ServicoProvedorRepository;

@RestController
@RequestMapping(path = "/api/provedor")
public class ProvedorController {

	@Autowired
	private ServicoProvedorRepository servicoProvedorRepository;
	
	@Autowired
	private ProvedorRepository provedorRepository;
	
	@Autowired
	private CamposProvedorRepository camposProvedorRepository;
	
	@GetMapping
	public ResponseEntity<List<ProvedorDTO>> findAll(){
		try {
			List<Provedor> result = provedorRepository.findAll();
			List<ProvedorDTO> provedores = new ArrayList<>();
			result.forEach(x -> {
				provedores.add(new ProvedorDTO(x));
			});
			
			return new ResponseEntity<>(provedores, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/page")
	public ResponseEntity<Page<ProvedorDTO>> findAllWithPage(Pageable pageable){
		try {
			Page<Provedor> result = provedorRepository.findAll(pageable);
			Page<ProvedorDTO> page = result.map(x -> new ProvedorDTO(x));
			
			return new ResponseEntity<>(page, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Provedor> findById(@PathVariable(name = "id") Long id){
		try {
			if(provedorRepository.existsById(id)) {
				return new ResponseEntity<>(provedorRepository.findById(id).get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/servico/{id}")
	public ResponseEntity<ServicoProvedor> findServicoById(@PathVariable(name = "id") Long id){
		try {
			if(servicoProvedorRepository.existsById(id)) {
				return new ResponseEntity<>(servicoProvedorRepository.findById(id).get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/{id}/page")
	public ResponseEntity<List<ServicoProvedor>> pageFindById(@PathVariable(name = "id") Long id){
		try {
			if(provedorRepository.existsById(id)){
				return new ResponseEntity<>(provedorRepository.findById(id).get().getServicos(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		 
	}
	
	@PostMapping(path = "/create")
	public ResponseEntity<HttpStatus> save(@RequestBody ProvedorDTO provedorDTO){
		try {
			if(provedorRepository.save(new Provedor(provedorDTO)) != null) {
				return new ResponseEntity<>(HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/add")
	public ResponseEntity<HttpStatus> addServicoProvedor(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			ServicoProvedor sp = new ServicoProvedor(json.getString("servico"), json.getLong("id_provedor"));
			
			servicoProvedorRepository.save(sp);
			
			Provedor p = provedorRepository.findById(json.getLong("id_provedor")).get();
			List<ServicoProvedor> list = p.getServicos();
			list.add(sp);
			p.setServicos(list);
			
			provedorRepository.save(p);
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/campos/{id}")
	public ResponseEntity<HttpStatus> editCampos(@RequestBody String body, @PathVariable(name = "id") Long id){
		try {
			if(provedorRepository.existsById(id)) {
				JSONArray jsonarray = new JSONArray(body);
				List<CamposProvedor> list = new ArrayList<CamposProvedor>();
				JSONObject obj;
				for(int i = 0; i < jsonarray.length(); i++) {
					obj = new JSONObject(jsonarray.get(i).toString());
					list.add(camposProvedorRepository.findById(obj.getLong("id")).get());
				}
				
				Provedor p = provedorRepository.findById(id).get();
				p.setCampos(list);
				
				if(provedorRepository.save(p) != null) {
					return new ResponseEntity<>(HttpStatus.OK);
				}else {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(path = "/edit")
	public ResponseEntity<HttpStatus> edit(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			Provedor p = provedorRepository.findById(json.getLong("id")).get();
			p.setName(json.getString("name"));
			
			provedorRepository.save(p);			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(path = "/servico/edit")
	public ResponseEntity<HttpStatus> editServico(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			ServicoProvedor sp = servicoProvedorRepository.findById(json.getLong("id_serv")).get();
			sp.setServico(json.getString("servico"));
			
			servicoProvedorRepository.save(sp);			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	
	@DeleteMapping(path = "/{id_provedor}/{id_servico}")
	public ResponseEntity<HttpStatus> deleteServicoProvedor(@PathVariable(name = "id_provedor") Long id_provedor, @PathVariable(name = "id_servico") Long id_servico){
		try {
			if(provedorRepository.existsById(id_provedor)) {
				if(servicoProvedorRepository.existsById(id_servico)) {
					Provedor p = provedorRepository.findById(id_provedor).get();
					ServicoProvedor sp = servicoProvedorRepository.findById(id_servico).get();
					p.getServicos().remove(sp);
					provedorRepository.save(p);
					servicoProvedorRepository.deleteById(id_servico);
					return new ResponseEntity<>(HttpStatus.OK);
				}else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}			
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id){
		try {
			if(provedorRepository.existsById(id)) {
				Provedor p = provedorRepository.findById(id).get();
				p.getServicos().clear();
				provedorRepository.save(p);	
				provedorRepository.deleteById(id);
				
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}	
}
