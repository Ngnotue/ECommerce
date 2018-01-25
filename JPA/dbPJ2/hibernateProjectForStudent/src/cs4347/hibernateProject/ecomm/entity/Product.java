package cs4347.hibernateProject.ecomm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
// product entity
@Entity
@Table(name = "Product", uniqueConstraints = {})
public class Product 
{
	private Long id; // store product id
	private String prodName; // store product name
	private String prodDescription; // store product description
	private int prodCategory; // store product category
	private String prodUPC; // store product UPC

	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prod_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Long getId() // getter method for id
	{
		return id;
	}
	// setter method for id
	public void setId(Long id)
	{
		this.id = id;
	}
	// maps product name to a column
	@Column(name = "prodName", unique = false, nullable = true)
	public String getProdName() // getter method for product name
	{
		return prodName;
	}
	// setter method for product name
	public void setProdName(String prodName)
	{
		this.prodName = prodName;
	}
	// maps product description to a column
	@Column(name = "prodDescription", unique = false, nullable = true)
	public String getProdDescription() // getter method for product description
	{
		return prodDescription;
	}
	// setter method for product description
	public void setProdDescription(String prodDescription)
	{
		this.prodDescription = prodDescription;
	}
	// maps product category to a column
	@Column(name = "prodCategory", unique = false, nullable = true)
	public int getProdCategory() // getter method for product category
	{
		return prodCategory;
	}
	// setter method for product category
	public void setProdCategory(int prodCategory)
	{
		this.prodCategory = prodCategory;
	}
	// maps product UPC to a column
	@Column(name = "prodUPC", unique = false, nullable = true)
	public String getProdUPC() // getter method for product  UPC
	{
		return prodUPC;
	}
	// setter method for product UPC
	public void setProdUPC(String prodUPC)
	{
		this.prodUPC = prodUPC;
	}

}
