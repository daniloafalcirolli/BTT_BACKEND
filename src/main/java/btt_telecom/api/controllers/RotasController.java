package btt_telecom.api.controllers;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.dto.RotaDTO;
import btt_telecom.api.models.Cidade;
import btt_telecom.api.models.Rota;
import btt_telecom.api.modules.funcionario.dto.FuncionarioConsumo;
import btt_telecom.api.modules.funcionario.dto.FuncionarioRubi;
import btt_telecom.api.modules.funcionario.external.FuncionarioDAO;
import btt_telecom.api.repositories.CidadeRepository;
import btt_telecom.api.repositories.RotaRepository;

@RestController
@RequestMapping(path = "/api/rotas")
public class RotasController {

	@Autowired
	private RotaRepository rotaRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;

	private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

	@PostMapping(path = "/registrar")
	public ResponseEntity<HttpStatus> register(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);

			Rota r = new Rota();
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy 00:00:00");
			Date dia = formato.parse(formato.format(new Date()));
			r.setData(dia);

			SimpleDateFormat formato2 = new SimpleDateFormat("hh:mm:ss aa");
			Date hora = formato2.parse(formato2.format(new Date()));
			r.setHora(hora);
			r.setCpf_funcionario(json.getString("cpf_funcionario"));
			r.setLatitude(json.getString("latitude"));
			r.setLongitude(json.getString("longitude"));

			FuncionarioRubi fr = funcionarioDAO.findByCpf(r.getCpf_funcionario());
			r.setConsumo(fr.getConsumo());
			r.setId_cidade(fr.getId_cidade());
			r.setGasolina(fr.getPreco_gasolina());

