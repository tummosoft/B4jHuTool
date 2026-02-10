package cn.hutool.system.oshi;

import anywheresoftware.b4a.BA;

import oshi.SystemInfo;

import oshi.hardware.CentralProcessor;

import oshi.hardware.ComputerSystem;

import oshi.hardware.GlobalMemory;

import oshi.hardware.HWDiskStore;

import oshi.hardware.HardwareAbstractionLayer;

import oshi.hardware.NetworkIF;

import oshi.hardware.Sensors;

import oshi.software.os.OSProcess;

import oshi.software.os.OperatingSystem;

import java.util.List;



public class OshiUtil {

    private static final SystemInfo systemInfo;

    private static final HardwareAbstractionLayer hardware;

    private static final OperatingSystem os;

    static {

        systemInfo = new SystemInfo();

        hardware = systemInfo.getHardware();

        os = systemInfo.getOperatingSystem();

    }

    public static OperatingSystem getOs() {

        return os;

    }

    public static OSProcess getCurrentProcess() {

        return os.getProcess(os.getProcessId());

    }

    public static HardwareAbstractionLayer getHardware() {

        return hardware;

    }

    public static ComputerSystem getSystem() {

        return hardware.getComputerSystem();

    }

    public static GlobalMemory getMemory() {

        return hardware.getMemory();

    }

    public static CentralProcessor getProcessor() {

        return hardware.getProcessor();

    }

    public static Sensors getSensors() {

        return hardware.getSensors();

    }

    public static List<HWDiskStore> getDiskStores() {

        return hardware.getDiskStores();

    }

    public static List<NetworkIF> getNetworkIFs() {

        return hardware.getNetworkIFs();

    }

    public static CpuInfo getCpuInfo() {

        return getCpuInfo(1000);

    }

    public static CpuInfo getCpuInfo(long waitingTime) {

        return new CpuInfo(OshiUtil.getProcessor(), waitingTime);

    }

}
