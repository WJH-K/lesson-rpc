package com.lesson.modelInfo.shop;

import java.io.Serializable;
import java.util.Date;

public class ShopBase implements Serializable {
    private Integer id;

    private String name;

    private String address;

    private String phone;

    private Integer userId;

    private Integer logoId;

    private String info;

    private Date createTime;

    private Date updateTime;

    public ShopBase(Integer id, String name, String address, String phone, Integer userId, Integer logoId, String info, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.userId = userId;
        this.logoId = logoId;
        this.info = info;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ShopBase() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLogoId() {
        return logoId;
    }

    public void setLogoId(Integer logoId) {
        this.logoId = logoId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}