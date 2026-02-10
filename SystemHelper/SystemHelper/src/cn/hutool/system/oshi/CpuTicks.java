package cn.hutool.system.oshi;

import anywheresoftware.b4a.BA;

import oshi.hardware.CentralProcessor;

import oshi.util.Util;


public class CpuTicks {

    long idle;

    long nice;

    long irq;

    long softIrq;

    long steal;

    long cSys;

    long user;

    long ioWait;

    public CpuTicks(CentralProcessor processor, long waitingTime) {

        final long[] prevTicks = processor.getSystemCpuLoadTicks();

        Util.sleep(waitingTime);

        final long[] ticks = processor.getSystemCpuLoadTicks();

        this.idle = tick(prevTicks, ticks, CentralProcessor.TickType.IDLE);

        this.nice = tick(prevTicks, ticks, CentralProcessor.TickType.NICE);

        this.irq = tick(prevTicks, ticks, CentralProcessor.TickType.IRQ);

        this.softIrq = tick(prevTicks, ticks, CentralProcessor.TickType.SOFTIRQ);

        this.steal = tick(prevTicks, ticks, CentralProcessor.TickType.STEAL);

        this.cSys = tick(prevTicks, ticks, CentralProcessor.TickType.SYSTEM);

        this.user = tick(prevTicks, ticks, CentralProcessor.TickType.USER);

        this.ioWait = tick(prevTicks, ticks, CentralProcessor.TickType.IOWAIT);

    }

    public long getIdle() {

        return idle;

    }

    public void setIdle(long idle) {

        this.idle = idle;

    }

    public long getNice() {

        return nice;

    }

    public void setNice(long nice) {

        this.nice = nice;

    }

    public long getIrq() {

        return irq;

    }

    public void setIrq(long irq) {

        this.irq = irq;

    }

    public long getSoftIrq() {

        return softIrq;

    }

    public void setSoftIrq(long softIrq) {

        this.softIrq = softIrq;

    }

    public long getSteal() {

        return steal;

    }

    public void setSteal(long steal) {

        this.steal = steal;

    }

    public long getcSys() {

        return cSys;

    }

    public void setcSys(long cSys) {

        this.cSys = cSys;

    }

    public long getUser() {

        return user;

    }

    public void setUser(long user) {

        this.user = user;

    }

    public long getIoWait() {

        return ioWait;

    }

    public void setIoWait(long ioWait) {

        this.ioWait = ioWait;

    }

    public long totalCpu() {

        return Math.max(user + nice + cSys + idle + ioWait + irq + softIrq + steal, 0);

    }

    @Override

    public String toString() {

        return "CpuTicks{"
                + "idle=" + idle
                + ", nice=" + nice
                + ", irq=" + irq
                + ", softIrq=" + softIrq
                + ", steal=" + steal
                + ", cSys=" + cSys
                + ", user=" + user
                + ", ioWait=" + ioWait
                + '}';

    }

    private static long tick(long[] prevTicks, long[] ticks, CentralProcessor.TickType tickType) {

        return ticks[tickType.getIndex()] - prevTicks[tickType.getIndex()];

    }

}
