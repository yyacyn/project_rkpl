package com.tokoteratai.project111.model;

import org.springframework.web.multipart.MultipartFile;

public class CategoryDto {

    private Integer code;
    private String name;
    private MultipartFile imgurl;

    public CategoryDto() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getImgurl() {
        return imgurl;
    }

    public void setImgurl(MultipartFile imgurl) {
        this.imgurl = imgurl;
    }

    
}
