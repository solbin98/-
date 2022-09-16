package com.project.domain.image;

import org.springframework.web.multipart.MultipartFile;

public class ImageData {
    MultipartFile[] images;
    String type;

    public ImageData(MultipartFile[] images, String type) {
        this.images = images;
        this.type = type;
    }

    public MultipartFile[] getImages() {
        return images;
    }

    public void setImages(MultipartFile[] images) {
        this.images = images;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
