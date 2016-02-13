package me.ibrt.loopback.query;

import static me.ibrt.loopback.query.Helpers.asList;
import static me.ibrt.loopback.query.Helpers.asMap;
import static me.ibrt.loopback.query.Include.newInclude;
import static me.ibrt.loopback.query.Order.newAsc;
import static me.ibrt.loopback.query.Order.newDesc;
import static me.ibrt.loopback.query.Serializers.serializeCustom;
import static me.ibrt.loopback.query.Serializers.serializeFields;
import static me.ibrt.loopback.query.Serializers.serializeInclude;
import static me.ibrt.loopback.query.Serializers.serializeLimit;
import static me.ibrt.loopback.query.Serializers.serializeOrders;
import static me.ibrt.loopback.query.Serializers.serializeSkip;
import static org.junit.Assert.assertEquals;
import me.ibrt.loopback.query.Order;

import org.junit.Test;

public final class SerializersTest {

	@Test
	public void testSerializeFields() {
		assertEquals("", serializeFields(Helpers.<String>asList()));
		assertEquals("\"fields\": { \"first\": true }", serializeFields(asList("first")));
		assertEquals("\"fields\": { \"first\": true, \"second\": true }", serializeFields(asList("first", "second")));
	}
	
	@Test
	public void testSerializeOrders() {
		assertEquals("", serializeOrders(Helpers.<Order>asList()));
		assertEquals("\"order\": \"p ASC\"", serializeOrders(asList(newAsc("p"))));
		assertEquals("\"order\": [ \"a ASC\", \"b DESC\" ]", serializeOrders(asList(newAsc("a"), newDesc("b"))));
	}
	
	@Test
	public void testSerializeCustom() {
		assertEquals("", serializeCustom(Helpers.<String,String>asMap(null, null)));
		assertEquals("\"k\": v", serializeCustom(asMap("k", "v")));
		assertEquals("\"k1\": v1, \"k2\": v2", serializeCustom(asMap("k1", "v1", "k2", "v2")));
	}
	
	@Test
	public void testSerializeLimit() {
		assertEquals("", serializeLimit(null));
		assertEquals("\"limit\": 100", serializeLimit(100));
	}

	@Test
	public void testSerializeSkip() {
		assertEquals("", serializeSkip(null));
		assertEquals("\"skip\": 100", serializeSkip(100));
	}
	
	@Test
	public void testSerializeInclude() {
		assertEquals("", serializeInclude(null));
		assertEquals("\"include\": \"m\"", serializeInclude(newInclude().addSimple("m")));
	}
}
