package io.rlx.sb.service

import groovy.util.logging.Slf4j
import io.rlx.sb.entity.Product
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
class RedisTemplateTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString() throws Exception {
        stringRedisTemplate.opsForValue().set("k1", "string one");
        Assert.assertEquals("string one", stringRedisTemplate.opsForValue().get("k1"));
        log.info("testString value : " + stringRedisTemplate.opsForValue().get("k1"))
    }

    @Test
    public void testObj() throws Exception {
        Product product = new Product(id: "P0021", name: "PTwo")
        redisTemplate.opsForValue().set("k2", product)
        boolean exists = redisTemplate.hasKey("k2");
        if (exists) {
            log.info("testObj value : " + redisTemplate.opsForValue().get("k2"))
        } else {
            System.out.println("exists is false");
        }
    }

}
