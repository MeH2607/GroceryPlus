package com.example.groceryplus.repositories;

import com.example.groceryplus.model.Grocery;
import com.example.groceryplus.model.Recipe;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("GroceryPlusStub")
public class GroceryPlusStubRepository implements iGroceryPlusRepository{
    @Override
    public List<Recipe> getAllRecipes() {
        return null;
    }
    @Override
    public List<Grocery> getAllGroceries() {
        return null;
    }
    @Override
    public void createRecipe(Recipe recipe) {

    }
    @Override
    public void createGrocery(Grocery grocery) {

    }
    @Override
    public void deleteRecipe(String name) {

    }
    @Override
    public void updateRecipe(Recipe recipe) {

    }
    @Override
    public void deleteGrocery(String name) {

    }
    @Override
    public List<Grocery> getShoppinglist() {
        return null;
    }
}
