package cn.hutool.system;

import anywheresoftware.b4a.BA;
import java.io.Serializable;

@BA.ShortName("JavaSpecInfo")
public class JavaSpecInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private final String JAVA_SPECIFICATION_NAME = SystemUtil.get("java.specification.name", false);
	private final String JAVA_SPECIFICATION_VERSION = SystemUtil.get("java.specification.version", false);
	private final String JAVA_SPECIFICATION_VENDOR = SystemUtil.get("java.specification.vendor", false);

	public final String getName() {
		return JAVA_SPECIFICATION_NAME;
	}
	
	public final String getVersion() {
		return JAVA_SPECIFICATION_VERSION;
	}
	
	public final String getVendor() {
		return JAVA_SPECIFICATION_VENDOR;
	}

	
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();

		SystemUtil.append(builder, "Java Spec. Name:    ", getName());
		SystemUtil.append(builder, "Java Spec. Version: ", getVersion());
		SystemUtil.append(builder, "Java Spec. Vendor:  ", getVendor());

		return builder.toString();
	}

}
