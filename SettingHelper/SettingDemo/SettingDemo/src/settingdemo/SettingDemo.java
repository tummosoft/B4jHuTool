package settingdemo;

import cn.hutool.setting.Setting;

public class SettingDemo {

    public static void main(String[] args) {
        //noinspection MismatchedQueryAndUpdateOfCollection
		Setting setting = new Setting("d:\\config.setting", true);

		
// Ghi giá trị mới
setting.set("app.version", "1.0.0");
setting.set("app.debug", "false");

// Lưu xuống file
setting.store();
		
    }
    
}
