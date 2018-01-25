	package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.util.DAOException;
// implementation of CustomerDAO
public class CustomerDaoImpl implements CustomerDAO
{
    // sql insert statement
	private static final String insertSQL = 
			"INSERT INTO Customer (firstName, lastName, gender, dob, email) VALUES (?, ?, ?, ?, ?);";

	
	// this method create a new customer entity
	public Customer create(Connection connection, Customer customer)
			throws SQLException, DAOException {
                // throw exception if id not found
		if (customer.getId() != null) {
			throw new DAOException("Trying to insert Customer with NON-NULL ID");
		}
                // create preparedstatement object
		PreparedStatement ps = null;
                // execution
		try {
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setString(3, Character.toString(customer.getGender()));
			ps.setDate(4, customer.getDob());
			ps.setString(5, customer.getEmail());
			
			ps.executeUpdate();

			// REQUIREMENT: Copy the auto-increment primary key to the customer ID.
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			customer.setId((long) lastKey);
			
			return customer;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	// this method retrieve customer with provided id
	public Customer retrieve(Connection connection, Long id)
			throws SQLException, DAOException {
		// sql retrieve statement
		final String retrieveC = "SELECT id, firstname, lastname, gender, dob, email FROM customer where id= ?";

		// throw exception if id not found
			if (id == null) {
				throw new DAOException("Trying to retrieve Customer with NULL ID");
			}
        // create a preparedstatement object
			PreparedStatement ps = null;
                // execution
			try {
				ps = connection.prepareStatement(retrieveC);
				ps.setLong(1, id);
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
					return null;
				}
        // create new customer object to return
				Customer cust = new Customer();
				cust.setId(rs.getLong("id"));
				cust.setFirstName(rs.getString("firstname"));
				cust.setLastName(rs.getString("lastname"));
				cust.setEmail(rs.getString("email"));
				cust.setDob(rs.getDate("dob"));
				cust.setGender(rs.getString("gender").charAt(0));
				
				return cust;
			}
			finally {
				if (ps != null && !ps.isClosed()) {
					ps.close();
				}
			}
		
	}

	// this method update customer attributes value
	public int update(Connection connection, Customer customer)
			throws SQLException, DAOException {
        // sql update statement
		final String updateSQL = "UPDATE customer SET firstName = ?, lastName = ?, gender = ?, dob = ?, email = ?"
		        + "WHERE id = ?;";
        // throw exception if invalid id
		if (customer.getId() == null) {
			throw new DAOException("Trying to update Customer with NULL ID");
		}

        // create a preparedstatement object
		PreparedStatement ps = null;
        // execution
		try {
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setString(3, customer.getGender().toString());
			ps.setDate(4, customer.getDob());
			ps.setString(5, customer.getEmail());
			ps.setLong(6,  customer.getId());
			int rows = ps.executeUpdate();
			return rows;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	

	}

	// this method deletes customer with provided id
	public int delete(Connection connection, Long id) throws SQLException,
			DAOException {
        // throw exception customer with invalid id
		if (id == null) {
			throw new DAOException("Trying to delete Customer with NULL ID");
		}
        // sql delete statement
		final String deleteSQL = "DELETE FROM customer WHERE ID = ?;";
        // create a preparedstatement object
		PreparedStatement ps = null;
        // execution
		try {
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);

			int rows = ps.executeUpdate();
			return rows;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
		
	}

	// this method returns a list of customers in provided zipcode
	public List<Customer> retrieveByZipCode(Connection connection,
			String zipCode) throws SQLException, DAOException {
        // sql retrieve statement
		final String retrieveC = "SELECT id, firstname, lastname, gender, dob, email "
				+ "FROM customer, address where zipcode = ?;";

		// throw exception if invalid zipcode
		if (zipCode == null) {
			throw new DAOException("Trying to retrieve Customer with NULL zipCode");
		}
        // create a preparedstatement object
		PreparedStatement ps = null;
        // execution
		try {
			ps = connection.prepareStatement(retrieveC);
			ps.setString(1, zipCode);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}
            // create a customer list
			List<Customer> result = new ArrayList<Customer>();
			while (rs.next()) {
            // create a new customer entity to return
			Customer cust = new Customer();
			cust.setId(rs.getLong("id"));
			cust.setFirstName(rs.getString("firstname"));
			cust.setLastName(rs.getString("lastname"));
			cust.setEmail(rs.getString("email"));
			cust.setDob(rs.getDate("dob"));
			cust.setGender(rs.getString("gender").charAt(0));
			result.add(cust);
			}
			return result;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	// this method retrieve customer by date of birth
	public List<Customer> retrieveByDOB(Connection connection, Date startDate,
			Date endDate) throws SQLException, DAOException {
        // sql retrieve statement
	final String retrieveC = "SELECT id, firstname, lastname, gender, dob, email "
			+ "FROM customer, address WHERE dob BETWEEN ? AND ?;";

		// throw exception if invalid date duration
		if ((endDate.compareTo(startDate)) <= 0) {
			throw new DAOException("endDate < startDate");
		}
        // create a preparedstatement object
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(retrieveC);
			ps.setDate(1, startDate);
			ps.setDate(2, endDate);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}
            // create a customer list
			List<Customer> result = new ArrayList<Customer>();
			while (rs.next()) {
            // create a customer entity to return
			Customer cust = new Customer();
			cust.setId(rs.getLong("id"));
			cust.setFirstName(rs.getString("firstname"));
			cust.setLastName(rs.getString("lastname"));
			cust.setEmail(rs.getString("email"));
			cust.setDob(rs.getDate("dob"));
			cust.setGender(rs.getString("gender").charAt(0));
			result.add(cust);
			}
			return result;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	
	}
	
}
