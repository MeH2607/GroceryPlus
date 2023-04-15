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
public class GroceryPlusRepository implements iGroceryPlusRepository {

    //TODO Asger: Implement this class
    @Override
    public List<RecipeDTO> getRecipeDTOs() {
        List<RecipeDTO> list = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "select * from recipes join recipes_has_groceries using (recipe_name)";

            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            String currentRecipeName = "";
            RecipeDTO currentRecipeDTO = null;

            while (rs.next()) {

                // tjekker hvis opskriften allerede findes. Hvis den ikke gør, så laver den et nyt opskrift objekt.
                String recipeName = rs.getString("recipe_name");
                String description = rs.getString("description");

                String groceryName = rs.getString("grocery_name");
                double amount = rs.getDouble("amount");
                String unit = rs.getString("unit");

                if (recipeName.equals(currentRecipeName)) {
                    currentRecipeDTO.addGrocery(new Grocery(groceryName, amount, unit));
                } else {
                    currentRecipeDTO = new RecipeDTO(recipeName, description, new ArrayList<>());
                    currentRecipeName = recipeName;
                    currentRecipeDTO.addGrocery(new Grocery(groceryName, amount, unit));
                }


                if (!list.contains(currentRecipeDTO))
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
        List<Grocery> shoppingList = new ArrayList();
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM Groceries";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String name = rs.getString("grocery_name");
                double amount = rs.getDouble("amount");
                String unit = rs.getString("unit");
                groceryList.add(new Grocery(name, amount, unit));
            }


            String SQL2 = "SELECT grocery_name, cart_amount FROM ShoppingList";
            rs = stmt.executeQuery(SQL2);

            while (rs.next()) {
                for (Grocery grocery : groceryList) {
                    if(rs.getString("grocery_name").equals(grocery.getName())){
                        grocery.setCartAmount(rs.getDouble("cart_amount"));
                    break;
                    }
                }
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
            String SQL = "INSERT INTO GroceryPlus.Groceries (grocery_name, amount, unit) VALUES (?, ?, ?);";
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
                double amount = rs.getDouble("cart_amount");
                String unit = rs.getString("unit");

                groceryList.add(new Grocery(name, amount, unit));
            }

            return groceryList;
        } catch (SQLException e) {
            throw new GroceryPlusException(e.getMessage());

        }

    }

    @Override
    public void addGroceryToShoppinglist(Grocery grocery) throws GroceryPlusException {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "INSERT INTO GroceryPlus.ShoppingList (grocery_name, cart_amount, unit) " +
                    "VALUES (?, ?, ?) AS new " +
                    "ON DUPLICATE KEY UPDATE GroceryPlus.ShoppingList.cart_amount = GroceryPlus.ShoppingList.cart_amount + new.cart_amount;";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, grocery.getName());
            ps.setDouble(2, grocery.getAmount());
            ps.setString(3, grocery.getUnit());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new GroceryPlusException(e.getMessage());
        }

    }

    @Override
    public void clearShoppinglist() throws GroceryPlusException { //metode som sletter shoppinglist row i SQL

        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "DELETE FROM GroceryPlus.ShoppingList;";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new GroceryPlusException(e.getMessage());
        }
    }


    public RecipeDTO getSingleRecipeDTO(String recipeNameSearch) {

        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "select * from recipes join recipes_has_groceries using (recipe_name) where recipe_name = ?";
            PreparedStatement psmt = conn.prepareCall(SQL);
            psmt.setString(1, recipeNameSearch);
            ResultSet rs = psmt.executeQuery();


            RecipeDTO currentRecipeDTO = null;
            while (rs.next()) {

                // tjekker hvis opskriften allerede findes. Hvis den ikke gør, så laver den et nyt opskrift objekt.
                String recipeName = rs.getString("recipe_name");
                String description = rs.getString("description");

                String groceryName = rs.getString("grocery_name");
                double amount = rs.getDouble("amount");
                String unit = rs.getString("unit");

                if (currentRecipeDTO == null) {
                    currentRecipeDTO = new RecipeDTO(recipeName, description, new ArrayList<>());
                }
                currentRecipeDTO.addGrocery(new Grocery(groceryName, amount, unit));
            }

            return currentRecipeDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addRecipeToShoppingList(String recipeName) {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "INSERT INTO ShoppingList (grocery_name, cart_amount, unit) " +
                    "SELECT grocery_name, amount, unit " +
                    "FROM recipes " +
                    "JOIN recipes_has_groceries USING (recipe_name) " +
                    "WHERE recipe_name = ? " +
                    "ON DUPLICATE KEY UPDATE " +
                    "ShoppingList.cart_amount = ShoppingList.cart_amount + amount";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, recipeName);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteGroceryFromShoppinglist(String name) {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "DELETE FROM GroceryPlus.ShoppingList WHERE grocery_name = ?;";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, name);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<RecipeDTO> getVeganList() {
        List<RecipeDTO> list = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "select * from recipes join recipes_has_groceries using (recipe_name) where Category_name = 'vegan'";

            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            String currentRecipeName = "";
            RecipeDTO currentRecipeDTO = null;

            while (rs.next()) {

                // tjekker hvis opskriften allerede findes. Hvis den ikke gør, så laver den et nyt opskrift objekt.
                String recipeName = rs.getString("recipe_name");
                String description = rs.getString("description");

                String groceryName = rs.getString("grocery_name");
                double amount = rs.getDouble("amount");
                String unit = rs.getString("unit");

                if (recipeName.equals(currentRecipeName)) {
                    currentRecipeDTO.addGrocery(new Grocery(groceryName, amount, unit));
                } else {
                    currentRecipeDTO = new RecipeDTO(recipeName, description, new ArrayList<>());
                    currentRecipeName = recipeName;
                    currentRecipeDTO.addGrocery(new Grocery(groceryName, amount, unit));
                }

                if (!list.contains(currentRecipeDTO))
                    list.add(currentRecipeDTO);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<RecipeDTO> getStandardList() {
        List<RecipeDTO> list = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "select * from recipes join recipes_has_groceries using (recipe_name) where Category_name = 'Standard'";

            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            String currentRecipeName = "";
            RecipeDTO currentRecipeDTO = null;

            while (rs.next()) {

                // tjekker hvis opskriften allerede findes. Hvis den ikke gør, så laver den et nyt opskrift objekt.
                String recipeName = rs.getString("recipe_name");
                String description = rs.getString("description");

                String groceryName = rs.getString("grocery_name");
                double amount = rs.getDouble("amount");
                String unit = rs.getString("unit");

                if (recipeName.equals(currentRecipeName)) {
                    currentRecipeDTO.addGrocery(new Grocery(groceryName, amount, unit));
                } else {
                    currentRecipeDTO = new RecipeDTO(recipeName, description, new ArrayList<>());
                    currentRecipeName = recipeName;
                    currentRecipeDTO.addGrocery(new Grocery(groceryName, amount, unit));
                }
                if (!list.contains(currentRecipeDTO))
                    list.add(currentRecipeDTO);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<RecipeDTO> getGlutenFreeList() {
        List<RecipeDTO> list = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "select * from recipes join recipes_has_groceries using (recipe_name) where Category_name = 'Gluten free'";

            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            String currentRecipeName = "";
            RecipeDTO currentRecipeDTO = null;

            while (rs.next()) {

                // tjekker hvis opskriften allerede findes. Hvis den ikke gør, så laver den et nyt opskrift objekt.
                String recipeName = rs.getString("recipe_name");
                String description = rs.getString("description");

                String groceryName = rs.getString("grocery_name");
                double amount = rs.getDouble("amount");
                String unit = rs.getString("unit");

                if (recipeName.equals(currentRecipeName)) {
                    currentRecipeDTO.addGrocery(new Grocery(groceryName, amount, unit));
                } else {
                    currentRecipeDTO = new RecipeDTO(recipeName, description, new ArrayList<>());
                    currentRecipeName = recipeName;
                    currentRecipeDTO.addGrocery(new Grocery(groceryName, amount, unit));
                }
                if (!list.contains(currentRecipeDTO))
                    list.add(currentRecipeDTO);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}



