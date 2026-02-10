package cn.hutool.system;

import anywheresoftware.b4a.BA;
import java.io.Serializable;

@BA.ShortName("OsInfo")
public class OsInfo implements Serializable{
	private static final long serialVersionUID = 1L;

	private final String OS_VERSION = SystemUtil.get("os.version", false);
	private final String OS_ARCH = SystemUtil.get("os.arch", false);
	private final String OS_NAME = SystemUtil.get("os.name", false);
	private final boolean IS_OS_AIX = getOSMatches("AIX");
	private final boolean IS_OS_HP_UX = getOSMatches("HP-UX");
	private final boolean IS_OS_IRIX = getOSMatches("Irix");
	private final boolean IS_OS_LINUX = getOSMatches("Linux") || getOSMatches("LINUX");
	private final boolean IS_OS_MAC = getOSMatches("Mac");
	private final boolean IS_OS_MAC_OSX = getOSMatches("Mac OS X");
	private final boolean IS_OS_OS2 = getOSMatches("OS/2");
	private final boolean IS_OS_SOLARIS = getOSMatches("Solaris");
	private final boolean IS_OS_SUN_OS = getOSMatches("SunOS");
	private final boolean IS_OS_WINDOWS = getOSMatches("Windows");
	private final boolean IS_OS_WINDOWS_2000 = getOSMatches("Windows", "5.0");
	private final boolean IS_OS_WINDOWS_95 = getOSMatches("Windows 9", "4.0");
	private final boolean IS_OS_WINDOWS_98 = getOSMatches("Windows 9", "4.1");
	private final boolean IS_OS_WINDOWS_ME = getOSMatches("Windows", "4.9");
	private final boolean IS_OS_WINDOWS_NT = getOSMatches("Windows NT");
	private final boolean IS_OS_WINDOWS_XP = getOSMatches("Windows", "5.1");

	private final boolean IS_OS_WINDOWS_7 = getOSMatches("Windows", "6.1");
	private final boolean IS_OS_WINDOWS_8 = getOSMatches("Windows", "6.2");
	private final boolean IS_OS_WINDOWS_8_1 = getOSMatches("Windows", "6.3");
	private final boolean IS_OS_WINDOWS_10 = getOSMatches("Windows", "10.0");
	private final boolean IS_OS_WINDOWS_11 = getOSMatches("Windows 11");

	private final String FILE_SEPARATOR = SystemUtil.get("file.separator", false);
	private final String LINE_SEPARATOR = SystemUtil.get("line.separator", false);
	private final String PATH_SEPARATOR = SystemUtil.get("path.separator", false);
	
	public final String getArch() {
		return OS_ARCH;
	}
	
	public final String getName() {
		return OS_NAME;
	}
	
	public final String getVersion() {
		return OS_VERSION;
	}

	public final boolean isAix() {
		return IS_OS_AIX;
	}

	public final boolean isHpUx() {
		return IS_OS_HP_UX;
	}

	
	public final boolean isIrix() {
		return IS_OS_IRIX;
	}

	public final boolean isLinux() {
		return IS_OS_LINUX;
	}

	
	public final boolean isMac() {
		return IS_OS_MAC;
	}
	
	public final boolean isMacOsX() {
		return IS_OS_MAC_OSX;
	}

	
	public final boolean isOs2() {
		return IS_OS_OS2;
	}

	
	public final boolean isSolaris() {
		return IS_OS_SOLARIS;
	}

	public final boolean isSunOS() {
		return IS_OS_SUN_OS;
	}

	
	public final boolean isWindows() {
		return IS_OS_WINDOWS;
	}

	
	public final boolean isWindows2000() {
		return IS_OS_WINDOWS_2000;
	}

	
	public final boolean isWindows95() {
		return IS_OS_WINDOWS_95;
	}

	
	public final boolean isWindows98() {
		return IS_OS_WINDOWS_98;
	}

	
	public final boolean isWindowsME() {
		return IS_OS_WINDOWS_ME;
	}

	
	public final boolean isWindowsNT() {
		return IS_OS_WINDOWS_NT;
	}

	
	public final boolean isWindowsXP() {
		return IS_OS_WINDOWS_XP;
	}

	
	public final boolean isWindows7() {
		return IS_OS_WINDOWS_7;
	}

	
	public final boolean isWindows8() {
		return IS_OS_WINDOWS_8;
	}

	
	public final boolean isWindows8_1() {
		return IS_OS_WINDOWS_8_1;
	}

	
	public final boolean isWindows10() {
		return IS_OS_WINDOWS_10 && !IS_OS_WINDOWS_11;
	}

	
	public final boolean isWindows11() {
		return IS_OS_WINDOWS_11;
	}

	
	private boolean getOSMatches(String osNamePrefix) {
		if (OS_NAME == null) {
			return false;
		}

		return OS_NAME.startsWith(osNamePrefix);
	}

	private boolean getOSMatches(String osNamePrefix, String osVersionPrefix) {
		if ((OS_NAME == null) || (OS_VERSION == null)) {
			return false;
		}

		return OS_NAME.startsWith(osNamePrefix) && OS_VERSION.startsWith(osVersionPrefix);
	}

	
	public final String getFileSeparator() {
		return FILE_SEPARATOR;
	}

	
	public final String getLineSeparator() {
		return LINE_SEPARATOR;
	}

	
	public final String getPathSeparator() {
		return PATH_SEPARATOR;
	}

	
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();

		SystemUtil.append(builder, "OS Arch:        ", getArch());
		SystemUtil.append(builder, "OS Name:        ", getName());
		SystemUtil.append(builder, "OS Version:     ", getVersion());
		SystemUtil.append(builder, "File Separator: ", getFileSeparator());
		SystemUtil.append(builder, "Line Separator: ", getLineSeparator());
		SystemUtil.append(builder, "Path Separator: ", getPathSeparator());

		return builder.toString();
	}

}
