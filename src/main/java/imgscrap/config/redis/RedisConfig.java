package imgscrap.config.redis;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import imgscrap.util.Code;

@EnableCaching
@Configuration
public class RedisConfig {
	
	@Bean(name="cacheManager")
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
				.disableCachingNullValues()		// null value 캐시안함
				.entryTtl(Duration.ofSeconds(60))		// 캐시의 기본 유효시간 설정(1분)
				.computePrefixWith(CacheKeyPrefix.simple())
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));	// redis 캐시 데이터 저장방식을 StringSeriallizer로 지정
		
		// 캐시키별 default 유효시간 설정
		Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
		cacheConfigurations.put(Code.CACHE_IMG_SCRAP, RedisCacheConfiguration.defaultCacheConfig()		// 이미지스크랩 상세조회
				.entryTtl(Duration.ofSeconds(10)));		// 캐시의 기본 유효시간 설정(초단위)
		
		return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory).cacheDefaults(configuration)
				.withInitialCacheConfigurations(cacheConfigurations).build();
		
	}
}
