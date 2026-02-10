package cn.hutool.system;

import anywheresoftware.b4a.BA;
import java.io.Serializable;

@BA.ShortName("JvmInfo")
public class JvmInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private final String JAVA_VM_NAME = SystemUtil.get("java.vm.name", false);
	private final String JAVA_VM_VERSION = SystemUtil.get("java.vm.version", false);
	private final String JAVA_VM_VENDOR = SystemUtil.get("java.vm.vendor", false);
	private final String JAVA_VM_INFO = SystemUtil.get("java.vm.info", false);
	
	public final String getName() {
		return JAVA_VM_NAME;
	}

	
	public final String getVersion() {
		return JAVA_VM_VERSION;
	}
	
	public final String getVendor() {
		return JAVA_VM_VENDOR;
	}
	
	public final String getInfo() {
		return JAVA_VM_INFO;
	}

	
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();

		SystemUtil.append(builder, "JavaVM Name:    ", getName());
		SystemUtil.append(builder, "JavaVM Version: ", getVersion());
		SystemUtil.append(builder, "JavaVM Vendor:  ", getVendor());
		SystemUtil.append(builder, "JavaVM Info:    ", getInfo());

		return builder.toString();
	}

}
