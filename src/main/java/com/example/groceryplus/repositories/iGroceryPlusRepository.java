package com.example.groceryplus.repositories;

import com.example.groceryplus.model.Grocery;
import com.example.groceryplus.model.RecipeDTO;
import com.example.groceryplus.services.GroceryPlusException;

import java.util.List;

public interface iGroceryPlusRepository {
    List<RecipeDTO> getRecipeDTOs();
    List<Grocery> getAllGroceries() throws GroceryPlusException;
    void createRecipe(RecipeDTO recipeDTO);
    void addGrocery(Grocery grocery);
    //void deleteRecipe(String name);
    List<Grocery> getShoppinglist() throws GroceryPlusException;





}
