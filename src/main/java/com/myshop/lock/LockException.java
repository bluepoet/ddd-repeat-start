package com.myshop.lock;

/**
 * Created by bluepoet on 2016. 7. 26..
 */
public class LockException extends RuntimeException {
    public LockException() {
    }

    public LockException(String message) {
        super(message);
    }

    public LockException(Throwable cause) {
        super(cause);
    }
}

