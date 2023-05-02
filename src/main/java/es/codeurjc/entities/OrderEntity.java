package es.codeurjc.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Contains all the information about a completed order can have inside the web application
 * @author gu4re
 * @version 1.0
 */
@Entity
@Table(name = "orders")
@Data
public class OrderEntity {
	/**
	 * Primary key of the Order table just an Order ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	/**
	 * Date stored when the order was made
	 */
	@Column(name = "date")
	private LocalDate date;
	
	/**
	 * Description of the order stored in (JSON) String format
	 * 1:1 internal relation between an Order and a Cart
	 */
	@Column(name = "description")
	private String jsonDescription;
	
	public OrderEntity(){}
}
