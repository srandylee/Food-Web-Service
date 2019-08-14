/*
 * Seungryoul Lee
 * RUID: 164003396 / netID: sl1263
 * Distributed Systems - Project 2
 */

package hello;

import com.fasterxml.jackson.databind.JsonNode;

public class RestaurantDetails {
	
	private JsonNode name;
	private JsonNode address;
	private JsonNode cuisines;
	private JsonNode rating;
	
	public RestaurantDetails(JsonNode nameNode, JsonNode addressNode, JsonNode cuisinesNode, JsonNode ratingNode) {
		this.name = nameNode;
		this.address = addressNode;
		this.cuisines = cuisinesNode;
		this.rating = ratingNode;
	}
	
	public JsonNode getName() {
		return name;
	}
	
	public JsonNode getAddress() {
		return address;
	}
	
	public JsonNode getCuisines() {
		return cuisines;
	}
	
	public JsonNode getRating() {
		return rating;
	}
	
}
