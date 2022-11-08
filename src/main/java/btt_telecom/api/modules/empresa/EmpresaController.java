package btt_telecom.api.modules.empresa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.config.general.AbstractMethods;

@RestController
@RequestMapping(path = "/api/empresa")
public class EmpresaController extends AbstractMethods{
	
	private EmpresaDAO empresaDAO = new EmpresaDAO();
	
	@GetMapping("/page")
	private ResponseEntity<Page<EmpresaRubi>> findAllWithPage(Pageable pageable){
		try {
			return new ResponseEntity<>(convertListToPage(empresaDAO.findAll(), pageable), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
