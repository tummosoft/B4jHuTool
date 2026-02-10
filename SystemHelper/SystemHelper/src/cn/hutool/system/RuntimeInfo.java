package cn.hutool.system;

import anywheresoftware.b4a.BA;
import cn.hutool.core.io.FileUtil;

import java.io.Serializable;

@BA.ShortName("RuntimeInfo")
public class RuntimeInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Runtime currentRuntime = Runtime.getRuntime();

	public final Runtime getRuntime() {
		return currentRuntime;
	}

	
	public final long getMaxMemory() {
		return currentRuntime.maxMemory();
	}

	
	public final long getTotalMemory() {
		return currentRuntime.totalMemory();
	}

	public final long getFreeMemory() {
		return currentRuntime.freeMemory();
	}

	
	public final long getUsableMemory() {
		return currentRuntime.maxMemory() - currentRuntime.totalMemory() + currentRuntime.freeMemory();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		SystemUtil.append(builder, "Max Memory:    ", FileUtil.readableFileSize(getMaxMemory()));
		SystemUtil.append(builder, "Total Memory:     ", FileUtil.readableFileSize(getTotalMemory()));
		SystemUtil.append(builder, "Free Memory:     ", FileUtil.readableFileSize(getFreeMemory()));
		SystemUtil.append(builder, "Usable Memory:     ", FileUtil.readableFileSize(getUsableMemory()));

		return builder.toString();
	}
}
