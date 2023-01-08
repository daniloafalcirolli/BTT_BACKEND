package btt_telecom.api.modules.rotas.controller;

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

import btt_telecom.api.config.general.AbstractMethods;
import btt_telecom.api.modules.cidade.Cidade;
import btt_telecom.api.modules.cidade.CidadeRepository;
import btt_telecom.api.modules.funcionario.dto.FuncionarioConsumo;
import btt_telecom.api.modules.funcionario.external.FuncionarioDAO;
import btt_telecom.api.modules.rotas.dto.RotaDTO;
import btt_telecom.api.modules.rotas.external.RotaDAO;
import btt_telecom.api.modules.rotas.model.Rota;
import btt_telecom.api.modules.rotas.repository.RotaRepository;

@RestController
@RequestMapping(path = "/api/rotas")
public class RotasController extends AbstractMethods{

	@Autowired
	private RotaRepository rotaRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

	private RotaDAO rotaRepo = new RotaDAO();

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
			
			if(json.has("descricao")) {
				switch (json.getInt("descricao")) {
					case 0: {
						r.setDescricao("rota");
						break;
					}
					case 1: {
						r.setDescricao("base");
						break;
					}
					case 2: {
						r.setDescricao("iniciou");
						break;
					}
					case 3: {
						r.setDescricao("finalizou");
						break;
					}
					case 4: {
						r.setDescricao("almoço");
						break;
					}
					default: {
						r.setDescricao("");
						break;
					}
				}
			}else {
				r.setDescricao("");
			}
			

			FuncionarioConsumo fr = funcionarioDAO.findConsumoFuncByCpf(r.getCpf_funcionario());
			r.setConsumo(fr.getConsumo());
			r.setId_cidade(fr.getId_cidade());
			r.setNome_cidade(fr.getNome_cidade());
			r.setGasolina(fr.getPreco_gasolina());

			if (rotaRepository.save(r) != null) {
				insertLog("[Rota] Registrada com sucesso - " + fr.getNome());
				return new ResponseEntity<>(HttpStatus.CREATED);
			} else {
				insertError("[Rota] Ocorreu um erro ao registrar uma rota - " + fr.getNome());
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			insertError("[Rota] Ocorreu um erro ao registrar uma rota - " + e);
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
						
			if(rotaRepository.saveAll(rotas) != null) {
				insertLog("[Rota] " + String.valueOf(rotas.size()) + " rotas alteradas com sucesso! - Novo consumo: " + func.getConsumo() + " - " + func.getNome());
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				insertError("[Rota] Ocorreu um erro ao recalcular o consumo - " + func.getNome());
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (JSONException e) {
			insertError("[Rota] Ocorreu um erro ao recalcular o consumo - " + e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/recalculo/combustivel")
	private ResponseEntity<HttpStatus> recalculo(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);
			Cidade cidade = cidadeRepository.findByCidade(json.getString("nome_cidade"));
			List<Rota> rotas = rotaRepository.findAllFuncsByCityInInterval(json.getString("nome_cidade"), json.getString("data_inicio"), json.getString("data_fim"));
			rotas.forEach(x -> {
				x.setId_cidade(cidade.getId());
				x.setGasolina(cidade.getPreco_gasolina());
			});

			if(rotaRepository.saveAll(rotas) != null) {
				insertLog("[Rota] " + String.valueOf(rotas.size()) + " rotas alteradas com sucesso! - Novo valor do combustível: " + cidade.getPreco_gasolina() + " para a cidade de " + json.getString("nome_cidade"));
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				insertError("[Rota] Ocorreu um erro ao recalcular o valor do combustível - Cidade: " + json.getString("nome_cidade") );
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (JSONException e) {
			insertError("[Rota] Ocorreu um erro ao recalcular o valor do combustível - " + e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/individual")
	public ResponseEntity<List<RotaDTO>> findByFuncAndDate(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);
			List<Rota> rotas = rotaRepository.findRotasByFuncAndData(json.getString("data"), json.getString("cpf_funcionario"));
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
	private ResponseEntity<Map<String, Map<Date, List<RotaDTO>>>> relatorioCombustivelAll(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);
			String data_inicio = json.getString("data_inicio");
			String data_final = json.getString("data_final");
			String cpf_funcionario = json.getString("cpf_funcionario");
			String nome_cidade = json.getString("nome_cidade");
			
			List<RotaDTO> rotas = rotaRepo.getRotas(data_inicio, data_final, nome_cidade, cpf_funcionario);

			Map<String, Map<Date, List<RotaDTO>>> result = rotas.stream().collect(
					Collectors.groupingBy(RotaDTO::getCpf_funcionario,
							Collectors.groupingBy(RotaDTO::getData)));

			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/validate")
	public ResponseEntity<HttpStatus> validar(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);
			List<Rota> list = rotaRepository.findRotasByFuncAndData(json.getString("data"), json.getString("cpf_funcionario"));
			int x = 0;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getDescricao() != null) {
					if (list.get(i).getDescricao().equals("iniciou") || list.get(i).getDescricao().equals("finalizou")) {
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
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path="/funcionario/almoco")
	public ResponseEntity<HttpStatus> funcionarioAlmoco(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
			String date = formatoData.format(new Date());
			List<Rota> list = rotaRepository.findAlmocoByFuncionario(json.getString("cpf_funcionario"), date);
			if(list.size() == 0) {				
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else if(list.size() > 1) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}else {
				return new ResponseEntity<>(HttpStatus.FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
