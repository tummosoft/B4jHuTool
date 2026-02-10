package cn.hutool.system.oshi;

import anywheresoftware.b4a.BA;

import cn.hutool.core.util.NumberUtil;

import oshi.hardware.CentralProcessor;

import java.text.DecimalFormat;


public class CpuInfo {

    private static final DecimalFormat LOAD_FORMAT = new DecimalFormat("#.00");

    private Integer cpuNum;

    private double toTal;

    private double sys;

    private double user;

    private double wait;

    private double free;

    private String cpuModel;

    private CpuTicks ticks;

    public CpuInfo() {

    }

    public CpuInfo(CentralProcessor processor, long waitingTime) {

        init(processor, waitingTime);

    }

    public CpuInfo(Integer cpuNum, double toTal, double sys, double user, double wait, double free, String cpuModel) {

        this.cpuNum = cpuNum;

        this.toTal = toTal;

        this.sys = sys;

        this.user = user;

        this.wait = wait;

        this.free = free;

        this.cpuModel = cpuModel;

    }

    public Integer getCpuNum() {

        return cpuNum;

    }

    public void setCpuNum(Integer cpuNum) {

        this.cpuNum = cpuNum;

    }

    public double getToTal() {

        return toTal;

    }

    public void setToTal(double toTal) {

        this.toTal = toTal;

    }

    public double getSys() {

        return sys;

    }

    public void setSys(double sys) {

        this.sys = sys;

    }

    public double getUser() {

        return user;

    }

    public void setUser(double user) {

        this.user = user;

    }

    public double getWait() {

        return wait;

    }

    public void setWait(double wait) {

        this.wait = wait;

    }

    public double getFree() {

        return free;

    }

    public void setFree(double free) {

        this.free = free;

    }

    public String getCpuModel() {

        return cpuModel;

    }

    public void setCpuModel(String cpuModel) {

        this.cpuModel = cpuModel;

    }

    public CpuTicks getTicks() {

        return ticks;

    }

    public void setTicks(CpuTicks ticks) {

        this.ticks = ticks;

    }

    public double getUsed() {

        return NumberUtil.sub(100, this.free);

    }

    @Override

    public String toString() {

        return "CpuInfo{"
                + "CPU core count=" + cpuNum
                + ", Total CPU utilization=" + toTal
                + ", CPU system utilization=" + sys
                + ", CPU user utilization=" + user
                + ", CPU current wait rate=" + wait
                + ", CPU current idle rate=" + free
                + ", CPU utilization=" + getUsed()
                + ", CPU model information='" + cpuModel + '\''
                + '}';

    }

    private void init(CentralProcessor processor, long waitingTime) {

        final CpuTicks ticks = new CpuTicks(processor, waitingTime);

        this.ticks = ticks;

        this.cpuNum = processor.getLogicalProcessorCount();

        this.cpuModel = processor.toString();

        final long totalCpu = ticks.totalCpu();

        this.toTal = totalCpu;

        this.sys = formatDouble(ticks.cSys, totalCpu);

        this.user = formatDouble(ticks.user, totalCpu);

        this.wait = formatDouble(ticks.ioWait, totalCpu);

        this.free = formatDouble(ticks.idle, totalCpu);

    }

    private static double formatDouble(long tick, long totalCpu) {

        if (0 == totalCpu) {

            return 0D;
        } else {
            return 0;
        }

    }

}
