package btt_telecom.api.modules.provedores.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.config.general.AbstractMethods;
import btt_telecom.api.modules.materiais.model.MaterialAplicadoBase;
import btt_telecom.api.modules.materiais.model.MaterialRetiradoBase;
import btt_telecom.api.modules.materiais.repository.MaterialAplicadoBaseRepository;
import btt_telecom.api.modules.materiais.repository.MaterialRetiradoBaseRepository;
import btt_telecom.api.modules.provedores.dto.ProvedorDTO;
import btt_telecom.api.modules.provedores.model.CamposProvedorBase;
import btt_telecom.api.modules.provedores.model.ImagemProvedor;
import btt_telecom.api.modules.provedores.model.Provedor;
import btt_telecom.api.modules.provedores.model.ServicoProvedor;
import btt_telecom.api.modules.provedores.repository.CamposProvedorBaseRepository;
import btt_telecom.api.modules.provedores.repository.CategoriaServicoProvedorRepository;
import btt_telecom.api.modules.provedores.repository.ImagensProvedorRepository;
import btt_telecom.api.modules.provedores.repository.ProvedorRepository;
import btt_telecom.api.modules.provedores.repository.ServicoProvedorRepository;

@RestController
@RequestMapping(path = "/api/provedor")
public class ProvedorController extends AbstractMethods{

	@Autowired
	private ServicoProvedorRepository servicoProvedorRepository;
	
	@Autowired
	private ProvedorRepository provedorRepository;
	
	@Autowired
	private MaterialAplicadoBaseRepository aplicadoBaseRepository;

	@Autowired
	private MaterialRetiradoBaseRepository retiradoBaseRepository;
	
	@Autowired
	private CamposProvedorBaseRepository camposProvedorBaseRepository;
	
	@Autowired
	private ImagensProvedorRepository imagensProvedorRepository;
	
	@Autowired
	private CategoriaServicoProvedorRepository categoriaServicoProvedorRepository;

