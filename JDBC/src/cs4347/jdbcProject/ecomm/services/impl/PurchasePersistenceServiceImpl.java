package cs4347.jdbcProject.ecomm.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.dao.ProductDAO;
import cs4347.jdbcProject.ecomm.dao.PurchaseDAO;
import cs4347.jdbcProject.ecomm.dao.impl.CustomerDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.ProductDaoImpl;
import cs4347.jdbcProject.ecomm.dao.impl.PurchaseDaoImpl;
import cs4347.jdbcProject.ecomm.entity.Customer;
import cs4347.jdbcProject.ecomm.entity.Product;
import cs4347.jdbcProject.ecomm.entity.Purchase;
import cs4347.jdbcProject.ecomm.services.PurchasePersistenceService;
import cs4347.jdbcProject.ecomm.services.PurchaseSummary;
import cs4347.jdbcProject.ecomm.util.DAOException;


public class PurchasePersistenceServiceImpl implements PurchasePersistenceService
{
	private DataSource dataSource;

	public PurchasePersistenceServiceImpl(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

    /**
     * this method must throw a DAOException if the
     * given Purchase has a non-null ID and
     * return the same Purcahse with the ID attribute set to the
     * value set by the application's auto-increment primary key column.
     * @throws DAOException if the given Purchase has a non-null id.
     */
	public Purchase create(Purchase purchase) throws SQLException, DAOException {
		PurchaseDAO dao = new PurchaseDaoImpl();
		Connection connection = dataSource.getConnection(); // get connection
		connection.setAutoCommit(false); // disable auto commit
		Purchase purch = dao.create(connection, purchase);// create purchase
        // throw exception if null id
		if(purch.getId() == null){
			throw new DAOException("Trying to rcreate Purchase with NULL purchase ID");
		}
		connection.commit(); // commit transaction
		return purch;
	}

	// this method retrieves purchase by id
	public Purchase retrieve(Long id) throws SQLException, DAOException {
		// open connection
		Connection connection = dataSource.getConnection();
		connection.setAutoCommit(false);
		// create dao and retrieve data
		PurchaseDAO dao = new PurchaseDaoImpl();
		Purchase purchase = dao.retrieve(connection, id);
		// close the connection and return the product
		connection.commit();
		connection.close();
		return  purchase;
	}

	// this method updates purchase data
	public int update(Purchase purchase) throws SQLException, DAOException {
		Connection connection = dataSource.getConnection(); // get connection

		PurchaseDAO dao = new PurchaseDaoImpl();

		int rows = dao.update(connection, purchase); // update execution
		connection.close(); // close connection
		return rows;
	}

	// this method deletes purchase
	public int delete(Long id) throws SQLException, DAOException {
		Connection connection = dataSource.getConnection(); // get connection
		PurchaseDAO dao = new PurchaseDaoImpl();
		int rows = dao.delete(connection, id); // delete execution
		connection.close(); // close connection
		return rows;
	}

	// this method retrieves all purchase for customer id
	public List<Purchase> retrieveForCustomerID(Long customerID)
			throws SQLException, DAOException {
		Connection connection = dataSource.getConnection(); // get connection
		PurchaseDAO purchaseDao = new PurchaseDaoImpl();
		// create a list of purchases to store retrive results
		List<Purchase> purchases = purchaseDao.retrieveForCustomerID(connection, customerID); 
		connection.close(); // close connection
		return purchases; // return list
	}

	// this method retrieves purchase summary
	public PurchaseSummary retrievePurchaseSummary(Long customerID)
			throws SQLException, DAOException {
		Connection connection = dataSource.getConnection(); // get connection
		PurchaseDAO purchaseDao = new PurchaseDaoImpl();
		// create a purchase summary object to store result
		PurchaseSummary summary = purchaseDao.retrievePurchaseSummary(connection, customerID);
		connection.close(); // close connection
		return summary;// return summary
	}

	// this method retrieve purchase for a product id
	public List<Purchase> retrieveForProductID(Long productID)
			throws SQLException, DAOException {
		Connection connection = dataSource.getConnection(); // get connection
		PurchaseDAO purchaseDao = new PurchaseDaoImpl();
		// create a list of purchases to store retrieve results
		List<Purchase> purchases = purchaseDao.retrieveForProductID(connection, productID); 
		connection.close(); // close connection
		return purchases;
	}

}
