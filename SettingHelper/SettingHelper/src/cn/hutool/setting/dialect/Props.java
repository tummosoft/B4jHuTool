package cn.hutool.setting.dialect;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.getter.BasicTypeGetter;
import cn.hutool.core.getter.OptBasicTypeGetter;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.FileResource;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.io.resource.UrlResource;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.WatchUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Date;
import java.util.Properties;

/**
 * Encapsulation class for reading Properties files
 *
 * @author loolly
 */
public final class Props extends Properties implements BasicTypeGetter<String>, OptBasicTypeGetter<String> {
	private static final long serialVersionUID = 1935981579709590740L;

	/**
	 * Default configuration file extension
	 */
	public final static String EXT_NAME = "properties";

	/**
	 * Build an empty Props for manual parameter entry
	 *
	 * @return Setting
	 * @since 5.4.3
	 */
	public static Props create() {
		return new Props();
	}

	// ----------------------------------------------------------------------- Private property start
	/**
	 * Attribute file Resource
	 */
	private Resource resource;
	private WatchMonitor watchMonitor;
	/**
	 * Properties file encoding<br>
	 * issue#1701, this attribute cannot be serialized, so serialization is ignored
	 */
	private transient Charset charset = CharsetUtil.CHARSET_ISO_8859_1;
	// ----------------------------------------------------------------------- Private property end

	/**
	 * Get Properties file from Classpath
	 *
	 * @param resource Resource (relative path to Classpath)
	 * @return Props
	 */
	public static Props getProp(String resource) {
		return new Props(resource);
	}

	/**
	 * Get Properties file from Classpath
	 *
	 * @param resource    Resource (relative path to Classpath)
	 * @param charsetName Character set
	 * @return Properties
	 */
	public static Props getProp(String resource, String charsetName) {
		return new Props(resource, charsetName);
	}

	/**
	 * Get Properties file from Classpath
	 *
	 * @param resource Resource (relative path to Classpath)
	 * @param charset  Character set
	 * @return Properties
	 */
	public static Props getProp(String resource, Charset charset) {
		return new Props(resource, charset);
	}

	// ----------------------------------------------------------------------- Constructor method start

	/**
	 * Constructor
	 */
	public Props() {
	}

	/**
	 * Constructor, using relative path relative to Class file root directory
	 *
	 * @param path Configuration file path, relative to ClassPath, or use absolute path
	 */
	public Props(String path) {
		this(path, CharsetUtil.CHARSET_ISO_8859_1);
	}

	/**
	 * Constructor, using relative path relative to Class file root directory
	 *
	 * @param path        Relative or absolute path
	 * @param charsetName Character set
	 */
	public Props(String path, String charsetName) {
		this(path, CharsetUtil.charset(charsetName));
	}

	/**
	 * Constructor, using relative path relative to Class file root directory
	 *
	 * @param path    Relative or absolute path
	 * @param charset Character set
	 */
	public Props(String path, Charset charset) {
		Assert.notBlank(path, "Blank properties file path !");
		if (null != charset) {
			this.charset = charset;
		}
		this.load(ResourceUtil.getResourceObj(path));
	}

	/**
	 * Constructor
	 *
	 * @param propertiesFile Configuration file object
	 */
	public Props(File propertiesFile) {
		this(propertiesFile, StandardCharsets.ISO_8859_1);
	}

	/**
	 * Constructor
	 *
	 * @param propertiesFile Configuration file object
	 * @param charsetName    Character set
	 */
	public Props(File propertiesFile, String charsetName) {
		this(propertiesFile, Charset.forName(charsetName));
	}

	/**
	 * Constructor
	 *
	 * @param propertiesFile Configuration file object
	 * @param charset        Character set
	 */
	public Props(File propertiesFile, Charset charset) {
		Assert.notNull(propertiesFile, "Null properties file!");
		this.charset = charset;
		this.load(new FileResource(propertiesFile));
	}

	/**
	 * Constructor, read file relative to classes
	 *
	 * @param path  Relative path
	 * @param clazz Base class
	 */
	public Props(String path, Class<?> clazz) {
		this(path, clazz, CharsetUtil.ISO_8859_1);
	}

	/**
	 * Constructor, read file relative to classes
	 *
	 * @param path        Relative path
	 * @param clazz       Base class
	 * @param charsetName Character set
	 */
	public Props(String path, Class<?> clazz, String charsetName) {
		this(path, clazz, CharsetUtil.charset(charsetName));
	}

	/**
	 * Constructor, read file relative to classes
	 *
	 * @param path    Relative path
	 * @param clazz   Base class
	 * @param charset Character set
	 */
	public Props(String path, Class<?> clazz, Charset charset) {
		Assert.notBlank(path, "Blank properties file path !");
		if (null != charset) {
			this.charset = charset;
		}
		this.load(new ClassPathResource(path, clazz));
	}

	/**
	 * Constructor, use URL to read
	 *
	 * @param propertiesUrl Attribute file path
	 */
	public Props(URL propertiesUrl) {
		this(propertiesUrl, StandardCharsets.ISO_8859_1);
	}

