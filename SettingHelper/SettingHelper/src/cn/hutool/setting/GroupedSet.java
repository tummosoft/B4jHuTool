package cn.hutool.setting;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Grouped Set collection class<br>
 * You can use square brackets in the configuration file to separate different groups, each group will be placed in an independent Set, distinguished by group<br>
 * Ungrouped collections and `[]` grouped collections will merge members, duplicate group names will also merge members<br>
 * The group configuration file is as follows:
 * 
 * <pre>
 * [group1]
 * aaa
 * bbb
 * ccc
 * 
 * [group2]
 * aaa
 * ccc
 * ddd
 * </pre>
 * 
 * @author Looly
 * @since 3.1.0
 */
public class GroupedSet extends HashMap<String, LinkedHashSet<String>> {
	private static final long serialVersionUID = -8430706353275835496L;
	// private final static Log log = StaticLog.get();

	/** Comment symbol (when this symbol is at the beginning of a line, it means this line is a comment) */
	private final static String COMMENT_FLAG_PRE = "#";
	/** Surrounding markers recognized by group lines */
	private final static char[] GROUP_SURROUND = { '[', ']' };

	/** Charset of this setting object */
	private Charset charset;
	/** URL of the setting file */
	private URL groupedSetUrl;

	/**
	 * Basic constructor<br>
	 * Need to customize initialization configuration file
	 * 
	 * @param charset Charset
	 */
	public GroupedSet(Charset charset) {
		this.charset = charset;
	}

	/**
	 * Constructor, using the relative path relative to the Class file root directory
	 * 
	 * @param pathBaseClassLoader Relative path (relative to the classes path of the current project)
	 * @param charset Charset
	 */
	public GroupedSet(String pathBaseClassLoader, Charset charset) {
		if (null == pathBaseClassLoader) {
			pathBaseClassLoader = StrUtil.EMPTY;
		}

		final URL url = URLUtil.getURL(pathBaseClassLoader);
		if (url == null) {
			throw new RuntimeException(StrUtil.format("Can not find GroupSet file: [{}]", pathBaseClassLoader));
		}
		this.init(url, charset);
	}

	/**
	 * Constructor
	 * 
	 * @param configFile Configuration file object
	 * @param charset Charset
	 */
	public GroupedSet(File configFile, Charset charset) {
		if (configFile == null) {
			throw new RuntimeException("Null GroupSet file!");
		}
		final URL url = URLUtil.getURL(configFile);
		this.init(url, charset);
	}

	/**
	 * Constructor, read file relative to classes
	 * 
	 * @param path Relative path
	 * @param clazz Base class
	 * @param charset Charset
	 */
	public GroupedSet(String path, Class<?> clazz, Charset charset) {
		final URL url = URLUtil.getURL(path, clazz);
		if (url == null) {
			throw new RuntimeException(StrUtil.format("Can not find GroupSet file: [{}]", path));
		}
		this.init(url, charset);
	}

	/**
	 * Constructor
	 * 
	 * @param url Setting file URL
	 * @param charset Charset
	 */
	public GroupedSet(URL url, Charset charset) {
		if (url == null) {
			throw new RuntimeException("Null url define!");
		}
		this.init(url, charset);
	}

	/**
	 * Constructor
	 * 
	 * @param pathBaseClassLoader Relative path (relative to current project's classes path)
	 */
	public GroupedSet(String pathBaseClassLoader) {
		this(pathBaseClassLoader, CharsetUtil.CHARSET_UTF_8);
	}

	/*--------------------------Public method start-------------------------------*/
	/**
	 * Initialize setting file
	 * 
	 * @param groupedSetUrl Setting file URL
	 * @param charset Charset
	 * @return Whether the initialization was successful
	 */
	public boolean init(URL groupedSetUrl, Charset charset) {
		if (groupedSetUrl == null) {
			throw new RuntimeException("Null GroupSet url or charset define!");
		}
		this.charset = charset;
		this.groupedSetUrl = groupedSetUrl;

		return this.load(groupedSetUrl);
	}

