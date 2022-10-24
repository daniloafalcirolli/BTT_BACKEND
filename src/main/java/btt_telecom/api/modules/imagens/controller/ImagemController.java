package btt_telecom.api.modules.imagens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import btt_telecom.api.modules.imagens.repository.ImagemRepository;

@RestController
@RequestMapping(path = "/api/imagens")
public class ImagemController {

	@Autowired
	private ImagemRepository imagemRepository;
	
	@PostMapping(consumes = { "multipart/form-data" })
	public ResponseEntity<HttpStatus> teste(@RequestPart(name = "image") MultipartFile[] name) {
		for(int i = 0; i < name.length; i++) {
			
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