			if (rotaRepository.save(r) != null) {
				return new ResponseEntity<>(HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/iniciar")
	public ResponseEntity<HttpStatus> iniciarDia(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);

			Rota r = new Rota();
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy 00:00:00");
			Date dia = formato.parse(formato.format(new Date()));
			r.setData(dia);

			SimpleDateFormat formato2 = new SimpleDateFormat("hh:mm:ss aa");
			Date hora = formato2.parse(formato2.format(new Date()));
			r.setHora(hora);
			r.setCpf_funcionario(json.getString("cpf_funcionario"));
			r.setLatitude(json.getString("latitude"));
			r.setLongitude(json.getString("longitude"));
			r.setDescricao("iniciou");

			FuncionarioRubi fr = funcionarioDAO.findByCpf(r.getCpf_funcionario());
			r.setConsumo(fr.getConsumo());
			r.setId_cidade(fr.getId_cidade());
			r.setGasolina(fr.getPreco_gasolina());

			if (rotaRepository.save(r) != null) {
				return new ResponseEntity<>(HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/finalizar")
	public ResponseEntity<HttpStatus> finalizarDia(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);

			Rota r = new Rota();
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy 00:00:00");
			Date dia = formato.parse(formato.format(new Date()));
			r.setData(dia);

			SimpleDateFormat formato2 = new SimpleDateFormat("hh:mm:ss aa");
			Date hora = formato2.parse(formato2.format(new Date()));
			r.setHora(hora);

			r.setCpf_funcionario(json.getString("cpf_funcionario"));
			r.setLatitude(json.getString("latitude"));
			r.setLongitude(json.getString("longitude"));
			r.setDescricao("finalizou");

			FuncionarioRubi fr = funcionarioDAO.findByCpf(r.getCpf_funcionario());
			r.setConsumo(fr.getConsumo());
			r.setId_cidade(fr.getId_cidade());
			r.setGasolina(fr.getPreco_gasolina());

			if (rotaRepository.save(r) != null) {
				return new ResponseEntity<>(HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/recalculo/consumo")
	private ResponseEntity<HttpStatus> recalculoConsumo(@RequestBody String body) throws SQLException {
		try {
			JSONObject json = new JSONObject(body);
			
			FuncionarioConsumo func = funcionarioDAO.findConsumoFuncByCpf(json.getString("cpf_funcionario"));
			List<Rota> rotas = rotaRepository.findAllByFuncInInterval(func.getCpf(), json.getString("data_inicio"), json.getString("data_fim"));
			rotas.forEach(x -> {
				x.setConsumo(func.getConsumo());
			});
			rotaRepository.saveAll(rotas);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (JSONException e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/recalculo/combustivel")
	private ResponseEntity<HttpStatus> recalculo(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);
			Long id_cidade = json.getLong("id_cidade");
			Cidade cidade = cidadeRepository.findById(id_cidade).get();
			List<Rota> rotas = rotaRepository.findAllFuncsByCityInInterval(json.getLong("id_cidade"), json.getString("data_inicio"), json.getString("data_final"));
			rotas.forEach(x -> {
				x.setGasolina(cidade.getPreco_gasolina());
			});
			rotaRepository.saveAll(rotas);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (JSONException e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/relatorio")
	public ResponseEntity<List<RotaDTO>> findByFuncAndDate(@RequestBody String body) {
		try {
			// Obter rotas de um funcionário especifico em um dia especifico
			JSONObject json = new JSONObject(body);
			List<Rota> rotas = rotaRepository.findRotasByFuncAndData(json.getString("data"), json.getLong("id_func"));
			List<RotaDTO> rotasDTO = new ArrayList<>();
			rotas.forEach(x -> {
				rotasDTO.add(new RotaDTO(x));
			});
			return new ResponseEntity<>(rotasDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/relatorio/combustivel")
	private ResponseEntity<Map<Long, Map<Date, List<RotaDTO>>>> relatorioCombustivelAll(@RequestBody String body) {
		// Obter rotas de todos os funcionarios em um certo intervalo de tempo
		try {
			JSONObject json = new JSONObject(body);

			if (json.has("data_inicio") && json.has("data_final")) {
				List<Rota> rotas = rotaRepository.findRotasOfAllFuncsInInterval(json.getString("data_inicio"),
						json.getString("data_final"));
				List<RotaDTO> rotasDTO = new ArrayList<>();
				rotas.forEach(x -> {
					rotasDTO.add(new RotaDTO(x));
				});
				Map<Long, Map<Date, List<RotaDTO>>> result = rotasDTO.stream().collect(
						Collectors.groupingBy(RotaDTO::getId_funcionario, Collectors.groupingBy(RotaDTO::getData)));

				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/relatorio/combustivel/cidade")
	private ResponseEntity<Map<Long, Map<Date, List<RotaDTO>>>> relatorioCombustivelByCidade(@RequestBody String body) {
		try {
			// Obter rotas de todos os funcionarios de uma determinada cidade, agrupando em
			// cidade, funcionario e data
			JSONObject json = new JSONObject(body);
			if (json.has("data_inicio") && json.has("data_final") && json.has("id")) {
				List<Rota> rotas = rotaRepository.findAllFuncsByCityInInterval(json.getLong("id"), json.getString("data_inicio"),
						json.getString("data_final"));
				List<RotaDTO> rotasDTO = new ArrayList<>();
				rotas.forEach(x -> {
					rotasDTO.add(new RotaDTO(x));
				});
				Map<Long, Map<Date, List<RotaDTO>>> result = rotasDTO.stream().collect(
						Collectors.groupingBy(RotaDTO::getId_funcionario, Collectors.groupingBy(RotaDTO::getData)));

				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/relatorio/combustivel/funcionario")
	private ResponseEntity<Map<Long, Map<Date, List<RotaDTO>>>> relatorioCombustivelByFunc(@RequestBody String body) {
		try {
			// Obter rotas de um funcionario especifico em um certo intervalo de tempo
			JSONObject json = new JSONObject(body);

			if (json.has("data_inicio") && json.has("data_final") && json.has("id")) {
				List<Rota> rotas = rotaRepository.findRotasOfSingleFuncInInterval(json.getString("data_inicio"),
						json.getString("data_final"), json.getLong("id"));
				List<RotaDTO> rotasDTO = new ArrayList<>();
				rotas.forEach(x -> {
					rotasDTO.add(new RotaDTO(x));
				});
				Map<Long, Map<Date, List<RotaDTO>>> result = rotasDTO.stream().collect(
						Collectors.groupingBy(RotaDTO::getId_funcionario, Collectors.groupingBy(RotaDTO::getData)));

				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/relatorio/anomalia")
	public ResponseEntity<Map<Long, Map<Date, List<RotaDTO>>>> relatorioAnomaliaAll(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);

			if (json.has("data_inicio") && json.has("data_final")) {
				List<Rota> rotas = rotaRepository.findRotasOfAllFuncsInInterval(json.getString("data_inicio"),
						json.getString("data_final"));
				List<RotaDTO> rotasDTO = new ArrayList<>();
				rotas.forEach(x -> {
					rotasDTO.add(new RotaDTO(x));
				});
				Map<Long, Map<Date, List<RotaDTO>>> result = rotasDTO.stream().collect(
						Collectors.groupingBy(RotaDTO::getId_funcionario, Collectors.groupingBy(RotaDTO::getData)));

				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/relatorio/anomalia/funcionario")
	public ResponseEntity<Map<Long, Map<Date, List<RotaDTO>>>> relatorioAnomaliaOfFunc(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);

			if (json.has("data_inicio") && json.has("data_final") && json.has("id_funcionario")) {
				List<Rota> rotas = rotaRepository.findRotasOfSingleFuncInInterval(json.getString("data_inicio"),
						json.getString("data_final"), json.getLong("id_funcionario"));
				List<RotaDTO> rotasDTO = new ArrayList<>();
				rotas.forEach(x -> {
					rotasDTO.add(new RotaDTO(x));
				});
				Map<Long, Map<Date, List<RotaDTO>>> result = rotasDTO.stream().collect(
						Collectors.groupingBy(RotaDTO::getId_funcionario, Collectors.groupingBy(RotaDTO::getData)));

				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/validate")
	public ResponseEntity<HttpStatus> validar(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);
			List<Rota> list = rotaRepository.findRotasByFuncAndData(json.getString("data"), json.getLong("id_func"));
			int x = 0;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getDescricao() != null) {
					if (list.get(i).getDescricao().equals("iniciou")
							|| list.get(i).getDescricao().equals("finalizou")) {
						x++;
					}
				}
			}

			if ((x % 2) == 1) {
				return new ResponseEntity<>(HttpStatus.FOUND);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
