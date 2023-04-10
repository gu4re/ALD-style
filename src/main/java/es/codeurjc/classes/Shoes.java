package es.codeurjc.classes;

import lombok.Data;

/**
 * Contains all the information shoes can have inside the web application
 * @author gu4re
 * @version 1.0
 */
@Data
public class Shoes {
	/**
	 * Name of the pair of shoes
	 */
	private String name;
	/**
	 * Price of the pair of shoes
	 */
	private float price;
	/**
	 * Size number fo the pair of shoes
	 */
	private int size;
	
	/**
	 * Constructor to generate a Shoes object with all necessary info
	 * @param name the name for the shoes
	 * @param price the price of the shoes
	 * @param size the size number of shoes
	 */
	public Shoes(String name, float price, int size){
		this.name = name;
		this.price = price;
		this.size = size;
	}
}
