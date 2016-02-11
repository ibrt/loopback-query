package com.ibrt.loopback.query;

import static com.ibrt.loopback.query.Serializer.newSerializer;
import static com.ibrt.loopback.query.Serializer.Literal.CLOSE_BRACE;
import static com.ibrt.loopback.query.Serializer.Literal.CLOSE_SQUARE;
import static com.ibrt.loopback.query.Serializer.Literal.COLUMN;
import static com.ibrt.loopback.query.Serializer.Literal.COMMA_SPACE;
import static com.ibrt.loopback.query.Serializer.Literal.OPEN_BRACE;
import static com.ibrt.loopback.query.Serializer.Literal.OPEN_SQUARE;
import static com.ibrt.loopback.query.Serializer.Literal.SPACE;
import static com.ibrt.loopback.query.Serializers.serializeObject;
import static com.ibrt.loopback.query.Where.Operation.newOperation;


public class Where {
	private final Clause clause;
	
	public Where(Clause clause) {
		this.clause = clause;
	}
	
	@Override
	public String toString() {
		return clause.toString();
	}
	
	public static Where newWhere(Clause clause) {
		return new Where(clause);
	}
	
	public static final class Clause {
		private final String propertyName;
		private final Object value; // Scalar or operation.
		
		public Clause(String propertyName, Object value) {
			this.propertyName = propertyName;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return newSerializer()
				.literal(OPEN_BRACE)
				.literal(SPACE)
				.quoted(propertyName)
				.literal(COLUMN)
				.literal(SPACE)
				.unquoted(serializeObject(value))
				.literal(SPACE)
				.literal(CLOSE_BRACE)
				.toString();
		}
		
		public static Clause newClause(String propertyName, Object value) {
			return new Clause(propertyName, value);
		}
		
		public static Clause c(String propertyName, Object value) {
			return newClause(propertyName, value);
		}
	}
	
	public static final class Operation {
		private final Operator operator;
		private final Object[] parameters;
		
		public Operation(Operator operator, Object... parameters) {
			if (parameters.length == 0) {
				throw new IllegalArgumentException("Operation requires at least one parameter.");
			}
			this.operator = operator;
			this.parameters = parameters;
		}
		
		@Override
		public String toString() {
			final Serializer serializer = newSerializer()
				.literal(OPEN_BRACE)
				.literal(SPACE)
				.quoted(operator)
				.literal(COLUMN)
				.literal(SPACE);
			
			if (parameters.length == 1) {
				serializer
					.unquoted(serializeObject(parameters[0]));
			}
			
			if (parameters.length > 1) {
				serializer
					.literal(OPEN_SQUARE)
					.literal(SPACE);
				
				for (Object parameter : parameters) {
					serializer
						.push()
						.unquoted(serializeObject(parameter));
				}
				
				serializer
					.pop(parameters.length, COMMA_SPACE)
					.literal(SPACE)
					.literal(CLOSE_SQUARE);
			}
			
			return serializer
				.literal(SPACE)
				.literal(CLOSE_BRACE)
				.toString();
		}
		
		public static Operation newOperation(Operator operator, Object... parameters) {
			return new Operation(operator, parameters);
		}
	}
	
	public static enum Operator {
		AND,
		OR,
		GT,
		GTE,
		LT,
		LTE,
		BETWEEN,
		INQ,
		NIN,
		NEAR,
		NEQ,
		LIKE,
		NLIKE,
		REGEXP;
		
		@Override
		public String toString() {
			return this.name().toLowerCase();
		}
		
		public Operation p(Object... parameters) {
			return newOperation(this, parameters);
		}
	}
}