	@GetMapping
	public ResponseEntity<List<ProvedorDTO>> findAll(@RequestParam(name = "value", defaultValue = "") String value){
		try {
			List<Provedor> result = new ArrayList<>();
			
			if(value.equals("")){
				result = provedorRepository.findAll();
			}else {
				result = provedorRepository.search(value);
			}
			
			List<ProvedorDTO> provedores = new ArrayList<>();
			result.forEach(x -> {
				provedores.add(new ProvedorDTO(x));
			});
			
			return new ResponseEntity<>(provedores, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/search")
	public ResponseEntity<Page<ProvedorDTO>> search(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			List<Provedor> result = provedorRepository.search(json.getString("value"));
			List<ProvedorDTO> provedores = new ArrayList<>();
			result.forEach(x -> {
				provedores.add(new ProvedorDTO(x));
			});
			Page<ProvedorDTO> page = new PageImpl<>(provedores);
			return new ResponseEntity<>(page, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/page")
	public ResponseEntity<Map<String, Object>> findAllWithPage(@RequestParam(name = "value", defaultValue = "") String value, @RequestParam(name = "size") Long size, @RequestParam(name = "page") Long page){
		try {
			if(value.equals("")) {
				return new ResponseEntity<>(convertListToPage(provedorRepository.findAll(), size, page), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(convertListToPage(provedorRepository.search(value.toUpperCase()), size, page), HttpStatus.OK);
			} 
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
	
	@PostMapping
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
	
	@PostMapping(path = "/campos")
	public ResponseEntity<HttpStatus> camposAplicadosAoProvedor(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			JSONArray jsonCampos = json.getJSONArray("campos_aplicados");
			
			List<CamposProvedorBase> campos_aplicados = new ArrayList<>();
			for(int i = 0; i < jsonCampos.length(); i++) {
				JSONObject jsonCampo = new JSONObject(jsonCampos.get(i).toString());
				
				CamposProvedorBase campo_aplicado = camposProvedorBaseRepository.findById(jsonCampo.getLong("id")).get();
				campos_aplicados.add(campo_aplicado);
			}
			
			Provedor p = provedorRepository.findById(json.getLong("id")).get();
			p.setCampos(campos_aplicados);
			
			if(provedorRepository.save(p) != null) {
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
		}
	}
	
	@PostMapping(path = "/imagens")
	public ResponseEntity<HttpStatus> imagensAplicadasAoProvedor(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			JSONArray jsonImagens = json.getJSONArray("imagens");

			List<ImagemProvedor> imagens_aplicadas = new ArrayList<>();
			for(int i = 0; i < jsonImagens.length(); i++) {
				JSONObject jsonImagem = new JSONObject(jsonImagens.get(i).toString());
				
				ImagemProvedor imagemProvedor = imagensProvedorRepository.findById(jsonImagem.getLong("id")).get();
				imagens_aplicadas.add(imagemProvedor);
			}
			
			Provedor p = provedorRepository.findById(json.getLong("id")).get();
			p.setImagens(imagens_aplicadas);
			
			if(provedorRepository.save(p) != null) {
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
		}
	}
	
	@PostMapping(path = "/materiais/aplicados")
	public ResponseEntity<HttpStatus> materiaisAplicadosAoProvedor(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			JSONArray jsonMatAplic = json.getJSONArray("materiais_aplicados");
			
			List<MaterialAplicadoBase> materiais_aplicados = new ArrayList<>();
			for(int i = 0; i < jsonMatAplic.length(); i++) {
				JSONObject jsonMat = new JSONObject(jsonMatAplic.get(i).toString());
				
				MaterialAplicadoBase materialAplicado = aplicadoBaseRepository.findById(jsonMat.getLong("id")).get();
				materiais_aplicados.add(materialAplicado);
			}
			
			Provedor p = provedorRepository.findById(json.getLong("id")).get();
			p.setMateriais_aplicados(materiais_aplicados);
			
			if(provedorRepository.save(p) != null) {
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
		}
	}
	
	@PostMapping(path = "/materiais/retirados")
	public ResponseEntity<HttpStatus> materiaisRetiradosAoProvedor(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			JSONArray jsonMatReti = json.getJSONArray("materiais_retirados");
			
			List<MaterialRetiradoBase> materiais_retirados = new ArrayList<>();
			for(int i = 0; i < jsonMatReti.length(); i++) {
				JSONObject jsonReti = new JSONObject(jsonMatReti.get(i).toString());
				
				MaterialRetiradoBase materialRetirado = retiradoBaseRepository.findById(Long.parseLong(jsonReti.getString("id"))).get();
				materiais_retirados.add(materialRetirado);
			}
			
			Provedor p = provedorRepository.findById(json.getLong("id")).get();
			p.setMateriais_retirados(materiais_retirados);
			
			if(provedorRepository.save(p) != null) {
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
		}
	}

	@PutMapping
	public ResponseEntity<HttpStatus> edit(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			Provedor p = provedorRepository.findById(json.getLong("id")).get();
			p.setName(json.getString("name"));
			p.setIdentificador(json.getString("identificador").toUpperCase());
			p.setId_senior(json.getString("id_senior").toUpperCase());
			provedorRepository.save(p);			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id){
		try {
			if(provedorRepository.existsById(id)) {
				Provedor p = provedorRepository.findById(id).get();
				List<ServicoProvedor> list = p.getServicos();
				p.setServicos(null);
				p.setMateriais_aplicados(null);
				p.setMateriais_retirados(null);
				p.setCampos(null);
				provedorRepository.save(p);
				list.forEach(x -> {
					servicoProvedorRepository.deleteById(x.getId());
				});
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
	
	@PostMapping(path = "/servico/search")
	public ResponseEntity<List<ServicoProvedor>> searchServicosByProvedor(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			List<ServicoProvedor> result = servicoProvedorRepository.search(json.getLong("id_provedor"), json.getString("value"));
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/servico/add")
	public ResponseEntity<HttpStatus> addServicoProvedor(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			ServicoProvedor sp = new ServicoProvedor();
			sp.setServico(json.getString("servico"));
			sp.setIdentificador(json.getString("identificador").toUpperCase());
			sp.setId_senior(json.getString("id_senior").toUpperCase());
			sp.setCategoria(categoriaServicoProvedorRepository.findById(json.getLong("id_categoria")).get());
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
	
	@PutMapping(path = "/servico/edit")
	public ResponseEntity<HttpStatus> editServico(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			ServicoProvedor sp = servicoProvedorRepository.findById(json.getLong("id_serv")).get();
			sp.setServico(json.getString("servico"));
			sp.setIdentificador(json.getString("identificador").toUpperCase());
			sp.setId_senior(json.getString("id_senior").toUpperCase());
			sp.setCategoria(categoriaServicoProvedorRepository.findById(json.getLong("id_categoria")).get());

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
					sp.setCategoria(null);
					servicoProvedorRepository.save(sp);
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
	

}
