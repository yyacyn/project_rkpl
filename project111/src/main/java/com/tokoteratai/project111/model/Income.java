package com.tokoteratai.project111.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "income")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cus_name;
    private LocalDateTime date;
    private Integer total;
    private Integer qty;
    private Integer oid;
    private String paymethod;
    private String status;

    // @OneToOne
    // @JoinColumn(name = "invo_id")
    // private Invoice invoid;

    @ManyToOne
    @JoinColumn(name = "p_code")
    private Product p_code;

    public Income() {
    }

    public Income(Integer id, String cus_name, LocalDateTime date, Integer total, Integer qty, Integer oid,
            String paymethod, String status, Product p_code) {
        this.id = id;
        this.cus_name = cus_name;
        this.date = date;
        this.total = total;
        this.qty = qty;
        this.oid = oid;
        this.paymethod = paymethod;
        this.status = status;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
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

    public Product getP_code() {
        return p_code;
    }

    public void setP_code(Product p_code) {
        this.p_code = p_code;
    }

    
    
}
