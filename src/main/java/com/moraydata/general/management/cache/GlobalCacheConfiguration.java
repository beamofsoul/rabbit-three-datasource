package com.moraydata.general.management.cache;

import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisElementReader;
import org.springframework.data.redis.serializer.RedisElementWriter;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Global Cache Configurations
 * Using Redis as the cache component, and Jedis is its' client.
 * @author Mingshu Jian
 * @date 2018-03-30
 */
@Configuration
public class GlobalCacheConfiguration extends RedisAutoConfiguration {
	
	@Value("${spring.cache.redis.time-to-live}")
	private Long timeTolive;
	
	final class SerializationPairImplementation<T> implements SerializationPair<T> {
		
		private final SerializationPair<T> pair;

		protected SerializationPairImplementation(@Nullable RedisSerializer<T> serializer) {
			pair = SerializationPair.fromSerializer(serializer);
		}
		
		@Override
		public RedisElementReader<T> getReader() {
			return pair.getReader();
		}

		@Override
		public RedisElementWriter<T> getWriter() {
			return pair.getWriter();
		}
	}

	/**
	 * @Title: cacheManager  
	 * @Description: https://yq.aliyun.com/articles/560035
	 * @param connectionFactory
	 * @param jackson2JsonRedisSerializer
	 * @return CacheManager
	 */
	@Bean
	CacheManager cacheManager(RedisConnectionFactory connectionFactory, Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
		SerializationPairImplementation<Object> serializationPairImplementation = new SerializationPairImplementation<Object>(jackson2JsonRedisSerializer);
	    RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
	    /**
	     * RedisCacheConfiguration defaultCacheConfig = ...
	     * It has to be in ONE line, otherwise it does NOT work.
	     */
	    RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(serializationPairImplementation).entryTtl(Duration.ofSeconds(timeTolive));
	    return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
	}
	
//	@Bean
//	public RedisConnectionFactory redisConnectionFactory() {
//		return new JedisConnectionFactory();
//	}
	
	@Override
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
		RedisTemplate<Object,Object> template = super.redisTemplate(redisConnectionFactory);
		template.setValueSerializer(jackson2JsonRedisSerializer());
		template.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
		template.afterPropertiesSet();
		return template;
	}

	@SuppressWarnings("unchecked")
	@Bean
	<T> Jackson2JsonRedisSerializer<T> jackson2JsonRedisSerializer() {
		Jackson2JsonRedisSerializer<T> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<T>((Class<T>)Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		return jackson2JsonRedisSerializer;
	}
}
