package btt_telecom.api.config.general;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import btt_telecom.api.external.Geocoder;

public class AbstractMethods {
	
	public <T> Page<T> convertListToPage(List<T> list, Pageable pageable){
		Integer size = list.size();
		
		List<T> pageList = list.stream()
			.skip(pageable.getPageSize() * pageable.getPageNumber())
			.limit(pageable.getPageSize())
			.collect(Collectors.toList());
		
		return new PageImpl<>(pageList, pageable, size);
	}
	
	public String getFormattedAddress(String tipo_logradouro, String rua, String numero, String bairro, String cidade, String estado, String cep) {		
		switch(tipo_logradouro) {
			case "R": 
				tipo_logradouro = "Rua ";
				break;
			case "AV":
				tipo_logradouro = "Avenida ";
				break;
			case "EST":
				tipo_logradouro = "Estrada ";
				break;
		}
		
		String finalAddress = String.format("%s %s, %s - %s, %s - %s", tipo_logradouro, rua, numero, bairro, cidade, estado);
		return finalAddress;
	}
	
	public JSONObject getLatAndLng(String endereco) throws JSONException {
		Geocoder geo = new Geocoder();
		try {
			return new JSONObject(geo.GeocodeSync(endereco).toString());
		} catch(InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
