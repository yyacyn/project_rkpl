package com.tokoteratai.project111.model;

import java.time.LocalDateTime;

public class InvoiceDto {

    private Integer id;
    private String cus_name;
    private LocalDateTime date;
    private Integer price;
    private Integer qty;
    private Integer p_code;
    private String paymethod;
    private String status;

    public InvoiceDto() {
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

    public Integer getP_code() {
        return p_code;
    }

    public void setP_code(Integer p_code) {
        this.p_code = p_code;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
