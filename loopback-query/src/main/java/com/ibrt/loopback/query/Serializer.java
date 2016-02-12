package com.ibrt.loopback.query;

import static com.ibrt.loopback.query.Serializer.Literal.QUOTE;

import java.util.ArrayList;
import java.util.List;

public class Serializer {
	private final List<StringBuilder> stack = new ArrayList<>();
	
	public Serializer() {
		stack.add(new StringBuilder());
	}
	
	public Serializer literal(Literal literal) {
		top().append(literal);
		return this;
	}

	public <T extends Object> Serializer quoted(T object) {
		top().append(QUOTE).append(object.toString()).append(QUOTE);
		return this;
	}
	
	public <T extends Object> Serializer unquoted(T object) {
		top().append(object.toString());
		return this;
	}
	
	public Serializer push() {
		stack.add(new StringBuilder());
		return this;
	}
	
	public Serializer pop(int count, Literal join) {
		while (count > 0) {
			final String last = pop().toString();
			if (last.length() > 0) {
				if (count > 1 && top().toString().length() > 0) {
					top().append(join);
				}
				top().append(last);
			}
			count--;
		}
		return this;
	}
	
	@Override
	public String toString() {
		if (stack.size() != 1) {
			throw new IllegalStateException("Stack must contain only one element in order to convert Serializer to string.");
		}
		return stack.get(0).toString();
	}
	
	private StringBuilder top() {
		ensureNotEmpty();
		return stack.get(stack.size() - 1);
	}
	
	private StringBuilder pop() {
		ensureNotEmpty();
		StringBuilder x = stack.remove(stack.size() - 1);
		return x;
	}
	
	private void ensureNotEmpty() {
		if (stack.size() == 0) {
			throw new IllegalStateException("Serializer stack is empty.");
		}
	}
	
	public static Serializer newSerializer() {
		return new Serializer();
	}
	
	public static enum Literal {
		OPEN_BRACE("{"),
		CLOSE_BRACE("}"),
		OPEN_SQUARE("["),
		CLOSE_SQUARE("]"),
		QUOTE("\""),
		SPACE(" "),
		COLUMN(":"),
		COMMA(","),
		COMMA_SPACE(", "),
		EMPTY(""),
		TRUE("true"),
		FALSE("false");
		
		private final String literal;
		
		private Literal(String literal) {
			this.literal = literal;
		}
		
		@Override
		public String toString() {
			return this.literal;
		}
	}
}
