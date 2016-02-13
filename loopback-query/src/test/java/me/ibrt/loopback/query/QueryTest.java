package me.ibrt.loopback.query;

import static me.ibrt.loopback.query.Include.newInclude;
import static me.ibrt.loopback.query.Order.newAsc;
import static me.ibrt.loopback.query.Query.newQuery;
import static me.ibrt.loopback.query.Where.newWhere;
import static me.ibrt.loopback.query.Where.Clause.newClause;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class QueryTest {
	
	@Test
	public void testQuery() {
		assertEquals("{  }", newQuery().toString());
		assertEquals("{ \"where\": { \"p\": \"v\" } }", newQuery().setWhere(newWhere(newClause("p", "v"))).toString());
		assertEquals("{ \"fields\": { \"f\": true } }", newQuery().addField("f").toString());
		assertEquals("{ \"order\": \"f ASC\" }", newQuery().addOrder(newAsc("f")).toString());
		assertEquals("{ \"include\": \"m\" }", newQuery().setInclude(newInclude().addSimple("m")).toString());
		assertEquals("{ \"limit\": 100 }", newQuery().setLimit(100).toString());
		assertEquals("{ \"skip\": 100 }", newQuery().setSkip(100).toString());
		assertEquals("{ \"k\": v }", newQuery().setCustom("k", "v").toString());
		assertEquals("{ \"limit\": 100, \"skip\": 100 }", newQuery().setLimit(100).setSkip(100).toString());
		assertEquals("%7B%20%20%7D", newQuery().toURLEncodedString());
	}
}
