 package net.mingsoft.basic.util;

 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import java.util.concurrent.TimeUnit;
 import org.springframework.data.redis.core.HashOperations;
 import org.springframework.data.redis.core.ListOperations;
 import org.springframework.data.redis.core.RedisTemplate;
 import org.springframework.data.redis.core.SetOperations;
 import org.springframework.data.redis.core.StringRedisTemplate;
 import org.springframework.data.redis.core.ValueOperations;
 import org.springframework.data.redis.core.ZSetOperations;




























 public class RedisUtil
 {
   private static RedisTemplate redis;

   private static RedisTemplate getRedisTemplate() {
     if (redis == null) {
       redis = (RedisTemplate)SpringUtil.getBean(StringRedisTemplate.class);
     }
     return redis;
   }







   public static boolean hasKey(String key) { return getRedisTemplate().hasKey(key).booleanValue(); }








   public static void delete(String key) { getRedisTemplate().delete(key); }










   public static boolean hasKey(String key, String hashKey) { return getRedisTemplate().opsForHash().hasKey(key, hashKey).booleanValue(); }










   public static void expire(String key, long timeout, TimeUnit unit) { getRedisTemplate().expire(key, timeout, unit); }









   public static long ttl(String key) { return getRedisTemplate().getExpire(key).longValue(); }









   public static Set<String> keys(String pattern) { return getRedisTemplate().keys(pattern); }








   public static void delete(Set<String> keys) { getRedisTemplate().delete(keys); }








   private static void setExpire(String key, long expire) {
     if (expire != -1L) {
       getRedisTemplate().expire(key, expire, TimeUnit.SECONDS);
     }
   }






   private static ValueOperations getValueOperations() { return getRedisTemplate().opsForValue(); }





   public static void addValue(String key, Object value, long expire) {
     getValueOperations().set(key, value);
     setExpire(key, expire);
   }





   public static void addValue(String key, Object value, long expire, TimeUnit timeUnit) { getValueOperations().set(key, value, expire, timeUnit); }







   public static void addValue(String key, Object value) { getValueOperations().set(key, value); }







   public static Object getValue(String key) { return getValueOperations().get(key); }









   public static String get(String key) {
     Object obj = getValueOperations().get(key);
     return (obj == null) ? null : obj.toString();
   }


   private static HashOperations getHashOperations() { return getRedisTemplate().opsForHash(); }

















   public static void addHashValue(String key, String hashKey, Object data, long expire) {
     getHashOperations().put(key, hashKey, data);
     setExpire(key, expire);
   }









   public static void addAllHashValue(String key, Map<String, Object> map, long expire) {
     getHashOperations().putAll(key, map);

     setExpire(key, expire);
   }










   public static long deleteHashValue(String key, String hashKey) { return getHashOperations().delete(key, new Object[] { hashKey }).longValue(); }






   public static Object getHashValue(String key, String hashKey) { return getHashOperations().get(key, hashKey); }






   public static List<Object> getHashAllValue(String key) { return getHashOperations().values(key); }






   public static List<Object> getHashMultiValue(String key, List<String> hashKeys) { return getHashOperations().multiGet(key, hashKeys); }






   public static Long getHashCount(String key) { return getHashOperations().size(key); }






   private static ZSetOperations getZSetOperations() { return getRedisTemplate().opsForZSet(); }






   public static boolean addZSetValue(String key, Object member, long score) { return getZSetOperations().add(key, member, score).booleanValue(); }






   public static boolean addZSetValue(String key, Object member, double score) { return getZSetOperations().add(key, member, score).booleanValue(); }






   public static long addBatchZSetValue(String key, Set<ZSetOperations.TypedTuple<Object>> tuples) { return getZSetOperations().add(key, tuples).longValue(); }






   public static void incZSetValue(String key, String member, long delta) { getZSetOperations().incrementScore(key, member, delta); }





   public static long getZSetScore(String key, String member) {
     Double score = getZSetOperations().score(key, member);
     if (score == null) {
       return 0L;
     }
     return score.longValue();
   }






   public static Set<ZSetOperations.TypedTuple<Object>> getZSetRank(String key, long start, long end) { return getZSetOperations().rangeWithScores(key, start, end); }






   private static ListOperations getListOperations() { return getRedisTemplate().opsForList(); }






   public static void addListValue(String key, Object list) { getListOperations().leftPush(key, list); }






   public static Object getListValue(String key) { return getListOperations().leftPop(key); }



   private static SetOperations getSetOperations() { return getRedisTemplate().opsForSet(); }






   public static void addSetValue(String key, Object list) { getSetOperations().add(key, new Object[] { list }); }






   public static Object getSetValue(String key) { return getSetOperations().members(key); }






   public static Object popSetValue(String key) { return getSetOperations().pop(key); }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basi\\util\RedisUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */