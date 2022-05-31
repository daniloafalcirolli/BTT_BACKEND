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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.dto.AnoDTO;
import btt_telecom.api.dto.FabricanteDTO;
import btt_telecom.api.dto.ModeloDTO;
import btt_telecom.api.models.carros.Ano;
import btt_telecom.api.models.carros.Fabricante;
import btt_telecom.api.models.carros.Modelo;
import btt_telecom.api.models.carros.Versao;
import btt_telecom.api.repositories.carros.AnoRepository;
import btt_telecom.api.repositories.carros.FabricanteRepository;
import btt_telecom.api.repositories.carros.ModeloRepository;
import btt_telecom.api.repositories.carros.VersaoRepository;

@RestController
@RequestMapping(path = "/api")
public class FabricanteController {
	private JSONObject json;
	
	@Autowired
	private FabricanteRepository fabricanteRepository;
	
	@Autowired
	private ModeloRepository modeloRepository;
	
	@Autowired
	private AnoRepository anoRepository;
	
	@Autowired
	private VersaoRepository versaoRepository;
	
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/carro")
	private <T> T findAll(	@RequestParam(name = "fabricante", required = false) Long id_fabricante, 
							@RequestParam(name = "modelo", required = false) Long id_modelo,
							@RequestParam(name = "ano", required = false) Long id_ano,
							@RequestParam(name = "versao", required = false) Long id_versao){
		if(id_fabricante == null && id_modelo == null && id_ano == null && id_versao == null) {
			List<Fabricante> result = fabricanteRepository.findAll();
			List<FabricanteDTO> fabricantes = new ArrayList<>();
			result.forEach(x -> {
				fabricantes.add(new FabricanteDTO(x));
			});
			
			return (T) fabricantes;
		}else if(id_modelo == null && id_ano == null && id_versao == null){
			List<Modelo> result = fabricanteRepository.findById(id_fabricante).get().getModelos();
			List<ModeloDTO> modelos = new ArrayList<>();
			result.forEach(x -> {
				modelos.add(new ModeloDTO(x));
			});
			return (T) modelos;
		}else if(id_ano == null && id_versao == null){
			List<Ano> result = modeloRepository.findById(id_modelo).get().getAnos();
			List<AnoDTO> anos = new ArrayList<>();
			result.forEach(x -> {
				anos.add(new AnoDTO(x));
			});
			
			return (T) anos;
		}else if (id_versao == null){
			List<Versao> result = anoRepository.findById(id_ano).get().getVersoes();
			return (T) result;
		}else {
			Versao versao = versaoRepository.findById(id_versao).get();
			return (T) versao;
		}
	}
	
	@GetMapping(path = "/fabricante")
	private Page<FabricanteDTO> findAll(Pageable pageable){
		Page<Fabricante> result = fabricanteRepository.findAll(pageable);
		Page<FabricanteDTO> page = result.map(x -> new FabricanteDTO(x));
		return page;
	}
	
	@GetMapping(path = "/fabricante/{id}")
	private FabricanteDTO findById(@PathVariable(name = "id") Long id) {
		return new FabricanteDTO(fabricanteRepository.findById(id).get());
	}
	
	@PutMapping(path = "/fabricante/{id}")
	private ResponseEntity<HttpStatus> alterar(@RequestBody String body, @PathVariable(name = "id") Long id){
		try {
			json = new JSONObject(body);
			Fabricante f = fabricanteRepository.findById(id).get();
			if(f != null) {
				f.setNome(json.getString("nome"));
				
				fabricanteRepository.save(f);
				
				return new ResponseEntity<HttpStatus>(HttpStatus.OK);
			}else {
				return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
			}
		}catch(JSONException e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.PRECONDITION_FAILED);
		}
	}
}
