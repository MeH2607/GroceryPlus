package com.example.groceryplus.controllers;
import com.example.groceryplus.model.RecipeDTO;
import com.example.groceryplus.services.GroceryPlusService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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
    @GetMapping("all_recipes")
    public String allRecipes(){
        return "all_recipes";
   /* @GetMapping("allRecipes")
    public String allRecipes(Model model){
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        model.addAttribute("recipeDTOList", recipeDTOList);
        return "allRecipes";
    }*/

    //Lavede denne her hurtig for at tjekke om http requested virkede
    @GetMapping("allRecipes")
    public ResponseEntity allRecipes(){
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        return new ResponseEntity<>(recipeDTOList, HttpStatus.OK);
    }


    @GetMapping("all_groceries")
    public String allGroceries(){
        return "all_groceries";
    }
    @GetMapping("shopping_list")
    public String shoppingList(){
        return "shopping_list";
    }
    @PostMapping("create_new_recipe")
    public String addRecipe(){
        return "create_new_recipe";
    }

}
