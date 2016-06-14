package ch.stageconcept.datatraffic.util.type;

import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
public class DefaultHashMap<K, V> extends HashMap<K, V> {

	// SRC:
	// http://stackoverflow.com/questions/7519339/hashmap-to-return-default-value-for-non-found-keys

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private V defaultValue;

	DefaultHashMap(V defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public V get(Object k) {
		return containsKey(k) ? super.get(k) : defaultValue;
	}
}
