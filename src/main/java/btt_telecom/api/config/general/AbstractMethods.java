package btt_telecom.api.config.general;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.support.PagedListHolder;

import btt_telecom.api.config.external.Geocoder;

public class AbstractMethods {
	static final Logger log = LoggerFactory.getLogger(AbstractMethods.class);
	
	public <T> Map<String, Object> convertListToPage(List<T> list, Long pageSize, Long pageNumber){			
		PagedListHolder<T> page = new PagedListHolder<>(list);
		page.setPage(Integer.parseInt(String.valueOf(pageNumber)));
		page.setPageSize(Integer.parseInt(String.valueOf(pageSize)));

		Map<String, Object> response = new HashMap<>();
		response.put("content", page.getPageList());
		response.put("last", page.isLastPage());
		response.put("first", page.isFirstPage());
		response.put("number", page.getPage());
		response.put("totalPages", page.getPageCount());
		return response;
	}
	
	public String getFormattedAddress(String tipo_logradouro, String rua, String numero, String bairro, String cidade, String estado, String cep) {		
		switch(tipo_logradouro) {
			case "R": 
				tipo_logradouro = "RUA ";
				break;
			case "AV":
				tipo_logradouro = "AVENIDA ";
				break;
			case "EST":
				tipo_logradouro = "ESTRADA ";
				break;
		}
		
		String finalAddress = String.format("%s %s, %s - %s, %s - %s", tipo_logradouro, rua.toUpperCase(), numero, bairro.toUpperCase(), cidade.toUpperCase(), estado.toUpperCase());
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
	
	public String replaceLast(String find, String replace, String string) {
		int lastIndex = string.lastIndexOf(find);
		
		if(lastIndex == -1) {
			return string;
		}
		
		String beginString = string.substring(0, lastIndex);
		String endString = string.substring(lastIndex + find.length());
		
		return beginString + replace + endString;
	}
	
	public void insertLog(String msg) {
		log.info(msg);
	}

	public void insertError(String msg) {
		log.error(msg);
	}
}
