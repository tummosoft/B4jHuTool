package com.tummosoft;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.BA.Version;
import cn.hutool.cache.Cache;
import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.cache.impl.LRUCache;
import cn.hutool.cache.impl.TimedCache;

@Version(1.01f)
@ShortName("CacheHelper")
public class CacheHelper {
    private BA ba;
    private Cache<String, String> cache;
    
    public void InitFIFOCache(final BA ba, int length) {
	this.ba = ba; 
        cache = new FIFOCache<>(length);
    }
    
     public void InitLRUCache(final BA ba, int length) {
	this.ba = ba; 
        cache = new LRUCache<>(length);
    }
     
     public void InitTimedCache(final BA ba, int time) {
	this.ba = ba; 
        cache = new TimedCache<>(time);
    }
    
    public void Put(String key, String value) {
        cache.put(key, value);
    }
    
    public String Get(String key) {        
        
        return cache.get(key);
    }
    
    public boolean isEmpty() {
        return cache.isEmpty();        
    }
    
    public boolean isFull() {
        return cache.isFull();
    }
    
    public void Clear() {
        cache.clear();
    }
    
    public int Capacity() {
        return cache.capacity();
    }
    
    //https://juejin.cn/post/7476497019361427495
    public int Size() {
        return cache.size();
    }
    
    public void Remove(String key) {
        cache.remove(key);
    }
    
    public long Timeout(String key) {
        return cache.timeout();
    }        
}
