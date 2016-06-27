package com.myshop.integration.infra;

import com.myshop.integration.OffsetStore;
import org.springframework.stereotype.Component;

/**
 * Created by Mac on 2016. 6. 27..
 */
@Component
public class MemoryOffsetStore implements OffsetStore {
    private long nextOffset = 0;

    @Override
    public long get() {
        return nextOffset;
    }

    @Override
    public void update(long nextOffSet) {
        this.nextOffset = nextOffSet;
    }
}
