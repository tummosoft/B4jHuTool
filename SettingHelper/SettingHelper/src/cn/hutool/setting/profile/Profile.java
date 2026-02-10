package cn.hutool.setting.profile;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.SafeConcurrentHashMap;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Profile allows us to define a series of configuration information and specify its activation conditions.<br>
 * In this class, we standardize a set of rules as follows:<br>
 * By default, we read the configuration file (*.setting file) under ${classpath}/default. When calling the setProfile method, you can specify a profile to read the configuration files in its directory.<br>
 * For example, we define several profiles: test, develop, production, respectively representing the test environment, development environment, and production environment. I want to read the database configuration file db.setting, then:
 * <ol>
 * <li>test =》 ${classpath}/test/db.setting</li>
 * <li>develop =》 ${classpath}/develop/db.setting</li>
 * <li>production =》 ${classpath}/production/db.setting</li>
 * </ol>
 *
 * @author Looly
 *
 */
public class Profile implements Serializable {
	private static final long serialVersionUID = -4189955219454008744L;

/** Default environment */
	public static final String DEFAULT_PROFILE = "default";

	/** Condition */
	private String profile;
	/** Encoding */
	private Charset charset;
	/** Whether to use variables */
	private boolean useVar;
	/** Configuration file cache */
	private final Map<String, Setting> settingMap = new SafeConcurrentHashMap<>();

	// -------------------------------------------------------------------------------- Constructor start
	/**
	 * Default constructor, environment uses default: default, encoding UTF-8, no variables used
	 */
	public Profile() {
		this(DEFAULT_PROFILE);
	}

	/**
	 * Constructor, encoding UTF-8, no variables used
	 *
	 * @param profile Environment
	 */
	public Profile(String profile) {
		this(profile, Setting.DEFAULT_CHARSET, false);
	}

	/**
	 * Constructor
	 *
	 * @param profile Environment
	 * @param charset Encoding
	 * @param useVar Whether to use variables
	 */
	public Profile(String profile, Charset charset, boolean useVar) {
		this.profile = profile;
		this.charset = charset;
		this.useVar = useVar;
	}
	// -------------------------------------------------------------------------------- Constructor end

	/**
	 * Get the configuration file in the current environment
	 *
	 * @param name File name, default is .setting if no extension
	 * @return Configuration file in the current environment
	 */
	public Setting getSetting(String name) {
		String nameForProfile = fixNameForProfile(name);
		Setting setting = settingMap.get(nameForProfile);
		if (null == setting) {
			setting = new Setting(nameForProfile, this.charset, this.useVar);
			settingMap.put(nameForProfile, setting);
		}
		return setting;
	}

	/**
	 * Set environment
	 *
	 * @param profile Environment
	 * @return Itself
	 */
	public Profile setProfile(String profile) {
		this.profile = profile;
		return this;
	}

	/**
	 * Set encoding
	 *
	 * @param charset Encoding
	 * @return Itself
	 */
	public Profile setCharset(Charset charset) {
		this.charset = charset;
		return this;
	}

	/**
	 * Set whether to use variables
	 *
	 * @param useVar Variables
	 * @return Itself
	 */
	public Profile setUseVar(boolean useVar) {
		this.useVar = useVar;
		return this;
	}

	/**
	 * Clear configuration files for all environments
	 *
	 * @return Itself
	 */
	public Profile clear() {
		this.settingMap.clear();
		return this;
	}

	// -------------------------------------------------------------------------------- Private method start
	/**
	 * Correct file name
	 *
	 * @param name File name
	 * @return Corrected file name
	 */
	private String fixNameForProfile(String name) {
		Assert.notBlank(name, "Setting name must be not blank !");
		final String actralProfile = StrUtil.nullToEmpty(this.profile);
		if (false == name.contains(StrUtil.DOT)) {
			return StrUtil.format("{}/{}.setting", actralProfile, name);
		}
		return StrUtil.format("{}/{}", actralProfile, name);
	}
	// -------------------------------------------------------------------------------- Private method end
}
