package me.ibrt.loopback.query;

import static me.ibrt.loopback.query.Where.newWhere;
import static me.ibrt.loopback.query.Where.Clause.c;
import static me.ibrt.loopback.query.Where.Clause.newClause;
import static me.ibrt.loopback.query.Where.Operation.newOperation;
import static me.ibrt.loopback.query.Where.Operator.BETWEEN;
import static me.ibrt.loopback.query.Where.Operator.LT;
import static me.ibrt.loopback.query.Where.Operator.OR;
import static me.ibrt.loopback.query.Where.Operator.REGEXP;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class WhereTest {

	@Test
	public void testWhere() {
		assertEquals("{ \"p\": { \"or\": [ { \"p1\": { \"lt\": 10 } }, { \"p2\": 1 } ] } }", newWhere(c("p", OR.p(c("p1", LT.p(10)), c("p2", 1)))).toString());
	}
	
	@Test
	public void testClause() {
		assertEquals("{ \"p\": \"v\" }", newClause("p", "v").toString());
		assertEquals("{ \"p\": 1 }", newClause("p", 1).toString());
		assertEquals("{ \"p\": 1.1 }", newClause("p", 1.1).toString());
		assertEquals("{ \"p\": \"v\" }", c("p", "v").toString());
	}
	
	@Test
	public void testOperation() {
		assertEquals("{ \"lt\": 10 }", newOperation(LT, 10).toString());
		assertEquals("{ \"regexp\": \"^a\" }", newOperation(REGEXP, "^a").toString());
		assertEquals("{ \"between\": [ 1, 2 ] }", newOperation(BETWEEN, 1, 2).toString());
		assertEquals("{ \"lt\": 10 }", LT.p(10).toString());
		assertEquals("{ \"between\": [ 1, 2 ] }", BETWEEN.p(1, 2).toString());
	}
}
