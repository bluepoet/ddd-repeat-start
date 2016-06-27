package com.myshop.common.event;

import net.jodah.typetools.TypeResolver;

/**
 * Created by Mac on 2016. 6. 26..
 */
public interface EventHandler<T> {
    void handle(T event);

    default boolean canHandle(Object event) {
        Class<?>[] typeArgs = TypeResolver.resolveRawArguments(
                EventHandler.class, this.getClass()
        );
        return typeArgs[0].isAssignableFrom(event.getClass());
    }
}
