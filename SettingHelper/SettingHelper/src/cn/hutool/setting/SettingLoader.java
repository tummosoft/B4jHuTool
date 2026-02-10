package cn.hutool.setting;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.*;
import cn.hutool.log.Log;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Setting file loader
 *
 * @author Looly
 *
 */
public class SettingLoader {
	private static final Log log = Log.get();

	/** Comment flag (when this symbol is at the beginning of a line, this line is a comment) */
	private final static char COMMENT_FLAG_PRE = '#';
	/** Assignment separator (used to separate key-value pairs) */
	private char assignFlag = '=';
	/** Regex of variable name */
	private String varRegex = "\\$\\{(.*?)\\}";

	/** Character set of this configuration object */
	private final Charset charset;
	/** Whether to use variables */
	private final boolean isUseVariable;
	/** GroupedMap */
	private final GroupedMap groupedMap;

	/**
	 * Constructor
	 *
	 * @param groupedMap GroupedMap
	 */
	public SettingLoader(GroupedMap groupedMap) {
		this(groupedMap, CharsetUtil.CHARSET_UTF_8, false);
	}

	/**
	 * Constructor
	 *
	 * @param groupedMap GroupedMap
	 * @param charset Character set
	 * @param isUseVariable Whether to use variables
	 */
	public SettingLoader(GroupedMap groupedMap, Charset charset, boolean isUseVariable) {
		this.groupedMap = groupedMap;
		this.charset = charset;
		this.isUseVariable = isUseVariable;
	}

	/**
	 * Load configuration file
	 *
	 * @param resource Configuration file URL
	 * @return Whether loading was successful
	 * @throws NoResourceException If the resource does not exist, throw this exception
	 */
	public boolean load(Resource resource) throws NoResourceException{
		if (resource == null) {
			throw new NullPointerException("Null setting url define!");
		}
		InputStream settingStream = null;
		try {
			settingStream = resource.getStream();
			load(settingStream);
			log.debug("Load setting file [{}]", resource);
		} catch (Exception e) {
			if(e instanceof NoResourceException){
				throw (NoResourceException)e;
			}
			throw new NoResourceException(e);
		} finally {
			IoUtil.close(settingStream);
		}
		return true;
	}

	/**
	 * Load configuration file. This method will not close the stream object
	 *
	 * @param settingStream File stream
	 * @return Whether loading was successful
	 * @throws IOException IO exception
	 */
	synchronized public boolean load(InputStream settingStream) throws IOException {
		this.groupedMap.clear();
		BufferedReader reader = null;
		try {
			reader = IoUtil.getReader(settingStream, this.charset);
		// Group

			String line;
			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				line = StrUtil.trim(line);
			// Skip blank and comment lines
				if (StrUtil.isBlank(line) || StrUtil.startWith(line, COMMENT_FLAG_PRE)) {
					continue;
				}

				// Record group name
				if (StrUtil.isSurround(line, CharUtil.BRACKET_START, CharUtil.BRACKET_END)) {
					group = StrUtil.trim(line.substring(1, line.length() - 1));
					continue;
				}

				final String[] keyValue = StrUtil.splitToArray(line, this.assignFlag, 2);
			// Skip lines that don't conform to key-value format
				if (keyValue.length < 2) {
					continue;
				}

				String value = StrUtil.trim(keyValue[1]);
			// Replace all variables in values (variables must be defined before this line, otherwise they cannot be found)
				if (this.isUseVariable) {
					value = replaceVar(group, value);
				}
				this.groupedMap.put(group, StrUtil.trim(keyValue[0]), value);
			}
		} finally {
			IoUtil.close(reader);
		}
		return true;
	}

	/**
	 * Set the regex for variables<br>
	 * The regex can only have one group representing the variable itself, the rest being characters, e.g., \\$\\{(name)\\} means the variable ${name} with variable name name
	 *
	 * @param regex Regex
	 */
	public void setVarRegex(String regex) {
		this.varRegex = regex;
	}

	/**
	 * Assignment separator (used to separate key-value pairs)
	 *
	 * @param assignFlag Regex
	 * @since 4.6.5
	 */
	public void setAssignFlag(char assignFlag) {
		this.assignFlag = assignFlag;
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
		Assert.notNull(file, "File to store must be not null !");
		log.debug("Store Setting to [{}]...", file.getAbsolutePath());
		PrintWriter writer = null;
		try {
			writer = FileUtil.getPrintWriter(file, charset, false);
			store(writer);
		} finally {
			IoUtil.close(writer);
		}
	}

	/**
	 * Save to Writer
	 *
	 * @param writer Writer
	 */
	synchronized private void store(PrintWriter writer) {
		for (Entry<String, LinkedHashMap<String, String>> groupEntry : this.groupedMap.entrySet()) {
			writer.println(StrUtil.format("{}{}{}", CharUtil.BRACKET_START, groupEntry.getKey(), CharUtil.BRACKET_END));
			for (Entry<String, String> entry : groupEntry.getValue().entrySet()) {
				writer.println(StrUtil.format("{} {} {}", entry.getKey(), this.assignFlag, entry.getValue()));
			}
		}
	}

	// ----------------------------------------------------------------------------------- Private method start
	/**
	 * Replace the variable identifier in the given value
	 *
	 * @param group Group
	 * @param value Value
	 * @return String after replacement
	 */
	private String replaceVar(String group, String value) {
		// Find all variable identifiers
		final Set<String> vars = ReUtil.findAll(varRegex, value, 0, new HashSet<>());
		String key;
		for (String var : vars) {
			key = ReUtil.get(varRegex, var, 1);
			if (StrUtil.isNotBlank(key)) {
				// Find the value corresponding to the variable name in this group
				String varValue = this.groupedMap.get(group, key);
				// Cross-group search
				if (null == varValue) {
					final List<String> groupAndKey = StrUtil.split(key, CharUtil.DOT, 2);
					if (groupAndKey.size() > 1) {
						varValue = this.groupedMap.get(groupAndKey.get(0), groupAndKey.get(1));
					}
				}
				// Find in system parameters and environment variables
				if (null == varValue) {
					varValue = SystemPropsUtil.get(key);
				}

				if (null != varValue) {
					// Replace identifier
					value = value.replace(var, varValue);
				}
			}
		}
		return value;
	}
	// ----------------------------------------------------------------------------------- Private method end
}
