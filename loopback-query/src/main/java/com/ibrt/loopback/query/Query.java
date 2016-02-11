package com.ibrt.loopback.query;

import static com.ibrt.loopback.query.Serializer.newSerializer;
import static com.ibrt.loopback.query.Serializer.Literal.CLOSE_BRACE;
import static com.ibrt.loopback.query.Serializer.Literal.COMMA_SPACE;
import static com.ibrt.loopback.query.Serializer.Literal.OPEN_BRACE;
import static com.ibrt.loopback.query.Serializer.Literal.SPACE;
import static com.ibrt.loopback.query.Serializers.serializeCustom;
import static com.ibrt.loopback.query.Serializers.serializeFields;
import static com.ibrt.loopback.query.Serializers.serializeInclude;
import static com.ibrt.loopback.query.Serializers.serializeLimit;
import static com.ibrt.loopback.query.Serializers.serializeOrders;
import static com.ibrt.loopback.query.Serializers.serializeSkip;
import static com.ibrt.loopback.query.Serializers.serializeWhere;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Query {
	private final List<String> fields = new ArrayList<>();
	private final List<Order> orders = new ArrayList<>();
	private final Map<String,String> custom = new HashMap<>();
	
	/*@Nullable*/ private Include include;
	/*@Nullable*/ private Integer limit;
	/*@Nullable*/ private Integer skip;
	/*@Nullable*/ private Where where;
	
	public Query addField(String field) {
		fields.add(field);
		return this;
	}
	
	public Query addFields(String... fields) {
		for (String field : fields) {
			this.fields.add(field);
		}
		return this;
	}
	
	public Query addOrder(Order order) {
		orders.add(order);
		return this;
	}
	
	public Query addOrders(Order... orders) {
		this.addOrders(orders);
		return this;
	}
	
	public Query addOrders(Iterable<Order> orders) {		
		for (Order order : orders) {
			this.orders.add(order);
		}
		return this;
	}
	
	public Query setInclude(/*@Nullable*/ Include include) {
		this.include = include;
		return this;
	}
	
	public Query setLimit(/*@Nullable*/ Integer limit) {
		this.limit = limit;
		return this;
	}
	
	public Query setSkip(/*@Nullable*/ Integer skip) {
		this.skip = skip;
		return this;
	}
	
	public Query setCustom(String key, String value) {
		custom.put(key, value);
		return this;
	}
	
	public Query setWhere(/*@Nullable*/ Where where) {
		this.where = where;
		return this;
	}
	
	@Override
	public String toString() {
		return newSerializer()
			.literal(OPEN_BRACE)
			.literal(SPACE)
			.push()
			.unquoted(serializeWhere(where))
			.push()
			.unquoted(serializeFields(fields))
			.push()
			.unquoted(serializeOrders(orders))
			.push()
			.unquoted(serializeInclude(include))
			.push()
			.unquoted(serializeLimit(limit))
			.push()
			.unquoted(serializeSkip(skip))
			.push()
			.unquoted(serializeCustom(custom))
			.pop(7, COMMA_SPACE)
			.literal(SPACE)
			.literal(CLOSE_BRACE)
			.toString();
	}
	
	public String toURLEncodedString() {
		try {
			return URLEncoder.encode(toString(), "UTF-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Query newQuery() {
		return new Query();
	}
}
