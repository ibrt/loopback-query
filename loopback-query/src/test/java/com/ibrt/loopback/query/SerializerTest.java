package com.ibrt.loopback.query;

import static com.ibrt.loopback.query.Serializer.newSerializer;
import static com.ibrt.loopback.query.Serializer.Literal.CLOSE_BRACE;
import static com.ibrt.loopback.query.Serializer.Literal.COLUMN;
import static com.ibrt.loopback.query.Serializer.Literal.COMMA;
import static com.ibrt.loopback.query.Serializer.Literal.OPEN_BRACE;
import static com.ibrt.loopback.query.Serializer.Literal.SPACE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class SerializerTest {

	@Test
	public void testSerializer() {
		assertEquals("", newSerializer().toString());
		assertEquals("{}", newSerializer().literal(OPEN_BRACE).literal(CLOSE_BRACE).toString());
		assertEquals("s", newSerializer().unquoted("s").toString());
		assertEquals("\"s\"", newSerializer().quoted("s").toString());
		assertEquals(":", newSerializer().push().literal(COLUMN).pop(1, COMMA).toString());
		assertEquals(" :,}", newSerializer().literal(SPACE).push().literal(COLUMN).push().literal(CLOSE_BRACE).pop(2, COMMA).toString());
		assertEquals(":,} ", newSerializer().push().literal(COLUMN).push().literal(CLOSE_BRACE).pop(2, COMMA).literal(SPACE).toString());
		assertEquals("", newSerializer().push().pop(1, COMMA).toString());
		assertEquals("", newSerializer().push().push().pop(2, COMMA).toString());
		assertEquals(":", newSerializer().push().literal(COLUMN).push().pop(2, COMMA).toString());
		assertEquals(":", newSerializer().push().push().literal(COLUMN).pop(2, COMMA).toString());
		assertEquals(":,:", newSerializer().push().push().literal(COLUMN).push().literal(COLUMN).pop(3, COMMA).toString());
		assertEquals(":,:", newSerializer().push().literal(COLUMN).push().push().literal(COLUMN).pop(3, COMMA).toString());
		assertEquals(":,:", newSerializer().push().literal(COLUMN).push().literal(COLUMN).push().pop(3, COMMA).toString());
	}
}
