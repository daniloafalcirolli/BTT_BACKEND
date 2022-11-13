package btt_telecom.api.modules.empresa;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import btt_telecom.api.config.general.AbstractMethods;

@RestController
@RequestMapping(path = "/api/empresa")
public class EmpresaController extends AbstractMethods{
	
	private EmpresaDAO empresaDAO = new EmpresaDAO();
	
	@GetMapping("/page")
	private ResponseEntity<Map<String, Object>> findAllWithPage(@RequestParam(name = "value", defaultValue = "") String value, @RequestParam(name = "size") Long size, @RequestParam(name = "page") Long page){
		try {
			if(value.equals("")) {
				return new ResponseEntity<>(convertListToPage(empresaDAO.findAll(), size, page), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(convertListToPage(empresaDAO.search(value), size, page), HttpStatus.OK);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
