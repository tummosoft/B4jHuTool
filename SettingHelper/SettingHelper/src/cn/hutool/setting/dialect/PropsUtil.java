package cn.hutool.setting.dialect;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.core.map.SafeConcurrentHashMap;
import cn.hutool.core.util.StrUtil;

import java.util.Map;

/**
 * Props utility class<br>
 * Provides static methods to get configuration files
 *
 * @author looly
 * @since 5.1.3
 */
public class PropsUtil {

	/**
	 * Configuration file cache
	 */
	private static final Map<String, Props> propsMap = new SafeConcurrentHashMap<>();

	/**
	 * Get configuration file under the current environment<br>
	 * name can be a file name without extension (default .properties), or the full file name
	 *
	 * @param name File name, default is .properties if no extension
	 * @return Configuration file under the current environment
	 */
	public static Props get(String name) {
		return propsMap.computeIfAbsent(name, (filePath)->{
			final String extName = FileUtil.extName(filePath);
			if (StrUtil.isEmpty(extName)) {
				filePath = filePath + "." + Props.EXT_NAME;
			}
			return new Props(filePath);
		});
	}

	/**
	 * Get the first configuration file found under the given path<br>
	 * * name can be a file name without extension (default .properties as suffix), or the full file name
	 *
	 * @param names File name, default is .properties if no extension
	 *
	 * @return Configuration file under the current environment
	 */
	public static Props getFirstFound(String... names) {
		for (String name : names) {
			try {
				return get(name);
			} catch (NoResourceException e) {
				//ignore
			}
		}
		return null;
	}

	/**
	 * Get system parameters, for example -Duse=hutool defined by a user when executing the java command
	 *
	 * @return System parameter Props
	 * @since 5.5.2
	 */
	public static Props getSystemProps(){
		return new Props(System.getProperties());
	}
}
