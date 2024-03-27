package com.alex.inwento.database.domain;

public class Branch {

    private int id;
    private String branch;
    private boolean active;

    public Branch() {
    }

    public Branch(int id, String branch, boolean active) {
        this.id = id;
        this.branch = branch;
        this.active = active;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
