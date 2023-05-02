package es.codeurjc.services;

import es.codeurjc.entities.CartEntity;
import es.codeurjc.entities.ShoesEntity;
import es.codeurjc.exceptions.UnsupportedExportException;
import es.codeurjc.repositories.CartRepository;
import es.codeurjc.repositories.ShoesRepository;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service that manage all mapping about CartService
 * specially used in cart functions like add and show
 * @author gu4re
 * @version 1.4
 */
@Service
public class CartService {
	/**
	 * Repository of the cart's table that links mySQL to backend
	 */
	@Autowired
	@SuppressWarnings("unused")
	private CartRepository cartRepository;
	
	@Autowired
	@SuppressWarnings("unused")
	private ShoesRepository shoesRepository;
	
	/**
	 * The max stock each pair of Shoes has
	 */
	private static final int STOCK = 3;
	
	/**
	 * Private constructor avoiding initialize of Service
	 */
	private CartService(){}
	
	/**
	 * Check if a pair of shoes exceeds the maximum of quantity inside cart
	 * @param name the name of the pair of shoes
	 * @param size the size of the pair of shoes
	 * @return <a style="color: #E89B6C; display: inline;">True</a> if the pair of shoes exists
	 * otherwise <a style="color: #E89B6C; display: inline;">False</a>
	 */
	public boolean maxQuantity(String name, int size){
		CartEntity.CartEntityId cartEntityId = new CartEntity.CartEntityId();
		cartEntityId.setSize(size);
		cartEntityId.setShoesName(name);
		return cartRepository.findById(cartEntityId).isPresent()
				&& cartRepository.findById(cartEntityId).get().getQuantity() > STOCK;
	}
	
	/**
	 * Adds the Shoes object passed through different args to the cart database
	 * @param name the name of the pair of shoes
	 * @param price the price of the pair of shoes
	 * @param size the size of the pair of shoes
	 */
	public void addToCart(String name, float price, int size){
		ShoesEntity shoesEntity = new ShoesEntity(name, price);
		CartEntity.CartEntityId cartEntityId = new CartEntity.CartEntityId();
		cartEntityId.setSize(size);
		cartEntityId.setShoesName(name);
		Optional<CartEntity> optionalCartEntity = cartRepository.findById(cartEntityId);
		CartEntity cartEntity;
		if (optionalCartEntity.isPresent()){
			cartEntity = optionalCartEntity.get();
			if (cartEntity.getCartEntityId().getSize() == size)
				cartEntity.setQuantity(cartEntity.getQuantity() + 1);
			else
				cartEntity = new CartEntity(shoesEntity, size, 1);
			cartRepository.save(cartEntity);
		}
		else{
			cartEntity = new CartEntity(shoesEntity, size, 1);
			cartRepository.save(cartEntity);
		}
	}
	
	/**
	 * Export the database in JSONArray form to give it to the frontend
	 * @return the Shoes database in JSONArray format
	 * @throws UnsupportedExportException if it was unable to convert the database to JSONArray
	 */
	public @NotNull JSONArray export() throws UnsupportedExportException {
		try{
			JSONArray jsonArray = new JSONArray();
			List<CartEntity> cartEntityList = cartRepository.findAll();
			for (CartEntity cartEntity: cartEntityList){
				
				Integer quantity = cartEntity.getQuantity();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("name", cartEntity.getCartEntityId().getShoesName());
				if (shoesRepository.findById(cartEntity.getCartEntityId().getShoesName()).isEmpty())
					throw new JSONException("Error has occurred during creating JSONArray.");
				jsonObject.put("price", shoesRepository.findById(cartEntity.getCartEntityId().getShoesName())
						.get().getPrice());
				jsonObject.put("size", cartEntity.getCartEntityId().getSize());
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
	public void clear(){
		cartRepository.deleteAll();
	}
}
