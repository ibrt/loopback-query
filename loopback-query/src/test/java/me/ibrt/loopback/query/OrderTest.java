package me.ibrt.loopback.query;


import static me.ibrt.loopback.query.Order.newAsc;
import static me.ibrt.loopback.query.Order.newDesc;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class OrderTest {

	@Test
	public void testOrder() {
		assertEquals("p ASC", newAsc("p").toString());
		assertEquals("p DESC", newDesc("p").toString());
	}
}
