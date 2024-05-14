package com.tokoteratai.project.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String code;

    private String name;
    private String category;
    private Double s_price;
    private Double p_price;
    private Integer stock;

    @Column(columnDefinition = "TEXT")
    private String imgurl;
    private Date createdAt;

    public Product() {
    }

    public Product(String code, String name, String category, Double s_price, Double p_price, Integer stock, String imgurl, Date createdAt) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.s_price = s_price;
        this.p_price = p_price;
        this.stock = stock;
        this.imgurl = imgurl;
        this.createdAt = createdAt;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Double getS_price() {
        return s_price;
    }

    public void setS_price(Double s_price) {
        this.s_price = s_price;
    }

    public Double getP_price() {
        return p_price;
    }

    public void setP_price(Double p_price) {
        this.p_price = p_price;
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

    // Getters and Setters
}
