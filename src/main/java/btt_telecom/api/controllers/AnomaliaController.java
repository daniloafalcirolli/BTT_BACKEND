package btt_telecom.api.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.models.Anomalia;
import btt_telecom.api.repositories.AnomaliaRepository;

@RestController
@RequestMapping(path = "/api/anomalia")
public class AnomaliaController {

	@Autowired
	private AnomaliaRepository anomaliaRepository;

	@GetMapping
	public ResponseEntity<List<Anomalia>> findAll() {
		try {
			return new ResponseEntity<>(anomaliaRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping
	public ResponseEntity<HttpStatus> create(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);
			Anomalia anomalia;
			
			if (anomaliaRepository.existsByIdServico(json.getLong("id_servico"))) {
				anomalia = anomaliaRepository.findByIdServico(json.getLong("id_servico"));
				
				if (anomalia.getDescricao().equals(json.getString("descricao"))) {
					Integer id = anomalia.getContador();
					id++;
					anomalia.setContador(id);
				} else {
					anomalia = new Anomalia();
					anomalia.setId_funcionario(json.getLong("id_funcionario"));
					anomalia.setDescricao(json.getString("descricao"));
					anomalia.setStatus_servico(json.getString("status_servico"));
					anomalia.setContador(1);

					SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy 00:00:00");
					Date dia = formato.parse(formato.format(new Date()));
					anomalia.setData(dia);
					
					SimpleDateFormat formato2 = new SimpleDateFormat("hh:mm:ss aa");
					Date hora = formato2.parse(formato2.format(new Date()));
					anomalia.setHora(hora);
				}
				
			} else {
				anomalia = new Anomalia();
				anomalia.setId_funcionario(json.getLong("id_funcionario"));
				anomalia.setDescricao(json.getString("descricao"));
				anomalia.setStatus_servico(json.getString("status_servico"));
				anomalia.setContador(1);
				
				SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy 00:00:00");
				Date dia = formato.parse(formato.format(new Date()));
				anomalia.setData(dia);
				
				SimpleDateFormat formato2 = new SimpleDateFormat("hh:mm:ss aa");
				Date hora = formato2.parse(formato2.format(new Date()));
				anomalia.setHora(hora);
			}
				
			if (json.has("id_cliente") && json.has("id_servico")) {
				anomalia.setId_cliente(json.getLong("id_cliente"));
				anomalia.setIdServico(json.getLong("id_servico"));
			}
			
			anomaliaRepository.save(anomalia);
			
			System.out.println("cheguei");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/provedor")
	public ResponseEntity<HttpStatus> createAnomaliaProvedor(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			Anomalia anomalia;
			Date datinha_bonita = new Date();
			
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy 00:00:00");
			String dia = formato.format(datinha_bonita);
			if (json.has("id_funcionario")) {
				if(anomaliaRepository.existsByFuncAndStatus(json.getString("status_servico"), json.getLong("id_funcionario"), dia)) {
					anomalia = anomaliaRepository.findByFuncAndStatus(json.getString("status_servico"), json.getLong("id_funcionario"), dia);
					Integer id = anomalia.getContador();
					id++;
					anomalia.setContador(id);
				}else {
					anomalia = new Anomalia();
					anomalia.setId_funcionario(json.getLong("id_funcionario"));
					anomalia.setDescricao(json.getString("descricao"));
					anomalia.setStatus_servico(json.getString("status_servico"));
					anomalia.setContador(1);
					SimpleDateFormat formato1 = new SimpleDateFormat("dd-MM-yyyy 00:00:00");
					Date dia_set = formato1.parse(formato.format(new Date()));
					anomalia.setData(dia_set);
					
					SimpleDateFormat formato2 = new SimpleDateFormat("hh:mm:ss aa");
					Date hora = formato2.parse(formato2.format(new Date()));
					anomalia.setHora(hora);
				}
				anomaliaRepository.save(anomalia);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping(path = "/relatorio")
	public ResponseEntity<List<Anomalia>> relatorioAnomaliaAll(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);
			List<Anomalia> result;
			if (json.has("data_inicio") && json.has("data_final")) {
				result = anomaliaRepository.findAnomaliasOfAllFuncsInInterval(json.getString("data_inicio"),
						json.getString("data_final"));
				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/relatorio/funcionario")
	public ResponseEntity<List<Anomalia>> relatorioAnomaliaOfFunc(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);
			List<Anomalia> result;
			if (json.has("data_inicio") && json.has("data_final") && json.has("id_funcionario")) {
				result = anomaliaRepository.findAnomaliasOfSingleFuncInInterval(json.getString("data_inicio"),
						json.getString("data_final"), json.getLong("id_funcionario"));
				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
