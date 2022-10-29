package com.application.application.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@Table(name = "product")
@Entity
@Getter
@Setter
@ToString
public class Product {

	@Id
	private long productId;
	private String productName;
	private String productType;
	private Integer productCount;
	private float productRating;
	private double productPrice;
	private String manufacturerName;
	private String tag;
	private String productImage;
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.REMOVE)
	private List<Cart> carts;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", updatable = false)
	private User user;

	public Product(long productId, String productName, String productType,
			Integer productCount, float product_rating, Float product_price,
			String manufacturerName, String tag, String productImage, User user) {
		this.productId = productId;
		this.productName = productName;
		this.productType = productType;
		this.productCount = productCount;
		this.productRating = product_rating;
		this.productPrice = product_price;
		this.manufacturerName = manufacturerName;
		this.tag = tag;
		this.productImage = productImage;
		this.user = user;
	}

}
