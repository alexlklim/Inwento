package com.alex.inwento.dto;

public class Product {

    private int id;
    private String title;
    private String bar_code;
    private String inventory_status;

    public Product() {
    }

    public Product(int id, String title, String barCode, String inventoryStatus) {
        this.id = id;
        this.title = title;
        this.bar_code = barCode;
        this.inventory_status = inventoryStatus;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBarCode() {
        return bar_code;
    }

    public void setBarCode(String barCode) {
        this.bar_code = barCode;
    }

    public String getInventoryStatus() {
        return inventory_status;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventory_status = inventoryStatus;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", barCode='" + bar_code + '\'' +
                ", inventoryStatus='" + inventory_status + '\'' +
                '}';
    }
}
