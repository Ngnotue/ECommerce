package cs4347.hibernateProject.ecomm.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


// Customer entity
@Entity
@Table(name = "Customer", uniqueConstraints = {})
public class Customer 
{
	@Id // primary key
	@GeneratedValue
	@Column(name = "emp_id")
	private Long id;
	@Column(name = "fistName") // maps first name to a column
	private String firstName;
	@Column(name = "lastName") // maps last name to a column
	private String lastName;
	@Column(name = "gender") // maps gender to a column
	private Character gender;
	@Column(name = "dob"/*, unique = false, nullable = false, insertable = true, updatable = true*/) // maps date of birth to a column with above options
	private Date dob;
	@Column(name = "email") // maps email to a column
	private String email;
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY) // customer address and Address entity has N-1 association
	@JoinColumn(name = "addr_id",unique = false, nullable = true, insertable = true, updatable = true) // maps address to a column with above options
	private Address address;
	@ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY) // customer credit card and CreditCard has N-1 association
	@JoinColumn(name = "cc_id",unique = false, nullable = true, insertable = true, updatable = true)
	private CreditCard creditCard;

	// getter method for id
	public Long getId()
	{
		return id;
	}
	// setter method for id
	public void setId(Long id)
	{
		this.id = id;
	}

	

	// getter method for first name
	public String getFirstName()
	{
		return firstName;
	}
	// setter method for first name
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}


	// getter method for last name
	public String getLastName()
	{
		return lastName;
	}
	// setter method for last name
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}


	// getter method for gender
	public Character getGender()
	{
		return gender;
	}
	// setter method for gender
	public void setGender(Character gender)
	{
		this.gender = gender;
	}

	// getter method for date of birth
	public Date getDob()
	{
		return dob;
	}
	// setter method for date of birth
	public void setDob(Date dob)
	{
		this.dob = dob;
	}

	// getter method for email
	public String getEmail()
	{
		return email;
	}
	// setter method for email
	public void setEmail(String email)
	{
		this.email = email;
	}

	// getter method for address
	public Address getAddress()
	{
		return address;
	}
	// setter method for address
	public void setAddress(Address address)
	{
		this.address = address;
	}

	
	// getter method for credit card
	public CreditCard getCreditCard()
	{
		return creditCard;
	}
	// setter method for credit card
	public void setCreditCard(CreditCard creditCard)
	{
		this.creditCard = creditCard;
	}
}
