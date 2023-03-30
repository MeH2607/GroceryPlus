package com.example.groceryplus.repositories;

import com.example.groceryplus.model.Grocery;
import com.example.groceryplus.model.RecipeDTO;
import com.example.groceryplus.repositories.util.ConnectionManager;
import com.example.groceryplus.services.GroceryPlusException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("GroceryPlusDB")
public class GroceryPlusRepository implements iGroceryPlusRepository{

    //TODO Asger: Implement this class
    @Override
    public List<RecipeDTO> getRecipeDTOs(){
        List<RecipeDTO> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/GroceryPlus", "root", "mohamed")) {
            String SQL = "select * from recipes join recipes_has_groceries using (recipe_name)";

            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            RecipeDTO currentRecipeDTO = null;

            while (rs.next()) {

                // tjekker hvis opskriften allerede findes. Hvis den ikke gør, så laver den et nyt opskrift objekt.
                String recipeName = rs.getString("recipe_name");
                String description = rs.getString("description");

                if (!list.contains(currentRecipeDTO)) {
                    currentRecipeDTO = new RecipeDTO(recipeName, description, new ArrayList<>());
                }

                // Vi tilføjer groceryen til opskriftens liste
                String groceryName = rs.getString("grocery_name");
                double amount = rs.getDouble("amount");
                String unit = rs.getString("unit");

                currentRecipeDTO.addGrocery(new Grocery(groceryName, amount, unit));
                list.add(currentRecipeDTO);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Grocery> getAllGroceries() throws GroceryPlusException {
        List<Grocery> groceryList = new ArrayList();
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM GroceryPlus.Groceries;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String name = rs.getString("name");
                double amount = rs.getDouble("amount");
                String unit = rs.getString("unit");
                groceryList.add(new Grocery(name, amount, unit));
            }

            return groceryList;
        } catch (SQLException e) {
            throw new GroceryPlusException(e.getMessage());
        }
    }
    @Override
    public void createRecipe(RecipeDTO recipeDTO) {

    }

    @Override
    public void addGrocery(Grocery grocery) {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "INSERT INTO GroceryPlus.Groceries (name, amount, unit) VALUES (?, ?, ?);";

            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, grocery.getName());
            ps.setDouble(2, grocery.getAmount());
            ps.setString(3, grocery.getUnit());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Grocery> getShoppinglist() throws GroceryPlusException {
        List<Grocery> groceryList = new ArrayList();


        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM GroceryPlus.ShoppingList;";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String name = rs.getString("grocery_name");
                double amount = rs.getDouble("amount");
                String unit = rs.getString("unit");
                groceryList.add(new Grocery(name, amount, unit));
            }

            return groceryList;
        } catch (SQLException e) {
            throw new GroceryPlusException(e.getMessage());
        }
    }
}
