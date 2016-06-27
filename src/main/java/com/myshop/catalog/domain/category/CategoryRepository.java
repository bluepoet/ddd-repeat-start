package com.myshop.catalog.domain.category;

import java.util.List;

/**
 * Created by Mac on 2016. 6. 20..
 */
public interface CategoryRepository {
    Category findById(CategoryId id);

    List<Category> findAll();
}
