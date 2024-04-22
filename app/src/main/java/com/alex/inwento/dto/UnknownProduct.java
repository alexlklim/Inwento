package com.alex.inwento.dto;

public class UnknownProduct {

    private int id;
    private String code;

    public UnknownProduct() {
    }

    public UnknownProduct(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "UnknownProduct{" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }

}
