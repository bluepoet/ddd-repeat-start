package com.myshop.common.jpaspec;

/**
 * Created by bluepoet on 2016. 7. 15..
 */

public class Specs {
    public static <T> Specification<T> and(Specification<T> ... specs) {
        return new AndSpecification<>(specs);
    }

    public static <T> Specification<T> or(Specification<T> ... specs) {
        return new OrSpecification<>(specs);
    }
}
