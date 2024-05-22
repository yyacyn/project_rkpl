package com.tokoteratai.project111.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cus_name;
    private LocalDateTime date;
    private Integer price;
    private Integer qty;
    private String paymethod;
    private String Status;


    @ManyToOne
    @JoinColumn(name = "p_code")
    private Product p_code;

    public Invoice() {
    }

    public Invoice(Integer id, String cus_name, LocalDateTime date, Integer price, Integer qty, String paymethod,
            String status, Product p_code) {
        this.id = id;
        this.cus_name = cus_name;
        this.date = date;
        this.price = price;
        this.qty = qty;
        this.paymethod = paymethod;
        Status = status;
        this.p_code = p_code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Product getP_code() {
        return p_code;
    }

    public void setP_code(Product p_code) {
        this.p_code = p_code;
    }

    
}
