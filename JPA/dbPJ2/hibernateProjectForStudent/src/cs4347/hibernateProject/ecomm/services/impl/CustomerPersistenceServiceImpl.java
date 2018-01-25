package cs4347.hibernateProject.ecomm.services.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import cs4347.hibernateProject.ecomm.entity.Customer;
import cs4347.hibernateProject.ecomm.services.CustomerPersistenceService;
import cs4347.hibernateProject.ecomm.util.DAOException;

public class CustomerPersistenceServiceImpl implements CustomerPersistenceService
{
	private EntityManager em; // create an EntityManager object
	//constructor
	public CustomerPersistenceServiceImpl(EntityManager em)
	{
		this.em = em;
	}
	
	// this method creates a new customer
	@Override
	public Customer create(Customer customer) throws SQLException, DAOException
	{
		// throw exception if customer already has id
		if(customer.getId() != null)
			throw new DAOException("Error: Trying to create Customer with non-null ID");
		// else create customer
		em.getTransaction().begin();
		em.persist(customer);
		em.getTransaction().commit();
		
		// does this object has an id yet?
		return customer;
	}
	// this method retrieves a customer with provided id
	@Override
	public Customer retrieve(Long id) throws SQLException, DAOException
	{	// throw exception if provided with a null id
		if(id == null)
			throw new DAOException("Error: Trying to retrieve Customer with null ID");
		// else retrieve customer with id
		em.getTransaction().begin();
		Customer customer = em.find(Customer.class, id);
		em.getTransaction().commit();
		// return customer
		return customer;
	}

	// this method updates customer
	@Override
	public Customer update(Customer c1) throws SQLException, DAOException
	{	// throw exception if null id
		if(c1.getId() == null)
			throw new DAOException("Error: Trying to update Customer with null ID");
		//else retrieve customer
		Customer result = retrieve(c1.getId());
		result = c1;
		
		return result;
	}
	// this method deletes a customer with provided id
	@Override
	public void delete(Long id) throws SQLException, DAOException
	{	// throw exceptions if null id
		if(id == null)
			throw new DAOException("Error: Trying to delete Customer with null ID");
		// else delete customer
		em.getTransaction().begin();
		// find
		Customer customer = em.find(Customer.class, id);
		// remove
		em.remove(customer);
		em.getTransaction().commit();
	}
	// this method retrieves a list of customer with provided zipcode
	@Override
	public List<Customer> retrieveByZipCode(String zipCode) throws SQLException, DAOException
	{	// throw exception if null zipcode
		if(zipCode == null)
			throw new DAOException("Error: Trying to retrieve Product with null UPC");
		// else retrieve customers
		em.getTransaction().begin();
		
		TypedQuery<Customer> query = em.createQuery("from Customer as c where c.address.zipcode = :zip", Customer.class);
		query.setParameter("zip", zipCode);

		List<Customer> customers = query.getResultList();
		
		em.getTransaction().commit();

		return customers; // return list of customer
	}

	/**
	 * this method retrieve all Customer whose DOB is in the given date range.
	 */
	@Override
	public List<Customer> retrieveByDOB(Date startDate, Date endDate) throws SQLException, DAOException
	{
		// assume only valid if both dates are defined
		if(startDate == null && endDate == null)
			throw new DAOException("Error: Trying to retrieve Customers with null DOB");
		
		em.getTransaction().begin();
		
		TypedQuery<Customer> query = em.createQuery("from Customer c where c.dob between :start and :end", Customer.class);
        query.setParameter("start", startDate);
        query.setParameter("end", endDate);
		List<Customer> customers = query.getResultList();
		
		em.getTransaction().commit();

		return customers;
	}
}
