package es.codeurjc.services;

import es.codeurjc.entities.OrderEntity;
import es.codeurjc.entities.ShoesEntity;
import es.codeurjc.exceptions.UnsupportedExportException;
import es.codeurjc.repositories.OrderRepository;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service that manage all mapping about OrderService
 * specially used in order functions like show
 * @author gu4re
 * @version 1.4
 */
@Service
public class OrderService {
    @Autowired
	@SuppressWarnings("unused")
	private OrderRepository orderRepository;

    /**
	 * Export the database in JSONArray form to give it to the frontend
	 * @return the Shoes database in JSONArray format
	 * @throws UnsupportedExportException if it was unable to convert the database to JSONArray
	 */
	public @NotNull JSONArray export() throws UnsupportedExportException {
		try{
			List<OrderEntity> orderEntityList = orderRepository.findAll();
			JSONArray jsonArray = new JSONArray();
			for (OrderEntity orderEntity : orderEntityList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("description", orderEntity.getJsonDescription());
                jsonObject.put("date", orderEntity.getDate());
                jsonObject.put("id", orderEntity.getId());
				jsonArray.put(jsonObject);
			}
			return jsonArray;
		} catch (JSONException e){
			Logger.getLogger("Error has occurred during creating JSONArray.");
			throw new UnsupportedExportException();
		}
	}
}
