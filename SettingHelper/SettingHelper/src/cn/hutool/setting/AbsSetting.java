package cn.hutool.setting;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.bean.copier.ValueProvider;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.getter.OptNullBasicTypeFromStringGetter;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Abstract class of Setting
 *
 * @author Looly
 */
public abstract class AbsSetting implements OptNullBasicTypeFromStringGetter<String>, Serializable {
	private static final long serialVersionUID = 6200156302595905863L;
	private final static Log log = LogFactory.get();

	/**
	 * Default delimiter for array type values
	 */
	public final static String DEFAULT_DELIMITER = ",";
	/**
	 * Default group
	 */
	public final static String DEFAULT_GROUP = StrUtil.EMPTY;

	@Override
	public String getStr(String key, String defaultValue) {
		return getStr(key, DEFAULT_GROUP, defaultValue);
	}

	/**
	 * Get string type value
	 *
	 * @param key          KEY
	 * @param group        GROUP
	 * @param defaultValue Default value
	 * @return Value, returns default value if string is {@code null}
	 */
	public String getStr(String key, String group, String defaultValue) {
		final String value = getByGroup(key, group);
		return ObjectUtil.defaultIfNull(value, defaultValue);
	}

	/**
	 * Get string type value, returns default value if string is {@code null} or empty
	 *
	 * @param key          KEY
	 * @param group        GROUP
	 * @param defaultValue Default value
	 * @return Value, returns default value if string is {@code null} or empty
	 * @since 5.2.4
	 */
	public String getStrNotEmpty(String key, String group, String defaultValue) {
		final String value = getByGroup(key, group);
		return ObjectUtil.defaultIfEmpty(value, defaultValue);
	}

	/**
	 * Get the value of the specified group key
	 *
	 * @param key   Key
	 * @param group GROUP
	 * @return Value
	 */
	public abstract String getByGroup(String key, String group);

	// --------------------------------------------------------------- Get

	/**
	 * Get with logging hint, print debug log if the specified KEY is not defined
	 *
	 * @param key Key
	 * @return Value
	 */
	public String getWithLog(String key) {
		final String value = getStr(key);
		if (value == null) {
			log.debug("No key define for [{}]!", key);
		}
		return value;
	}

	/**
	 * Get with logging hint, print debug log if the specified KEY of the group is not defined
	 *
	 * @param key   Key
	 * @param group GROUP
	 * @return Value
	 */
	public String getByGroupWithLog(String key, String group) {
		final String value = getByGroup(key, group);
		if (value == null) {
			log.debug("No key define for [{}] of group [{}] !", key, group);
		}
		return value;
	}

	// --------------------------------------------------------------- Get string array

	/**
	 * Get array type
	 *
	 * @param key Property name
	 * @return Property value
	 */
	public String[] getStrings(String key) {
		return getStrings(key, null);
	}

	/**
	 * Get array type
	 *
	 * @param key          Property name
	 * @param defaultValue Default value
	 * @return Property value
	 */
	public String[] getStringsWithDefault(String key, String[] defaultValue) {
		String[] value = getStrings(key, null);
		if (null == value) {
			value = defaultValue;
		}

		return value;
	}

	/**
	 * Get array type
	 *
	 * @param key   Property name
	 * @param group Group name
	 * @return Property value
	 */
	public String[] getStrings(String key, String group) {
		return getStrings(key, group, DEFAULT_DELIMITER);
	}

	/**
	 * Get array type
	 *
	 * @param key       Property name
	 * @param group     Group name
	 * @param delimiter Delimiter
	 * @return Property value
	 */
	public String[] getStrings(String key, String group, String delimiter) {
		final String value = getByGroup(key, group);
		if (StrUtil.isBlank(value)) {
			return null;
		}
		return StrUtil.splitToArray(value, delimiter);
	}

	// --------------------------------------------------------------- Get int

	/**
	 * Get numeric type property value
	 *
	 * @param key   Property name
	 * @param group Group name
	 * @return Property value
	 */
	public Integer getInt(String key, String group) {
		return getInt(key, group, null);
	}

