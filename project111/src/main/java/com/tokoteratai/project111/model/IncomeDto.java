package com.tokoteratai.project111.model;


public class IncomeDto {
    private String id;
    private String paymethod;
    private String status;
    private String invoid;


    public IncomeDto(){
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public String getInvoid() {
        return invoid;
    }


    public void setInvoid(String invoid) {
        this.invoid = invoid;
    }

    
}
