package es.codeurjc.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Contains all the information about a completed order can have inside the web application
 * @author gu4re
 * @version 1.0
 */
@Entity
@Table(name = "orders")
@NoArgsConstructor
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
	@Column(name = "description", length = 7000)
	private String jsonDescription;
	
	/**
	 * N Orders can own to the same user
	 */
	@ManyToOne
	private UserEntity user;
	
	@ManyToMany
	@JoinTable(name = "order_shoes",
    joinColumns = {@JoinColumn(name = "order_id")},
    inverseJoinColumns = {@JoinColumn(name = "shoes_name"), @JoinColumn(name = "size")})
	private List<ShoesEntity> shoesEntities;
}