package com.example.groceryplus.repositories;

import com.example.groceryplus.model.Grocery;
import com.example.groceryplus.model.Recipe;
import com.example.groceryplus.services.GroceryPlusException;

import java.util.List;

public interface iGroceryPlusRepository {
    List<Recipe> getAllRecipes();
    List<Grocery> getAllGroceries();
    void createRecipe(Recipe recipe);
    void createGrocery(Grocery grocery);
    void deleteRecipe(String name);
    void deleteGrocery(String name);
    void updateRecipe(Recipe recipe);
    List<Grocery> getShoppinglist();



}
