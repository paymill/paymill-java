package de.paymill.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Payment;
import de.paymill.model.Preauthorization;
import de.paymill.net.ApiException;

public class PreauthorizationServiceTest extends TestCase {

	@Test
	public void testGetNonExistingPreauthorization() {
		PreauthorizationService srv = Paymill.getService(PreauthorizationService.class);

		try {
			srv.get("idontexist");
			fail("Expected exception");
		} catch (ApiException ex) {
			assertEquals("preauthorization_not_found", ex.getCode());
		}
	}

	@Test
	public void testCreatePreauthorization() {
		String token = getToken();
		PreauthorizationService srv = Paymill.getService(PreauthorizationService.class);
		Preauthorization params = new Preauthorization();
		params.setToken(token);
		params.setAmount(399);
		params.setCurrency("EUR");
		Preauthorization preauthorization = srv.create(params);
		assertNotNull(preauthorization.getId());
		assertNull(preauthorization.getToken());
		assertEquals(399, (int) preauthorization.getAmount());
		assertNotNull(preauthorization.getPayment());
		assertNotNull(preauthorization.getPayment().getId());
	}

	@Test
	public void testListPreauthorizations() {
		PreauthorizationService srv = Paymill.getService(PreauthorizationService.class);
		List<Preauthorization> list = srv.list(0, 10);

		assertTrue(list.size() > 0);
		assertTrue(list.get(0) instanceof Preauthorization);
	}

	@Test
	public void testGetPreauthorization() {
		PreauthorizationService srv = Paymill.getService(PreauthorizationService.class);
		List<Preauthorization> list = srv.list(0, 1);
		Preauthorization tx = srv.get(list.get(0).getId());

		assertNotNull(tx);
		assertNotNull(tx.getId());
	}

	@Test
	public void testRemovePreauthorization() {
            PreauthorizationService srv = Paymill.getService(PreauthorizationService.class);
            List<Preauthorization> list = srv.list(0, 1, "created_at_desc");
            Preauthorization preauthorization = srv.get(list.get(0).getId());
            
            String id = preauthorization.getId();
            srv.delete(id);
            
            try {
                    srv.get(id);
            } catch (ApiException ex) {
                    //ex.printStackTrace();
                    assertEquals("preauthorization_not_found", ex.getCode());
            }
	}
	
	@Test
	public void testCreateWithPayment() {
		PaymentService srvPayment = Paymill.getService(PaymentService.class); 
		Payment payment = srvPayment.create(getToken());

		PreauthorizationService svrTx = Paymill.getService(PreauthorizationService.class); 
		Preauthorization preauthorizationParams = new Preauthorization(); 
		preauthorizationParams.setPayment(payment); 
		preauthorizationParams.setAmount(100); 
		preauthorizationParams.setCurrency("EUR"); 
		Preauthorization tx = svrTx.create(preauthorizationParams);
		
		assertNotNull(tx);
		assertNotNull(tx.getId());
		assertEquals((int)tx.getAmount(), 100);
		assertNotNull(tx.getPayment());
		assertEquals(payment.getId(), tx.getPayment().getId());
	}
}
