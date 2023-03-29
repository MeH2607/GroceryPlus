package com.example.groceryplus.repositories;

import com.example.groceryplus.model.Grocery;
import com.example.groceryplus.model.RecipeDTO;

import java.util.List;

public interface iGroceryPlusRepository {
    List<RecipeDTO> getRecipeDTOs();
    List<Grocery> getAllGroceries();
    void createRecipe(RecipeDTO recipeDTO);
    void deleteRecipe(String name);
    List<Grocery> getShoppinglist();





}
