package pl.mczpk.med.sr.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MapUtils {

	public static <K,V> void putNewValueToListMap(Map<K, List<V>> map, K key, V value) {
		List<V> list = map.get(key);
		if(list == null) {
			map.put(key, new ArrayList<V>(Arrays.asList(value)));
		} else {
			list.add(value);
		}
	}
}
