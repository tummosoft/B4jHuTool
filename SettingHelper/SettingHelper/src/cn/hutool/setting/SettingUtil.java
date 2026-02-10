package cn.hutool.setting;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.core.map.SafeConcurrentHashMap;
import cn.hutool.core.util.StrUtil;

import java.util.Map;

/**
 * Setting tool class<br>
 * Provide static methods to get configuration files
 *
 * @author looly
 */
public class SettingUtil {
	/**
	 * Configuration file cache
	 */
	private static final Map<String, Setting> SETTING_MAP = new SafeConcurrentHashMap<>();

	/**
	 * Get the configuration file in the current environment<br>
	 * name can be a file name without extension (default .setting as suffix), or the full file name
	 *
	 * @param name File name, if no extension, default to .setting
	 * @return Configuration file in the current environment
	 */
	public static Setting get(String name) {
		return SETTING_MAP.computeIfAbsent(name, (filePath)->{
			final String extName = FileNameUtil.extName(filePath);
			if (StrUtil.isEmpty(extName)) {
				filePath = filePath + "." + Setting.EXT_NAME;
			}
			return new Setting(filePath, true);
		});
	}

	/**
	 * Get the first configuration file found for the given path<br>
	 * * name can be a file name without extension (default .setting as suffix), or the full file name
	 *
	 * @param names File name, if no extension, default to .setting
	 *
	 * @return Configuration file in the current environment
	 * @since 5.1.3
	 */
	public static Setting getFirstFound(String... names) {
		for (String name : names) {
			try {
				return get(name);
			} catch (NoResourceException e) {
				//ignore
			}
		}
		return null;
	}
}
