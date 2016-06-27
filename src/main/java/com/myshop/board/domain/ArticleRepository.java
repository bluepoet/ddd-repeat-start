package com.myshop.board.domain;

/**
 * Created by Mac on 2016. 6. 28..
 */
public interface ArticleRepository {
    void save(Article article);
    Article findById(Long id);
}
