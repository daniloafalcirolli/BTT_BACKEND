package btt_telecom.api.modules.users;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
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

@RestController
@RequestMapping(path = "/api/master/user")
public class MasterUserController extends AbstractMethods {
	
	@Autowired
	private MasterUserRepository masterUserRepository;
	
	@GetMapping("/page")
	private ResponseEntity<Map<String, Object>> findAllWithPage(@RequestParam(name = "value", defaultValue = "") String value, @RequestParam(name = "size") Long size, @RequestParam(name = "page") Long page){
		try {
			if(value.equals("")) {				
				return new ResponseEntity<>(convertListToPage(masterUserRepository.findAll(), size, page), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(convertListToPage(masterUserRepository.search(value.toUpperCase()), size, page), HttpStatus.OK);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/search")
	private ResponseEntity<Page<MasterUser>> search(@RequestBody String body){
		try {
			JSONObject json = new JSONObject(body);
			List<MasterUser> result = masterUserRepository.search(json.getString("value"));
			Page<MasterUser> page = new PageImpl<>(result);
			return new ResponseEntity<>(page, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/{id}")
	private ResponseEntity<MasterUser> findById(@PathVariable(name = "id") Long id){
		try {
			if(masterUserRepository.existsById(id)) {
				return new ResponseEntity<>(masterUserRepository.findById(id).get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping
	private ResponseEntity<HttpStatus> save(@RequestBody MasterUser masterUser){
		if(masterUserRepository.save(masterUser) != null) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(path = "/login")
	private ResponseEntity<MasterUser> efetuarLogin(@RequestBody String body) {
		try {
			JSONObject json = new JSONObject(body);
			if(masterUserRepository.findByUsernameAndPassword(json.getString("email"), json.getString("password")) != null) {
				return new ResponseEntity<>(masterUserRepository.findByUsernameAndPassword(json.getString("username"), json.getString("password")), HttpStatus.OK);
			}else{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (JSONException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping
	private ResponseEntity<HttpStatus> edit(@RequestBody MasterUser masterUser){
		if(masterUserRepository.existsById(masterUser.getId())) {
			if(masterUserRepository.save(masterUser) != null) {
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(path = "/{id}")
	private ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id){
		try {
			if(masterUserRepository.existsById(id)) {
				masterUserRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
	}
}
