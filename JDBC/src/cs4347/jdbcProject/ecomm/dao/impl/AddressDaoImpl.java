package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs4347.jdbcProject.ecomm.dao.AddressDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.util.DAOException;
// implementation of AddressDAO
public class AddressDaoImpl implements AddressDAO
{
	private static final String insertSQL = 
			"INSERT INTO Address (Customer_id, address1, address2, city, state, zipcode) VALUES (?, ?, ?, ?, ?, ?);";

	// create a new address associated with id
	// throw exception if id is not found
	public Address create(Connection connection, Address address,
			Long customerID) throws SQLException, DAOException {
		// create a PreparedStatement object
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			// initiate values
			ps.setLong(1, customerID);
			ps.setString(2, address.getAddress1());
			ps.setString(3, address.getAddress2());
			ps.setString(4, address.getCity());
			ps.setString(5, address.getState());
			ps.setString(6, address.getZipcode());
			// execute creation
			ps.executeUpdate();
			
			return address;
		}
		
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}

		}
	}

	// retrieve customer entity with provided id
	// throw exception if id is not found
	public Address retrieveForCustomerID(Connection connection, Long customerID)
			throws SQLException, DAOException { 
		if (customerID == null) {
			throw new DAOException("Trying to retrieve Address with NULL CustomerID");
		}
		// select statement to retrieve customer attributes
		final String selectQuery = "select address1, address2, city, state, zipcode, Customer_id "
				+ "from address where Customer_id = ?;";
		// create a PreparedStatement object
		PreparedStatement ps = null;
		
		try {
			// pass the select statement to ps
			ps = connection.prepareStatement(selectQuery);
			ps.setLong(1, customerID);
			// create a result set
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}
			// create an address object
			Address address = new Address();
			// copy value from result set to address
			address.setAddress1(rs.getString("address1"));
			address.setAddress2(rs.getString("address2"));
			address.setCity(rs.getString("city"));
			address.setState(rs.getString("state"));
			address.setZipcode(rs.getString("zipcode"));
			
			return address;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}

		}
	}

	// delete address with provided id
	// throw exception if id is not found
	public void deleteForCustomerID(Connection connection, Long customerID)
			throws SQLException, DAOException {
		if (customerID == null) {
			throw new DAOException("Trying to delete Credit Card with NULL CustomerID");
		}
		// delete query with based on customer id
		final String deleteSQL = "DELETE FROM address WHERE Customer_id = ?;";
		// create a prepared statement object
		PreparedStatement ps = null;
		try {
			// pass query as argument
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, customerID);
			// delete execution
			int rows = ps.executeUpdate();
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}

		}
		
	}

}
