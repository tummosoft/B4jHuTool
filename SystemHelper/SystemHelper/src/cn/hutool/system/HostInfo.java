package cn.hutool.system;

import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.BA.Version;
import cn.hutool.core.net.NetUtil;

import java.io.Serializable;
import java.net.InetAddress;

@ShortName("HostInfo")
public class HostInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String HOST_NAME;
    private final String HOST_ADDRESS;

    public HostInfo() {
        final InetAddress localhost = NetUtil.getLocalhost();
        if (null != localhost) {
            HOST_NAME = localhost.getHostName();
            HOST_ADDRESS = localhost.getHostAddress();
        } else {
            HOST_NAME = null;
            HOST_ADDRESS = null;
        }
    }

    /**
     * Get the name of the current host.
     *
     *
     * <p>
     * For example: <code>"webserver1"</code>
     *
     * </p>
     *
     *
     * @return Hostname
     *
     */
    public final String getName() {

        return HOST_NAME;

    }

    /**
     *
     * Get the address of the current host.
     *
     *
     * <p>
     *
* For example: <code>"192.168.0.1"</code>
     *
     * </p>
     *
     *
     * @return Host address
     *
     */
    public final String getAddress() {

        return HOST_ADDRESS;

    }

    /**
     *
     * Convert the information of the current host into a string.
     *
     *
     * @return String representation of host information
     */
    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();

        SystemUtil.append(builder, "Host Name: ", getName());
        SystemUtil.append(builder, "Host Address: ", getAddress());

        return builder.toString();
    }

}
