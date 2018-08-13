package com.lesson.userService.model.lesson.image;

import java.util.Date;

public class Image {
    private Integer id;

    private String path;

    private String ext;

    private String name;

    private Date createTime;

    private Date updateTime;

    public Image(Integer id, String path, String ext, String name, Date createTime, Date updateTime) {
        this.id = id;
        this.path = path;
        this.ext = ext;
        this.name = name;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Image() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext == null ? null : ext.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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