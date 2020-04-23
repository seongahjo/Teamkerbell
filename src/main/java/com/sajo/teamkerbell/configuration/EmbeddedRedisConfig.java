package com.sajo.teamkerbell.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import redis.embedded.RedisServer;

import java.util.Optional;

@Profile("redis")
@Configuration
@Conditional(EmbeddedRedisConfig.RedisCondition.class)
@PropertySource("classpath:spring.properties")
@Slf4j
public class EmbeddedRedisConfig implements InitializingBean, DisposableBean {


    @Value("${app.redis.port:6379}")
    private int redisPort;

    @Override
    public void destroy() throws Exception {
        Optional.ofNullable(redisServer).ifPresent(RedisServer::stop);
    }

    private RedisServer redisServer;


    @Override
    public void afterPropertiesSet() throws Exception {
        redisServer = RedisServer.builder().port(redisPort).build();
        redisServer.start();
    }

    static class RedisCondition implements Condition {
        @Override
        public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
            return conditionContext.getEnvironment().getProperty("app.redis.use_embedded", Boolean.class, true);
        }
    }
}
