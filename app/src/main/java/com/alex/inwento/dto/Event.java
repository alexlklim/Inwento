package com.alex.inwento.dto;

import com.alex.inwento.dto.Product;
import com.alex.inwento.dto.UnknownProduct;

import java.util.List;

public class Event {
    private int id;
    private String branch;
    private String username;
    private String email;
    private int unknown_products_amount;
    private int total_product_amount;
    private int scanned_product_amount;

    private List<UnknownProduct> unknown_products;
    private List<Product> products;

    public Event() {
    }

    public Event(int id, String branch, String username, String email, int unknownProductAmount, int totalProductAmount, int scannedProductAmount, List<UnknownProduct> unknownProducts, List<Product> products) {
        this.id = id;
        this.branch = branch;
        this.username = username;
        this.email = email;
        this.unknown_products_amount = unknownProductAmount;
        this.total_product_amount = totalProductAmount;
        this.scanned_product_amount = scannedProductAmount;
        this.unknown_products = unknownProducts;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUnknownProductsAmount() {
        return unknown_products_amount;
    }

    public void setUnknownProductsAmount(int unknown_products_amount) {
        this.unknown_products_amount = unknown_products_amount;
    }

    public int getTotalProductsAmount() {
        return total_product_amount;
    }

    public void setTotalProductsAmount(int total_product_amount) {
        this.total_product_amount = total_product_amount;
    }

    public int getScannedProductsAmount() {
        return scanned_product_amount;
    }

    public void setScannedProductsAmount(int scanned_product_amount) {
        this.scanned_product_amount = scanned_product_amount;
    }

    public List<UnknownProduct> getUnknownProducts() {
        return unknown_products;
    }

    public void setUnknownProducts(List<UnknownProduct> unknown_products) {
        this.unknown_products = unknown_products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", branch='" + branch + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", unknown_products_amount=" + unknown_products_amount +
                ", total_product_amount=" + total_product_amount +
                ", scanned_product_amount=" + scanned_product_amount +
                ", unknown_products=" + unknown_products +
                ", products=" + products +
                '}';
    }
}
