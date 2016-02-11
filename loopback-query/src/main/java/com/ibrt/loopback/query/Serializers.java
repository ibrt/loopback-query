package com.ibrt.loopback.query;

import static com.ibrt.loopback.query.Serializer.newSerializer;
import static com.ibrt.loopback.query.Serializer.Literal.CLOSE_BRACE;
import static com.ibrt.loopback.query.Serializer.Literal.CLOSE_SQUARE;
import static com.ibrt.loopback.query.Serializer.Literal.COLUMN;
import static com.ibrt.loopback.query.Serializer.Literal.COMMA_SPACE;
import static com.ibrt.loopback.query.Serializer.Literal.OPEN_BRACE;
import static com.ibrt.loopback.query.Serializer.Literal.OPEN_SQUARE;
import static com.ibrt.loopback.query.Serializer.Literal.QUOTE;
import static com.ibrt.loopback.query.Serializer.Literal.SPACE;
import static com.ibrt.loopback.query.Serializer.Literal.TRUE;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class Serializers {
	private Serializers() {
		// Intentionally empty.
	}
	
	public static String serializeFields(List<String> fields) {
		if (fields.size() == 0) {
			return "";
		}
		
		final Serializer serializer = newSerializer()
			.quoted("fields")
			.literal(COLUMN)
			.literal(SPACE)
			.literal(OPEN_BRACE)
			.literal(SPACE);
		
		for (String field : fields) {
			serializer
				.push()
				.quoted(field)
				.literal(COLUMN)
				.literal(SPACE)
				.literal(TRUE);
		}
		
		return serializer
			.pop(fields.size(), COMMA_SPACE)
			.literal(SPACE)
			.literal(CLOSE_BRACE)
			.toString();
	}
	
	public static String serializeOrders(List<Order> orders) {
		if (orders.size() == 0) {
			return "";
		}
		
		final Serializer serializer = newSerializer()
			.quoted("order")
			.literal(COLUMN)
			.literal(SPACE);
		
		if (orders.size() == 1) {
			return serializer
				.quoted(orders.get(0))
				.toString();
		}
		
		serializer
			.literal(OPEN_SQUARE)
			.literal(SPACE);
		
		for (Order order : orders) {
			serializer
				.push()
				.quoted(order);
		}
		
		return serializer
			.pop(orders.size(), COMMA_SPACE)
			.literal(SPACE)
			.literal(CLOSE_SQUARE)
			.toString();
	}
	
	public static String serializeCustom(Map<String,String> custom) {
		if (custom.size() == 0) {
			return "";
		}
		
		final Serializer serializer = newSerializer();
		
		for (Entry<String,String> entry : custom.entrySet()) {
			serializer
				.push()
				.quoted(entry.getKey())
				.literal(COLUMN)
				.literal(SPACE)
				.unquoted(entry.getValue());
		}
		
		return serializer
			.pop(custom.size(), COMMA_SPACE)
			.toString();
	}
	
	public static String serializeLimit(/*@Nullable*/ Integer limit) {
		return serializeKeyValue("limit", limit);
	}
	
	public static String serializeSkip(/*@Nullable*/ Integer skip) {
		return serializeKeyValue("skip", skip);
	}
	
	public static String serializeInclude(/*@Nullable*/ Include include) {
		return serializeKeyValue("include", include);
	}
	
	public static String serializeWhere(/*@Nullable*/ Where where) {
		return serializeKeyValue("where", where);
	}
	
	public static String serializeKeyValue(String key, /*@Nullable*/ Object value) {
		if (value == null) {
			return "";
		}
		
		return newSerializer()
			.quoted(key)
			.literal(COLUMN)
			.literal(SPACE)
			.unquoted(value)
			.toString();
	}
	
	public static String serializeObject(Object object) {
		if (object instanceof String) {
			return QUOTE + object.toString() + QUOTE;
		}
		return object.toString();
	}
}
