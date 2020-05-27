package io.rlx.sb

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class RedisCacheApp {
    static void main(String[] args) {
        SpringApplication.run(RedisCacheApp.class);
    }
}
