package cn.hutool.setting;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.*;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.WatchUtil;
import cn.hutool.core.io.watch.watchers.DelayWatcher;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import cn.hutool.setting.dialect.Props;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.*;
import java.util.function.Consumer;

/**
 * Setting tool class for supporting configuration files<br>
 * BasicSetting is used to replace the Properties class and provide more powerful configuration files while maintaining backward compatibility with Properties files
 *
 * <pre>
 *  1. Support for variables, default variable naming is ${variable name}, variables can only recognize variables from the read line, for example the variable on line 6 cannot be read on line 3
 *  2. Support for grouping, grouping is content surrounded by brackets, lines below the brackets belong to this group, no grouping is equivalent to empty string grouping, if a key is name, after adding grouping the key is equivalent to group.name
 *  3. Comments start with #, but blank lines and lines without "=" are also skipped, but # is recommended
 *  4. The store method will not save comment content, use with caution
 * </pre>
 *
 * @author looly
 */
public class Setting extends AbsSetting implements Map<String, String> {
	private static final long serialVersionUID = 3618305164959883393L;

	/**
	 * Default character set
	 */
	public static final Charset DEFAULT_CHARSET = CharsetUtil.CHARSET_UTF_8;
	/**
	 * Default configuration file extension
	 */
	public static final String EXT_NAME = "setting";

	/**
	 * Build an empty Setting for manual parameter entry
	 *
	 * @return Setting
	 * @since 5.4.3
	 */
	public static Setting create() {
		return new Setting();
	}

	/**
	 * Key-value pair storage with grouping
	 */
	private final GroupedMap groupedMap = new GroupedMap();

	/**
	 * Character set of this configuration object
	 */
	protected Charset charset;
	/**
	 * Whether to use variables
	 */
	protected boolean isUseVariable;
	/**
	 * Resource of the configuration file
	 */
	protected Resource resource;

	private SettingLoader settingLoader;
	private WatchMonitor watchMonitor;

	// ------------------------------------------------------------------------------------- Constructor start

	/**
	 * Empty constructor
	 */
	public Setting() {
		this.charset = DEFAULT_CHARSET;
	}

	/**
	 * Constructor
	 *
	 * @param path Relative path or absolute path
	 */
	public Setting(String path) {
		this(path, false);
	}

	/**
	 * Constructor
	 *
	 * @param path          Relative path or absolute path
	 * @param isUseVariable Whether to use variables
	 */
	public Setting(String path, boolean isUseVariable) {
		this(path, DEFAULT_CHARSET, isUseVariable);
	}

	/**
	 * Constructor, using relative path relative to Class file root directory
	 *
	 * @param path          Relative path or absolute path
	 * @param charset       Character set
	 * @param isUseVariable Whether to use variables
	 */
	public Setting(String path, Charset charset, boolean isUseVariable) {
		Assert.notBlank(path, "Blank setting path !");
		this.init(ResourceUtil.getResourceObj(path), charset, isUseVariable);
	}

	/**
	 * Constructor
	 *
	 * @param configFile    Configuration file object
	 * @param charset       Character set
	 * @param isUseVariable Whether to use variables
	 */
	public Setting(File configFile, Charset charset, boolean isUseVariable) {
		Assert.notNull(configFile, "Null setting file define!");
		this.init(new FileResource(configFile), charset, isUseVariable);
	}

	/**
	 * Constructor, read file relative to classes
	 *
	 * @param path          Relative ClassPath path or absolute path
	 * @param clazz         Base class
	 * @param charset       Character set
	 * @param isUseVariable Whether to use variables
	 */
	public Setting(String path, Class<?> clazz, Charset charset, boolean isUseVariable) {
		Assert.notBlank(path, "Blank setting path !");
		this.init(new ClassPathResource(path, clazz), charset, isUseVariable);
	}

	/**
	 * Constructor
	 *
	 * @param url           URL of the configuration file
	 * @param charset       Character set
	 * @param isUseVariable Whether to use variables
	 */
	public Setting(URL url, Charset charset, boolean isUseVariable) {
		Assert.notNull(url, "Null setting url define!");
		this.init(new UrlResource(url), charset, isUseVariable);
	}

