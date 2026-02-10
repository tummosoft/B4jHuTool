package cn.hutool.system;

import anywheresoftware.b4a.BA;

import cn.hutool.core.util.StrUtil;

import java.io.File;

import java.io.Serializable;

@BA.ShortName("UserInfo")

public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String USER_NAME;

    private final String USER_HOME;

    private final String USER_DIR;

    private final String JAVA_IO_TMPDIR;

    private final String USER_LANGUAGE;

    private final String USER_COUNTRY;

    public UserInfo() {

        USER_NAME = SystemUtil.get("user.name", false);

        USER_HOME = fixPath(SystemUtil.get("user.home", false));

        USER_DIR = fixPath(SystemUtil.get("user.dir", false));

        JAVA_IO_TMPDIR = fixPath(SystemUtil.get("java.io.tmpdir", false));

        USER_LANGUAGE = SystemUtil.get("user.language", false);

        String userCountry = SystemUtil.get("user.country", false);

        if (null == userCountry) {

            userCountry = SystemUtil.get("user.region", false);

        }

        USER_COUNTRY = userCountry;

    }

    public final String getName() {

        return USER_NAME;

    }

    public final String getHomeDir() {

        return USER_HOME;

    }

    public final String getCurrentDir() {

        return USER_DIR;

    }

    public final String getTempDir() {

        return JAVA_IO_TMPDIR;

    }

    public final String getLanguage() {

        return USER_LANGUAGE;

    }

    public final String getCountry() {

        return USER_COUNTRY;

    }

    @Override

    public final String toString() {

        StringBuilder builder = new StringBuilder();

        SystemUtil.append(builder, "User Name:        ", getName());

        SystemUtil.append(builder, "User Home Dir:    ", getHomeDir());

        SystemUtil.append(builder, "User Current Dir: ", getCurrentDir());

        SystemUtil.append(builder, "User Temp Dir:    ", getTempDir());

        SystemUtil.append(builder, "User Language:    ", getLanguage());

        SystemUtil.append(builder, "User Country:     ", getCountry());

        return builder.toString();

    }

    private static String fixPath(String path) {

        return StrUtil.addSuffixIfNot(path, File.separator);

    }

}
