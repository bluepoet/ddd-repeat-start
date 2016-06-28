package com.myshop.catalog.domain.product;

import com.myshop.catalog.domain.category.CategoryId;

import java.util.List;

/**
 * Created by Mac on 2016. 6. 18..
 */
public interface ProductRepository {
    void save(Product product);

    List<Product> findAll();

    Product findById(ProductId id);

    List<Product> findByCategoryId(CategoryId categoryId, int page, int size);

    long countByCategoryId(CategoryId id);
}
