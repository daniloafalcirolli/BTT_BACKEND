package btt_telecom.api.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import btt_telecom.api.dto.RotaDTO;
import btt_telecom.api.models.Rota;
import btt_telecom.api.repositories.FuncionarioRepository;
import btt_telecom.api.repositories.RotaRepository;

@RestController
@RequestMapping(path = "/api/rotas")
public class RotasController {

	@Autowired
	private RotaRepository rotaRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@GetMapping
	public ResponseEntity<List<Rota>> findAll(){
		try {
			return new ResponseEntity<>(rotaRepository.findAll(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/page")
	public ResponseEntity<Page<Rota>> findAllWithPage(Pageable pageable){
		try {
			return new ResponseEntity<>(rotaRepository.findAll(pageable), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Rota> findById(@PathVariable(name = "id") Long id){
		try {
			if(rotaRepository.existsById(id)) {
				return new ResponseEntity<>(rotaRepository.findById(id).get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/relatorio")
	public ResponseEntity<List<RotaDTO>> findByFuncAndDate(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			List<Rota> rotas = rotaRepository.findRotasByFuncAndData(json.getString("data"), json.getLong("id_func"));
			List<RotaDTO> rotasDTO = new ArrayList<>();
			rotas.forEach(x -> {
				rotasDTO.add(new RotaDTO(x));
			});
			return new ResponseEntity<>(rotasDTO, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/registrar")
	public ResponseEntity<HttpStatus> register(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			
			Rota r = new Rota();
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy 00:00:00");
			Date dia = formato.parse(formato.format(new Date()));
			r.setData(dia);
			
			SimpleDateFormat formato2 = new SimpleDateFormat("hh:mm:ss");
			Date hora = formato2.parse(formato2.format(new Date()));
			r.setHora(hora);
			
			r.setFuncionario(funcionarioRepository.findById(json.getLong("id_func")).get());
			r.setLatitude(json.getString("latitude"));
			r.setLongitude(json.getString("longitude"));
			
			if(rotaRepository.save(r) != null) {
				return new ResponseEntity<>( HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
