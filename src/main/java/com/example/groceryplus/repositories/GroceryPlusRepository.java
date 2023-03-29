package com.example.groceryplus.repositories;

import com.example.groceryplus.model.Grocery;
import com.example.groceryplus.model.Recipe;
import com.example.groceryplus.services.GroceryPlusException;
import com.example.groceryplus.repositories.util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@Repository("GroceryPlusDB")
public class GroceryPlusRepository implements iGroceryPlusRepository{


    //TODO Asger: Implement this class
    @Override
    public List<Recipe> getAllRecipes(){

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryPlus", "root", "Tor42Am41")) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public void deleteGrocery(String name) {

    }
    @Override
    public void updateRecipe(Recipe recipe) {

    }
    @Override
    public List<Grocery> getShoppinglist() {
        return null;
    }
}
