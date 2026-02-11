package com.tummosoft;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.Events;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.BA.Version;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import java.util.HashMap;
import java.util.Map;

@Version(1.01f)
@ShortName("JWTHelper")
public class JWTHelper {
    private BA ba;
    
    public void Initialize(final BA ba) {
	this.ba = ba;
    
    }
    
    public String createToken(anywheresoftware.b4a.objects.collections.Map payload, String key) {
	Map<String, Object> payload2 = new HashMap();
        for (int i=0; i < payload.getSize(); i++) {
            String k = (String) payload.GetKeyAt(i);
            Object v = payload.GetValueAt(i);
            payload2.put(k, v);
        }
        
        return JWTUtil.createToken(payload2, key.getBytes());
    }
    
    public boolean verify(String token, String key) {
	 return JWTUtil.verify(token, key.getBytes());
    }
    
    public Object GetPayload(String token, String key) {
        JWT payload = JWT.of(token);
        return payload.getPayload(key);        
    }
}
