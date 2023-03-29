package com.example.groceryplus.controllers;
import com.example.groceryplus.model.RecipeDTO;
import com.example.groceryplus.services.GroceryPlusService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("allGroceries")
    public String allGroceries(Model model){
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