	/**
	 * Get numeric type property value
	 *
	 * @param key          Property name
	 * @param group        Group name
	 * @param defaultValue Default value
	 * @return Property value
	 */
	public Integer getInt(String key, String group, Integer defaultValue) {
		return Convert.toInt(getByGroup(key, group), defaultValue);
	}

	// --------------------------------------------------------------- Get bool

	/**
	 * Get boolean type property value
	 *
	 * @param key   Property name
	 * @param group Group name
	 * @return Property value
	 */
	public Boolean getBool(String key, String group) {
		return getBool(key, group, null);
	}

	/**
	 * Get boolean type property value
	 *
	 * @param key          Property name
	 * @param group        Group name
	 * @param defaultValue Default value
	 * @return Property value
	 */
	public Boolean getBool(String key, String group, Boolean defaultValue) {
		return Convert.toBool(getByGroup(key, group), defaultValue);
	}

	// --------------------------------------------------------------- Get long

	/**
	 * Get long type property value
	 *
	 * @param key   Property name
	 * @param group Group name
	 * @return Property value
	 */
	public Long getLong(String key, String group) {
		return getLong(key, group, null);
	}

	/**
	 * Get long type property value
	 *
	 * @param key          Property name
	 * @param group        Group name
	 * @param defaultValue Default value
	 * @return Property value
	 */
	public Long getLong(String key, String group, Long defaultValue) {
		return Convert.toLong(getByGroup(key, group), defaultValue);
	}

	// --------------------------------------------------------------- Get char

	/**
	 * Get char type property value
	 *
	 * @param key   Property name
	 * @param group Group name
	 * @return Property value
	 */
	public Character getChar(String key, String group) {
		final String value = getByGroup(key, group);
		if (StrUtil.isBlank(value)) {
			return null;
		}
		return value.charAt(0);
	}

	// --------------------------------------------------------------- Get double

	/**
	 * Get double type property value
	 *
	 * @param key   Property name
	 * @param group Group name
	 * @return Property value
	 */
	public Double getDouble(String key, String group) {
		return getDouble(key, group, null);
	}

	/**
	 * Get double type property value
	 *
	 * @param key          Property name
	 * @param group        Group name
	 * @param defaultValue Default value
	 * @return Property value
	 */
	public Double getDouble(String key, String group, Double defaultValue) {
		return Convert.toDouble(getByGroup(key, group), defaultValue);
	}

	/**
	 * Map key-value relationships in setting to an object. The principle is to call the object's corresponding set method<br>
	 * Only supports conversion of basic types
	 *
	 * @param <T>   Bean type
	 * @param group Group
	 * @param bean  Bean object
	 * @return Bean
	 */
	public <T> T toBean(final String group, T bean) {
		return BeanUtil.fillBean(bean, new ValueProvider<String>() {

			@Override
			public Object value(String key, Type valueType) {
				return getByGroup(key, group);
			}

			@Override
			public boolean containsKey(String key) {
				return null != getByGroup(key, group);
			}
		}, CopyOptions.create());
	}

	/**
	 * Map key-value relationships in setting to an object. The principle is to call the object's corresponding set method<br>
	 * Only supports conversion of basic types
	 *
	 * @param <T>       Bean type
	 * @param group     Group
	 * @param beanClass Bean type
	 * @return Bean
	 * @since 5.0.6
	 */
	public <T> T toBean(String group, Class<T> beanClass) {
		return toBean(group, ReflectUtil.newInstanceIfPossible(beanClass));
	}

	/**
	 * Map key-value relationships in setting to an object. The principle is to call the object's corresponding set method<br>
	 * Only supports conversion of basic types
	 *
	 * @param <T>  Bean type
	 * @param bean Bean
	 * @return Bean
	 */
	public <T> T toBean(T bean) {
		return toBean(null, bean);
	}

	/**
	 * Map key-value relationships in setting to an object. The principle is to call the object's corresponding set method<br>
	 * Only supports conversion of basic types
	 *
	 * @param <T>       Bean type
	 * @param beanClass Bean type
	 * @return Bean
	 * @since 5.0.6
	 */
	public <T> T toBean(Class<T> beanClass) {
		return toBean(null, beanClass);
	}
}
