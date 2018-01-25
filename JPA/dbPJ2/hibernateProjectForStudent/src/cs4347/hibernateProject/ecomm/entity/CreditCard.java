package cs4347.hibernateProject.ecomm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
// Credit Card Entity
@Entity
@Table(name = "CreditCard", uniqueConstraints = {})
public class CreditCard 
{
	@Id // primary key
	@GeneratedValue
	@Column(name = "cc_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Long id;
	@Column(name = "credit_Card") // maps name to a column
	private String name;
	@Column(name = "cc_Number") // maps credit card number to a column
	private String ccNumber;
	@Column(name = "exp_Date") // maps expiration date to a column
	private String expDate;
	@Column(name = "security_Code") // maps security code to a column
	private String securityCode;

	//getter method for id
	public Long getId() 
	{
		return id;
	}
	// setter method for id
	public void setId(Long id)
	{
		this.id = id;
	}

	// getter method for name
	public String getName()
	{
		return name;
	}
	// setter method for name
	public void setName(String name)
	{
		this.name = name;
	}

	// getter method for credit card number
	public String getCcNumber()
	{
		return ccNumber;
	}
	// setter method for credit card number
	public void setCcNumber(String ccNumber)
	{
		this.ccNumber = ccNumber;
	}

	// getter method for expiration date
	public String getExpDate()
	{
		return expDate;
	}
	// setter method for expiration date
	public void setExpDate(String expDate)
	{
		this.expDate = expDate;
	}

	// getter method for security code
	public String getSecurityCode()
	{
		return securityCode;
	}
	// setter method for security code
	public void setSecurityCode(String securityCode)
	{
		this.securityCode = securityCode;
	}

}
