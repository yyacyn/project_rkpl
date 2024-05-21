package com.tokoteratai.project111.model;

import org.springframework.web.multipart.MultipartFile;

public class ProductDto {
    
    private Integer code;
    private String name;
    private String category;
    private Integer s_price;
    private Integer p_price;
    private Integer stock;
    private MultipartFile imgurl;

    public ProductDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getS_price() {
        return s_price;
    }

    public void setS_price(Integer s_price) {
        this.s_price = s_price;
    }

    public Integer getP_price() {
        return p_price;
    }

    public void setP_price(Integer p_price) {
        this.p_price = p_price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public MultipartFile getImgurl() {
        return imgurl;
    }

    public void setImgurl(MultipartFile imgurl) {
        this.imgurl = imgurl;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    
}
