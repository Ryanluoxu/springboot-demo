package io.rlx.sb.service

import groovy.util.logging.Slf4j
import io.rlx.sb.entity.Product
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
@Slf4j
class ProductServiceImpl implements ProductService {

    private static final Map<String, Product> MAP = new HashMap<>()

    static {
        MAP.put("P001", new Product(id: "P001", name: "Poo1"))
        MAP.put("P002", new Product(id: "P002", name: "Poo2"))
        MAP.put("P003", new Product(id: "P003", name: "Poo3"))
    }

    // CachePut 每次都会触发真实方法的调用
    // e.g. redis key : "product::P005"
    @CachePut(value = "product", key = "#product.id")
    @Override
    Product save(Product product) {
        log.info("ProductService#save: ${product}")
        MAP.put(product.id, product)
        return product
    }

    // key 可以不提供，默认按照方法的所有参数进行组合
    // condition 可以为空，使用 SpEL 编写，只有 true 才进行缓存
    @Cacheable(value = "product", key = "#id", condition = "#id != 'P004'")
    @Override
    Product get(String id) {
        Product product = MAP.get(id)
        log.info("ProductService#get: ${product}")
        return product
    }

    // 对缓存进行清空
    @CacheEvict(value = "product", key = "#id")
    @Override
    void delete(String id) {
        MAP.remove(id)
        log.info("ProductService#delete: ${id}")
    }

    @Override
    List<Product> list() {
        List<Product> products = MAP.collect { it.value }
        return products
    }

    @Override
    Product update(Product product) {
        return null
    }


}
