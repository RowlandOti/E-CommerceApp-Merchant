package com.rowland.delivery.features.dash.domain.models.category;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rowland on 5/5/2018.
 */

public class Category {

    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryScope{" +
                "name='" + name + '\'' +
                '}';
    }
}
