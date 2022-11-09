package btt_telecom.api.config.external;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Geocoder {

    private static final String GEOCODING_RESOURCE = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String API_KEY = "AIzaSyD1j-aZgDYi3Wq7Na29lk45otM22aYF8uM";

    public JSONObject GeocodeSync(String query) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        String encodedQuery = URLEncoder.encode(query,"UTF-8");
        String requestUri = GEOCODING_RESOURCE + "?key=" + API_KEY + "&address=" + encodedQuery;

        HttpRequest geocodingRequest = HttpRequest.newBuilder().GET().uri(URI.create(requestUri))
                .timeout(Duration.ofMillis(600)).build();

        HttpResponse<?> geocodingResponse = httpClient.send(geocodingRequest,
                HttpResponse.BodyHandlers.ofString());
        try {
			JSONObject json = new JSONObject(geocodingResponse.body().toString());
			JSONArray jarr = json.getJSONArray("results");
			JSONObject geometry = new JSONObject(jarr.get(0).toString());
			JSONObject location = new JSONObject(geometry.get("geometry").toString());
			JSONObject coord = new JSONObject(location.get("location").toString());
			return coord;
		} catch (JSONException e) {
			System.out.println(e);
			return null;
		}
    }

}