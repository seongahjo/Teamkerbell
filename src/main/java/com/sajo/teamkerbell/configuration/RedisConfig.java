package com.sajo.teamkerbell.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.session.web.http.SessionRepositoryFilter;

/**
 * Created by seongahjo on 2016. 7. 14..
 */
@Configuration
@EnableCaching
@EnableRedisHttpSession
@PropertySource("classpath:spring.properties")
public class RedisConfig extends CachingConfigurerSupport {


    @Bean
    public JedisConnectionFactory redisConnectionFactory(Environment env) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(env.getProperty("redis.property.address"));
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisOperationsSessionRepository sessionRepository(RedisConnectionFactory rf) {
        RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(rf);
        sessionRepository.setDefaultMaxInactiveInterval(600);
        return sessionRepository;
    }

    @Bean
    public SessionRepositoryFilter springSessionRepositoryFilter(RedisOperationsSessionRepository sr) {
        return new SessionRepositoryFilter(sr);
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }

    /*
    @Bean
    public RedisHttpSessionConfiguration redisHttpSessionConfiguration(){
        return new RedisHttpSessionConfiguration();
    }
    */

    // RedisTemplate
    // ListOperations


    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory rf) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(rf);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(300);
        return cacheManager;
    }

}
