package com.alex.inwento.dto;

public class ProductDto {
    private int id;
    private String title;
    private String description;
    private String bar_code;
    private Double price;
    private String liable;
    private String receiver;

    public ProductDto() {
    }

    public ProductDto(int id, String title, String description, String bar_code, Double price, String liable, String receiver) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.bar_code = bar_code;
        this.price = price;
        this.liable = liable;
        this.receiver = receiver;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLiable() {
        return liable;
    }

    public void setLiable(String liable) {
        this.liable = liable;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", bar_code='" + bar_code + '\'' +
                ", price=" + price +
                ", liable='" + liable + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
