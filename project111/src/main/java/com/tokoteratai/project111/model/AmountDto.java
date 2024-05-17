package com.tokoteratai.project111.model;

public class AmountDto {

    private Integer id;
    private Integer value;

    public AmountDto() {
    }


    public AmountDto(Integer id, Integer value) {
        this.id = id;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }
}
