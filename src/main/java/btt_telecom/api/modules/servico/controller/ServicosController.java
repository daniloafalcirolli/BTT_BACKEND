package btt_telecom.api.modules.servico.controller;

import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.models.Cliente;
import btt_telecom.api.modules.funcionario.external.FuncionarioDAO;
import btt_telecom.api.modules.imagens.model.Imagem;
import btt_telecom.api.modules.imagens.repository.ImagemRepository;
import btt_telecom.api.modules.materiais.model.MaterialAplicado;
import btt_telecom.api.modules.materiais.model.MaterialRetirado;
import btt_telecom.api.modules.materiais.repository.MaterialAplicadoBaseRepository;
import btt_telecom.api.modules.materiais.repository.MaterialRetiradoBaseRepository;
import btt_telecom.api.modules.provedores.model.CamposProvedor;
import btt_telecom.api.modules.provedores.model.Provedor;
import btt_telecom.api.modules.provedores.model.ServicoProvedor;
import btt_telecom.api.modules.provedores.repository.CamposProvedorBaseRepository;
import btt_telecom.api.modules.provedores.repository.ImagensProvedorRepository;
import btt_telecom.api.modules.provedores.repository.ProvedorRepository;
import btt_telecom.api.modules.provedores.repository.ServicoProvedorRepository;
import btt_telecom.api.modules.servico.dto.ServicoRubi;
import btt_telecom.api.modules.servico.external.ServicoDAO;
import btt_telecom.api.modules.servico.model.Servico;
import btt_telecom.api.modules.servico.repository.ServicoRepository;
import btt_telecom.api.repositories.ClienteRepository;

@RestController
@RequestMapping(path = "/api/servico")
public class ServicosController {
		
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
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
	
	@Autowired
	private ImagemRepository imagemRepository;
	
	@Autowired
	private ImagensProvedorRepository imagensProvedorRepository;
	
	private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
	
	private ServicoDAO servicoDAO = new ServicoDAO();
	
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
	
