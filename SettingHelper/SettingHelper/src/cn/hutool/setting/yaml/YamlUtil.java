package cn.hutool.setting.yaml;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Dict;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * YAML read and write tool based on Snakeyaml
 *
 * @author looly
 * @since 5.7.14
 */
public class YamlUtil {

	/**
	 * Load YAML file from classpath or absolute path
	 *
	 * @param path YAML path, relative path is relative to classpath
	 * @return Loaded content, default Map
	 */
	public static Dict loadByPath(String path) {
		return loadByPath(path, Dict.class);
	}

	/**
	 * Load YAML file from classpath or absolute path
	 *
	 * @param <T>  Bean type, default map
	 * @param path YAML path, relative path is relative to classpath
	 * @param type The bean type to be loaded, i.e., the bean to be converted to
	 * @return Loaded content, default Map
	 */
	public static <T> T loadByPath(String path, Class<T> type) {
		return load(ResourceUtil.getStream(path), type);
	}

	/**
	 * Load YAML from stream
	 *
	 * @param <T>  Bean type, default map
	 * @param in   Stream
	 * @param type The bean type to be loaded, i.e., the bean to be converted to
	 * @return Loaded content, default Map
	 */
	public static <T> T load(InputStream in, Class<T> type) {
		return load(IoUtil.getBomReader(in), type);
	}

	/**
	 * Load YAML, close the {@link Reader} after loading is complete
	 *
	 * @param reader {@link Reader}
	 * @return Loaded Map
	 */
	public static Dict load(Reader reader) {
		return load(reader, Dict.class);
	}

	/**
	 * Load YAML, close the {@link Reader} after loading is complete
	 *
	 * @param <T>    Bean type, default map
	 * @param reader {@link Reader}
	 * @param type   The bean type to be loaded, i.e., the bean to be converted to
	 * @return Loaded content, default Map
	 */
	public static <T> T load(Reader reader, Class<T> type) {
		return load(reader, type, true);
	}

	/**
	 * Load YAML
	 *
	 * @param <T>           Bean type, default map
	 * @param reader        {@link Reader}
	 * @param type          The bean type to be loaded, i.e., the bean to be converted to
	 * @param isCloseReader Whether to close the {@link Reader} after loading is complete
	 * @return Loaded content, default Map
	 */
	public static <T> T load(Reader reader, Class<T> type, boolean isCloseReader) {
		Assert.notNull(reader, "Reader must be not null !");
		if (null == type) {
			//noinspection unchecked
			type = (Class<T>) Object.class;
		}

		final Yaml yaml = new Yaml();
		try {
			return yaml.loadAs(reader, type);
		} finally {
			if (isCloseReader) {
				IoUtil.close(reader);
			}
		}
	}

	/**
	 * Write a Bean object or Map to {@link Writer}
	 *
	 * @param object Object
	 * @param writer {@link Writer}
	 */
	public static void dump(Object object, Writer writer) {
		final DumperOptions options = new DumperOptions();
		options.setIndent(2);
		options.setPrettyFlow(true);
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

		dump(object, writer, options);
	}

	/**
	 * Write a Bean object or Map to {@link Writer}
	 *
	 * @param object        Object
	 * @param writer        {@link Writer}
	 * @param dumperOptions Output style
	 */
	public static void dump(Object object, Writer writer, DumperOptions dumperOptions) {
		if (null == dumperOptions) {
			dumperOptions = new DumperOptions();
		}
		final Yaml yaml = new Yaml(dumperOptions);
		yaml.dump(object, writer);
	}
}
