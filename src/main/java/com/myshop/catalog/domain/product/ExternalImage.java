package com.myshop.catalog.domain.product;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Mac on 2016. 6. 18..
 */
@Entity
@DiscriminatorValue("EI")
public class ExternalImage extends Image {
    private ExternalImage() {}
    public ExternalImage(String path) {
        super(path);
    }

    @Override
    public String getUrl() {
        return getPath();
    }

    @Override
    public boolean hasThumbnail() {
        return false;
    }

    @Override
    public String getThumbnailUrl() {
        return null;
    }
}