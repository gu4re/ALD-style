package es.codeurjc.services;

import es.codeurjc.classes.Shoes;
import es.codeurjc.exceptions.UnsupportedExportException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Service that manage all mapping about CartService
 * specially used in cart functions like add and show
 * @author gu4re
 * @version 1.4
 */
@Service
public class CartService implements Serializable {
	/**
	 * Fake database that controls the shoes inside cart in the App
	 * and maps the name of the product (Key) with a Java Object called
	 * Shoes that contains all info about it (Value)
	 */
	private static Map<Shoes, Integer> shoesMap;
	
	/**
	 * The max stock each pair of Shoes has
	 */
	private static final int STOCK = 3;
	
	/**
	 * Private constructor avoiding initialize of Service
	 */
	private CartService(){}
	
	/**
	 * Starts the CartService creating the database of the Service.<br><br>
	 * @deprecated <a style="color: #E89B6C; display: inline;">
	 * This method can <b>only</b> be used by SpringBeanSystem</a> so abstain from using it
	 */
	@EventListener
	@SuppressWarnings(value = "unused")
	@Deprecated(since = "1.3")
    public static void run(ContextRefreshedEvent event) {
		shoesMap = new HashMap<>();
	}
	
	/**
	 * Stops the CartService before the Spring Application ends, clearing the shoes database
	 * @deprecated <a style="color: #E89B6C; display: inline;">
	 * This method can <b>only</b> be used by SpringBeanSystem</a> so abstain from using it
	 */
	@SuppressWarnings(value = "unused")
	@Deprecated(since = "1.3")
	@EventListener
	public static void stop(ContextClosedEvent event){
		shoesMap.clear();
	}
	
	/**
	 * Check if a pair of shoes exceeds the maximum of quantity inside cart
	 * @param name the name of the pair of shoes
	 * @param price the price of the pair of shoes
	 * @param size the size of the pair of shoes
	 * @return <a style="color: #E89B6C; display: inline;">True</a> if the pair of shoes exists
	 * otherwise <a style="color: #E89B6C; display: inline;">False</a>
	 */
	public static boolean maxQuantity(String name, float price, int size){
		Shoes shoes = new Shoes(name, price, size);
		return shoesMap.containsKey(shoes) && shoesMap.get(shoes) > STOCK;
	}
	
	/**
	 * Adds the Shoes object passed through different args to the cart database
	 * @param name the name of the pair of shoes
	 * @param price the price of the pair of shoes
	 * @param size the size of the pair of shoes
	 */
	public static void addToCart(String name, float price, int size){
		Shoes shoes = new Shoes(name, price, size);
		if (shoesMap.containsKey(shoes))
			shoesMap.put(shoes, shoesMap.get(shoes) + 1);
		else
			shoesMap.put(shoes, 1);
	}
	
	/**
	 * Export the database in JSONArray form to give it to the frontend
	 * @return the Shoes database in JSONArray format
	 * @throws UnsupportedExportException if it was unable to convert the database to JSONArray
	 */
	public static @NotNull JSONArray export() throws UnsupportedExportException {
		try{
			JSONArray jsonArray = new JSONArray();
			for (Map.Entry<Shoes, Integer> entry: shoesMap.entrySet()){
				Shoes shoes = entry.getKey();
				Integer quantity = entry.getValue();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("name", shoes.getName());
				jsonObject.put("price", shoes.getPrice());
				jsonObject.put("size", shoes.getSize());
				jsonObject.put("quantity", quantity);
				jsonArray.put(jsonObject);
			}
			return jsonArray;
		} catch (JSONException e){
			Logger.getLogger("Error has occurred during creating JSONArray.");
			throw new UnsupportedExportException();
		}
	}
	
	/**
	 * Allows us to clear the cart
	 */
	public static void clear(){
		shoesMap.clear();
	}
}