	/**
	 * Load setting file
	 * 
	 * @param groupedSetUrl Configuration file URL
	 * @return Whether the loading was successful
	 */
	synchronized public boolean load(URL groupedSetUrl) {
		if (groupedSetUrl == null) {
			throw new RuntimeException("Null GroupSet url define!");
		}
		// log.debug("Load GroupSet file [{}]", groupedSetUrl.getPath());
		InputStream settingStream = null;
		try {
			settingStream = groupedSetUrl.openStream();
			load(settingStream);
		} catch (IOException e) {
			// log.error(e, "Load GroupSet error!");
			return false;
		} finally {
			IoUtil.close(settingStream);
		}
		return true;
	}

	/**
	 * Reload configuration file
	 */
	public void reload() {
		this.load(groupedSetUrl);
	}

	/**
	 * Load setting file. This method will not close the stream object
	 * 
	 * @param settingStream File stream
	 * @return Whether the loading was successful
	 * @throws IOException IO exception
	 */
	public boolean load(InputStream settingStream) throws IOException {
		super.clear();
		BufferedReader reader = null;
		try {
			reader = IoUtil.getReader(settingStream, charset);
			// Group
			String group;
			LinkedHashSet<String> valueSet = null;

			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				line = line.trim();
				// Skip comment lines and blank lines
				if (StrUtil.isBlank(line) || line.startsWith(COMMENT_FLAG_PRE)) {
					// Blank lines and comments are ignored
					continue;
				} else if (line.startsWith(StrUtil.BACKSLASH + COMMENT_FLAG_PRE)) {
					// For strings starting with # in values, escape processing is required, reverse escape here
					line = line.substring(1);
				}

			// Record group name
				if (line.charAt(0) == GROUP_SURROUND[0] && line.charAt(line.length() - 1) == GROUP_SURROUND[1]) {
					// Start a new group value, when duplicate group names appear, merge group values
					group = line.substring(1, line.length() - 1).trim();
					valueSet = super.get(group);
					if (null == valueSet) {
						valueSet = new LinkedHashSet<>();
					}
					super.put(group, valueSet);
					continue;
				}

				// Add value
				if (null == valueSet) {
					// When there are no group values, valueSet will be empty, at which time group is ""
					valueSet = new LinkedHashSet<>();
					super.put(StrUtil.EMPTY, valueSet);
				}
				valueSet.add(line);
			}
		} finally {
			IoUtil.close(reader);
		}
		return true;
	}

	/**
	 * @return Get the path of the setting file
	 */
	public String getPath() {
		return groupedSetUrl.getPath();
	}

	/**
	 * @return Get all group names
	 */
	public Set<String> getGroups() {
		return super.keySet();
	}

	/**
	 * Get all values of the corresponding group
	 * 
	 * @param group Group name
	 * @return Group value collection
	 */
	public LinkedHashSet<String> getValues(String group) {
		if (group == null) {
			group = StrUtil.EMPTY;
		}
		return super.get(group);
	}

	/**
	 * Whether the collection of the given group contains the specified value<br>
	 * If the collection corresponding to the given group does not exist, false is returned
	 * 
	 * @param group Group name
	 * @param value Test value
	 * @param otherValues Other values
	 * @return Whether it contains
	 */
	public boolean contains(String group, String value, String... otherValues) {
		if (ArrayUtil.isNotEmpty(otherValues)) {
			// Need to test multiple values		
			final List<String> valueList = ListUtil.toList(otherValues);
			valueList.add(value);
			return contains(group, valueList);
		} else {
			// Test single value
			final LinkedHashSet<String> valueSet = getValues(group);
			if (CollectionUtil.isEmpty(valueSet)) {
				return false;
			}

			return valueSet.contains(value);
		}
	}

	/**
	 * Whether the collection of the given group completely contains the specified value collection<br>
	 * If the collection corresponding to the given group does not exist, false is returned
	 * 
	 * @param group Group name
	 * @param values Test value collection
	 * @return Whether it contains
	 */
	public boolean contains(String group, Collection<String> values) {
		final LinkedHashSet<String> valueSet = getValues(group);
		if (CollectionUtil.isEmpty(values) || CollectionUtil.isEmpty(valueSet)) {
			return false;
		}

		return valueSet.containsAll(values);
	}
}