	/**
	 * Constructor, use URL to read
	 *
	 * @param propertiesUrl Attribute file path
	 * @param charsetName   Character set
	 */
	public Props(URL propertiesUrl, String charsetName) {
		this(propertiesUrl, CharsetUtil.charset(charsetName));
	}

	/**
	 * Constructor, use URL to read
	 *
	 * @param propertiesUrl Attribute file path
	 * @param charset       Character set
	 */
	public Props(URL propertiesUrl, Charset charset) {
		Assert.notNull(propertiesUrl, "Null properties URL !");
		if (null != charset) {
			this.charset = charset;
		}
		this.load(propertiesUrl);
	}

	/**
	 * Constructor, use URL to read
	 *
	 * @param properties Attribute file path
	 */
	public Props(Properties properties) {
		if (MapUtil.isNotEmpty(properties)) {
			this.putAll(properties);
		}
	}

	// ----------------------------------------------------------------------- Constructor method end

	/**
	 * Initialize configuration file
	 *
	 * @param url {@link URL}
	 * @since 5.5.2
	 */
	public void load(URL url) {
		load(new UrlResource(url));
	}

	/**
	 * Initialize configuration file
	 *
	 * @param resource {@link Resource}
	 */
	public void load(Resource resource) {
		Assert.notNull(resource, "Props resource must be not null!");
		this.resource = resource;

		try (final BufferedReader reader = resource.getReader(charset)) {
			super.load(reader);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	/**
	 * Reload configuration file
	 */
	public void load() {
		this.load(this.resource);
	}

	/**
	 * Auto-load when configuration file changes
	 *
	 * @param autoReload Whether to auto-load
	 */
	public void autoLoad(boolean autoReload) {
		if (autoReload) {
			Assert.notNull(this.resource, "Properties resource must be not null!");
			if (null != this.watchMonitor) {
				// First close previous listeners
				this.watchMonitor.close();
			}
			this.watchMonitor = WatchUtil.createModify(this.resource.getUrl(), new SimpleWatcher() {
				@Override
				public void onModify(WatchEvent<?> event, Path currentPath) {
					load();
				}
			});
			this.watchMonitor.start();
		} else {
			IoUtil.close(this.watchMonitor);
			this.watchMonitor = null;
		}
	}

	// ----------------------------------------------------------------------- Get start
	@Override
	public Object getObj(String key, Object defaultValue) {
		return getStr(key, null == defaultValue ? null : defaultValue.toString());
	}

	@Override
	public Object getObj(String key) {
		return getObj(key, null);
	}

	@Override
	public String getStr(String key, String defaultValue) {
		return super.getProperty(key, defaultValue);
	}

	@Override
	public String getStr(String key) {
		return super.getProperty(key);
	}

	@Override
	public Integer getInt(String key, Integer defaultValue) {
		return Convert.toInt(getStr(key), defaultValue);
	}

	@Override
	public Integer getInt(String key) {
		return getInt(key, null);
	}

	@Override
	public Boolean getBool(String key, Boolean defaultValue) {
		return Convert.toBool(getStr(key), defaultValue);
	}

	@Override
	public Boolean getBool(String key) {
		return getBool(key, null);
	}

	@Override
	public Long getLong(String key, Long defaultValue) {
		return Convert.toLong(getStr(key), defaultValue);
	}

	@Override
	public Long getLong(String key) {
		return getLong(key, null);
	}

	@Override
	public Character getChar(String key, Character defaultValue) {
		final String value = getStr(key);
		if (StrUtil.isBlank(value)) {
			return defaultValue;
		}
		return value.charAt(0);
	}

	@Override
	public Character getChar(String key) {
		return getChar(key, null);
	}

	@Override
	public Float getFloat(String key) {
		return getFloat(key, null);
	}

	@Override
	public Float getFloat(String key, Float defaultValue) {
		return Convert.toFloat(getStr(key), defaultValue);
	}

	@Override
	public Double getDouble(String key, Double defaultValue) throws NumberFormatException {
		return Convert.toDouble(getStr(key), defaultValue);
	}

	@Override
	public Double getDouble(String key) throws NumberFormatException {
		return getDouble(key, null);
	}

	@Override
	public Short getShort(String key, Short defaultValue) {
		return Convert.toShort(getStr(key), defaultValue);
	}

	@Override
	public Short getShort(String key) {
		return getShort(key, null);
	}

	@Override
	public Byte getByte(String key, Byte defaultValue) {
		return Convert.toByte(getStr(key), defaultValue);
	}

	@Override
	public Byte getByte(String key) {
		return getByte(key, null);
	}

	@Override
	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		final String valueStr = getStr(key);
		if (StrUtil.isBlank(valueStr)) {
			return defaultValue;
		}

		try {
			return new BigDecimal(valueStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	@Override
	public BigDecimal getBigDecimal(String key) {
		return getBigDecimal(key, null);
	}

	@Override
	public BigInteger getBigInteger(String key, BigInteger defaultValue) {
		final String valueStr = getStr(key);
		if (StrUtil.isBlank(valueStr)) {
			return defaultValue;
		}

		try {
			return new BigInteger(valueStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	@Override
	public BigInteger getBigInteger(String key) {
		return getBigInteger(key, null);
	}

	@Override
	public <E extends Enum<E>> E getEnum(Class<E> clazz, String key, E defaultValue) {
		return Convert.toEnum(clazz, getStr(key), defaultValue);
	}

	@Override
	public <E extends Enum<E>> E getEnum(Class<E> clazz, String key) {
		return getEnum(clazz, key, null);
	}

	@Override
	public Date getDate(String key, Date defaultValue) {
		return Convert.toDate(getStr(key), defaultValue);
	}

	@Override
	public Date getDate(String key) {
		return getDate(key, null);
	}

	/**
	 * Get and remove key-value pair, when the value corresponding to the specified key is not empty, return and delete this value, and no longer search for the values corresponding to subsequent keys
	 *
	 * @param keys Key list, commonly used for aliases
	 * @return String value
	 * @since 4.1.21
	 */
	public String getAndRemoveStr(String... keys) {
		Object value = null;
		for (String key : keys) {
			value = remove(key);
			if (null != value) {
				break;
			}
		}
		return (String) value;
	}

	/**
	 * Convert to standard {@link Properties} object
	 *
	 * @return {@link Properties} object
	 * @since 5.7.4
	 */
	public Properties toProperties() {
		final Properties properties = new Properties();
		properties.putAll(this);
		return properties;
	}

	/**
	 * Convert configuration file to Bean, support nested Bean<br>
	 * Supported expressions:
	 *
	 * <pre>
	 * persion
	 * persion.name
	 * persons[3]
	 * person.friends[5].name
	 * ['person']['friends'][5]['name']
	 * </pre>
	 *
	 * @param <T>       Bean type
	 * @param beanClass Bean class
	 * @return Bean object
	 * @since 4.6.3
	 */
	public <T> T toBean(Class<T> beanClass) {
		return toBean(beanClass, null);
	}

	/**
	 * Convert configuration file to Bean, support nested Bean<br>
	 * Supported expressions:
	 *
	 * <pre>
	 * persion
	 * persion.name
	 * persons[3]
	 * person.friends[5].name
	 * ['person']['friends'][5]['name']
	 * </pre>
	 *
	 * @param <T>       Bean type
	 * @param beanClass Bean class
	 * @param prefix    Common prefix, pass null if not specified, when prefix is specified, properties that are not prefixed are ignored
	 * @return Bean object
	 * @since 4.6.3
	 */
	public <T> T toBean(Class<T> beanClass, String prefix) {
		final T bean = ReflectUtil.newInstanceIfPossible(beanClass);
		return fillBean(bean, prefix);
	}

	/**
	 * Convert configuration file to Bean, support nested Bean<br>
	 * Supported expressions:
	 *
	 * <pre>
	 * persion
	 * persion.name
	 * persons[3]
	 * person.friends[5].name
	 * ['person']['friends'][5]['name']
	 * </pre>
	 *
	 * @param <T>    Bean type
	 * @param bean   Bean object
	 * @param prefix Common prefix, pass null if not specified, when prefix is specified, properties that are not prefixed are ignored
	 * @return Bean object
	 * @since 4.6.3
	 */
	public <T> T fillBean(T bean, String prefix) {
		prefix = StrUtil.nullToEmpty(StrUtil.addSuffixIfNot(prefix, StrUtil.DOT));

		String key;
		for (java.util.Map.Entry<Object, Object> entry : this.entrySet()) {
			key = (String) entry.getKey();
			if (false == StrUtil.startWith(key, prefix)) {
				// Ignore properties not starting with the specified prefix
				continue;
			}
			try {
				BeanUtil.setProperty(bean, StrUtil.subSuf(key, prefix.length()), entry.getValue());
			} catch (Exception e) {
				// Ignore fields that fail to inject (these fields may be used for other configurations)
				StaticLog.debug("Ignore property: [{}],because of: {}", key, e);
			}
		}

		return bean;
	}

	// ----------------------------------------------------------------------- Get end

	// ----------------------------------------------------------------------- Set start

	/**
	 * Set value, create the given key if it does not exist. The change is not persisted after setting
	 *
	 * @param key   Property key
	 * @param value Property value
	 */
	public void setProperty(String key, Object value) {
		super.setProperty(key, value.toString());
	}

	/**
	 * Persist the current settings, which will overwrite previous settings
	 *
	 * @param absolutePath Absolute path of the setting file
	 * @throws IORuntimeException IO exception, might be file not found
	 */
	public void store(String absolutePath) throws IORuntimeException {
		Writer writer = null;
		try {
			writer = FileUtil.getWriter(absolutePath, charset, false);
			super.store(writer, null);
		} catch (IOException e) {
			throw new IORuntimeException(e, "Store properties to [{}] error!", absolutePath);
		} finally {
			IoUtil.close(writer);
		}
	}

	/**
	 * Store current settings, will overwrite previous settings
	 *
	 * @param path  Relative path
	 * @param clazz Relative class
	 */
	public void store(String path, Class<?> clazz) {
		this.store(FileUtil.getAbsolutePath(path, clazz));
	}
	// ----------------------------------------------------------------------- Set end
}
