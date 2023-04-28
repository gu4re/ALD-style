package es.codeurjc.controllers;

import es.codeurjc.exceptions.UnsupportedExportException;
import es.codeurjc.services.CartService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 * Controls the mapping of the cart page and all related to
 * add and keep the control of products, in this case, shoes
 * @author gu4re
 * @version 1.5
 */
@Controller
@RequestMapping("/cart")
public class CartController {
	/**
	 * Private constructor avoiding initialize of CartController
	 */
	private CartController(){}
	
	/**
	 * Treat the post requests that the controller receives from the frontend during adding
	 * Shoes to the cart and sending a ResponseEntity back to the web page as a response
	 * @param jsonRequested The JSON Object as String sent from the frontend
	 * @return A ResponseEntity based of what happened in the Service
	 * returning <a style="color: #E89B6C; display: inline;">200 OK</a> if all goes good,
	 * <a style="color: #E89B6C; display: inline;">400 Bad Request</a> if not and
	 * otherwise <a style="color: #E89B6C; display: inline;">404 Not Found</a> if any resource is not able
	 */
	@PostMapping("/add")
	public ResponseEntity<Void> add(@RequestBody String jsonRequested){
		try{
			JSONObject jsonObject = new JSONObject(jsonRequested);
			if (CartService.maxQuantity(jsonObject.getString("name"),
					Float.parseFloat(jsonObject.getString("prize")),
					Integer.parseInt(jsonObject.getString("size"))))
				return ResponseEntity.badRequest().build();
			CartService.addToCart(jsonObject.getString("name"),
					Float.parseFloat(jsonObject.getString("prize")),
					Integer.parseInt(jsonObject.getString("size")));
			return ResponseEntity.ok().build();
		} catch (JSONException e){
			Logger.getLogger("Error has occurred during parsing JSON.");
			return ResponseEntity.notFound().build();
		} catch (NumberFormatException | NullPointerException e){
			Logger.getLogger("Error has occurred during parsing prize or size");
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * Treat the returning of the whole Shoes database to the frontend to show it
	 * inside cart
	 * @return A ResponseEntity based of what happened in the Service
	 * returning <a style="color: #E89B6C; display: inline;">200 OK with the JSONArray</a>
	 * if all goes good and <a style="color: #E89B6C; display: inline;">400 Bad Request</a>
	 * if not
	 */
	@GetMapping("/show")
	public ResponseEntity<String> show(){
		try{
			return ResponseEntity.ok(CartService.export().toString());
		} catch (UnsupportedExportException e){
			return ResponseEntity.badRequest().build();
		}
	}
	
	/**
	 * Treat the clearing of the cart
	 * @return A ResponseEntity based of what happened in the Service
	 * returning <a style="color: #E89B6C; display: inline;">200 OK</a>
	 */
	@PutMapping("/clear")
	public ResponseEntity<Void> clear(){
		CartService.clear();
		return ResponseEntity.ok().build();
	}
}
