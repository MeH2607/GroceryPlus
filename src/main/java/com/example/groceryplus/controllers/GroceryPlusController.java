package com.example.groceryplus.controllers;
import com.example.groceryplus.services.GroceryPlusService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("groceryplus")
public class GroceryPlusController {
    private GroceryPlusService groceryPlusService;

    public GroceryPlusController(GroceryPlusService groceryPlusService) {
        this.groceryPlusService = groceryPlusService;
    }
    @GetMapping("")
    public String index(){
        return "index";
    }
    @GetMapping("allRecipes")
    public String allRecipes(){
        return "allRecipes";
    }
    @GetMapping("allGroceries")
    public String allGroceries(){
        return "allGroceries";
    }
    @GetMapping("shoppingList")
    public String shoppingList(){
        return "shoppingList";
    }
    @PostMapping("addRecipe")
    public String addRecipe(){
        return "addRecipe";
    }
    @PostMapping("addGrocery")
    public String addGrocery(){
        return "addGrocery";
    }
    @PostMapping("deleteRecipe")
    public String deleteRecipe(){
        return "deleteRecipe";
    }
    @PostMapping("deleteGrocery")
    public String deleteGrocery(){
        return "deleteGrocery";
    }
    @PostMapping("updateRecipe")
    public String updateRecipe(){
        return "updateRecipe";
    }


}
