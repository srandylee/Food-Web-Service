/*
 * Distributed Systems - Project 2
 */

package hello;

import java.util.ArrayList;

public class Greeting {
 
    private final String address;
    private ArrayList<RestaurantDetails> restaurants;
    
    public Greeting(String address) {
        this.address = address;
        this.restaurants = new ArrayList<RestaurantDetails>();
    }

    public String getaddress() {
        return address;
    }
    
    public ArrayList<RestaurantDetails> getRestaurants(){
    	return restaurants;
    }
    
    public void addThingy(RestaurantDetails newDetails) {
    	restaurants.add(newDetails);
    }
   
}
