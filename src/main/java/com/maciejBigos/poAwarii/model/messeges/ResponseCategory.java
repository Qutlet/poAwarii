package com.maciejBigos.poAwarii.model.messeges;

import com.maciejBigos.poAwarii.model.Category;

public class ResponseCategory {

    private String id;
    private String name;

    public ResponseCategory(Category category){
        this.id = category.getId();
        this.name = category.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
