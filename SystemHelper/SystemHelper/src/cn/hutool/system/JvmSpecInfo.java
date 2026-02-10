package cn.hutool.system;

import anywheresoftware.b4a.BA;
import java.io.Serializable;

@BA.ShortName("JvmSpecInfo")
public class JvmSpecInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private final String JAVA_VM_SPECIFICATION_NAME = SystemUtil.get("java.vm.specification.name", false);
	private final String JAVA_VM_SPECIFICATION_VERSION = SystemUtil.get("java.vm.specification.version", false);
	private final String JAVA_VM_SPECIFICATION_VENDOR = SystemUtil.get("java.vm.specification.vendor", false);
	
	public final String getName() {
		return JAVA_VM_SPECIFICATION_NAME;
	}

	public final String getVersion() {
		return JAVA_VM_SPECIFICATION_VERSION;
	}

	public final String getVendor() {
		return JAVA_VM_SPECIFICATION_VENDOR;
	}

	
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();

		SystemUtil.append(builder, "JavaVM Spec. Name:    ", getName());
		SystemUtil.append(builder, "JavaVM Spec. Version: ", getVersion());
		SystemUtil.append(builder, "JavaVM Spec. Vendor:  ", getVendor());

		return builder.toString();
	}

}
