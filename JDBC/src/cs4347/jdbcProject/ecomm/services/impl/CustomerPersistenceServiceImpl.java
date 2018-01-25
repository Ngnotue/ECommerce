package cs4347.jdbcProject.ecomm.services.impl;


import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.dao.impl.AddressDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CreditCardDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.CustomerDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.services.CustomerPersistenceService;
import cs4347.jdbcProject.ecomm.util.DAOException;

public class CustomerPersistenceServiceImpl implements CustomerPersistenceService
{
	private DataSource dataSource;

	public CustomerPersistenceServiceImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	/**
	 * This method provided as an example of transaction support across multiple inserts.
	 * 
	 * Persists a new Customer instance by inserting new Customer, Address, 
	 * and CreditCard instances. Notice the transactional nature of this 
	 * method which inludes turning off autocommit at the start of the 
	 * process, and rolling back the transaction if an exception 
	 * is caught. 
	 */
	
	public Customer create(Customer customer) throws SQLException, DAOException
	{
		CustomerDAO customerDAO = new CustomerDaoImpl();
		AddressDAO addressDAO = new AddressDaoImpl();
		CreditCardDAO creditCardDAO = new CreditCardDaoImpl();
        // get connection to datasource
		Connection connection = dataSource.getConnection();
		try {
			connection.setAutoCommit(false); // disable auto commit
			Customer cust = customerDAO.create(connection, customer); // create a new customer entity
			Long custID = cust.getId(); // get id
            // throw exception if null id
			if (cust.getAddress() == null) {
				throw new DAOException("Customers must include an Address instance.");
			}
            // get address of customer
			Address address = cust.getAddress();
			addressDAO.create(connection, address, custID);
            // throw exception if no credit card
			if (cust.getCreditCard() == null) {
				throw new DAOException("Customers must include an CreditCard instance.");
			}
            // get credit card
			CreditCard creditCard = cust.getCreditCard();
			creditCardDAO.create(connection, creditCard, custID);
            // commit transaction
			connection.commit();
			return cust;
		}
		catch (Exception ex) {
			connection.rollback();
			throw ex;
		}
		finally {
			if (connection != null) {
				connection.setAutoCommit(true);
			}
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	// this method retrieves customer
	public Customer retrieve(Long id) throws SQLException, DAOException {
		// get connection
		Connection connection = dataSource.getConnection();
		CustomerDAO dao = new CustomerDaoImpl();
		AddressDAO addressDAO = new AddressDaoImpl();
		CreditCardDAO creditCardDAO = new CreditCardDaoImpl();
		// create a new customer entity to retrieve data
		Customer customer = dao.retrieve(connection, id);
		customer.setAddress(addressDAO.retrieveForCustomerID(connection, id));
		customer.setCreditCard(creditCardDAO.retrieveForCustomerID(connection, id));
		// close connection and return customer
		connection.close();
		return customer;
	}

	// this method updates customer data
	public int update(Customer customer) throws SQLException, DAOException {
        // get connection
		Connection connection = dataSource.getConnection();

		CustomerDAO dao = new CustomerDaoImpl();
        // update data
		int rows = dao.update(connection, customer);
		connection.close(); // close connections
		return rows;
	}

	// this method deletes a customer
	public int delete(Long id) throws SQLException, DAOException {
        // get connection
		Connection connection = dataSource.getConnection();
		CustomerDAO dao = new CustomerDaoImpl();
        // delete data
		int rows = dao.delete(connection, id);
		connection.close(); // close connection
		return rows;
	}

	// this method returns a list of all customers in a zipcode
	public List<Customer> retrieveByZipCode(String zipCode)
			throws SQLException, DAOException {
        // get connection
		Connection connection = dataSource.getConnection();
		CustomerDAO dao = new CustomerDaoImpl();
		AddressDAO addressDAO = new AddressDaoImpl();
		CreditCardDAO creditCardDAO = new CreditCardDaoImpl();
		
		// retrieve a list of customers
		List<Customer> customers = dao.retrieveByZipCode(connection, zipCode);
		
		// for each customer, set the address and credit card
		for(Customer c : customers) {
			c.setAddress(addressDAO.retrieveForCustomerID(connection, c.getId()));
			c.setCreditCard(creditCardDAO.retrieveForCustomerID(connection, c.getId()));
		}
		
		// close connection
		connection.close();
		return customers;
	}

	// this method returns a list of all customers within a specified range date of birth
	public List<Customer> retrieveByDOB(Date startDate, Date endDate)
			throws SQLException, DAOException {
        // get connection to server
		Connection connection = dataSource.getConnection();
		CustomerDAO dao = new CustomerDaoImpl();
		AddressDAO addressDAO = new AddressDaoImpl();
		CreditCardDAO creditCardDAO = new CreditCardDaoImpl();
		// create a list to store customers
		List<Customer> customers = dao.retrieveByDOB(connection, startDate, endDate);

		for(Customer c : customers) {
			c.setAddress(addressDAO.retrieveForCustomerID(connection, c.getId()));
			c.setCreditCard(creditCardDAO.retrieveForCustomerID(connection, c.getId()));
		}
		// close connection
		
		connection.close();
		return customers;
	}
}
