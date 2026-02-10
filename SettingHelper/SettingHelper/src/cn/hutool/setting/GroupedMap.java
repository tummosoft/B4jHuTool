package cn.hutool.setting;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * Map based on grouping<br>
 * This object method is thread-safe
 * 
 * @author looly
 * @since 4.0.11
 */
public class GroupedMap extends LinkedHashMap<String, LinkedHashMap<String, String>> {
	private static final long serialVersionUID = -7777365130776081931L;

	private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
	private final ReadLock readLock = cacheLock.readLock();
	private final WriteLock writeLock = cacheLock.writeLock();
	private int size = -1;

	/**
	 * Get the value corresponding to the group. If the group does not exist or the value does not exist, return null
	 * 
	 * @param group Group
	 * @param key Key
	 * @return Value, return null if the group does not exist or the value does not exist
	 */
	public String get(String group, String key) {
		readLock.lock();
		try {
			LinkedHashMap<String, String> map = this.get(StrUtil.nullToEmpty(group));
			if (MapUtil.isNotEmpty(map)) {
				return map.get(key);
			}
		} finally {
			readLock.unlock();
		}
		return null;
	}

	@Override
	public LinkedHashMap<String, String> get(Object key) {
		readLock.lock();
		try {
			return super.get(key);
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Total number of key-value pairs
	 * 
	 * @return Total number of key-value pairs
	 */
	@Override
	public int size() {
		writeLock.lock();
		try {
			if (this.size < 0) {
				this.size = 0;
				for (LinkedHashMap<String, String> value : this.values()) {
					this.size += value.size();
				}
			}
		} finally {
			writeLock.unlock();
		}
		return this.size;
	}

	/**
	 * Put key-value pair to the corresponding group
	 * 
	 * @param group Group
	 * @param key Key
	 * @param value Value
	 * @return The value that existed before this key, or null if it didn't exist
	 */
	public String put(String group, String key, String value) {
		group = StrUtil.nullToEmpty(group).trim();
		writeLock.lock();
		try {
			final LinkedHashMap<String, String> valueMap = this.computeIfAbsent(group, k -> new LinkedHashMap<>());
			this.size = -1;
			return valueMap.put(key, value);
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Put multiple key-value pairs to a group
	 * 
	 * @param group Group
	 * @param m Key-value pairs
	 * @return this
	 */
	public GroupedMap putAll(String group, Map<? extends String, ? extends String> m) {
		for (Entry<? extends String, ? extends String> entry : m.entrySet()) {
			this.put(group, entry.getKey(), entry.getValue());
		}
		return this;
	}

	/**
	 * Remove specified value from specified group
	 * 
	 * @param group Group
	 * @param key Key
	 * @return The removed value, or null if the value doesn't exist
	 */
	public String remove(String group, String key) {
		group = StrUtil.nullToEmpty(group).trim();
		writeLock.lock();
		try {
			final LinkedHashMap<String, String> valueMap = this.get(group);
			if (MapUtil.isNotEmpty(valueMap)) {
				return valueMap.remove(key);
			}
		} finally {
			writeLock.unlock();
		}
		return null;
	}

	/**
	 * Whether the key-value pairs corresponding to a group are empty
	 * 
	 * @param group Group
	 * @return Whether it is empty
	 */
	public boolean isEmpty(String group) {
		group = StrUtil.nullToEmpty(group).trim();
		readLock.lock();
		try {
			final LinkedHashMap<String, String> valueMap = this.get(group);
			if (MapUtil.isNotEmpty(valueMap)) {
				return valueMap.isEmpty();
			}
		} finally {
			readLock.unlock();
		}
		return true;
	}

	/**
	 * Whether it is empty. If multiple groups are simultaneously empty, it is also treated as empty
	 * 
	 * @return Whether it is empty. If multiple groups are simultaneously empty, it is also treated as empty
	 */
	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Whether specified group contains specified key
	 * 
	 * @param group Group
	 * @param key Key
	 * @return Whether key is contained
	 */
	public boolean containsKey(String group, String key) {
		group = StrUtil.nullToEmpty(group).trim();
		readLock.lock();
		try {
			final LinkedHashMap<String, String> valueMap = this.get(group);
			if (MapUtil.isNotEmpty(valueMap)) {
				return valueMap.containsKey(key);
			}
		} finally {
			readLock.unlock();
		}
		return false;
	}

	/**
	 * Whether specified group contains specified value
	 * 
	 * @param group Group
	 * @param value Value
	 * @return Whether value is contained
	 */
	public boolean containsValue(String group, String value) {
		group = StrUtil.nullToEmpty(group).trim();
		readLock.lock();
		try {
			final LinkedHashMap<String, String> valueMap = this.get(group);
			if (MapUtil.isNotEmpty(valueMap)) {
				return valueMap.containsValue(value);
			}
		} finally {
			readLock.unlock();
		}
		return false;
	}

	/**
	 * Clear all key-value pairs under specified group
	 * 
	 * @param group Group
	 * @return this
	 */
	public GroupedMap clear(String group) {
		group = StrUtil.nullToEmpty(group).trim();
		writeLock.lock();
		try {
			final LinkedHashMap<String, String> valueMap = this.get(group);
			if (MapUtil.isNotEmpty(valueMap)) {
				valueMap.clear();
			}
		} finally {
			writeLock.unlock();
		}
		return this;
	}

	@Override
	public Set<String> keySet() {
		readLock.lock();
		try {
			return super.keySet();
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * Set of all keys for specified group
	 * 
	 * @param group Group
	 * @return Key Set
	 */
	public Set<String> keySet(String group) {
		group = StrUtil.nullToEmpty(group).trim();
		readLock.lock();
		try {
			final LinkedHashMap<String, String> valueMap = this.get(group);
			if (MapUtil.isNotEmpty(valueMap)) {
				return valueMap.keySet();
			}
		} finally {
			readLock.unlock();
		}
		return Collections.emptySet();
	}

	/**
	 * All values under specified group
	 * 
	 * @param group Group
	 * @return Values
	 */
	public Collection<String> values(String group) {
		group = StrUtil.nullToEmpty(group).trim();
		readLock.lock();
		try {
			final LinkedHashMap<String, String> valueMap = this.get(group);
			if (MapUtil.isNotEmpty(valueMap)) {
				return valueMap.values();
			}
		} finally {
			readLock.unlock();
		}
		return Collections.emptyList();
	}

	@Override
	public Set<java.util.Map.Entry<String, LinkedHashMap<String, String>>> entrySet() {
		readLock.lock();
		try {
			return super.entrySet();
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * All key-value pairs under specified group
	 * 
	 * @param group Group
	 * @return Key-value pairs
	 */
	public Set<Entry<String, String>> entrySet(String group) {
		group = StrUtil.nullToEmpty(group).trim();
		readLock.lock();
		try {
			final LinkedHashMap<String, String> valueMap = this.get(group);
			if (MapUtil.isNotEmpty(valueMap)) {
				return valueMap.entrySet();
			}
		} finally {
			readLock.unlock();
		}
		return Collections.emptySet();
	}

	@Override
	public String toString() {
		readLock.lock();
		try {
			return super.toString();
		} finally {
			readLock.unlock();
		}
	}
}
