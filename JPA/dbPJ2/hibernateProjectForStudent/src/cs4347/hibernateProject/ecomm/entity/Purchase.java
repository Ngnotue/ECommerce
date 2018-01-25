package cs4347.hibernateProject.ecomm.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
// Purchase entity
@Entity
@Table(name = "Purchase", uniqueConstraints = {})
public class Purchase 
{
	private Long id; // to hold purchase id
	private Date purchaseDate; // to hold purchase date
	private double purchaseAmount; // to hold purchase amount
	private Customer customer; // to hold customer
	private Product product; // to hold product

	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pur_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Long getId() // getter method for id
	{
		return id;
	}
	// setter method for id
	public void setId(Long id)
	{
		this.id = id;
	}
	// maps purchase date to a column with options as below
	@Column(name = "purchaseDate", unique = false, nullable = false, insertable = true, updatable = true)
	public Date getPurchaseDate() // getter method for purchase date
	{
		return purchaseDate;
	}
	// setter method for purchase date
	public void setPurchaseDate(Date purchaseDate)
	{
		this.purchaseDate = purchaseDate;
	}
	// maps purchase amount to a column
	@Column(name = "purchaseAmount", unique = false, nullable = false, 
			insertable = true, updatable = true, precision = 8, scale = 0)
	public double getPurchaseAmount() // getter method for purchase amount
	{
		return purchaseAmount;
	}
	// setter method for purchase amount
	public void setPurchaseAmount(double purchaseAmount)
	{
		this.purchaseAmount = purchaseAmount;
	}
	// purchase and customer has 1-1 association
	@OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Customer getCustomer() // getter method for customer
	{
		return customer;
	}
	// setter method for customer
	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}
	// purchase has 1-1 association with product
	@OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@JoinColumn(name = "prod_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Product getProduct() // getter method for product
	{
		return product;
	}
	// setter method for product
	public void setProduct(Product product)
	{
		this.product = product;
	}

}
