/*
 * Seungryoul Lee
 * RUID: 164003396 / netID: sl1263
 * Distributed Systems - Project 2
 */

package hello;

import java.io.IOException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GreetingController {
	
    @RequestMapping("/restaurant")
    public ResponseEntity<Greeting> greeting(@RequestParam(value="address", defaultValue="World") String address) throws IOException{
    	
    	Greeting g = new Greeting(String.format(address));
    	
    	ResponseEntity<Greeting> res = new ResponseEntity<Greeting>(g, HttpStatus.BAD_GATEWAY);
    	
    	//make API call to Geocod
    	RestTemplate restTemplateGeocod = new RestTemplate();
    	
    	String geocodKey = "&api_key=e305b9c29a5a3ebfef2bf6b2860c96c05c153af";
    	String geocodURL = "https://api.geocod.io/v1.3/geocode?q="+address+geocodKey;
    	
    	ResponseEntity<String> responseGeocod = restTemplateGeocod.exchange(geocodURL, HttpMethod.GET, null, String.class);
    	
    	//Let's find lat and lng
    	ObjectMapper mapperGeocod = new ObjectMapper();
    	JsonNode rootGeocod = mapperGeocod.readTree(responseGeocod.getBody());
    	JsonNode latitudeNode = rootGeocod.findPath("lat");
    	JsonNode longitudeNode = rootGeocod.findPath("lng");
    	
    	//Now time to use lat and lng to make API call to Zomato
    	RestTemplate restTemplateZomato = new RestTemplate();
    	String zomatoURL = "https://developers.zomato.com/api/v2.1/geocode";
    	
    	//Let's add lat and lng as parameters to zomatoURL
    	zomatoURL += "?lat=" + latitudeNode.asText() + "&lon=" + longitudeNode.asText();
    	    	
    	//Let's pass headers
    	HttpHeaders headersZomato = new HttpHeaders();
    	headersZomato.add("user-key", "769cf62c61ebecb628dd28d5481f0b04");
    	HttpEntity<String> entityZomato = new HttpEntity<String>(headersZomato);
    	
    	ResponseEntity<String> responseZomato = restTemplateZomato.exchange(zomatoURL, HttpMethod.GET, entityZomato, String.class);

    	ObjectMapper mapperZomato = new ObjectMapper();
    	JsonNode rootZomato = mapperZomato.readTree(responseZomato.getBody());
    	JsonNode nearbyZomato = rootZomato.findPath("nearby_restaurants");
   	  	
    	//int counter = 0;
    	
    	//Create JSON Object for each restaurant
    	for (JsonNode restaurantNode : nearbyZomato) {
    		
    		//counter += 1;
    		//System.out.println(counter);
    		
    		JsonNode nameNode = restaurantNode.findPath("name");
    		JsonNode addressNode = restaurantNode.findPath("address");
    		JsonNode cuisinesNode = restaurantNode.findPath("cuisines");
    		JsonNode ratingNode = restaurantNode.findPath("aggregate_rating");
    		
    		RestaurantDetails newDetails = new RestaurantDetails(nameNode, addressNode, cuisinesNode, ratingNode);
    		g.addThingy(newDetails);
    		
    	}
    	
    	return res;
	
    }
       
}