	/**
	 * Constructor
	 *
	 * @param resource      Resource for Setting
	 * @param charset       Character set
	 * @param isUseVariable Whether to use variables
	 * @since 5.4.4
	 */
	public Setting(Resource resource, Charset charset, boolean isUseVariable) {
		this.init(resource, charset, isUseVariable);
	}
	// ------------------------------------------------------------------------------------- Constructor end

	/**
	 * Initialize configuration file
	 *
	 * @param resource      {@link Resource}
	 * @param charset       Character set
	 * @param isUseVariable Whether to use variables
	 * @return Whether initialization was successful
	 */
	public boolean init(Resource resource, Charset charset, boolean isUseVariable) {
		Assert.notNull(resource, "Setting resource must be not null!");
		this.resource = resource;
		this.charset = charset;
		this.isUseVariable = isUseVariable;

		return load();
	}

	/**
	 * Reload configuration file
	 *
	 * @return Whether the loading was successful
	 */
	synchronized public boolean load() {
		if (null == this.settingLoader) {
			settingLoader = new SettingLoader(this.groupedMap, this.charset, this.isUseVariable);
		}
		return settingLoader.load(this.resource);
	}

	/**
	 * Auto-load when configuration file changes
	 *
	 * @param autoReload Whether to auto-load
	 */
	public void autoLoad(boolean autoReload) {
		autoLoad(autoReload, null);
	}

	/**
	 * Auto-load when configuration file changes
	 *
	 * @param autoReload Whether to auto-load
	 * @param callback   Callback after loading is complete
	 */
	public void autoLoad(boolean autoReload, Consumer<Boolean> callback) {
		if (autoReload) {
			Assert.notNull(this.resource, "Setting resource must be not null !");
			if (null != this.watchMonitor) {
				// Close previous listener first
				this.watchMonitor.close();
			}
			this.watchMonitor = WatchUtil.createModify(resource.getUrl(), new DelayWatcher(new SimpleWatcher() {
				@Override
				public void onModify(WatchEvent<?> event, Path currentPath) {
					boolean success = load();
					// If there is a callback, execute it after loading is complete
					if (callback != null) {
						callback.accept(success);
					}
				}
			}, 600));

			this.watchMonitor.start();
			StaticLog.debug("Auto load for [{}] listenning...", this.resource.getUrl());
		} else {
			IoUtil.close(this.watchMonitor);
			this.watchMonitor = null;
		}
	}

	/**
	 * Get the URL of the configuration file
	 *
	 * @return The path of the configuration file
	 * @since 5.4.3
	 */
	public URL getSettingUrl() {
		return (null == this.resource) ? null : this.resource.getUrl();
	}

	/**
	 * Get the path of the configuration file
	 *
	 * @return The path of the configuration file
	 */
	public String getSettingPath() {
		final URL settingUrl = getSettingUrl();
		return (null == settingUrl) ? null : settingUrl.getPath();
	}

	/**
	 * Total number of keys and values
	 *
	 * @return Total number of keys and values
	 */
	@Override
	public int size() {
		return this.groupedMap.size();
	}

	@Override
	public String getByGroup(String key, String group) {
		return this.groupedMap.get(group, key);
	}

	/**
	 * Get and remove key-value pair, when the value corresponding to the specified key is not empty, return and delete this value, and no longer search for the values corresponding to subsequent keys
	 *
	 * @param keys Key list, commonly used for aliases
	 * @return Value
	 * @since 3.1.2
	 */
	public Object getAndRemove(String... keys) {
		Object value = null;
		for (String key : keys) {
			value = remove(key);
			if (null != value) {
				break;
			}
		}
		return value;
	}

