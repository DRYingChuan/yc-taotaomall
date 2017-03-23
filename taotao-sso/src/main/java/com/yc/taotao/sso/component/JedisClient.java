package com.yc.taotao.sso.component;

/**
 * Created by YcDr on 2017/3/4.
 */
public interface JedisClient
{
    public String set(String key, String value);
    public String get(String key);
    public Long hset(String key, String item, String value);
    public String hget(String key, String item);
    public Long incr(String key);
    public Long decr(String ke);
    public Long expirc(String key, int secondes);
    public Long ttl(String key);
    public Long hdel(String key, String... fields);
}
