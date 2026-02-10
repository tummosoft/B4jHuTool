package com.tummosoft;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.BA.Version;
import cn.hutool.setting.Setting;
import java.nio.charset.Charset;
import java.util.Date;

@ShortName("SettingHelper")
@Version(1.01f)
public class SettingHelper {

    private BA ba;
    private String eventName;
    private Setting setting;

    public void Initialize(final BA ba, String fileName) {
        this.ba = ba;
        setting = new Setting(fileName);
    }
    
    public void Initialize2(final BA ba, String fileName, String charset, boolean isUseVariable) {
        this.ba = ba;        
        setting = new Setting(fileName, Charset.forName(charset), isUseVariable);
    }
    
    public void Set(String key, String value) {
        setting.set(key, value);                  
    }
    
    public boolean containsKey(String key) {
        return setting.containsKey(key);
    }
    
    public boolean containsKey2(String group, String key) {
        return setting.containsKey(group, key);
    }
    
    public boolean containsValue(String value) {    
        return setting.containsValue(value);       
    }
    
    public boolean containsValue2(String group, String value) {    
        return setting.containsValue(group, value);       
    }
    
    public void SetByGroup(String key, String group, String value) {
        setting.setByGroup(key, group, value);        
    }
    
    public String Get(Object key) {        
        return setting.get(key);
    }
    
    public String Get2(String group, String key) {                
        return setting.get(group, key);
    }
    
    public Date GetDate(String key) {
        return setting.getDate(key);
    }
    
    public String GetString(String key) {
        return setting.getStr(key);
    }
    
    public int GetInt(String key) {
        return setting.getInt(key);
    }
    
    public long GetLong(String key) {
        return setting.getLong(key);
    }
    
    public float GetFloat(String key) {
        return setting.getFloat(key);
    }
    
    public float GetByte(String key) {
        return setting.getByte(key);
    }
    
     public boolean GetBoolean(String key) {
        return setting.getBool(key);
    }
     
    public boolean isEmpty() {        
        return setting.isEmpty();
    }
    
    public void Store() {
        setting.store();
    }
    
    public void Clear() {
        setting.clear();
    }
    
    public void ClearGroup(String group) {
        setting.clear(group);
        
    }
}