	/**
	 * Get and remove key-value pair, when the value corresponding to the specified key is not empty, return and delete this value, and no longer search for the values corresponding to subsequent keys
	 *
	 * @param keys Key list, commonly used for aliases
	 * @return String value
	 * @since 3.1.2
	 */
	public String getAndRemoveStr(String... keys) {
		String value = null;
		for (String key : keys) {
			value = remove(key);
			if (null != value) {
				break;
			}
		}
		return value;
	}

	/**
	 * Get all key-value pairs for a specified group. This method retrieves the original key-value pairs, which can be modified
	 *
	 * @param group Group
	 * @return Map
	 */
	public Map<String, String> getMap(String group) {
		final LinkedHashMap<String, String> map = this.groupedMap.get(group);
		return (null != map) ? map : new LinkedHashMap<>(0);
	}

	/**
	 * Get all configuration key-value pairs under the group partition and form a new Setting
	 *
	 * @param group Group
	 * @return Setting
	 */
	public Setting getSetting(String group) {
		final Setting setting = new Setting();
		setting.putAll(this.getMap(group));
		return setting;
	}

	/**
	 * Get all configuration key-value pairs under the group partition and form a new {@link Properties}
	 *
	 * @param group Group
	 * @return Properties object
	 */
	public Properties getProperties(String group) {
		final Properties properties = new Properties();
		properties.putAll(getMap(group));
		return properties;
	}

	/**
	 * Get all configuration key-value pairs under the group partition and form a new {@link Props}
	 *
	 * @param group Group
	 * @return Props object
	 * @since 4.1.21
	 */
	public Props getProps(String group) {
		final Props props = new Props();
		props.putAll(getMap(group));
		return props;
	}

	// --------------------------------------------------------------------------------- Functions

	/**
	 * Persist the current settings, which will overwrite previous settings<br>
	 * Persistence will not retain previous groups, note that if the configuration file is inside a jar or in an exe, this method will throw an error.
	 *
	 * @since 5.4.3
	 */
	public void store() {
		final URL resourceUrl = getSettingUrl();
		Assert.notNull(resourceUrl, "Setting path must be not null !");
		store(FileUtil.file(resourceUrl));
	}

	/**
	 * Persist the current settings, which will overwrite previous settings<br>
	 * Persistence will not retain previous groups
	 *
	 * @param absolutePath Absolute path of the setting file
	 */
	public void store(String absolutePath) {
		store(FileUtil.touch(absolutePath));
	}

	/**
	 * Persist the current settings, which will overwrite previous settings<br>
	 * Persistence will not retain previous groups
	 *
	 * @param file Setting file
	 * @since 5.4.3
	 */
	public void store(File file) {
		if (null == this.settingLoader) {
			settingLoader = new SettingLoader(this.groupedMap, this.charset, this.isUseVariable);
		}
		settingLoader.store(file);
	}

	/**
	 * Convert to Properties object, original group becomes prefix
	 *
	 * @return Properties object
	 */
	public Properties toProperties() {
		final Properties properties = new Properties();
		String group;
		for (Entry<String, LinkedHashMap<String, String>> groupEntry : this.groupedMap.entrySet()) {
			group = groupEntry.getKey();
			for (Entry<String, String> entry : groupEntry.getValue().entrySet()) {
				properties.setProperty(StrUtil.isEmpty(group) ? entry.getKey() : group + CharUtil.DOT + entry.getKey(), entry.getValue());
			}
		}
		return properties;
	}

	/**
	 * Get GroupedMap
	 *
	 * @return GroupedMap
	 * @since 4.0.12
	 */
	public GroupedMap getGroupedMap() {
		return this.groupedMap;
	}

	/**
	 * Get all groups
	 *
	 * @return Get all group names
	 */
	public List<String> getGroups() {
		return CollUtil.newArrayList(this.groupedMap.keySet());
	}

	/**
	 * Set the regex for variables<br>
	 * The regex can only have one group representing the variable itself, the rest being characters, e.g., \\$\\{(name)\\} means the variable ${name} with variable name name
	 *
	 * @param regex Regex
	 * @return this
	 */
	public Setting setVarRegex(String regex) {
		if (null == this.settingLoader) {
			throw new NullPointerException("SettingLoader is null !");
		}
		this.settingLoader.setVarRegex(regex);
		return this;
	}

