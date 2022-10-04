package imgscrap.config.redis;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import redis.embedded.RedisServer;

@Configuration
public class EmbeddedRedisConfig {
	@Value("${spring.redis.port}")
	private int springRedisPort;
	
	private RedisServer redisServer;
	
	@PostConstruct
	public void redisServer() {
		try{
			redisServer = RedisServer.builder().port(springRedisPort).setting("maxmemory 128M").build();
			redisServer.start();
		} catch (Exception e){
			//e.printStackTrace();
		}

	}
	
	@PreDestroy
	public void stopRedis() {
		if(redisServer != null) {
			redisServer.stop();
		}
	}
}
