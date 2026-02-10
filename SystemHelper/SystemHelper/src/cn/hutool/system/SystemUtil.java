package cn.hutool.system;

import anywheresoftware.b4a.BA;

import cn.hutool.core.convert.Convert;

import cn.hutool.core.lang.Singleton;

import cn.hutool.core.util.StrUtil;

import cn.hutool.core.util.SystemPropsUtil;

import java.io.PrintWriter;

import java.lang.management.ClassLoadingMXBean;

import java.lang.management.CompilationMXBean;

import java.lang.management.GarbageCollectorMXBean;

import java.lang.management.ManagementFactory;

import java.lang.management.MemoryMXBean;

import java.lang.management.MemoryManagerMXBean;

import java.lang.management.MemoryPoolMXBean;

import java.lang.management.OperatingSystemMXBean;

import java.lang.management.RuntimeMXBean;

import java.lang.management.ThreadMXBean;

import java.util.List;

@BA.Version(3.22f)

@BA.ShortName("SystemUtil")

public class SystemUtil extends SystemPropsUtil {

    public final static String SPECIFICATION_NAME = SystemPropsKeys.SPECIFICATION_NAME;

    public final static String VERSION = SystemPropsKeys.VERSION;

    public final static String SPECIFICATION_VERSION = SystemPropsKeys.SPECIFICATION_VERSION;

    public final static String VENDOR = SystemPropsKeys.VENDOR;

    public final static String SPECIFICATION_VENDOR = SystemPropsKeys.SPECIFICATION_VENDOR;

    public final static String VENDOR_URL = SystemPropsKeys.VENDOR_URL;

    public final static String HOME = SystemPropsKeys.HOME;

    public final static String LIBRARY_PATH = SystemPropsKeys.LIBRARY_PATH;

    public final static String TMPDIR = SystemPropsKeys.TMPDIR;

    public final static String COMPILER = SystemPropsKeys.COMPILER;

    public final static String EXT_DIRS = SystemPropsKeys.EXT_DIRS;

    public final static String VM_NAME = SystemPropsKeys.VM_NAME;

    public final static String VM_SPECIFICATION_NAME = SystemPropsKeys.VM_SPECIFICATION_NAME;

    public final static String VM_VERSION = SystemPropsKeys.VM_VERSION;

    public final static String VM_SPECIFICATION_VERSION = SystemPropsKeys.VM_SPECIFICATION_VERSION;

    public final static String VM_VENDOR = SystemPropsKeys.VM_VENDOR;

    public final static String VM_SPECIFICATION_VENDOR = SystemPropsKeys.VM_SPECIFICATION_VENDOR;

    public final static String CLASS_VERSION = SystemPropsKeys.CLASS_VERSION;

    public final static String CLASS_PATH = SystemPropsKeys.CLASS_PATH;

    public final static String OS_NAME = SystemPropsKeys.OS_NAME;

    public final static String OS_ARCH = SystemPropsKeys.OS_ARCH;

    public final static String OS_VERSION = SystemPropsKeys.OS_VERSION;

    public final static String FILE_SEPARATOR = SystemPropsKeys.FILE_SEPARATOR;

    public final static String PATH_SEPARATOR = SystemPropsKeys.PATH_SEPARATOR;

    public final static String LINE_SEPARATOR = SystemPropsKeys.LINE_SEPARATOR;

    public final static String USER_NAME = SystemPropsKeys.USER_NAME;

    public final static String USER_HOME = SystemPropsKeys.USER_HOME;

    public final static String USER_DIR = SystemPropsKeys.USER_DIR;

    public static long getCurrentPID() {

        return Long.parseLong(getRuntimeMXBean().getName().split("@")[0]);

    }

    public static ClassLoadingMXBean getClassLoadingMXBean() {

        return ManagementFactory.getClassLoadingMXBean();

    }

    public static MemoryMXBean getMemoryMXBean() {

        return ManagementFactory.getMemoryMXBean();

    }

    public static ThreadMXBean getThreadMXBean() {

        return ManagementFactory.getThreadMXBean();

    }

    public static RuntimeMXBean getRuntimeMXBean() {

        return ManagementFactory.getRuntimeMXBean();

    }

    public static CompilationMXBean getCompilationMXBean() {

        return ManagementFactory.getCompilationMXBean();

    }

    public static OperatingSystemMXBean getOperatingSystemMXBean() {

        return ManagementFactory.getOperatingSystemMXBean();

    }

    public static List<MemoryPoolMXBean> getMemoryPoolMXBeans() {

        return ManagementFactory.getMemoryPoolMXBeans();

    }

    public static List<MemoryManagerMXBean> getMemoryManagerMXBeans() {

        return ManagementFactory.getMemoryManagerMXBeans();

    }

    public static List<GarbageCollectorMXBean> getGarbageCollectorMXBeans() {

        return ManagementFactory.getGarbageCollectorMXBeans();

    }

    public static JvmSpecInfo getJvmSpecInfo() {

        return Singleton.get(JvmSpecInfo.class);

    }

    public static JvmInfo getJvmInfo() {

        return Singleton.get(JvmInfo.class);

    }

    public static JavaSpecInfo getJavaSpecInfo() {

        return Singleton.get(JavaSpecInfo.class);

    }

    public static JavaInfo getJavaInfo() {

        return Singleton.get(JavaInfo.class);

    }

    public static JavaRuntimeInfo getJavaRuntimeInfo() {

        return Singleton.get(JavaRuntimeInfo.class);

    }

    public static OsInfo getOsInfo() {

        return Singleton.get(OsInfo.class);

    }

    public static UserInfo getUserInfo() {

        return Singleton.get(UserInfo.class);

    }

    public static HostInfo getHostInfo() {

        return Singleton.get(HostInfo.class);

    }

    public static RuntimeInfo getRuntimeInfo() {

        return Singleton.get(RuntimeInfo.class);

    }

    public static long getTotalMemory() {

        return Runtime.getRuntime().totalMemory();

    }

    public static long getFreeMemory() {

        return Runtime.getRuntime().freeMemory();

    }

    public static long getMaxMemory() {

        return Runtime.getRuntime().maxMemory();

    }

    public static int getTotalThreadCount() {

        ThreadGroup parentThread = Thread.currentThread().getThreadGroup();

        while (null != parentThread.getParent()) {

            parentThread = parentThread.getParent();

        }

        return parentThread.activeCount();

    }

    public static void dumpSystemInfo() {

        dumpSystemInfo(new PrintWriter(System.out));

    }

    public static void dumpSystemInfo(PrintWriter out) {

        out.println("--------------");

        out.println(getJvmSpecInfo());

        out.println("--------------");

        out.println(getJvmInfo());

        out.println("--------------");

        out.println(getJavaSpecInfo());

        out.println("--------------");

        out.println(getJavaInfo());

        out.println("--------------");

        out.println(getJavaRuntimeInfo());

        out.println("--------------");

        out.println(getOsInfo());

        out.println("--------------");

        out.println(getUserInfo());

        out.println("--------------");

        out.println(getHostInfo());

        out.println("--------------");

        out.println(getRuntimeInfo());

        out.println("--------------");

        out.flush();

    }

    protected static void append(StringBuilder builder, String caption, Object value) {

    }

}
