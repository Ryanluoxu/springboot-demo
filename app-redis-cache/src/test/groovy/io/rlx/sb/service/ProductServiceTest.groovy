package io.rlx.sb.service

import groovy.util.logging.Slf4j
import io.rlx.sb.entity.Product
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
class ProductServiceTest {
    @Autowired
    ProductService productService

    @Test
    void testSave() {
        Product product = new Product(id: "P005", name: "Poo5")
        productService.save(product)    // redis key: "product::P005"
        List<Product> products = productService.list()
        log.info("after save : ${products}")
    }

    @Test
    void testGet() {
        Product product
        product = productService.get("P001")    // call the method
        log.info("1st get : ${product}")
        product = productService.get("P001")    // will take from cache
        log.info("2nd get : ${product}")
    }

    @Test
    void testDelete() {
        productService.get("P002")      // load into cache
        productService.delete("P002")   // delete both cache and map
        List<Product> products = productService.list()
        log.info("after delete : ${products}")
    }

}
