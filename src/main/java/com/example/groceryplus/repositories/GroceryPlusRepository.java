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


    /*
    Laver en liste over alle RecipeDTO's, fra db.
    RecipeDTO er al nødvendig data for at vise en opskrift
     */

    @Override
    public List<RecipeDTO> getRecipeDTOs() {
        List<RecipeDTO> list = new ArrayList<>();

        // Henter relevante tables fra db, joines med recipe_name, og lægges i listen
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "select * from recipes join recipes_has_groceries using (recipe_name)";

            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            String currentRecipeName = "";
            RecipeDTO currentRecipeDTO = null;

            while (rs.next()) {

                String recipeName = rs.getString("recipe_name");
                String description = rs.getString("description");

                String groceryName = rs.getString("grocery_name");
                double amount = rs.getDouble("amount");
                String unit = rs.getString("unit");

                /*
                Her lægges groceries til i recipeDTO'en, og et ny RecipeDTO oprettes hvis den ikke eksisterer fra før.
                 */

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


    /*
    Henter alle groceries fra groceryList og shoppingList.
    ShoppingList skal bruges fordi vi ønsker at vise hvor mange af en grocery der findes i shoppinglisten,
    når man kigger på all_groceries i appen.
     */
    @Override
    public List<Grocery> getAllGroceries() throws GroceryPlusException {
        List<Grocery> groceryList = new ArrayList();

        // Henter alle groceries fra db og lægger dem i groceryList
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

            //
            String SQL2 = "SELECT grocery_name, cart_amount FROM ShoppingList";
            rs = stmt.executeQuery(SQL2);

            // itererer shoppingList tablet
            while (rs.next()) {
                // itererer groceryList og opdaterer cart_amount hvis groceryen findes i shoppinglist
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


    // Bruges til at oprette og tilføje en ny grocery til systemet og db
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

    // Henter shoppingList
    @Override
    public List<Grocery> getShoppinglist() throws GroceryPlusException {
        List<Grocery> shoppingList = new ArrayList();

        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM GroceryPlus.ShoppingList;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                String name = rs.getString("grocery_name");
                double amount = rs.getDouble("cart_amount");
                String unit = rs.getString("unit");

                shoppingList.add(new Grocery(name, amount, unit));
            }

            return shoppingList;
        } catch (SQLException e) {
            throw new GroceryPlusException(e.getMessage());
        }
    }

    // Tilføjer en grocery til shoppinglisten
    @Override
    public void addGroceryToShoppinglist(Grocery grocery) throws GroceryPlusException {
        try {
            Connection conn = ConnectionManager.getConnection();
            // Tilføjer grocery til shoppinglist.
            String SQL = "INSERT INTO ShoppingList (grocery_name, cart_amount, unit) " +
                    // Groceryet som tilføjes får aliaset "new" så vi kan referere til det senere i queryen.
                    "VALUES (?, ?, ?) AS new " +
                    // Her oppdateres cart_amount HVIS der er en duplicate key (grocery_name),
                    // altså vi lægger det cart_amount, istedet for at lave en duplikat af groceryet
                    "ON DUPLICATE KEY UPDATE ShoppingList.cart_amount = ShoppingList.cart_amount + new.cart_amount;";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, grocery.getName());
            ps.setDouble(2, grocery.getAmount());
            ps.setString(3, grocery.getUnit());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new GroceryPlusException(e.getMessage());
        }

    }

    // Fjerner alle groceries fra shoppinglisten, simpel SQL query
    @Override
    public void clearShoppinglist() throws GroceryPlusException {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "DELETE FROM GroceryPlus.ShoppingList;";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new GroceryPlusException(e.getMessage());
        }
    }


    // Henter recipeDTO for én specifik opskrift (recipeNameSearch)
    public RecipeDTO getSingleRecipeDTO(String recipeNameSearch) {

        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "select * from recipes join recipes_has_groceries using (recipe_name) where recipe_name = ?";
            PreparedStatement psmt = conn.prepareCall(SQL);
            psmt.setString(1, recipeNameSearch);
            ResultSet rs = psmt.executeQuery();


            RecipeDTO currentRecipeDTO = null;
            while (rs.next()) {
                String recipeName = rs.getString("recipe_name");
                String description = rs.getString("description");
                String groceryName = rs.getString("grocery_name");
                double amount = rs.getDouble("amount");
                String unit = rs.getString("unit");

                // tjekker om opskriften allerede findes. Hvis den ikke gør, så laver den et nyt opskrift objekt.
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

    // Tilføjer alle groceryes i en opskrift til shoppinglisten
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

               /* if (!list.contains(currentRecipeDTO)) {
                    currentRecipeDTO = new RecipeDTO(recipeName, description, new ArrayList<>());
                }
                                currentRecipeDTO.addGrocery(new Grocery(groceryName, amount, unit));
                */


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

               /* if (!list.contains(currentRecipeDTO)) {
                    currentRecipeDTO = new RecipeDTO(recipeName, description, new ArrayList<>());
                }
                                currentRecipeDTO.addGrocery(new Grocery(groceryName, amount, unit));
                */


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

               /* if (!list.contains(currentRecipeDTO)) {
                    currentRecipeDTO = new RecipeDTO(recipeName, description, new ArrayList<>());
                }
                                currentRecipeDTO.addGrocery(new Grocery(groceryName, amount, unit));
                */


                if (!list.contains(currentRecipeDTO))
                    list.add(currentRecipeDTO);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}



