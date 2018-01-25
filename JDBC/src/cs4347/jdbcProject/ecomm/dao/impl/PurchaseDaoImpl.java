package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.PurchaseSummary;
import cs4347.jdbcProject.ecomm.util.DAOException;
// Implementation of PurchaseDAO
public class PurchaseDaoImpl implements PurchaseDAO
{
    // insert sql statement
	private static final String insertSQL = 
			"INSERT INTO Purchase (Product_id, Customer_id, purchaseDate, purchaseAmount) VALUES (?, ?, ?, ?);";
	// this method creates a new purchase
	public Purchase create(Connection connection, Purchase purchase)
			throws SQLException, DAOException {
        // throw exception if null id
		if (purchase.getId() != null) {
			throw new DAOException("Trying to insert Purchase with NON-NULL ID");
		}
        // create a new preparedstatement object
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(2, purchase.getCustomerID());
			ps.setLong(1, purchase.getProductID());
			ps.setDate(3, purchase.getPurchaseDate());
			ps.setDouble(4, purchase.getPurchaseAmount());
			// Set other statement attributes here...
			ps.executeUpdate();

			// REQUIREMENT: Copy the auto-increment primary key to the customer ID.
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			purchase.setId((long) lastKey);
			
			return purchase;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
		
	}

	
	// this method retrieves purchase with provided id
	public Purchase retrieve(Connection connection, Long id)
			throws SQLException, DAOException {
		if (id == null) {
			//throw new DAOException("Trying to retrieve Purchase with NULL ID");
		}
		// sql retrieve statement
		final String retrieveSQL = "SELECT id, purchaseDate, purchaseAmount, "
				+ "customer_id, product_id FROM purchase where id=?;";
		
        // create a new preparedstatement object
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(retrieveSQL);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}
			// create a new purchase entity
			Purchase purc = new Purchase();
			purc.setId(rs.getLong("id"));
			purc.setPurchaseAmount(rs.getDouble("purchaseAmount"));
			purc.setPurchaseDate(rs.getDate("purchaseDate"));
			purc.setCustomerID(rs.getLong("Customer_id"));
			purc.setProductID(rs.getLong("Product_id"));
			rs.close();
			return  purc;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}

		}

	}
	
	// this method updates purchase attributes
	public int update(Connection connection, Purchase purchase)
			throws SQLException, DAOException {
        // sql update statement
		final String updateSQL = "UPDATE purchase SET Product_id = ?, Customer_id = ?, purchaseDate = ?, purchaseAmount = ?"
		        + "WHERE id = ?;";
        // throw exception if null id
		if (purchase.getId() == null) {
			throw new DAOException("Trying to update purchase with NULL ID");
		}
        // create a new preparedstatement object
		PreparedStatement ps = null;
        // execution
		try {
			ps = connection.prepareStatement(updateSQL);
			ps.setLong(1, purchase.getProductID());
			ps.setLong(2, purchase.getCustomerID());
			ps.setDate(3, purchase.getPurchaseDate());
			ps.setDouble(4, purchase.getPurchaseAmount());
			ps.setLong(5,  purchase.getId());
			int rows = ps.executeUpdate();
			return rows;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}

	}

	// this method deletes a purchase with provided id
	public int delete(Connection connection, Long id) throws SQLException,
			DAOException {
        // throw exception if null id
		if (id == null) {
			throw new DAOException("Trying to delete Purchase with NULL ID");
		}
        // sql delete statement
		final String deleteSQL = "DELETE FROM purchase WHERE ID = ?;";
        // create a new preparedstatement object
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

	// this method retrieves all purchases with customer id
	public List<Purchase> retrieveForCustomerID(Connection connection,
			Long customerID) throws SQLException, DAOException {
        // sql select statement
		final String selectQuery = 
				"SELECT id, purchaseDate, purchaseAmount, Product_id, Customer_id"
				+ " FROM purchase WHERE Customer_id = ?";
        // create a new preparedstatement object
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectQuery);
			ps.setLong(1, customerID);
			ResultSet rs = ps.executeQuery();

			List<Purchase> result = new ArrayList<Purchase>();
			while (rs.next()) {
                // create a new purchase entity
				Purchase purchase = new Purchase();
				purchase.setId(rs.getLong("id"));
				purchase.setPurchaseAmount(rs.getDouble("purchaseAmount"));
				purchase.setPurchaseDate(rs.getDate("purchaseDate"));
				purchase.setCustomerID(customerID);
				purchase.setProductID(rs.getLong("Product_id"));
				result.add(purchase);
			}
			rs.close();
			return result;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	// this method retrieves all purchases with product id
	public List<Purchase> retrieveForProductID(Connection connection,
			Long productID) throws SQLException, DAOException {
        // sql select statement
		final String selectQuery = 
				"SELECT id, purchaseDate, purchaseAmount, Product_id, Customer_id"
				+ " FROM purchase WHERE Product_id = ?";
        // create a new preparedstatement object
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectQuery);
			ps.setLong(1, productID);
			ResultSet rs = ps.executeQuery();
            // create a list of purchases
			List<Purchase> result = new ArrayList<Purchase>();
			while (rs.next()) {
                // create a new purchase entity
				Purchase purchase = new Purchase();
				purchase.setId(rs.getLong("id"));
				purchase.setPurchaseAmount(rs.getDouble("purchaseAmount"));
				purchase.setPurchaseDate(rs.getDate("purchaseDate"));
				purchase.setProductID(productID);
				purchase.setCustomerID(rs.getLong("Customer_id"));
				result.add(purchase);
			}
			rs.close();
			return result;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	// this method retrieves purchase summary
	public PurchaseSummary retrievePurchaseSummary(Connection connection,
			Long customerID) throws SQLException, DAOException {
		// sql statement to retrieve summary
		final String storeQuery = "select avg(purchaseAmount), max(purchaseAmount), "
				+ "min(purchaseAmount) from purchase where Customer_id = ?;";
        // create a new preparedstatement object
		PreparedStatement ps = null;
		// execution
		try {
			ps = connection.prepareStatement(storeQuery);
			ps.setLong(1, customerID);
			ResultSet rss = ps.executeQuery();
			rss.next();
			PurchaseSummary summary = new PurchaseSummary();
			
			summary.avgPurchase = rss.getFloat(1);
			summary.maxPurchase = rss.getFloat(2);
			summary.minPurchase = rss.getFloat(3);
			
			rss.close();
			return summary;
			
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}
	
}
