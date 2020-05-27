package io.rlx.sb.service

import io.rlx.sb.entity.Product

interface ProductService {

    Product save(Product product)

    Product update(Product product)

    void delete(String id)

    Product get(String id)

    List<Product> list()
}
