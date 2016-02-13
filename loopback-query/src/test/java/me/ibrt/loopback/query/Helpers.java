package me.ibrt.loopback.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Helpers {
	private Helpers() {
		// Intentionally empty.
	}
	
	@SafeVarargs
	public static <T> List<T> asList(T... elements) {
		List<T> list = new ArrayList<T>(elements.length);
		for (T element : elements) {
			list.add(element);
		}
		return list;
	}
	
	public static <K, V> Map<K,V> asMap() {
		return asMap(null,null);
	}
	
	public static <K,V> Map<K,V> asMap(K key, V value) {
		return asMap(key, value, null, null, null, null);
	}
	
	public static <K,V> Map<K,V> asMap(K key1, V value1, K key2, V value2) {
		return asMap(key1, value1, key2, value2, null, null);
	}
	
	public static <K, V> Map<K,V> asMap(K key1, V value1, K key2, V value2, K key3, V value3) {
		Map<K,V> map = new HashMap<K,V>();
		if (key1 != null) {
			map.put(key1, value1);
		}
		if (key2 != null) {
			map.put(key2, value2);
		}
		if (key3 != null) {
			map.put(key3, value3);
		}
		return map;
	}
}
