package com.tokoteratai.project111.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;

    private String name;
    private Integer s_price;
    private Integer p_price;

    private String category;
    private Integer stock;

    @Column(columnDefinition = "TEXT")
    private String imgurl;
    private Date createdAt;

    public Product() {
        super();
    }

    public Product(Integer code, String name, Integer s_price, Integer p_price, String category, Integer stock,
            String imgurl, Date createdAt) {
        this.code = code;
        this.name = name;
        this.s_price = s_price;
        this.p_price = p_price;
        this.category = category;
        this.stock = stock;
        this.imgurl = imgurl;
        this.createdAt = createdAt;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // public void setImgurl(MultipartFile imgurl2) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'setImgurl'");
    // }

}