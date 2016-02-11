package com.ibrt.loopback.query;

import static com.ibrt.loopback.query.Order.Direction.ASCENDING;
import static com.ibrt.loopback.query.Order.Direction.DESCENDING;
import static com.ibrt.loopback.query.Serializer.newSerializer;
import static com.ibrt.loopback.query.Serializer.Literal.SPACE;

public final class Order {
	private final String propertyName;
	private final Direction direction;
	
	public Order(String propertyName, Direction direction) {
		this.propertyName = propertyName;
		this.direction = direction;
	}
	
	public static Order newAsc(String propertyName) {
		return new Order(propertyName, ASCENDING);
	}
	
	public static Order newDesc(String propertyName) {
		return new Order(propertyName, DESCENDING);
	}
	
	@Override
	public String toString() {
		return newSerializer()
			.unquoted(propertyName)
			.literal(SPACE)
			.unquoted(direction.toString())
			.toString();
	}
	
	public static enum Direction {
		ASCENDING("ASC"),
		DESCENDING("DESC");
		
		private final String string;
		
		private Direction(String string) {
			this.string = string;
		}
		
		@Override
		public String toString() {
			return string;
		}
	}
}
