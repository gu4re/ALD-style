package es.codeurjc.services;

import es.codeurjc.entities.ShoesEntity;
import es.codeurjc.exceptions.UnsupportedExportException;
import es.codeurjc.repositories.CartRepository;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
		ShoesEntity.ShoesEntityId shoesEntityId = new ShoesEntity.ShoesEntityId();
		shoesEntityId.setSize(size);
		shoesEntityId.setShoesName(name);
		return cartRepository.findById(shoesEntityId).isPresent()
				&& cartRepository.findById(shoesEntityId).get().getQuantity() > STOCK;
	}
	
	/**
	 * Adds the Shoes object passed through different args to the cart database
	 * @param name the name of the pair of shoes
	 * @param price the price of the pair of shoes
	 * @param size the size of the pair of shoes
	 */
	public void addToCart(String name, float price, int size){
		ShoesEntity.ShoesEntityId shoesEntityId = new ShoesEntity.ShoesEntityId();
		shoesEntityId.setSize(size);
		shoesEntityId.setShoesName(name);
		Optional<ShoesEntity> optionalCartEntity = cartRepository.findById(shoesEntityId);
		ShoesEntity shoesEntity;
		if (optionalCartEntity.isPresent()){
			shoesEntity = optionalCartEntity.get();
			if (shoesEntity.getShoesEntityId().getSize() == size)
				shoesEntity.setQuantity(shoesEntity.getQuantity() + 1);
			else
				shoesEntity = new ShoesEntity(name, size, 1, price);
			cartRepository.save(shoesEntity);
		}
		else{
			shoesEntity = new ShoesEntity(name, size, 1, price);
			cartRepository.save(shoesEntity);
		}
	}
	
	/**
	 * Export the database in JSONArray form to give it to the frontend
	 * @return the Shoes database in JSONArray format
	 * @throws UnsupportedExportException if it was unable to convert the database to JSONArray
	 */
	public @NotNull JSONArray export(@NotNull String mode) throws UnsupportedExportException {
		try{
			List<ShoesEntity> shoesEntityList = new ArrayList<>();
			JSONArray jsonArray = new JSONArray();
			switch (mode){
				case "default" -> shoesEntityList = cartRepository.findAll();
				case "Higher Price" -> shoesEntityList = cartRepository.findAllOrderedByDESCPrice();
				case "Lower Price" -> shoesEntityList = cartRepository.findAllOrderedByASCPrice();
				default -> throw new UnsupportedExportException();
			}
			for (ShoesEntity shoesEntity : shoesEntityList){
				Integer quantity = shoesEntity.getQuantity();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("name", shoesEntity.getShoesEntityId().getShoesName());
				jsonObject.put("price", shoesEntity.getPrice());
				jsonObject.put("size", shoesEntity.getShoesEntityId().getSize());
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
