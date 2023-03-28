package com.example.groceryplus.model;

import java.util.List;

public class Recipe {
    private String name;
    private String description;
    private List<Grocery> ingredients;

    public Recipe(String name, String description, List<Grocery> ingredients) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<Grocery> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<Grocery> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }
}
