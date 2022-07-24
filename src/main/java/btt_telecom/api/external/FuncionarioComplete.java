package btt_telecom.api.external;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.dto.FuncionarioDTO;
import btt_telecom.api.models.Funcionario;
import btt_telecom.api.repositories.EmpresaRepository;
import btt_telecom.api.repositories.FuncionarioRepository;

@RestController
@RequestMapping("/complete/funcionario")
public class FuncionarioComplete {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@GetMapping
	public void completar() {
		ArrayList<Funcionario> funcionarios = new ArrayList<>();
		getConnection c = new getConnection();
		ArrayList<FuncionarioDTO> funcsDTO = new ArrayList<>();
		
		Funcionario f;
		for(int i = 0; i < funcsDTO.size(); i++) {
			
			if(funcionarioRepository.existsByCpf(funcsDTO.get(i).getCpf())) {
				f = funcionarioRepository.findByCpf(funcsDTO.get(i).getCpf());
			}else {
				f = new Funcionario();
			}
			f.setCpf(funcsDTO.get(i).getCpf());
			f.setUsername(funcsDTO.get(i).getUsername());
			f.setPrimeiro_nome(funcsDTO.get(i).getPrimeiro_nome());
			f.setUltimo_nome(funcsDTO.get(i).getUltimo_nome());
			f.setEmpresa(empresaRepository.findById(funcsDTO.get(i).getEmpresa().getId()).get());
			
			funcionarios.add(f);
		}
		
		
		funcionarioRepository.saveAll(funcionarios);
		
	}
}
