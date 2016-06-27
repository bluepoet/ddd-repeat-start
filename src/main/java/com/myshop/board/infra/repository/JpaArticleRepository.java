package com.myshop.board.infra.repository;

import com.myshop.board.domain.Article;
import com.myshop.board.domain.ArticleRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Mac on 2016. 6. 28..
 */
@Repository
public class JpaArticleRepository implements ArticleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Article article) {
        entityManager.persist(article);
    }

    @Override
    public Article findById(Long id) {
        return entityManager.find(Article.class, id);
    }
}
