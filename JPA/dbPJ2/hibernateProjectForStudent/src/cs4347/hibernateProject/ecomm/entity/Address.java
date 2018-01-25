package cs4347.hibernateProject.ecomm.entity;

import javax.persistence.*;
// Address Entity
@Entity
@Table(name = "Address", uniqueConstraints = {})
public class Address 
{	@Id // primary key
	@GeneratedValue
	@Column(name = "addr_id", unique = true, nullable = false, insertable = true, updatable = true) // maps addr_id to a column with above options
	private Long id;
	@Column(name = "address") // maps address1 to a column
	private String address1;
	@Column(name = "address2") // maps address2 to a column
	private String address2;
	@Column(name = "city") // maps city to a column
	private String city;
	@Column(name = "state") // maps state to a column
	private String state;
	@Column(name = "zipcode") // maps zipcode to a column
	private String zipcode;

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
	
	// getter method for address1
	public String getAddress1()
	{
		return address1;
	}
	// setter method for address1
	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}
	// getter method for address2
	public String getAddress2()
	{
		return address2;
	}
	// setter method for address2
	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}


	// getter method for city
	public String getCity()
	{
		return city;
	}
	// setter method for city
	public void setCity(String city)
	{
		this.city = city;
	}


	// getter method for state
	public String getState()
	{
		return state;
	}
	// setter method for state
	public void setState(String state)
	{
		this.state = state;
	}


	// getter method for zipcode
	public String getZipcode()
	{
		return zipcode;
	}
	// setter method for zipcode
	public void setZipcode(String zipcode)
	{
		this.zipcode = zipcode;
	}

}
