package es.codeurjc.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Contains all the information shoes can have inside the web application
 * @author gu4re
 * @version 1.1
 */
@Entity
@Table(name = "shoes")
@Data
public class ShoesEntity {
	/**
	 * Name of the pair of shoes
	 */
	@Id
	@Column(name = "name")
	private String name;
	
	/**
	 * Price of the pair of shoes
	 */
	@Column(name = "price")
	private float price;
	
	/**
	 * Empty Constructor
	 */
	public ShoesEntity(){}
	
	/**
	 * Constructor to generate a Shoes object with all necessary info
	 * @param name the name for the shoes
	 * @param price the price of the shoes
	 */
	public ShoesEntity(String name, float price){
		this.name = name;
		this.price = price;
	}
}