	/**
	 * Custom character encoding
	 *
	 * @param charset Character encoding
	 * @return this
	 * @since 4.6.2
	 */
	public Setting setCharset(Charset charset) {
		this.charset = charset;
		return this;
	}

	// ------------------------------------------------- Map interface with group

	/**
	 * Whether the key-value pairs corresponding to a certain group are empty
	 *
	 * @param group GROUP
	 * @return Whether it is empty
	 */
	public boolean isEmpty(String group) {
		return this.groupedMap.isEmpty(group);
	}

	/**
	 * Whether the specified group contains the specified key
	 *
	 * @param group Group
	 * @param key   Key
	 * @return Whether it contains the key
	 */
	public boolean containsKey(String group, String key) {
		return this.groupedMap.containsKey(group, key);
	}

	/**
	 * Whether the specified group contains the specified value
	 *
	 * @param group Group
	 * @param value Value
	 * @return Whether it contains the value
	 */
	public boolean containsValue(String group, String value) {
		return this.groupedMap.containsValue(group, value);
	}

	/**
	 * Get the value of the specified group. If the group does not exist or the value does not exist, return null
	 *
	 * @param group Group
	 * @param key   Key
	 * @return Value, if the group does not exist or the value does not exist, return null
	 */
	public String get(String group, String key) {
		return this.groupedMap.get(group, key);
	}

	/**
	 * Add key-value pairs to the corresponding group
	 *
	 * @param key   Key
	 * @param group Group
	 * @param value Value
	 * @return The value that existed before this key, or null if it did not exist
	 */
	public String putByGroup(String key, String group, String value) {
		return this.groupedMap.put(group, key, value);
	}

	/**
	 * Delete the specified value from the specified group
	 *
	 * @param group Group
	 * @param key   Key
	 * @return The deleted value, or null if the value does not exist
	 */
	public String remove(String group, Object key) {
		return this.groupedMap.remove(group, Convert.toStr(key));
	}

	/**
	 * Add multiple key-value pairs to a group
	 *
	 * @param group Group
	 * @param m     Key-value pairs
	 * @return this
	 */
	public Setting putAll(String group, Map<? extends String, ? extends String> m) {
		this.groupedMap.putAll(group, m);
		return this;
	}

	/**
	 * Add a Setting to the main configuration
	 *
	 * @param setting Setting configuration
	 * @return this
	 * @since 5.2.4
	 */
	public Setting addSetting(Setting setting) {
		for (Entry<String, LinkedHashMap<String, String>> e : setting.getGroupedMap().entrySet()) {
			this.putAll(e.getKey(), e.getValue());
		}
		return this;
	}

	/**
	 * Clear all key-value pairs in the specified group
	 *
	 * @param group Group
	 * @return this
	 */
	public Setting clear(String group) {
		this.groupedMap.clear(group);
		return this;
	}

	/**
	 * Set of all keys in the specified group
	 *
	 * @param group Group
	 * @return Set of keys
	 */
	public Set<String> keySet(String group) {
		return this.groupedMap.keySet(group);
	}

	/**
	 * All values under the specified group
	 *
	 * @param group Group
	 * @return Value
	 */
	public Collection<String> values(String group) {
		return this.groupedMap.values(group);
	}

	/**
	 * All key-value pairs under the specified group
	 *
	 * @param group Group
	 * @return Key-value pairs
	 */
	public Set<Entry<String, String>> entrySet(String group) {
		return this.groupedMap.entrySet(group);
	}

	/**
	 * Set value
	 *
	 * @param key   Key
	 * @param value Value
	 * @return this
	 * @since 3.3.1
	 */
	public Setting set(String key, String value) {
		this.put(key, value);
		return this;
	}

