package com.example.groceryplus.controllers;

import com.example.groceryplus.model.Grocery;
import com.example.groceryplus.model.RecipeDTO;
import com.example.groceryplus.model.User;
import com.example.groceryplus.repositories.UserRepository;
import com.example.groceryplus.services.GroceryPlusException;
import com.example.groceryplus.services.GroceryPlusService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("groceryplus")
public class GroceryPlusController {
    private GroceryPlusService groceryPlusService;
    private UserRepository userRepository;

    public GroceryPlusController(GroceryPlusService groceryPlusService, UserRepository userRepo) {
        this.groceryPlusService = groceryPlusService;
        this.userRepository = userRepo;
    }

    private boolean isLoogedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("login")
    public String showLogin() {
        // return login form
        return "login";
    }

    @PostMapping("login")
    public String login(@RequestParam("uid") String uid, @RequestParam("pw") String pw,
                        HttpSession session,
                        Model model) {
        // find user in repo - return admin1 if success
        User user = userRepository.getUser(uid);
        if (user != null)
            if (user.getPw().equals(pw)) {
                // create session for user and set session timeout to 30 sec (container default: 15 min)
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(30);
                return  "redirect:/groceryplus/shopping_list";
            }
        // wrong credentials
        model.addAttribute("wrongCredentials", true);
        return "login";
    }


    @GetMapping("all_recipes")
    public String allRecipes(Model model, HttpSession session) {
        //  List<RecipeDTO> list = groceryPlusService.getAllRecipes();
        // model.addAttribute("list", list);

        List<RecipeDTO> standardList = groceryPlusService.getStandardList();
        List<RecipeDTO> veganList = groceryPlusService.getVeganList();
        List<RecipeDTO> glutenFreeList = groceryPlusService.getGlutenFreeList();
        model.addAttribute("standardList", standardList);
        model.addAttribute("veganList", veganList);
        model.addAttribute("glutenFreeList", glutenFreeList);
        return isLoogedIn(session) ? "all_recipes" : "login";
    }

    @GetMapping("all_recipes/{recipeName}")
    public String getSingleRecipe(@PathVariable String recipeName, Model model , HttpSession session) {
        RecipeDTO recipe = groceryPlusService.getSingleRecipe(recipeName);
        model.addAttribute("recipe", recipe);
        return isLoogedIn(session) ? "single_Recipe" : "redirect:/groceryplus/login";
    }


    @GetMapping("all_groceries")
    public String allGroceries(Model model, HttpSession session) throws GroceryPlusException {
        Grocery grocery = new Grocery();
        model.addAttribute("grocery", grocery);

        List<Grocery> list = groceryPlusService.getAllGroceries();
        System.out.println(list);
        model.addAttribute("list", list);
        return isLoogedIn(session) ? "all_groceries" : "login";
    }

    @PostMapping("create_new_grocery")
    public String submitForm(@ModelAttribute("grocery") Grocery grocery, HttpSession session) throws GroceryPlusException {
        System.out.println(grocery);
        groceryPlusService.addGrocery(grocery);
        return isLoogedIn(session) ? "redirect:/groceryplus/all_groceries" : "login";
    }

    @PostMapping("add_grocery_to_shoppinglist")
    public String addGroceryToShoppinglist(@ModelAttribute("grocery") Grocery grocery) throws GroceryPlusException {
        System.out.println(grocery);
        groceryPlusService.addGroceryToShoppinglist(grocery);
        return "redirect:/groceryplus/all_groceries";
    }


    @GetMapping("delete_grocery_from_shoppinglist/{name}")
    public String deleteGroceryFromShoppinglist(@PathVariable String name) throws GroceryPlusException {
        System.out.println(name);
        groceryPlusService.deleteGroceryFromShoppinglist(name);
        return "redirect:/groceryplus/shopping_list";
    }

    @GetMapping("add_recipe_to_shoppingList/{recipeName}")
    public String addRecipeToShoppingList(@PathVariable String recipeName) throws GroceryPlusException {
        groceryPlusService.addRecipeToShoppingList(recipeName);
        System.out.println(recipeName);
        return "redirect:/groceryplus/shopping_list";
    }

    @GetMapping("/shopping_list")
    public String shoppingList(Model model, HttpSession session) throws GroceryPlusException {
        List<Grocery> list = groceryPlusService.getShoppinglist();
        System.out.println(list);
        model.addAttribute("list", list);
        return isLoogedIn(session) ? "shopping_list" : "login";
    }

    @GetMapping("clear_shopping_list")
    public String clearShoppingList() throws GroceryPlusException {
        groceryPlusService.clearShoppinglist();
        return "redirect:/groceryplus/shopping_list";
    }


    @PostMapping("create_new_recipe")
    public String addRecipe() {
        return "create_new_recipe";
    }



}
