package me.ibrt.loopback.query;
import static me.ibrt.loopback.query.Serializer.newSerializer;
import static me.ibrt.loopback.query.Serializer.Literal.CLOSE_BRACE;
import static me.ibrt.loopback.query.Serializer.Literal.CLOSE_SQUARE;
import static me.ibrt.loopback.query.Serializer.Literal.COLUMN;
import static me.ibrt.loopback.query.Serializer.Literal.COMMA_SPACE;
import static me.ibrt.loopback.query.Serializer.Literal.OPEN_BRACE;
import static me.ibrt.loopback.query.Serializer.Literal.OPEN_SQUARE;
import static me.ibrt.loopback.query.Serializer.Literal.SPACE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public final class Include {
	private final List<String> simple = new ArrayList<>();
	private final Map<String,Include> nested = new HashMap<>();
	
	public Include addSimple(String modelName) {
		simple.add(modelName);
		return this;
	}
	
	public Include addNested(String propertyName, Include include) {
		nested.put(propertyName, include);
		return this;
	}
	
	@Override
	public String toString() {
		if (simple.size() == 0 && nested.size() == 0) {
			return "";
		}
		
		final Serializer serializer = newSerializer();
		
		if (simple.size() == 1 && nested.size() == 0) {
			return serializer
				.quoted(simple.get(0))
				.toString();
		}
		
		if (simple.size() == 0 && nested.size() == 1) {
			for (Entry<String,Include> entry : nested.entrySet()) {
				return serializer
					.literal(OPEN_BRACE)
					.literal(SPACE)
					.quoted(entry.getKey())
					.literal(COLUMN)
					.literal(SPACE)
					.unquoted(entry.getValue())
					.literal(SPACE)
					.literal(CLOSE_BRACE)
					.toString();
			}
		}
		
		serializer
			.literal(OPEN_SQUARE)
			.literal(SPACE);
		
		for (String model : simple) {
			serializer
				.push()
				.quoted(model);
		}
		
		for (Entry<String,Include> entry : nested.entrySet()) {
			serializer
				.push()
				.literal(OPEN_BRACE)
				.literal(SPACE)
				.quoted(entry.getKey())
				.literal(COLUMN)
				.literal(SPACE)
				.unquoted(entry.getValue())
				.literal(SPACE)
				.literal(CLOSE_BRACE);
		}
		
		return serializer
			.pop(simple.size() + nested.size(), COMMA_SPACE)
			.literal(SPACE)
			.literal(CLOSE_SQUARE)
			.toString();
	}
	
	public static Include newInclude() {
		return new Include();
	}
}