	@PostMapping(path = "/filter")
	public ResponseEntity<List<ServicoRubi>> findServicosInInterval(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			String data_inicio = json.getString("data_inicio");
			String data_final = json.getString("data_final");
			String cpf_funcionario = json.getString("cpf_funcionario");
			String status = json.getString("status") ;

			List<ServicoRubi> list = servicoDAO.getServicos(data_inicio, data_final, cpf_funcionario, status);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/create")
	public ResponseEntity<HttpStatus> save(@RequestBody String body) throws ParseException, JSONException, SQLException{
		try {
			SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm:ss aa");
			SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			Date hora = formatoHora.parse(formatoHora.format(new Date()));
			Date data = formatoData.parse(formatoData.format(new Date()));
			Servico s = new Servico();
			JSONObject json = new JSONObject(body);
			Cliente c = clienteRepository.findById(json.getLong("id_cliente")).get();
			Provedor p = provedorRepository.findById(json.getLong("id_provedor")).get();
			ServicoProvedor sp = servicoProvedorRepository.findById(json.getLong("id_servico_provedor")).get();
			
			s.setCliente(c);
			s.setProvedor(p);
			s.setServicoProvedor(sp);
			s.setStatus("em andamento");
			s.setCpf_funcionario(json.getString("cpf_funcionario"));
			s.setProtocolo(json.getString("protocolo"));
			s.setData(data);
			s.setHora(hora);
			
			if(funcionarioDAO.existsFuncionarioByCpf(s.getCpf_funcionario())) {
				if(servicoRepository.save(s) != null) {
					return new ResponseEntity<>(HttpStatus.CREATED);
				}else {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
				JSONArray jsonImagens = json.getJSONArray("imagens");
				
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
				
				List<Imagem> imagens = new ArrayList<>();
				for(int i = 0; i < jsonImagens.length(); i++) {
					JSONObject campo = new JSONObject(jsonImagens.get(i).toString());
					
					Imagem imagem = new Imagem();
					imagem.setContent(campo.getString("content"));
					imagem.setFileName(campo.getString("fileName"));
					imagem.setFileType(campo.getString("fileType"));
					imagem.setImagem_provedor(imagensProvedorRepository.findById(campo.getLong("id_imagem_provedor")).get());
					
					imagem = imagemRepository.save(imagem);
					
					imagens.add(imagem);
				}
				
				s.setMateriais_aplicados(materiais_aplicados);
				s.setMateriais_retirados(materiais_retirados);
				s.setCampos_aplicados(campos_aplicados);
				s.setImagens(imagens);
				s.setStatus("finalizado");
			}
			
			SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm:ss aa");
			SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd");
			Date hora = formatoHora.parse(formatoHora.format(new Date()));
			Date data = formatoData.parse(formatoData.format(new Date()));
			s.setHora_finalizacao(hora);
			s.setData_finalizacao(data);
						
			if(servicoRepository.save(s) != null) {
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

//	@PostMapping(path = "/import")
//	public ResponseEntity<HttpStatus> importCsv(@RequestBody String body){
//		try {
//			JSONArray jsonarray = new JSONArray(body);
//			for(int i = 0; i < jsonarray.length(); i++) {
//				JSONObject json = new JSONObject(jsonarray.get(i).toString());
//				
//				Servico servico = new Servico();
//				if(clienteRepository.existsByContrato(json.getString("contrato_cliente"))) {
//					servico.setFuncionario(funcionarioRepository.findByNomeProvedor(json.getString("cpf_funcionario")).get());
//
//					servico.setCliente(clienteRepository.findByContrato(json.getString("contrato_cliente")));
//					servico.setProtocolo(json.getString("protocolo"));
//					servico.setProvedor(provedorRepository.findByIdentificador(json.getString("nome_provedor").toUpperCase()).get());
//					
//					String nome_servico = json.getString("nome_servico").toUpperCase();
//
//					provedorRepository.findByIdentificador(json.getString("nome_provedor").toUpperCase()).get().getServicos().forEach(x -> {
//						servicoProvedorRepository.findByIdentificador(nome_servico).forEach(y -> {
//							if(x.equals(y)) {
//								servico.setServicoProvedor(x);
//							}
//						});
//					});
//					//servico.setServicoProvedor(servicoProvedorRepository.findByIdentificador(json.getString("nome_servico").toUpperCase()).get());
//
//					servico.setStatus(json.getString("status"));
//					
//					String data_finalizacao = json.getString("data_finalizacao");
//					String data_to_sql = "";
//					Date data;
//					if(data_finalizacao.contains("-")) {
//						data_to_sql = json.getString("data_finalizacao");
//						data = new SimpleDateFormat("yyyy-MM-dd").parse(data_finalizacao);
//					}else {
//						data = new SimpleDateFormat("dd/MM/yyyy").parse(data_finalizacao);
//						data_to_sql = data_finalizacao.split("/")[2] + "-" + data_finalizacao.split("/")[1] + "-" + data_finalizacao.split("/")[0];
//					}
//					servico.setData(data);
//				
//					List<Servico> servicos = servicoRepository.findServiceByProtocolAndDate(servico.getProtocolo(), data_to_sql);
//					
//					if(servicos.isEmpty()) {
//						servicoRepository.save(servico);
//					}else {
//						servicos.forEach(x -> {
//							if(x.getCliente().equals(servico.getCliente())) {
//								validacao = true;
//							}
//						});
//						
//						if(!validacao) {
//							servicoRepository.save(servico);
//						}
//						
//						validacao = false;
//					}
//				}
//			}
//		
//			return new ResponseEntity<>(HttpStatus.OK);
//		} catch (Exception e) {
//			System.out.println(e);
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}

//	@PutMapping
//	public ResponseEntity<HttpStatus> edit(@RequestBody String body) throws ParseException, JSONException{
//		try {
//			JSONObject json = new JSONObject(body);
//			Servico s = servicoRepository.findById(json.getLong("id_servico")).get();
//			Date data;
//			
//			s.setCliente(clienteRepository.findById(json.getLong("id_cliente")).get());
//			s.setFuncionario(funcionarioRepository.findById(json.getLong("id_funcionario")).get());
//			s.setProtocolo(json.getString("protocolo"));
//			s.setStatus(json.getString("status"));
//			s.setProvedor(provedorRepository.findById(json.getLong("id_prov")).get());
//			s.setServicoProvedor(servicoProvedorRepository.findById(json.getLong("id_serv")).get());
//			
//			String data_finalizacao = json.getString("data_finalizacao");
//			data = new SimpleDateFormat("yyyy-MM-dd").parse(data_finalizacao);
//			s.setData(data);
//			
//			if(servicoRepository.save(s) != null) {
//				return new ResponseEntity<>(HttpStatus.OK);
//			}else {
//				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//			}
//		}catch(JSONException e) {
//			System.out.println(e);
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}
	
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