package com.alex.inwento.dto;

public class EventDto {

    private int id;
    private String branch;
    private String info;

    public EventDto() {
    }

    public EventDto(int id, String branch, String info) {
        this.id = id;
        this.branch = branch;
        this.info = info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
