package cn.hutool.setting.profile;

import cn.hutool.core.lang.Singleton;
import cn.hutool.setting.Setting;

/**
 * Global Profile configuration center
 *
 * @author Looly
 *
 */
public class GlobalProfile {

	private GlobalProfile() {
	}

	// -------------------------------------------------------------------------------- Static method start
	/**
	 * Set global environment
	 * @param profile Environment
	 * @return {@link Profile}
	 */
	public static Profile setProfile(String profile) {
		return Singleton.get(Profile.class, profile);
	}

	/**
	 * Get the configuration file corresponding to the current global environment
	 * @param settingName Configuration file name, default suffix (.setting) can be omitted
	 * @return {@link Setting}
	 */
	public static Setting getSetting(String settingName) {
		return Singleton.get(Profile.class).getSetting(settingName);
	}
	// -------------------------------------------------------------------------------- Static method end
}
