package com.example.heady.headyassignment.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "Categories")
public class CategoryDbParams {
    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private Long categoryId;

    private Long categoryIdReceived;

    private String name;

    @Generated(hash = 1889346800)
    public CategoryDbParams(Long categoryId, Long categoryIdReceived, String name) {
        this.categoryId = categoryId;
        this.categoryIdReceived = categoryIdReceived;
        this.name = name;
    }

    @Generated(hash = 758727482)
    public CategoryDbParams() {
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCategoryIdReceived() {
        return this.categoryIdReceived;
    }

    public void setCategoryIdReceived(Long categoryIdReceived) {
        this.categoryIdReceived = categoryIdReceived;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
