package me.ibrt.loopback.query;

import static me.ibrt.loopback.query.Include.newInclude;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class IncludeTest {

	@Test
	public void testInclude() {
		assertEquals("", newInclude().toString());
		assertEquals("\"m\"", newInclude().addSimple("m").toString());
		assertEquals("{ \"m\": \"v\" }", newInclude().addNested("m", newInclude().addSimple("v")).toString());
		assertEquals("[ \"m1\", \"m2\" ]", newInclude().addSimple("m1").addSimple("m2").toString());
		assertEquals("[ { \"m1\": \"v1\" }, { \"m2\": \"v2\" } ]", newInclude().addNested("m1", newInclude().addSimple("v1")).addNested("m2", newInclude().addSimple("v2")).toString());
		assertEquals("[ \"m1\", { \"m2\": \"v\" } ]", newInclude().addSimple("m1").addNested("m2", newInclude().addSimple("v")).toString());
	}
}
