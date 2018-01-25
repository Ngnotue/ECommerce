package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs4347.jdbcProject.ecomm.dao.CreditCardDAO;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.CreditCard;
import cs4347.jdbcProject.ecomm.util.DAOException;

// implementation of CreditCardDAO
public class CreditCardDaoImpl implements CreditCardDAO
{
	// sql insert query
	private static final String insertSQL = 
			"INSERT INTO CREDIT_CARD (customer_id, name, ccNumber, expDate, securityCode) VALUES (?, ?, ?, ?, ?);";
	
	// create a credit card object
	public CreditCard create(Connection connection, CreditCard creditCard,
			Long customerID) throws SQLException, DAOException {
		// create a PreparedStatement object
		PreparedStatement ps = null;
		
		try {
			
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			// initiate values
			ps.setString(2, creditCard.getName());
			ps.setString(3, creditCard.getCcNumber());
			ps.setString(4, creditCard.getExpDate());
			ps.setString(5, creditCard.getSecurityCode());
			ps.setLong(1, customerID);
			// execute creation
			ps.executeUpdate();
			
			return creditCard;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	// retrieve credit card from provided ID
	public CreditCard retrieveForCustomerID(Connection connection,
			Long customerID) throws SQLException, DAOException {
		if (customerID == null) {
			throw new DAOException("Trying to retrieve Credit Card with NULL CustomerID");
		}
        // sql select query
		final String selectQuery = "select name, ccNumber, expDate, securityCode, Customer_id "
				+ "from credit_card where Customer_id = ?;";
		// create preparedstatement object
		PreparedStatement ps = null;
		// retrieve execution
		try {
			ps = connection.prepareStatement(selectQuery);
			ps.setLong(1, customerID);
            // store result set
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}
			// create a new credit card object
			CreditCard card = new CreditCard();
			card.setCcNumber(rs.getString("ccNumber"));
			card.setExpDate(rs.getString("expDate"));
			card.setName(rs.getString("name"));
			card.setSecurityCode(rs.getString("securityCode"));
			
			rs.close();
			
			return card;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}

		}
	}

	// delete credit card with provided customer id
	public void deleteForCustomerID(Connection connection, Long customerID)
			throws SQLException, DAOException {
                // throw exception if id not found
		if (customerID == null) {
			throw new DAOException("Trying to delete Credit Card with NULL CustomerID");
		}
                // sql delete statement
		final String deleteSQL = "DELETE FROM credit_card WHERE Customer_id = ?;";
                // create a preparedstatement object
		PreparedStatement ps = null;
		try {
            // pass statement
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, customerID);
            // execution
			int rows = ps.executeUpdate();

		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}

		}
		
	}

}
