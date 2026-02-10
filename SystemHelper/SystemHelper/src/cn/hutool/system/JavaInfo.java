package cn.hutool.system;

import anywheresoftware.b4a.BA;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReUtil;

import java.io.Serializable;

@BA.ShortName("JavaInfo")
public class JavaInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String JAVA_VERSION = SystemUtil.get("java.version", false);
	private final float JAVA_VERSION_FLOAT = getJavaVersionAsFloat();
	private final int JAVA_VERSION_INT = getJavaVersionAsInt();
	private final String JAVA_VENDOR = SystemUtil.get("java.vendor", false);
	private final String JAVA_VENDOR_URL = SystemUtil.get("java.vendor.url", false);

	private final boolean IS_JAVA_1_8 = getJavaVersionMatches("1.8");
	private final boolean IS_JAVA_9 = getJavaVersionMatches("9");
	private final boolean IS_JAVA_10 = getJavaVersionMatches("10");
	private final boolean IS_JAVA_11 = getJavaVersionMatches("11");
	private final boolean IS_JAVA_12 = getJavaVersionMatches("12");
	private final boolean IS_JAVA_13 = getJavaVersionMatches("13");
	private final boolean IS_JAVA_14 = getJavaVersionMatches("14");
	private final boolean IS_JAVA_15 = getJavaVersionMatches("15");
	private final boolean IS_JAVA_16 = getJavaVersionMatches("16");
	private final boolean IS_JAVA_17 = getJavaVersionMatches("17");
	private final boolean IS_JAVA_18 = getJavaVersionMatches("18");

        public final String getVersion() {
		return JAVA_VERSION;
	}

	public final float getVersionFloat() {
		return JAVA_VERSION_FLOAT;
	}
	
	public final int getVersionInt() {
		return JAVA_VERSION_INT;
	}

	private float getJavaVersionAsFloat() {
		if (JAVA_VERSION == null) {
			return 0f;
		}

		String str = JAVA_VERSION;

		str = ReUtil.get("^[0-9]{1,2}(\\.[0-9]{1,2})?", str, 0);

		return Float.parseFloat(str);
	}

	private int getJavaVersionAsInt() {
		if (JAVA_VERSION == null) {
			return 0;
		}

		final String javaVersion = ReUtil.get("^[0-9]{1,2}(\\.[0-9]{1,2}){0,2}", JAVA_VERSION, 0);

		final String[] split = javaVersion.split("\\.");
		String result = ArrayUtil.join(split, "");
		
		if (split[0].length() > 1) {
			result = (result + "0000").substring(0, 4);
		}

		return Integer.parseInt(result);
	}
	
	public final String getVendor() {
		return JAVA_VENDOR;
	}
	
	public final String getVendorURL() {
		return JAVA_VENDOR_URL;
	}
	
	@Deprecated
	public final boolean isJava1_1() {
		return false;
	}
	
	@Deprecated
	public final boolean isJava1_2() {
		return false;
	}

	
	@Deprecated
	public final boolean isJava1_3() {
		return false;
	}
	
	@Deprecated
	public final boolean isJava1_4() {
		return false;
	}
	
	@Deprecated
	public final boolean isJava1_5() {
		return false;
	}

	
	@Deprecated
	public final boolean isJava1_6() {
		return false;
	}

	
	@Deprecated
	public final boolean isJava1_7() {
		return false;
	}
	
	public final boolean isJava1_8() {
		return IS_JAVA_1_8;
	}

	public final boolean isJava9() {
		return IS_JAVA_9;
	}
	
	public final boolean isJava10() {
		return IS_JAVA_10;
	}
	
	public final boolean isJava11() {
		return IS_JAVA_11;
	}
	
	public final boolean isJava12() {
		return IS_JAVA_12;
	}
	
	public final boolean isJava13() {
		return IS_JAVA_13;
	}

	
	public final boolean isJava14() {
		return IS_JAVA_14;
	}

	public final boolean isJava15() {
		return IS_JAVA_15;
	}

	public final boolean isJava16() {
		return IS_JAVA_16;
	}
	
	public final boolean isJava17() {
		return IS_JAVA_17;
	}
	
	public final boolean isJava18() {
		return IS_JAVA_18;
	}
	
	private boolean getJavaVersionMatches(final String versionPrefix) {
		if (JAVA_VERSION == null) {
			return false;
		}

		return JAVA_VERSION.startsWith(versionPrefix);
	}
	
	public final boolean isJavaVersionAtLeast(final float requiredVersion) {
		return getVersionFloat() >= requiredVersion;
	}


	public final boolean isJavaVersionAtLeast(final int requiredVersion) {
		return getVersionInt() >= requiredVersion;
	}

	
	@Override
	public final String toString() {
		final StringBuilder builder = new StringBuilder();

		SystemUtil.append(builder, "Java Version:    ", getVersion());
		SystemUtil.append(builder, "Java Vendor:     ", getVendor());
		SystemUtil.append(builder, "Java Vendor URL: ", getVendorURL());

		return builder.toString();
	}

}
