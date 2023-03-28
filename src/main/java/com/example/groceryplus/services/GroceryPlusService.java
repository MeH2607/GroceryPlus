package com.example.groceryplus.services;
import com.example.groceryplus.model.Grocery;
import com.example.groceryplus.model.Recipe;
import com.example.groceryplus.repositories.iGroceryPlusRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class GroceryPlusService {
    private iGroceryPlusRepository groceryPlusRepository;

    public GroceryPlusService(ApplicationContext context, @Value("${groceryplus.repository}") String impl) {
        groceryPlusRepository = (iGroceryPlusRepository) context.getBean(impl);
    }

    public List<Recipe> getAllRecipes(){
        return groceryPlusRepository.getAllRecipes();
    };
    public List<Grocery> getAllGroceries(){
        return groceryPlusRepository.getAllGroceries();
    };
    public void createRecipe(Recipe recipe){
        groceryPlusRepository.createRecipe(recipe);
    };
    public void createGrocery(Grocery grocery){
        groceryPlusRepository.createGrocery(grocery);
    };
    public void deleteRecipe(String name){
        groceryPlusRepository.deleteRecipe(name);
    };
    public void deleteGrocery(String name){
        groceryPlusRepository.deleteGrocery(name);
    };
    public List<Grocery> getShoppinglist(){
        return groceryPlusRepository.getShoppinglist();
    };




}
