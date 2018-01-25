package cs4347.hibernateProject.ecomm.unitTesting.service;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;

import cs4347.hibernateProject.ecomm.entity.Product;
import cs4347.hibernateProject.ecomm.services.ProductPersistenceService;
import cs4347.hibernateProject.ecomm.services.impl.ProductPersistenceServiceImpl;
import cs4347.hibernateProject.ecomm.testing.PersistenceManager;

public class ProductPersistenceServiceTest
{

	@Test
	public void testCreate() throws Exception
	{
		EntityManager em = PersistenceManager.getEntityManager();
		ProductPersistenceService ppService = new ProductPersistenceServiceImpl(em);
		
		Product product = buildProduct();
		assertNull(product.getId());
		Product prod2 = ppService.create(product);
		assertNotNull(prod2.getId());
		em.close();
	}

	@Test
	public void testRetrieve() throws Exception
	{
		EntityManager em = PersistenceManager.getEntityManager();
		ProductPersistenceService ppService = new ProductPersistenceServiceImpl(em);
		
		Product product = buildProduct();
		product = ppService.create(product);
		Long newProdID = product.getId();
		
		Product prod2 = ppService.retrieve(newProdID);
		assertNotNull(prod2);
		assertEquals(product.getProdCategory(), prod2.getProdCategory());
		assertEquals(product.getProdDescription(), prod2.getProdDescription());
		assertEquals(product.getProdName(), prod2.getProdName());
		assertEquals(product.getProdUPC(), prod2.getProdUPC());
		em.close();
	}

	@Test
	public void testUpdate() throws Exception
	{
		EntityManager em = PersistenceManager.getEntityManager();
		ProductPersistenceService ppService = new ProductPersistenceServiceImpl(em);
		
		Product product = buildProduct();
		Product prod2 = ppService.create(product);
		Long id = prod2.getId();
		
		String newUPC = "3333333333";
		prod2.setProdUPC(newUPC);
		Product p2 = ppService.update(prod2);
		assertNotNull(p2);

		Product prod3 = ppService.retrieve(id);
		assertEquals(prod2.getId(), prod3.getId());
		assertEquals(prod2.getProdName(), prod3.getProdName());
		assertEquals(newUPC, prod3.getProdUPC());
		em.close();
	}

	@Test
	public void testDelete() throws Exception
	{
		EntityManager em = PersistenceManager.getEntityManager();
		ProductPersistenceService ppService = new ProductPersistenceServiceImpl(em);
		
		Product product = buildProduct();
		Product prod2 = ppService.create(product);

		Long id = prod2.getId();
		ppService.delete(id);
		
		Product prod3 = ppService.retrieve(id);
		assertNull(prod3);
		em.close();
	}

	@Test
	public void testRetrieveByUPC() throws Exception
	{
		EntityManager em = PersistenceManager.getEntityManager();
		ProductPersistenceService ppService = new ProductPersistenceServiceImpl(em);
		
		Product product = ppService.retrieveByUPC("576236786900");
		assertNotNull(product);
		em.close();
	}

	@Test
	public void testRetrieveByCategory() throws Exception
	{
		EntityManager em = PersistenceManager.getEntityManager();
		ProductPersistenceService ppService = new ProductPersistenceServiceImpl(em);
		
		List<Product> products = ppService.retrieveByCategory(2);
		assertTrue(products.size() > 1);
		em.close();
	}

	private Product buildProduct()
    {
		Product product = new Product();
		product.setProdCategory(1);
		product.setProdDescription("Product Description");
		product.setProdName("Product Name");
		product.setProdUPC("1112223333");
	    return product;
    }
}
