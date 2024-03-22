package com.alex.inwento.database.domain;

public class Event {
    private int id;
    private String branch;
    private String username;
    private String email;
    private int unknownProductAmount;
    private int totalProductAmount;
    private int scannedProductAmount;

    public Event(int id, String branch, String username, String email, int unknownProducts, int totalAmountProducts, int scannedProducts) {
        this.id = id;
        this.branch = branch;
        this.username = username;
        this.email = email;
        this.unknownProductAmount = unknownProducts;
        this.totalProductAmount = totalAmountProducts;
        this.scannedProductAmount = scannedProducts;
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

    public int getUnknownProductAmount() {
        return unknownProductAmount;
    }

    public void setUnknownProductAmount(int unknownProductAmount) {
        this.unknownProductAmount = unknownProductAmount;
    }

    public int getTotalProductAmount() {
        return totalProductAmount;
    }

    public void setTotalProductAmount(int totalProductAmount) {
        this.totalProductAmount = totalProductAmount;
    }

    public int getScannedProductAmount() {
        return scannedProductAmount;
    }

    public void setScannedProductAmount(int scannedProductAmount) {
        this.scannedProductAmount = scannedProductAmount;
    }
}
