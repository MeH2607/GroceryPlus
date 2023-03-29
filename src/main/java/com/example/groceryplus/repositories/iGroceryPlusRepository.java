package com.example.groceryplus.repositories;

import com.example.groceryplus.model.Grocery;
import com.example.groceryplus.model.RecipeDTO;

import java.util.List;

public interface iGroceryPlusRepository {
    List<RecipeDTO> getRecipeDTOs();
    List<Grocery> getAllGroceries();
    void createRecipe(RecipeDTO recipeDTO);
    void createGrocery(Grocery grocery);
    void deleteRecipe(String name);
    void deleteGrocery(String name);
    void updateRecipe(RecipeDTO recipeDTO);
    List<Grocery> getShoppinglist();





}
