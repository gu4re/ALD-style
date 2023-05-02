package es.codeurjc.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Contains all the information cart can have inside the web application
 * @author gu4re
 * @version 1.0
 */
@Entity
@Table(name = "cart")
@Data
public class CartEntity {
	@EmbeddedId
	@Column(name = "cart_id")
	private CartEntityId cartEntityId;
	
	@Data
	@Embeddable
	public static class CartEntityId implements Serializable {
		@JoinColumn(name = "name_shoes")
		@ManyToOne(fetch = FetchType.LAZY)
        @MapsId("name")
		private String shoesName;
		
		@Column(name = "size")
		private int size;
	}
	
	@Column(name = "quantity")
	private int quantity;
	
	public CartEntity(){}
	
	public CartEntity(@NotNull ShoesEntity shoesEntity, int size, int quantity){
		this.cartEntityId = new CartEntityId();
		this.cartEntityId.setShoesName(shoesEntity.getName());
		this.cartEntityId.setSize(size);
		this.quantity = quantity;
	}
}
