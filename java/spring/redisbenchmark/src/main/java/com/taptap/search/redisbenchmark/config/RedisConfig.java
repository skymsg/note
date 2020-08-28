package com.taptap.search.redisbenchmark.config;

import org.springframework.cache.jcache.config.JCacheConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@PropertySource("classpath:application.properties")
public class RedisConfig extends JCacheConfigurerSupport {


    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("39.106.14.18", 6379));
    }

//    @Bean
//    public RedisConnectionFactory lettuceConnectionFactory() {
//        return new JedisConnectionFactory(new RedisStandaloneConfiguration("39.106.14.18", 6379));
//    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redis = new RedisTemplate<>();
        redis.setKeySerializer(new StringRedisSerializer());
        redis.setValueSerializer(new StringRedisSerializer());
        redis.setConnectionFactory(redisConnectionFactory);
        return redis;
    }

}
