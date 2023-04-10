package es.codeurjc.services;

import es.codeurjc.classes.Shoes;
import org.jetbrains.annotations.NotNull;
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
 * @version 1.1
 */
@Service
public class CartService implements Serializable {
	/**
	 * Fake database that controls the shoes inside cart in the App
	 * and maps the name of the product (Key) with a Java Object called
	 * Shoes that contains all info about it (Value)
	 */
	private static Map<String, Shoes> shoesMap;
	
	/**
	 * Private constructor avoiding initialize of Service
	 */
	private CartService(){}
	
	/**
	 * Starts the CartService connecting the database to the Service.<br><br>
	 * An <a style="color: #ff6666; display: inline;"><b>error</b></a> can occur
	 * <a style="color: #E89B6C; display: inline;">if the database is not found</a>
	 * (IOException) <a style="color: #E89B6C; display: inline;">or</a> reading
	 * <a style="color: #E89B6C; display: inline;">casting fails</a> during deserialize
	 * process inside database (ClassNotFoundException) in
	 * <a style="color: #E89B6C; display: inline;">both cases</a> the app will run with
	 * a <a style="color: #E89B6C; display: inline;">new and empty database</a>
	 * @deprecated <a style="color: #E89B6C; display: inline;">
	 * This method can <b>only</b> be used by SpringBeanSystem</a> so abstain from using it
	 */
	@EventListener
	@SuppressWarnings(value = "unchecked, unused")
	@Deprecated(since = "1.0")
    public static void run(ContextRefreshedEvent event) {
		try (ObjectInputStream ois = new ObjectInputStream(
		        new FileInputStream("src/main/resources/database/shoes_database.bin"))) {
		    Object obj = ois.readObject();
	        shoesMap = (Map<String, Shoes>) obj;
		} catch (IOException e) {
			Logger.getLogger("Unable to find database or maybe does not exists.");
		    shoesMap = new HashMap<>();
		} catch (ClassNotFoundException e){
			Logger.getLogger("Unable to read content of database.");
			shoesMap = new HashMap<>();
		}
	}
	
	/**
	 * Stops the CartService before the Spring Application ends, saving the shoes database
	 * to a binary file
	 * @deprecated <a style="color: #E89B6C; display: inline;">
	 * This method can <b>only</b> be used by SpringBeanSystem</a> so abstain from using it
	 */
	@SuppressWarnings(value = "unused")
	@Deprecated(since = "1.0")
	@EventListener
	public static void stop(ContextClosedEvent event){
		try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("src/main/resources/database/shoes_database.bin"))) {
            oos.writeObject(shoesMap);
        } catch (IOException e) {
            Logger.getLogger("Unable to reach file to write on.");
        }
	}
	
	/**
	 * Check if a pair of shoes is already inside the cart
	 * @param name the name of the pair of shoes
	 * @return <a style="color: #E89B6C; display: inline;">True</a> if the pair of shoes exists
	 * otherwise <a style="color: #E89B6C; display: inline;">False</a>
	 */
	public static boolean shoesExists(@NotNull String name){
		return shoesMap.get(name) != null;
	}
	
	/**
	 * Adds the Shoes object passed through different args to the cart database
	 * @param name the name of the pair of shoes
	 * @param price the price of the pair of shoes
	 * @param size the size of the pair of shoes
	 */
	public static void addToCart(String name, float price, int size){
		shoesMap.put(name, new Shoes(name, price, size));
	}
	
	/**
	 * Getter method for the Shoes database needed to return the database to the frontend
	 * @return the Shoes database
	 */
	public static Map<String, Shoes> getShoesMap(){
		return shoesMap;
	}
}
