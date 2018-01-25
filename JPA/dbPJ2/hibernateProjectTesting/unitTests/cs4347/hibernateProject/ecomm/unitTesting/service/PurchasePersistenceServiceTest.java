package cs4347.hibernateProject.ecomm.unitTesting.service;

import static org.junit.Assert.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;

import cs4347.hibernateProject.ecomm.entity.Customer;
import cs4347.hibernateProject.ecomm.entity.Product;
import cs4347.hibernateProject.ecomm.entity.Purchase;
import cs4347.hibernateProject.ecomm.services.PurchasePersistenceService;
import cs4347.hibernateProject.ecomm.services.PurchaseSummary;
import cs4347.hibernateProject.ecomm.services.impl.PurchasePersistenceServiceImpl;
import cs4347.hibernateProject.ecomm.testing.PersistenceManager;

public class PurchasePersistenceServiceTest
{
	// Must be existing Customer and Product IDs
	Long customerID = 2l;
	Long productID = 2l;

	@Test
	public void testCreate() throws Exception
	{
		EntityManager em = PersistenceManager.getEntityManager();
		PurchasePersistenceService ppService = new PurchasePersistenceServiceImpl(em);

		Purchase purchase = buildPurchase(em, customerID, productID);
		assertNull(purchase.getId());
		Purchase purch2 = ppService.create(purchase);
		assertNotNull(purch2.getId());
	}

	@Test
	public void testRetrieve() throws Exception
	{
		EntityManager em = PersistenceManager.getEntityManager();
		PurchasePersistenceService ppService = new PurchasePersistenceServiceImpl(em);

		Purchase purchase = buildPurchase(em, customerID, productID);
		purchase = ppService.create(purchase);
		Long newID = purchase.getId();

		Purchase purch2 = ppService.retrieve(newID);
		assertNotNull(purch2);
		assertEquals(purchase.getId(), purch2.getId());
		assertEquals(purchase.getPurchaseAmount(), purch2.getPurchaseAmount(), 0.1);
		assertEquals(purchase.getCustomer().getId(), purch2.getCustomer().getId());
		assertEquals(purchase.getProduct().getId(), purch2.getProduct().getId());

		// Elminate time component from date comparison
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		String d1 = formatter.format(purchase.getPurchaseDate());
		String d2 = formatter.format(purch2.getPurchaseDate());
		assertEquals(d1, d2);
	}

	@Test
	public void testUpdate() throws Exception
	{
		EntityManager em = PersistenceManager.getEntityManager();
		PurchasePersistenceService ppService = new PurchasePersistenceServiceImpl(em);

		Purchase purchase = buildPurchase(em, customerID, productID);
		Purchase purchase2 = ppService.create(purchase);
		Long newID = purchase.getId();

		Double newPrice = 2222.00;
		purchase2.setPurchaseAmount(newPrice);
		Purchase purch = ppService.update(purchase2);
		assertNotNull(purch);

		Purchase purchase3 = ppService.retrieve(newID);
		assertEquals(purchase2.getId(), purchase3.getId());
		assertEquals((double) newPrice, purchase.getPurchaseAmount(), 0.1);
	}

	@Test
	public void testDelete() throws Exception
	{
		EntityManager em = PersistenceManager.getEntityManager();
		PurchasePersistenceService ppService = new PurchasePersistenceServiceImpl(em);

		Purchase purchase = buildPurchase(em, customerID, productID);
		Purchase purchase2 = ppService.create(purchase);

		Long id = purchase2.getId();
		ppService.delete(id);
		
		Purchase purchase3 = ppService.retrieve(id);
		assertNull(purchase3);
	}

	@Test
	public void testRetrieveForCustomerID() throws Exception
	{
		EntityManager em = PersistenceManager.getEntityManager();
		PurchasePersistenceService ppService = new PurchasePersistenceServiceImpl(em);

		List<Purchase> purchases = ppService.retrieveForCustomerID(customerID);
		assertTrue(purchases.size() > 0);
	}

	@Test
	public void testRetrievePurchaseSummary() throws Exception
	{
		EntityManager em = PersistenceManager.getEntityManager();
		PurchasePersistenceService ppService = new PurchasePersistenceServiceImpl(em);

		PurchaseSummary summary = ppService.retrievePurchaseSummary(customerID);
		assertNotNull(summary);
		assertTrue(summary.avgPurchase > 0);
		assertTrue(summary.minPurchase > 0);
		assertTrue(summary.maxPurchase > 0);
	}

	@Test
	public void testRetrieveForProductID() throws Exception
	{
		EntityManager em = PersistenceManager.getEntityManager();
		PurchasePersistenceService ppService = new PurchasePersistenceServiceImpl(em);

		List<Purchase> purchases = ppService.retrieveForProductID(productID);
		assertTrue(purchases.size() > 0);
	}

	private Purchase buildPurchase(EntityManager em, Long customerID, Long productID)
	{
		Customer customer = em.find(Customer.class, customerID);
		assertNotNull(customer);

		Product product = em.find(Product.class, productID);
		assertNotNull(product);
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseAmount(100.00);
		purchase.setPurchaseDate(new Date(System.currentTimeMillis()));
		purchase.setCustomer(customer);
		purchase.setProduct(product);
		return purchase;
	}
}
