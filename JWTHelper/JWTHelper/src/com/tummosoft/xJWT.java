package com.tummosoft;

import anywheresoftware.b4a.AbsObjectWrapper;
import anywheresoftware.b4a.BA;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;

@BA.ShortName("xJWT")
public class xJWT extends AbsObjectWrapper<cn.hutool.jwt.JWT>{
    private BA ba;
    private JWT jwt;
    private JWTPayload payload;
    
    public void Initialize(final BA ba) {
	this.ba = ba;
        setObject(new JWT());        
       
    }
    
    public String getAlgorithm() {
        return getObject().getAlgorithm();
    }
    
  
}
