package com.myshop.common.jpaspec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Mac on 2016. 6. 26..
 */
public interface Specification<T> {
    Predicate toPredicate(Root<T> root, CriteriaBuilder cb);
}
