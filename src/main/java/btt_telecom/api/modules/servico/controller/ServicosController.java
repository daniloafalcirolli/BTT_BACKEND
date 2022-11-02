package btt_telecom.api.modules.servico.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
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

import btt_telecom.api.models.Cliente;
import btt_telecom.api.modules.funcionario.model.Funcionario;
import btt_telecom.api.modules.funcionario.repository.FuncionarioRepository;
import btt_telecom.api.modules.materiais.model.MaterialAplicado;
import btt_telecom.api.modules.materiais.model.MaterialRetirado;
import btt_telecom.api.modules.materiais.repository.MaterialAplicadoBaseRepository;
import btt_telecom.api.modules.materiais.repository.MaterialRetiradoBaseRepository;
import btt_telecom.api.modules.provedores.model.CamposProvedor;
import btt_telecom.api.modules.provedores.model.Provedor;
import btt_telecom.api.modules.provedores.model.ServicoProvedor;
import btt_telecom.api.modules.provedores.repository.CamposProvedorBaseRepository;
import btt_telecom.api.modules.provedores.repository.ProvedorRepository;
import btt_telecom.api.modules.provedores.repository.ServicoProvedorRepository;
import btt_telecom.api.modules.servico.dto.ServicoDTO;
import btt_telecom.api.modules.servico.model.Servico;
import btt_telecom.api.modules.servico.repository.ServicoRepository;
import btt_telecom.api.repositories.ClienteRepository;

@RestController
@RequestMapping(path = "/api/servico")
public class ServicosController {
	
	private boolean validacao = false;
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private ServicoProvedorRepository servicoProvedorRepository;
	
	@Autowired
	private ProvedorRepository provedorRepository;
	
	@Autowired
	private MaterialAplicadoBaseRepository aplicadoBaseRepository;
	
	@Autowired
	private MaterialRetiradoBaseRepository retiradoBaseRepository;

	@Autowired
	private CamposProvedorBaseRepository campoBaseRepository;
	
