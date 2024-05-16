package com.tokoteratai.project111.model;

public class InvoiceDto {

    private Integer id;
    private String cus_name;
    private String date;
    private Integer price;
    private Integer qty;
    private Product p_code;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public Product getP_code() {
        return p_code;
    }

    public void setP_code(Product p_code) {
        this.p_code = p_code;
    }

    

    
}
