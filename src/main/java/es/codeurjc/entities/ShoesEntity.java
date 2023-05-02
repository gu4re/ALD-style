package es.codeurjc.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * Contains all the information cart can have inside the web application
 * @author gu4re
 * @version 1.0
 */
@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
public class ShoesEntity {
	
	@EmbeddedId
	@Column(name = "cart_id")
	private ShoesEntityId shoesEntityId;
	
	@Data
	@Embeddable
	public static class ShoesEntityId implements Serializable {
	    @Column(name = "shoesName")
	    private String shoesName;
	
	    @Column(name = "size")
	    private int size;
	}
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "price")
	private float price;
	
	@ManyToMany(mappedBy = "shoesEntities")
	private List<OrderEntity> orderEntities;
	
	public ShoesEntity(@NotNull String shoesName, int size, int quantity, float price){
		shoesEntityId = new ShoesEntityId();
		shoesEntityId.setShoesName(shoesName);
		shoesEntityId.setSize(size);
		this.quantity = quantity;
		this.price = price;
	}
}