	@GetMapping
	public ResponseEntity<List<Servico>> findAll(){
		try {
			return new ResponseEntity<>(servicoRepository.findAll(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/page")
	public ResponseEntity<Page<Servico>> findAllWithPage(Pageable pageable){
		try {
			return new ResponseEntity<Page<Servico>>(servicoRepository.findAll(pageable), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Servico> findById(@PathVariable(name = "id") Long id){
		try {
			if(servicoRepository.existsById(id)) {
				return new ResponseEntity<>(servicoRepository.findById(id).get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/funcionario")
	public ResponseEntity<List<Servico>> findServicosByStatusByFuncInExactlyDate(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			Long id = json.getLong("id_func");
			String status = json.getString("status");
			
			if(json.has("data")) {
				return new ResponseEntity<>(servicoRepository.findByFuncAndExactlyDate(id, json.getString("data"), status), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/interval")
	public ResponseEntity<List<ServicoDTO>> findServicosInInterval(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			List<Servico> list;
			if(json.has("data_inicio") && json.has("data_finalizacao") && json.has("id_funcionario") && json.has("status")) {
				list = servicoRepository.findServicesInIntervalAndFuncAndStatus(json.getString("data_inicio"), json.getString("data_finalizacao"), json.getLong("id_funcionario"), json.getString("status"));
			}else if(json.has("data_inicio") && json.has("data_finalizacao") && json.has("id_funcionario")){
				list = servicoRepository.findServicesInProgressByFunc(json.getString("data_inicio"), json.getString("data_finalizacao"), json.getLong("id_funcionario"));
			}else if(json.has("data_inicio") && json.has("data_finalizacao") && json.has("status")) {
				list = servicoRepository.findServicesInProgressByStatus(json.getString("data_inicio"), json.getString("data_finalizacao"), json.getString("status"));
			}else if(json.has("data_inicio") && json.has("data_finalizacao")) {
				list = servicoRepository.findServicesInInterval(json.getString("data_inicio"), json.getString("data_finalizacao"));
			}else if(json.has("id_funcionario") && json.has("status")) {
				list = servicoRepository.findServicesByFuncAndStatus(json.getLong("id_funcionario"), json.getString("status"));
			}else if(json.has("status")){
				list = servicoRepository.findServiceByStatus(json.getString("status"));
			}else if(json.has("id_funcionario")){
				list = servicoRepository.findByFunc(json.getLong("id_funcionario"));
			}else {
				list = servicoRepository.findAll();
			}
			List<ServicoDTO> result = new ArrayList<>();
			list.forEach(x -> {
				result.add(new ServicoDTO(x));
			});
			return new ResponseEntity<>(result, HttpStatus.OK);
		}catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/create")
	public ResponseEntity<HttpStatus> save(@RequestBody String body) throws ParseException, JSONException{
		try {
			Date data = new Date();
			Servico s = new Servico();
			JSONObject json = new JSONObject(body);
			Cliente c = clienteRepository.findById(json.getLong("id_cliente")).get();
			Funcionario f = funcionarioRepository.findById(json.getLong("id_funcionario")).get();
			Provedor p = provedorRepository.findById(json.getLong("id_provedor")).get();
			ServicoProvedor sp = servicoProvedorRepository.findById(json.getLong("id_servico_provedor")).get();
			
			s.setCliente(c);
			s.setFuncionario(f);
			s.setProvedor(p);
			s.setServicoProvedor(sp);
			s.setStatus("em andamento");
			s.setProtocolo(json.getString("protocolo"));
			s.setData(data);

			if(servicoRepository.save(s) != null) {
				return new ResponseEntity<>(HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}catch(JSONException e) {
			return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
		}
	}
	
	@PostMapping(path = "/encerrar")
	public ResponseEntity<HttpStatus> encerrarServico(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			Long id_servico = json.getLong("id");

			Servico s = servicoRepository.findById(id_servico).get();
			
			if(json.has("obs") && json.has("cod")) {
				s.setObservacoes(json.getString("obs"));
				s.setCod_quebra(json.getString("cod"));
				s.setStatus(json.getString("status"));
			}else {
				JSONArray jsonMatApli = json.getJSONArray("materiais_aplicados");
				JSONArray jsonMatReti = json.getJSONArray("materiais_retirados");
				JSONArray jsonCamposApli = json.getJSONArray("campos_aplicados");
				
				List<MaterialAplicado> materiais_aplicados = new ArrayList<>();
				for(int i = 0; i < jsonMatApli.length(); i++) {
					JSONObject jsonMat = new JSONObject(jsonMatApli.get(i).toString());
					
					MaterialAplicado materialAplicado = new MaterialAplicado();
					materialAplicado.setValue(jsonMat.getString("value"));
					materialAplicado.setMaterial_aplicado(aplicadoBaseRepository.findById(jsonMat.getLong("id")).get());
					
					materiais_aplicados.add(materialAplicado);
				}
				
				List<MaterialRetirado> materiais_retirados = new ArrayList<>();
				for(int i = 0; i < jsonMatReti.length(); i++) {
					JSONObject jsonMat = new JSONObject(jsonMatReti.get(i).toString());
					
					MaterialRetirado materialRetirado = new MaterialRetirado();
					materialRetirado.setValue(jsonMat.getString("value"));
					materialRetirado.setMaterial_retirado(retiradoBaseRepository.findById(jsonMat.getLong("id")).get());
					
					materiais_retirados.add(materialRetirado);
				}
				
				List<CamposProvedor> campos_aplicados = new ArrayList<>();
				for(int i = 0; i < jsonCamposApli.length(); i++) {
					JSONObject campo = new JSONObject(jsonCamposApli.get(i).toString());
					
					CamposProvedor campoAplicado = new CamposProvedor();
					campoAplicado.setValue(campo.getString("value"));
					campoAplicado.setCampo_provedor(campoBaseRepository.findById(campo.getLong("id")).get());
					
					campos_aplicados.add(campoAplicado);
				}
				
				s.setMateriais_aplicados(materiais_aplicados);
				s.setMateriais_retirados(materiais_retirados);
				s.setCampos_aplicados(campos_aplicados);
				SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss aa");
				Date hora = formato.parse(formato.format(new Date()));
				s.setHora(hora);
				s.setStatus("finalizado");
			}
						
			servicoRepository.save(s);
			return null;
		}catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/import")
	public ResponseEntity<HttpStatus> importCsv(@RequestBody String body){
		try {
			JSONArray jsonarray = new JSONArray(body);
			for(int i = 0; i < jsonarray.length(); i++) {
				JSONObject json = new JSONObject(jsonarray.get(i).toString());
				
				Servico servico = new Servico();
				if(clienteRepository.existsByContrato(json.getString("contrato_cliente"))) {
					servico.setFuncionario(funcionarioRepository.findByNomeProvedor(json.getString("cpf_funcionario")).get());

					servico.setCliente(clienteRepository.findByContrato(json.getString("contrato_cliente")));
					servico.setProtocolo(json.getString("protocolo"));
					servico.setProvedor(provedorRepository.findByIdentificador(json.getString("nome_provedor").toUpperCase()).get());
					
					String nome_servico = json.getString("nome_servico").toUpperCase();

					provedorRepository.findByIdentificador(json.getString("nome_provedor").toUpperCase()).get().getServicos().forEach(x -> {
						servicoProvedorRepository.findByIdentificador(nome_servico).forEach(y -> {
							if(x.equals(y)) {
								servico.setServicoProvedor(x);
							}
						});
					});
					//servico.setServicoProvedor(servicoProvedorRepository.findByIdentificador(json.getString("nome_servico").toUpperCase()).get());

					servico.setStatus(json.getString("status"));
					
					String data_finalizacao = json.getString("data_finalizacao");
					String data_to_sql = "";
					Date data;
					if(data_finalizacao.contains("-")) {
						data_to_sql = json.getString("data_finalizacao");
						data = new SimpleDateFormat("yyyy-MM-dd").parse(data_finalizacao);
					}else {
						data = new SimpleDateFormat("dd/MM/yyyy").parse(data_finalizacao);
						data_to_sql = data_finalizacao.split("/")[2] + "-" + data_finalizacao.split("/")[1] + "-" + data_finalizacao.split("/")[0];
					}
					servico.setData(data);
				
					List<Servico> servicos = servicoRepository.findServiceByProtocolAndDate(servico.getProtocolo(), data_to_sql);
					
					if(servicos.isEmpty()) {
						servicoRepository.save(servico);
					}else {
						servicos.forEach(x -> {
							if(x.getCliente().equals(servico.getCliente())) {
								validacao = true;
							}
						});
						
						if(!validacao) {
							servicoRepository.save(servico);
						}
						
						validacao = false;
					}
				}
			}
		
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	public ResponseEntity<HttpStatus> edit(@RequestBody String body) throws ParseException, JSONException{
		try {
			JSONObject json = new JSONObject(body);
			Servico s = servicoRepository.findById(json.getLong("id_servico")).get();
			Date data;
			
			s.setCliente(clienteRepository.findById(json.getLong("id_cliente")).get());
			s.setFuncionario(funcionarioRepository.findById(json.getLong("id_funcionario")).get());
			s.setProtocolo(json.getString("protocolo"));
			s.setStatus(json.getString("status"));
			s.setProvedor(provedorRepository.findById(json.getLong("id_prov")).get());
			s.setServicoProvedor(servicoProvedorRepository.findById(json.getLong("id_serv")).get());
			
			String data_finalizacao = json.getString("data_finalizacao");
			data = new SimpleDateFormat("yyyy-MM-dd").parse(data_finalizacao);
			s.setData(data);
			
			if(servicoRepository.save(s) != null) {
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}catch(JSONException e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	private ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id){
		try {
			if(servicoRepository.existsById(id)) {
				servicoRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
	}
}