package es.codeurjc.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * Contains all the information related to the N:M relation that exists between an Order
 * and Shoes. An order can have multiple Shoes and multiple Shoes can appear inside an Order
 * @author gu4re
 * @version 1.0
 */
@Entity
@Table(name = "orders_shoes")
@Data
public class OrderShoesEntity {
	@EmbeddedId
	private OrderShoesId id;
	@Data
	@Embeddable
	public static class OrderShoesId implements Serializable {
		@ManyToOne
		@JoinColumn(name = "id")
		private Long idOrder;
		
		@ManyToOne
		@JoinColumn(name = "name")
		private String shoesName;
		
		public OrderShoesId(){}
	}
	public OrderShoesEntity(){}
}