	/**
	 * Add key-value pairs to the corresponding group<br>
	 * This method is used to unify the parameter order with getXXX
	 *
	 * @param key   Key
	 * @param group Group
	 * @param value Value
	 * @return The value that existed before this key, or null if it did not exist
	 * @since 5.5.7
	 */
	public Setting setByGroup(String key, String group, String value) {
		this.putByGroup(key, group, value);
		return this;
	}

	// ------------------------------------------------- Override Map interface
	@Override
	public boolean isEmpty() {
		return this.groupedMap.isEmpty();
	}

	/**
	 * Whether the default group (empty group) contains the value corresponding to the specified key
	 *
	 * @param key Key
	 * @return Whether the default group contains the value corresponding to the specified key
	 */
	@Override
	public boolean containsKey(Object key) {
		return this.groupedMap.containsKey(DEFAULT_GROUP, Convert.toStr(key));
	}

	/**
	 * Whether the default group (empty group) contains the specified value
	 *
	 * @param value Value
	 * @return Whether the default group contains the specified value
	 */
	@Override
	public boolean containsValue(Object value) {
		return this.groupedMap.containsValue(DEFAULT_GROUP, Convert.toStr(value));
	}

	/**
	 * Get the value corresponding to the specified key from the default group (empty group)
	 *
	 * @param key Key
	 * @return The value corresponding to the specified key in the default group (empty group)
	 */
	@Override
	public String get(Object key) {
		return this.groupedMap.get(DEFAULT_GROUP, Convert.toStr(key));
	}

	/**
	 * Add the specified key-value pair to the default group (empty group)
	 *
	 * @param key   Key
	 * @param value Value
	 * @return The added value
	 */
	@Override
	public String put(String key, String value) {
		return this.groupedMap.put(DEFAULT_GROUP, key, value);
	}

	/**
	 * Remove the specified value from the default group (empty group)
	 *
	 * @param key Key
	 * @return The removed value
	 */
	@Override
	public String remove(Object key) {
		return this.groupedMap.remove(DEFAULT_GROUP, Convert.toStr(key));
	}

	/**
	 * Add a Map of key-value pairs to the default group (empty group)
	 *
	 * @param m Map
	 */
	@SuppressWarnings("NullableProblems")
	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		this.groupedMap.putAll(DEFAULT_GROUP, m);
	}

	/**
	 * Clear all key-value pairs in the default group (empty group)
	 */
	@Override
	public void clear() {
		this.groupedMap.clear(DEFAULT_GROUP);
	}

	/**
	 * Get all key lists in the default group (empty group)
	 *
	 * @return All key lists in the default group (empty group)
	 */
	@SuppressWarnings("NullableProblems")
	@Override
	public Set<String> keySet() {
		return this.groupedMap.keySet(DEFAULT_GROUP);
	}

	/**
	 * Get all value lists in the default group (empty group)
	 *
	 * @return All value lists in the default group (empty group)
	 */
	@SuppressWarnings("NullableProblems")
	@Override
	public Collection<String> values() {
		return this.groupedMap.values(DEFAULT_GROUP);
	}

	/**
	 * Get all key-value pair lists in the default group (empty group)
	 *
	 * @return All key-value pair lists in the default group (empty group)
	 */
	@SuppressWarnings("NullableProblems")
	@Override
	public Set<Entry<String, String>> entrySet() {
		return this.groupedMap.entrySet(DEFAULT_GROUP);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((charset == null) ? 0 : charset.hashCode());
		result = prime * result + groupedMap.hashCode();
		result = prime * result + (isUseVariable ? 1231 : 1237);
		result = prime * result + ((this.resource == null) ? 0 : this.resource.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Setting other = (Setting) obj;
		if (charset == null) {
			if (other.charset != null) {
				return false;
			}
		} else if (false == charset.equals(other.charset)) {
			return false;
		}
		if (false == groupedMap.equals(other.groupedMap)) {
			return false;
		}
		if (isUseVariable != other.isUseVariable) {
			return false;
		}
		if (this.resource == null) {
			return other.resource == null;
		} else {
			return resource.equals(other.resource);
		}
	}

	@Override
	public String toString() {
		return groupedMap.toString();
	}
}
