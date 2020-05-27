import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import io.rlx.sb.util.JsonUtil
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Value('redis://${redis.host}:${redis.port}')
    private String address
    @Value('${redis.database}')
    private int database
    @Value('${redis.password}')
    private String password
    @Value('${redis.host}')
    private String host
    @Value('${redis.port}')
    private int port

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration poolConfig = new RedisStandaloneConfiguration()
        poolConfig.database = database
        poolConfig.port = port
        poolConfig.hostName = host
        (poolConfig as RedisConfiguration.WithPassword).setPassword(password)
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(poolConfig)
        return connectionFactory
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory rcf) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>()
        redisTemplate.setConnectionFactory(rcf)
        StringRedisSerializer stringSerializer = new StringRedisSerializer()
        redisTemplate.setHashKeySerializer(stringSerializer)
        redisTemplate.setKeySerializer(stringSerializer)

        ObjectMapper mapper = JsonUtil.COMMON_OBJECT_MAPPER.copy()
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY)
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer(mapper)
        redisTemplate.setValueSerializer(jsonSerializer)
        redisTemplate.setHashValueSerializer(jsonSerializer)
        return redisTemplate
    }

    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory rcf) {
        StringRedisTemplate template = new StringRedisTemplate()
        template.setConnectionFactory(rcf)
        return template
    }

    @Bean
    RedissonClient redissonClient() {
        Config config = new Config()
        config.useSingleServer()
            .setAddress(address)
            .setDatabase(database)
            .setPassword(password)
        return Redisson.create(config)
    }

}
