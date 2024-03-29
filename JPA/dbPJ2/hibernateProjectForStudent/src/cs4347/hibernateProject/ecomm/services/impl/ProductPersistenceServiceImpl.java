package cs4347.hibernateProject.ecomm.services.impl;



import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import cs4347.hibernateProject.ecomm.entity.Product;
import cs4347.hibernateProject.ecomm.services.ProductPersistenceService;
import cs4347.hibernateProject.ecomm.util.DAOException;

public class ProductPersistenceServiceImpl implements ProductPersistenceService
{
	private EntityManager em; 
	// constructor
	public ProductPersistenceServiceImpl(EntityManager em)
	{
		this.em = em;
	}
	
	/**
	 * The create method must throw a DAOException if the 
	 * given Product has a non-null ID. The create method must 
	 * return the same Product with the ID attribute set to the
	 * value set by the application's auto-increment primary key column. 
	 * @throws DAOException if the given Purchase has a non-null id.
	 */
	@Override
	public Product create(Product product) throws SQLException, DAOException
	{	// throw exception if non-null id
		if(product.getId() != null)
			throw new DAOException("Error: Trying to create Product with non-null ID");
		// else create product
		em.getTransaction().begin();
		em.persist(product);
		em.getTransaction().commit();
		
		// return product
		return product;
	}

	
	// this method retrieves a product with provided id
	@Override
	public Product retrieve(Long id) throws SQLException, DAOException 
	{	// throws exception if null id
		if(id == null)
			throw new DAOException("Error: Trying to retrieve Product with null ID");
		// else retrieve product
		em.getTransaction().begin();
		Product product = em.find(Product.class, id);
		em.getTransaction().commit();
		// return product
		return product;
	}

	// this method updates product
	@Override
	public Product update(Product product) throws SQLException, DAOException
	{	// throw exception if product has null id
		if(product.getId() == null)
			throw new DAOException("Error: Trying to update Product with null ID");
		// else retrieve product
		Product result = retrieve(product.getId());
		result = product;
		
		return result;
		
	}

	
	// this method deletes a product
	@Override
	public void delete(Long id) throws SQLException, DAOException
	{	// throws exception if null id
		if(id == null)
			throw new DAOException("Error: Trying to delete Product with null ID");
		// else delete product
		em.getTransaction().begin();
		// find
		Product product = em.find(Product.class, id);
		// remove
		em.remove(product);
		em.getTransaction().commit();
	}

	
	/**
	 * this method retrieves a product by its unique UPC
	 */
	@Override
	public Product retrieveByUPC(String upc) throws SQLException, DAOException
	{	// throw exception if null upc
		if(upc == null)
			throw new DAOException("Error: Trying to retrieve Product with null UPC");
		// else retrieves product
		em.getTransaction().begin();
		
		TypedQuery<Product> query = em.createQuery("from Product p where p.prodUPC = :upc", Product.class);
        query.setParameter("upc", upc);
        Product product = query.getSingleResult();

		em.getTransaction().commit();
		
		
		return product; // return product
	}

	
	/**
	 * this method retrieves products in the given category
	 */
	@Override
	public List<Product> retrieveByCategory(int category) throws SQLException, DAOException
	{
//		if(category == null)
//			throw new DAOException("Error: Trying to retrieve Product with null UPC");
		
		em.getTransaction().begin();
		
		
		TypedQuery<Product> query = em.createQuery("from Product p where p.prodCategory = :cat", Product.class);
        query.setParameter("cat", category);
		List<Product> products = query.getResultList();

		em.getTransaction().commit();

		return products; // return list of products
	}

}
