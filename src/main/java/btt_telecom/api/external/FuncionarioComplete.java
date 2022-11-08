package btt_telecom.api.external;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.openjson.JSONObject;

import btt_telecom.api.modules.funcionario.model.Funcionario;
import btt_telecom.api.modules.funcionario.repository.FuncionarioRepository;
import btt_telecom.api.modules.funcionario.repository.StatusFuncRepository;
import btt_telecom.api.repositories.CidadeRepository;
import btt_telecom.api.repositories.EmpresaRepository;

@RestController
@RequestMapping("/complete/funcionario")
public class FuncionarioComplete {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private StatusFuncRepository statusFuncRepository;

//	@GetMapping
//	public void completar() {
//		ArrayList<Funcionario> funcionarios = new ArrayList<>();
//		ArrayList<FuncionarioDTO> funcsDTO = new ArrayList<>();
//		
//		Geocoder geo = new Geocoder();
//		Funcionario f;
//
//		getConnection c = new getConnection();
//		funcsDTO = c.pegarfuncs();
//		
//		for(int i = 0; i < funcsDTO.size(); i++) {
//			try {
//				if(funcionarioRepository.existsByCpf(funcsDTO.get(i).getCpf())) {
//					f = funcionarioRepository.findByCpf(funcsDTO.get(i).getCpf()).get();
//				}else {
//					f = new Funcionario();
//				}
//			}catch(Exception e) {
//				f = new Funcionario();
//			}
//			
////			f.setUsername(funcsDTO.get(i).getUsername());
////			f.setNome(funcsDTO.get(i).getNome());
////			
////			f.setCpf(funcsDTO.get(i).getCpf());
////			f.setRg(funcsDTO.get(i).getRg());
////			f.setTelefone(funcsDTO.get(i).getTelefone());
////			f.setEndereco(funcsDTO.get(i).getEndereco());
//			
////			f.setKilometragem_por_litro("12");
////			
////			if(statusFuncRepository.existsByCodigo(Long.parseLong(funcsDTO.get(i).getCodigo_status()))){
////				if(statusFuncRepository.findByCodigo(Long.parseLong(funcsDTO.get(i).getCodigo_status())).isSituacao()) {
////					f.setStatus(true);
////				}else {
////					f.setStatus(false);
////				}
////			}else {
////				f.setStatus(false);
////			}
////
////			if(funcsDTO.get(i).getEmpresa().getId().equals(Long.parseLong("3"))) {
////				f.setEmpresa(empresaRepository.findById(Long.parseLong("20152")).get());
////			}else if(funcsDTO.get(i).getEmpresa().getId().equals(Long.parseLong("4"))){
////				f.setEmpresa(empresaRepository.findById(Long.parseLong("20153")).get());
////			}
////			
////			if(cidadeRepository.existsByCidade(funcsDTO.get(i).getCidade().getCidade())) {
////				f.setCidade(cidadeRepository.findByCidade(funcsDTO.get(i).getCidade().getCidade()));
////			}else {
////				f.setCidade(cidadeRepository.findById(Long.parseLong(String.valueOf("20202"))).get());
////			}
////			
////			try {
////				JSONObject cords = new JSONObject(geo.GeocodeSync(funcsDTO.get(i).getEndereco()).toString());
////				f.setLatitude(cords.getString("lat"));
////				f.setLongitude(cords.getString("lng"));
////			} catch (IOException e) {
////				e.printStackTrace();
////			} catch (InterruptedException e) {
////				e.printStackTrace();
////			}
//			funcionarios.add(f);
//			System.out.print("a");
//		}
//		
//		funcionarioRepository.saveAll(funcionarios);
//	}
}
